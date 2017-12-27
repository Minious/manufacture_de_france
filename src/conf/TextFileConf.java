package conf;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;

public class TextFileConf {

	final static Charset ENCODING = Charset.forName("UTF-8");

	public static HashMap<String, Double> loadConf(String fileName) throws UnprocessableConfFileException, IOException {
		return loadConf(fileName, new HashMap<String, Double>());
	}

	public static HashMap<String, Double> loadConf(String fileName, HashMap<String, Double> initialMap)
			throws UnprocessableConfFileException, IOException {
		return loadConf(fileName, initialMap, new ArrayList<Function>());
	}

	public static HashMap<String, Double> loadConf(String fileName, HashMap<String, Double> initialMap, ArrayList<Function> customFunctions) throws UnprocessableConfFileException, IOException {
		Path path = Paths.get(fileName);
		List<String> file = Files.readAllLines(path, ENCODING);

		ArrayList<String> exps = new ArrayList<String>();
		Pattern pExp = Pattern.compile(".*(?<![=!])=(?!=).*"); // compliqué du cul
		for (String line : file) {
			String exp = line.split("//", -1)[0].replaceAll("\\s", "");
			if (!exp.isEmpty()) {
				Matcher mExp = pExp.matcher(exp);
				if (mExp.matches())
					exps.add(exp);
				else {
					String start = exps.remove(exps.size() - 1);
					exps.add(start + exp);
				}
			}
		}

		HashMap<String, Double> map = new HashMap<String, Double>();
		map.putAll(initialMap);

		String[] builtinFunctions = { "abs", "acos", "asin", "atan", "cbrt", "ceil", "cos", "cosh", "exp", "floor", "log", "log10", "log2", "sin", "sinh", "sqrt", "tan", "tanh", "signum" };

		while (!exps.isEmpty()) {
			int i = 0;
			boolean almostOneExpProcessed = false;
			ArrayList<String> unprocessedExp = new ArrayList<String>();
			while (i < exps.size()) {
				String curExp = exps.get(i);

				String curAff = curExp.split("=", 2)[0];
				String curOp = curExp.split("=", 2)[1];

				ExpressionBuilder eb = new ExpressionBuilder(curOp);

				String separator = "[-+*/)(<>,]|==|!=|<=|>=";
				Pattern pTest = Pattern.compile("(?<="+separator+")|(?="+separator+")");

				String[] curOpArr = pTest.split(curOp);
				
				ArrayList<Function> includedFunctions = getIncludedFunctions();
				
				ArrayList<String> functionsNames = new ArrayList<String>();
				functionsNames.addAll(Arrays.asList(builtinFunctions));
				for(Function curFunc : includedFunctions)
					functionsNames.add(curFunc.getName());
				for(Function curFunc : customFunctions)
					functionsNames.add(curFunc.getName());

				ArrayList<String> vars = new ArrayList<String>();
				Pattern p = Pattern.compile("^[a-zA-Z_][a-zA-Z0-9_]*$");
				for (String token : curOpArr) {
					Matcher m = p.matcher(token);

					if (m.matches() && !functionsNames.contains(token))
						vars.add(token);
				}
				
				eb.variables(new HashSet<String>(vars));
				eb.functions(includedFunctions);
				eb.functions(customFunctions);
				eb.operator(getIncludedOperators());

				Expression e = eb.build();

				boolean processable = true;
				for (String var : vars) {
					if (map.keySet().contains(var))
						e.setVariable(var, map.get(var));
					else
						processable = false;
				}

				if (processable) {
					double result = e.evaluate();
					map.put(curAff, result);

					exps.remove(exps.get(i));

					almostOneExpProcessed = true;
				} else {
					unprocessedExp.add(curExp);
					i++;
				}
			}

			if (!almostOneExpProcessed) {
				for(String exp : unprocessedExp)
					System.out.println(exp);
				throw new UnprocessableConfFileException(unprocessedExp);
			}
		}
		
		return map;
	}
	
	private static ArrayList<Function> getIncludedFunctions() {
		ArrayList<Function> functions = new ArrayList<Function>();
		
		functions.add(new Function("if", 3) {
			@Override
			public double apply(double... args) {
				// TODO
				Double condition = args[0];
				Double instructionsIf = args[1];
				Double instructionsElse = args[2];
				if (condition != 0)
					return instructionsIf;
				else
					return instructionsElse;
			}
		});
		
		return functions;
	}

	private static ArrayList<Operator> getIncludedOperators() {
		ArrayList<Operator> operators = new ArrayList<Operator>();

		operators.add(new Operator(">=", 2, true, Operator.PRECEDENCE_ADDITION - 1) {
			@Override
			public double apply(double[] values) {
				if (values[0] >= values[1]) {
					return 1d;
				} else {
					return 0d;
				}
			}
		});

		operators.add(new Operator(">", 2, true, Operator.PRECEDENCE_ADDITION - 1) {
			@Override
			public double apply(double[] values) {
				if (values[0] > values[1]) {
					return 1d;
				} else {
					return 0d;
				}
			}
		});

		operators.add(new Operator(">=", 2, true, Operator.PRECEDENCE_ADDITION - 1) {
			@Override
			public double apply(double[] values) {
				if (values[0] <= values[1]) {
					return 1d;
				} else {
					return 0d;
				}
			}
		});

		operators.add(new Operator("<", 2, true, Operator.PRECEDENCE_ADDITION - 1) {
			@Override
			public double apply(double[] values) {
				if (values[0] < values[1]) {
					return 1d;
				} else {
					return 0d;
				}
			}
		});

		operators.add(new Operator("==", 2, true, Operator.PRECEDENCE_ADDITION - 1) {
			@Override
			public double apply(double[] values) {
				if (values[0] == values[1]) {
					return 1d;
				} else {
					return 0d;
				}
			}
		});

		operators.add(new Operator("!=", 2, true, Operator.PRECEDENCE_ADDITION - 1) {
			@Override
			public double apply(double[] values) {
				if (values[0] != values[1]) {
					return 1d;
				} else {
					return 0d;
				}
			}
		});

		return operators;
	}
}

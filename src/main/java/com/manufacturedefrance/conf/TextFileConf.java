package com.manufacturedefrance.conf;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;

import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextFileConf {

	private static String[] BUILTIN_FUNCTIONS = { "abs", "acos", "asin", "atan", "cbrt", "ceil", "cos", "cosh", "exp", "floor", "log", "log10", "log2", "sin", "sinh", "sqrt", "tan", "tanh", "signum" };

	private TextFileConf() {
		throw new IllegalStateException("Utility class");
	}

	public static Map<String, Double> loadConf(String fileName) throws UnprocessableConfFileException {
		return loadConf(fileName, new HashMap<>());
	}

	public static Map<String, Double> loadConf(String fileName, Map<String, Double> initialMap) throws UnprocessableConfFileException {
		return loadConf(fileName, initialMap, new ArrayList<>());
	}

	public static List<String> getExps(InputStream stream){
		ArrayList<String> exps = new ArrayList<>();
		Pattern pExp = Pattern.compile(".*(?<![=!])=(?!=).*"); // compliqué du cul

		Scanner scanner = new Scanner(stream);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();

			String exp = line.split("//", -1)[0].replaceAll("\\s+", "");
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
		scanner.close();

		return exps;
	}

	private static String[] getSplitCurOp(String curOp){
		String separator = "[-+*/)(<>,]|==|!=|<=|>=";
		Pattern pSplitOp = Pattern.compile("(?<="+separator+")|(?="+separator+")");

		String[] splitCurOp = pSplitOp.split(curOp);

		return splitCurOp;
	}

	private static List<String> getOpVars(String[] splitCurOp, ArrayList<String> functionsNames){
		ArrayList<String> vars = new ArrayList<>();
		Pattern p = Pattern.compile("^[a-zA-Z_][a-zA-Z0-9_]*$");
		for (String token : splitCurOp) {
			Matcher m = p.matcher(token);

			if (m.matches() && !functionsNames.contains(token))
				vars.add(token);
		}

		return vars;
	}

	public static Map<String, Double> loadConf(String fileName, Map<String, Double> initialMap, List<Function> customFunctions) throws UnprocessableConfFileException {
		InputStream stream = TextFileConf.class.getResourceAsStream("/" + fileName);

		List<String> exps = getExps(stream);

		HashMap<String, Double> map = new HashMap<>(initialMap);

		while (!exps.isEmpty()) {
			int i = 0;
			boolean almostOneExpProcessed = false;
			List<String> unprocessedExp = new ArrayList<>();
			while (i < exps.size()) {
				String curExp = exps.get(i);
				String[] splitCurExp = curExp.split("=", 2);
				String curAff = splitCurExp[0];
				String curOp = splitCurExp[1];

				ExpressionBuilder eb = new ExpressionBuilder(curOp);

				String[] splitCurOp = getSplitCurOp(curOp);

				ArrayList<Function> includedFunctions = getIncludedFunctions();

				ArrayList<String> functionsNames = new ArrayList<>(Arrays.asList(BUILTIN_FUNCTIONS));
				for(Function curFunc : includedFunctions)
					functionsNames.add(curFunc.getName());
				for(Function curFunc : customFunctions)
					functionsNames.add(curFunc.getName());

				List<String> vars = getOpVars(splitCurOp, functionsNames);
				
				eb.variables(new HashSet<>(vars));
				eb.functions(includedFunctions);
				eb.functions(customFunctions);
				eb.operator(getIncludedOperators());

				Expression exp = eb.build();

				boolean processable = true;
				for (String var : vars) {
					if (map.keySet().contains(var))
						exp.setVariable(var, map.get(var));
					else {
						processable = false;
						break;
					}
				}

				if (processable) {
					try {
						double result = exp.evaluate();
						map.put(curAff, result);
					} catch (ArithmeticException e) {
						Logger.getAnonymousLogger().log(Level.SEVERE, e.toString());
					}

					exps.remove(exps.get(i));

					almostOneExpProcessed = true;
				} else {
					unprocessedExp.add(curExp);
					i++;
				}
			}

			if (!almostOneExpProcessed) {
				for(String exp : unprocessedExp)
					Logger.getAnonymousLogger().log(Level.SEVERE, exp);
				throw new UnprocessableConfFileException(unprocessedExp);
			}
		}
		
		return map;
	}
	
	private static ArrayList<Function> getIncludedFunctions() {
		ArrayList<Function> functions = new ArrayList<>();
		
		functions.add(new Function("if", 3) {
			@Override
			public double apply(double... args) {
				Double condition = args[0];
				Double instructionsIf = args[1];
				Double instructionsElse = args[2];
				if (condition != 0)
					return instructionsIf;
				else
					return instructionsElse;
			}
		});
		
		functions.add(new Function("min", 2) {
			@Override
			public double apply(double... args) {
				Double a = args[0];
				Double b = args[1];
				if (a < b)
					return a;
				else
					return b;
			}
		});
		
		functions.add(new Function("max", 2) {
			@Override
			public double apply(double... args) {
				Double a = args[0];
				Double b = args[1];
				if (a > b)
					return a;
				else
					return b;
			}
		});
		
		return functions;
	}

	private static ArrayList<Operator> getIncludedOperators() {
		ArrayList<Operator> operators = new ArrayList<>();

		operators.add(new Operator(">=", 2, true, Operator.PRECEDENCE_ADDITION - 1) {
			@Override
			public double apply(double[] values) {
				return values[0] >= values[1] ? 1d : 0d;
			}
		});

		operators.add(new Operator(">", 2, true, Operator.PRECEDENCE_ADDITION - 1) {
			@Override
			public double apply(double[] values) {
				return values[0] > values[1] ? 1d : 0d;
			}
		});

		operators.add(new Operator(">=", 2, true, Operator.PRECEDENCE_ADDITION - 1) {
			@Override
			public double apply(double[] values) {
				return values[0] <= values[1] ? 1d : 0d;
			}
		});

		operators.add(new Operator("<", 2, true, Operator.PRECEDENCE_ADDITION - 1) {
			@Override
			public double apply(double[] values) {
				return values[0] < values[1] ? 1d : 0d;
			}
		});

		operators.add(new Operator("==", 2, true, Operator.PRECEDENCE_ADDITION - 1) {
			@Override
			public double apply(double[] values) {
				return values[0] == values[1] ? 1d : 0d;
			}
		});

		operators.add(new Operator("!=", 2, true, Operator.PRECEDENCE_ADDITION - 1) {
			@Override
			public double apply(double[] values) {
				return values[0] != values[1] ? 1d : 0d;
			}
		});

		return operators;
	}
}

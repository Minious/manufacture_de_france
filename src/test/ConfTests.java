package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import net.objecthunter.exp4j.function.Function;

public class ConfTests {

	@Test
	public void test() {
		Double lol = 40.0;

		//String fileName = "confTest.txt";
		String fileName = "conf_mecanique.txt";
		
		HashMap<String, Double> initialMap = new HashMap<String, Double>();
		initialMap.put("lol", lol);
		
		ArrayList<Function> functions = new ArrayList<Function>();
		functions.add(new Function("funcTest", 2) {
		    @Override
		    public double apply(double... args) {
		    	// TODO
		    	Double a = args[0];
		    	Double b = args[1];
				if(a + b < 60)
					return a + b;
				else
					return 60;
		    }
		});
		
		try {
			HashMap<String, Double> conf = textFileConfTest.loadConf(fileName, initialMap, functions);
		} catch (IOException | UnprocessableConfFileException e) {
			e.printStackTrace();
		}
	}
}

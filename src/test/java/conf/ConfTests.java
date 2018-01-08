package conf;

import net.objecthunter.exp4j.function.Function;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class ConfTests {

	@Test
	public void test() {
		String fileName = "confTest.txt";
		
		HashMap<String, Double> initialMap = new HashMap<String, Double>();
		initialMap.put("e", 40d);
		
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
			HashMap<String, Double> conf = TextFileConf.loadConf(fileName, initialMap, functions);

			assertEquals(conf.get("a"), Double.valueOf(5d));
			assertEquals(conf.get("b"), Double.valueOf(12d));
			assertEquals(conf.get("c"), Double.valueOf(-50.5d));
			assertEquals(conf.get("d"), Double.valueOf(0d));
			assertEquals(conf.get("e"), Double.valueOf(40d));
		} catch (IOException | UnprocessableConfFileException e) {
			e.printStackTrace();
		}
	}
}

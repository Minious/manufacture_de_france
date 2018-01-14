package test;

import com.manufacturedefrance.conf.TextFileConf;
import com.manufacturedefrance.conf.UnprocessableConfFileException;
import net.objecthunter.exp4j.function.Function;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ConfTest {

	@Test
	public void testTest(){
		assertEquals(10, 5 + 5);
	}

	@Test
	public void test() {
		String fileName = "confTest.txt";
		
		HashMap<String, Double> initialMap = new HashMap<>();
		initialMap.put("e", 40d);
		
		ArrayList<Function> functions = new ArrayList<>();
		functions.add(new Function("funcTest", 2) {
		    @Override
		    public double apply(double... args) {
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

			Double[] actuals = new Double[]{
				conf.get("a"),
				conf.get("b"),
				conf.get("c"),
				conf.get("d"),
				conf.get("e")
			};

			Double[] expecteds = new Double[]{
				5d,
				12d,
				-50.5d,
				0d,
				40d
			};

			assertArrayEquals("Test conf file gives expected values.", expecteds, actuals);
		} catch (UnprocessableConfFileException e) {
			fail("Unprocessable test conf file.");
		}
	}
}

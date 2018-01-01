package generateurCoteVerriere.modeles.premium;

import java.io.IOException;
import java.util.HashMap;

import conf.TextFileConf;
import conf.UnprocessableConfFileException;
import generateurCoteVerriere.ModeleGenerique;

public class Premium extends ModeleGenerique {
	public Premium(HashMap<String,Object> data) {
		super(data);

		String fileName = "conf_premium.txt";

		HashMap<String, Double> initialMap = new HashMap<String, Double>();
		
		initialMap.put("hauteurVerriere", (Double) data.get("hauteurVerriere"));
		initialMap.put("largeurVerriere", (Double) data.get("largeurVerriere"));
		initialMap.put("nbPartitions", ((Integer) data.get("nbPartitions")).doubleValue());

		try {
			this.conf = TextFileConf.loadConf(fileName, initialMap);
		} catch (IOException | UnprocessableConfFileException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String[] getElementsClasses() {
		return new String[] {/*"MontantCorniere",*/ "MontantIntermediaire"/*, "TraverseCorniere"*/};
	}

	@Override
	protected String getPackage() {
		return "generateurCoteVerriere.modeles.premium.elements";
	}
}

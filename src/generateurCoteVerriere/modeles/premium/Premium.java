package generateurCoteVerriere.modeles.premium;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import conf.TextFileConf;
import conf.UnprocessableConfFileException;
import generateurCoteVerriere.ModeleGenerique;
import net.objecthunter.exp4j.function.Function;

public class Premium extends ModeleGenerique {
	public Premium(String ARC, String client, String reference, double hauteurVerriere, double largeurVerriere, int nbPartitions) {
		super(ARC, client, reference);

		String fileName = "conf_premium.txt";

		HashMap<String, Double> initialMap = new HashMap<String, Double>();
		
		initialMap.put("hauteurVerriere", hauteurVerriere);
		initialMap.put("largeurVerriere", largeurVerriere);
		initialMap.put("nbPartitions", (double) nbPartitions);

		try {
			this.conf = TextFileConf.loadConf(fileName, initialMap);
		} catch (IOException | UnprocessableConfFileException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String[] getElementsClasses() {
		return new String[] {}; // {"MontantIntermediaire", "MontantCorniere", "TraverseCorniere"};
	}

	@Override
	protected String getPackage() {
		return "generateurCoteVerriere.modeles.mecanica.elements";
	}
}

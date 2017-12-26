package generateurCoteVerriere.modeles.mecanique;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import conf.TextFileConf;
import conf.UnprocessableConfFileException;
import generateurCoteVerriere.ModeleGenerique;
import net.objecthunter.exp4j.function.Function;

public class Mecanique extends ModeleGenerique {
	public Mecanique(String ARC, String client, String reference, double hauteurVerriere, double largeurVerriere, int nbPartitions) {
		super(ARC, client, reference);

		String fileName = "conf_mecanique.txt";

		HashMap<String, Double> initialMap = new HashMap<String, Double>();
		
		initialMap.put("hauteurVerriere", hauteurVerriere);
		initialMap.put("largeurVerriere", largeurVerriere);
		initialMap.put("nbPartitions", (double) nbPartitions);

		ArrayList<Function> functions = new ArrayList<Function>();
		functions.add(new Function("getNbTrousVerticaux", 1) {
			@Override
			public double apply(double... args) {
				// TODO
				Double hauteurVerriere = args[0];
				if (hauteurVerriere < 500)
					return 1;
				else
					return (int) Math.floor(hauteurVerriere / 500);
			}
		});
		functions.add(new Function("getNbAttachesIntermediairesTraverseCorniere", 2) {
			@Override
			public double apply(double... args) {
				// TODO
				double entreAxeAttachesSouhaite = args[0];
				double longueurADiviser = args[1];
				int nbAttaches = (int) Math.floor(longueurADiviser / entreAxeAttachesSouhaite);
				double entreAxeAttaches1 = longueurADiviser / nbAttaches;
				double entreAxeAttaches2 = longueurADiviser / (1 + nbAttaches);
				double ecartEntreSouhaiteEtReel1 = Math.abs(entreAxeAttachesSouhaite - entreAxeAttaches1);
				double ecartEntreSouhaiteEtReel2 = Math.abs(entreAxeAttachesSouhaite - entreAxeAttaches2);
				
				return ecartEntreSouhaiteEtReel1 < ecartEntreSouhaiteEtReel2 ? nbAttaches - 1 : nbAttaches;
			}
		});

		try {
			this.conf = TextFileConf.loadConf(fileName, initialMap, functions);
		} catch (IOException | UnprocessableConfFileException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String[] getElementsClasses() {
		return new String[] {"MontantPartition", "ContreCadreMontantPartition", "MontantCorniere", "ContreCadreMontantCorniere", "TraverseCorniere", "ContreCadreTraverseCorniere", "AttachesTraverseCorniere"};
	}

	@Override
	protected String getPackage() {
		return "generateurCoteVerriere.modeles.mecanique.elements";
	}
}

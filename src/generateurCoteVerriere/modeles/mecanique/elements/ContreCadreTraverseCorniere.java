package generateurCoteVerriere.modeles.mecanique.elements;

import java.util.HashMap;

import generateurCoteVerriere.ElementGenerique;
import generateurCoteVerriere.dessinProfil.DessinProfil;
import myCustomSvgLibrary.MyCustomSvg;

public class ContreCadreTraverseCorniere extends ElementGenerique {
	private final int nbTraverses = 2;
	private final String valeurDiametreTrous = "Ø9";

	public ContreCadreTraverseCorniere(HashMap<String, Double> conf, HashMap<String, Object> data) {
		super(conf, data);
	}

	@Override
	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.get("largeurContreCadreTraverseCorniere"), conf.get("hauteurContreCadreTraverseCorniere"), 13.5);
		profil.setCorniere();
		profil.setValeurPercage(this.valeurDiametreTrous);
		double ordonnee = conf.get("ecartEntreExtremiteEtPremierTrouContreCadreTraverseCorniere");
		profil.addPercage(ordonnee, false);
		ordonnee += conf.get("ecartEntrePremierTrouEtDeuxiemeTrouTraverseCorniere");
		profil.addPercage(ordonnee, false);
		ordonnee += conf.get("entreAxeLateralTraverseCorniere");
		profil.addPercage(ordonnee, false);
		
		for(int i=0;i<conf.get("nbPartitions") - 2;i++) {
			ordonnee += conf.get("entreAxeT");
			profil.addPercage(ordonnee, false);
			ordonnee += conf.get("entreAxeCentralTraverseCorniere");
			profil.addPercage(ordonnee, false);
		}
		
		if(conf.get("nbPartitions") >= 2) {
			ordonnee += conf.get("entreAxeT");
			profil.addPercage(ordonnee, false);
			ordonnee += conf.get("entreAxeLateralTraverseCorniere");
			profil.addPercage(ordonnee, false);
			profil.addCoteDroiteEntrePercages(2, 3, 0);
		}

		if(conf.get("nbPartitions") >= 3)
			profil.addCoteDroiteEntrePercages(3, 4, 0);
		
		ordonnee += conf.get("ecartEntrePremierTrouEtDeuxiemeTrouTraverseCorniere");
		profil.addPercage(ordonnee, true);
		
		profil.addCoteDroiteEntrePercages(0, 1, 0);
		profil.addCoteDroiteEntrePercages(1, 2, 0);

		MyCustomSvg g = profil.render();
		
		return g;
	}

	@Override
	public int getNbElements() {
		return this.nbTraverses;
	}
}

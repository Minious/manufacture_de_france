package generateurCoteVerriere.modeles.mecanique.elements;

import java.util.HashMap;

import generateurCoteVerriere.ElementGenerique;
import generateurCoteVerriere.dessinProfil.DessinProfil;
import generateurCoteVerriere.dessinProfil.DessinProfil.Side;
import myCustomSvgLibrary.MyCustomSvg;

public class MontantCorniere extends ElementGenerique {
	private final int nbMontants = 2;
	private final String valeurDiametreTrous = "ØM5";
	
	public MontantCorniere(HashMap<String, Double> conf, HashMap<String, Object> data) {
		super(conf, data);
	}

	@Override
	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.get("largeurMontantCorniere"), conf.get("hauteurMontantCorniere"));
		profil.setCorniere();
		profil.setChamp(conf.get("epaisseurProfil"), Side.RIGHT	);
		profil.setValeurPercage(this.valeurDiametreTrous);
		double ordonnee = conf.get("ecartEntreExtremiteEtPremierTrouMontantCorniere");
		profil.addPercage(ordonnee, false);
		ordonnee += conf.get("ecartEntrePremierTrouEtDeuxiemeTrouMontantCorniere");
		for(int i=0;i<conf.get("nbTrousIntermediairesVerticaux")+2;i++) {
			if(i != conf.get("nbTrousIntermediairesVerticaux") + 1) {
				profil.addPercage(ordonnee, false);
				ordonnee += conf.get("entreAxeMontant");
			}
			else
				profil.addPercage(ordonnee, true);
		}
		ordonnee += conf.get("ecartEntrePremierTrouEtDeuxiemeTrouMontantCorniere");
		profil.addPercage(ordonnee, false);
		profil.addCoteDroiteEntrePercages(0, 1, 0);
		profil.addCoteDroiteEntrePercages(1, 2, 0);
		MyCustomSvg g = profil.render();
		
		return g;
	}

	@Override
	public int getNbElements() {
		return this.nbMontants;
	}
}

package generateurCoteVerriere.modeles.mecanique.elements;

import java.util.HashMap;

import generateurCoteVerriere.ElementGenerique;
import generateurCoteVerriere.DessinProfil;
import generateurCoteVerriere.DessinProfil.Side;
import myCustomSvgLibrary.MyCustomSvg;

public class AttachesTraverseCorniere extends ElementGenerique {
	private final int nbTraverses = 2;
	private final String valeurDiametreTrous = "ØM5";

	public AttachesTraverseCorniere(HashMap<String, Double> conf, HashMap<String, Object> data) {
		super(conf, data);
	}

	@Override
	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.get("largeurTraverseCorniere"), conf.get("hauteurTraverseCorniere"));
		profil.setValeurPercage(this.valeurDiametreTrous);
		profil.setChamp(conf.get("epaisseurProfil"), Side.LEFT);
		profil.setIsChampCorniere(true);
		
		double ordonnee = conf.get("ecartEntreExtremiteEtPremierTrouAttachesTraverseCorniere");
		profil.addPercage(ordonnee);
		
		for(int i=0;i<conf.get("nbAttachesIntermediaires")+1;i++) {
			ordonnee += conf.get("entreAxeAttachesTraverseCorniere");
			if(i == conf.get("nbAttachesIntermediaires"))
				profil.addPercage(ordonnee, true);
			else
				profil.addPercage(ordonnee, false);
		}
		
		profil.addCoteDroiteEntrePercages(0, 1, 0);
		
		return profil.render();
	}

	@Override
	public int getNbElements() {
		return this.nbTraverses;
	}
}

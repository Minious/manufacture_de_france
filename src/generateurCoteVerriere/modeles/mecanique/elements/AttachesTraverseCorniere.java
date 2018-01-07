package generateurCoteVerriere.modeles.mecanique.elements;

import java.util.HashMap;

import generateurCoteVerriere.ElementGenerique;
import generateurCoteVerriere.dessinProfil.DessinProfil;
import myCustomSvgLibrary.MyCustomSvg;

public class AttachesTraverseCorniere extends ElementGenerique {
	private String nomFichierDeRendu = "attaches_traverse_corniere";
	private final int nbTraverses = 2;
	private final double diametreTrous = conf.get("largeurTraverseCorniere") / 8; // INCORRECT
	private final String valeurDiametreTrous = "ØM5";

	public AttachesTraverseCorniere(HashMap<String, Double> conf, HashMap<String, Object> data) {
		super(conf, data);
	}

	@Override
	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.get("largeurTraverseCorniere"), conf.get("hauteurTraverseCorniere"));
		profil.setLargeurPercage(this.diametreTrous);
		
		double ordonnee = conf.get("ecartEntreExtremiteEtPremierTrouAttachesTraverseCorniere");
		profil.addPercage(ordonnee);
		
		for(int i=0;i<conf.get("nbAttachesIntermediaires")+1;i++) {
			ordonnee += conf.get("entreAxeAttachesTraverseCorniere");
			if(i == conf.get("nbAttachesIntermediaires"))
				profil.addPercage(ordonnee, valeurDiametreTrous);
			else
				profil.addPercage(ordonnee);
		}
		
		profil.addCoteDroite(0, 1, 0);
		MyCustomSvg g = profil.render();
		
		return g;
	}

	@Override
	public String getNomFichierDeRendu() {
		return this.nomFichierDeRendu;
	}

	@Override
	public int getNbElements() {
		return this.nbTraverses;
	}
}

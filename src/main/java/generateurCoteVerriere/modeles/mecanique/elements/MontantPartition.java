package generateurCoteVerriere.modeles.mecanique.elements;

import java.util.HashMap;

import generateurCoteVerriere.ElementGenerique;
import generateurCoteVerriere.dessinProfil.DessinProfil;
import myCustomSvgLibrary.MyCustomSvg;

public class MontantPartition extends ElementGenerique {
	private final int nbMontants = (int) (conf.get("nbPartitions") - 1);
	private final String valeurDiametreTrous = "ØM5";
	
	public MontantPartition(HashMap<String, Double> conf, HashMap<String, Object> data) {
		super(conf, data);
	}

	@Override
	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.get("largeurMontantPartition"), conf.get("hauteurMontantPartition"));
		profil.setValeurPercage(this.valeurDiametreTrous);
		for(int i=0;i<conf.get("nbTrousIntermediairesVerticaux")+2;i++) {
			double ordonnee = conf.get("ecartEntreExtremiteEtPremierTrouMontantPartition") + i * conf.get("entreAxeMontant");
			if(i != conf.get("nbTrousIntermediairesVerticaux") + 1)
				profil.addPercage(ordonnee, false);
			else
				profil.addPercage(ordonnee, true);
		}
		profil.addCoteDroiteEntrePercages(0, 1, 0);
		MyCustomSvg g = profil.render();
		
		return g;
	}

	@Override
	public int getNbElements() {
		return this.nbMontants;
	}
}

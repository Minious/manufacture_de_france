package generateurCoteVerriere.modeles.mecanique.elements;

import java.util.HashMap;

import generateurCoteVerriere.ElementGenerique;
import generateurCoteVerriere.dessinProfil.DessinProfil;
import myCustomSvgLibrary.MyCustomSvg;

public class ContreCadreMontantPartition extends ElementGenerique {
	private String nomFichierDeRendu = "contre_cadre_montant_partition";
	private final int nbMontants = (int) (conf.get("nbPartitions") - 1);
	private final double diametreTrous = conf.get("largeurMontantPartition") / 8; // INCORRECT
	private final String valeurDiametreTrous = "Ø7";
	
	public ContreCadreMontantPartition(HashMap<String, Double> conf, HashMap<String, Object> data) {
		super(conf, data);
	}

	@Override
	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.get("largeurMontantPartition"), conf.get("hauteurMontantPartition"));
		profil.setLargeurPercage(this.diametreTrous);
		for(int i=0;i<conf.get("nbTrousIntermediairesVerticaux")+2;i++) {
			double ordonnee = conf.get("ecartEntreExtremiteEtPremierTrouMontantPartition") + i * conf.get("entreAxeMontant");
			if(i != conf.get("nbTrousIntermediairesVerticaux") + 1)
				profil.addPercage(ordonnee);
			else
				profil.addPercage(ordonnee, this.valeurDiametreTrous);
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
		return this.nbMontants;
	}
}

package generateurCoteVerriere.modeles.mecanique.elements;

import java.util.HashMap;

import generateurCoteVerriere.ElementGenerique;
import generateurCoteVerriere.dessinProfil.DessinProfil;
import myCustomSvgLibrary.MyCustomSvg;

public class ContreCadreTraverseCorniere extends ElementGenerique {
	private String nomFichierDeRendu = "contre_cadre_traverse_corniere";
	private final int nbTraverses = 2;
	private final double diametreTrous = conf.get("largeurContreCadreTraverseCorniere") / 8; // INCORRECT
	private final String valeurDiametreTrous = "Ø9";

	public ContreCadreTraverseCorniere(HashMap<String, Double> conf, HashMap<String, Object> data) {
		super(conf, data);
	}

	@Override
	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.get("largeurContreCadreTraverseCorniere"), conf.get("hauteurContreCadreTraverseCorniere"), 13.5);
		profil.setCorniere();
		profil.setLargeurPercage(this.diametreTrous);
		double ordonnee = conf.get("ecartEntreExtremiteEtPremierTrouContreCadreTraverseCorniere");
		profil.addPercage(ordonnee);
		ordonnee += conf.get("ecartEntrePremierTrouEtDeuxiemeTrouTraverseCorniere");
		profil.addPercage(ordonnee);
		ordonnee += conf.get("entreAxeLateralTraverseCorniere");
		profil.addPercage(ordonnee);
		
		for(int i=0;i<conf.get("nbPartitions") - 2;i++) {
			ordonnee += conf.get("entreAxeT");
			profil.addPercage(ordonnee);
			ordonnee += conf.get("entreAxeCentralTraverseCorniere");
			profil.addPercage(ordonnee);
		}
		
		if(conf.get("nbPartitions") >= 2) {
			ordonnee += conf.get("entreAxeT");
			profil.addPercage(ordonnee);
			ordonnee += conf.get("entreAxeLateralTraverseCorniere");
			profil.addPercage(ordonnee);
			profil.addCoteDroite(2, 3, 0);
		}

		if(conf.get("nbPartitions") >= 3)
			profil.addCoteDroite(3, 4, 0);
		
		ordonnee += conf.get("ecartEntrePremierTrouEtDeuxiemeTrouTraverseCorniere");
		profil.addPercage(ordonnee, this.valeurDiametreTrous);
		
		profil.addCoteDroite(0, 1, 0);
		profil.addCoteDroite(1, 2, 0);

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

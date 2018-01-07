package generateurCoteVerriere.modeles.mecanique.elements;

import java.util.HashMap;

import generateurCoteVerriere.ElementGenerique;
import generateurCoteVerriere.dessinProfil.DessinProfil;
import generateurCoteVerriere.dessinProfil.DessinProfil.Side;
import myCustomSvgLibrary.MyCustomSvg;


public class TraverseCorniere extends ElementGenerique {
	private String nomFichierDeRendu = "traverse_corniere";
	private final int nbTraverses = 2;
	private final double diametreTrous = conf.get("largeurTraverseCorniere") / 8; // INCORRECT
	private final String valeurDiametreTrous = "ØM5";
	
	public TraverseCorniere(HashMap<String, Double> conf, HashMap<String, Object> data) {
		super(conf, data);
	}

	@Override
	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.get("largeurTraverseCorniere"), conf.get("hauteurTraverseCorniere"));
		profil.setCorniere();
		profil.setChamp(conf.get("epaisseurProfil"), Side.RIGHT);
		profil.setLargeurPercage(this.diametreTrous);
		double ordonnee = conf.get("ecartEntreExtremiteEtPremierTrouTraverseCorniere");
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
			profil.addCoteDroiteEntrePercages(2, 3, 0);
		}

		if(conf.get("nbPartitions") >= 3)
			profil.addCoteDroiteEntrePercages(3, 4, 0);
		
		ordonnee += conf.get("ecartEntrePremierTrouEtDeuxiemeTrouTraverseCorniere");
		profil.addPercage(ordonnee, this.valeurDiametreTrous);
		
		profil.addCoteDroiteEntrePercages(0, 1, 0);
		profil.addCoteDroiteEntrePercages(1, 2, 0);

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

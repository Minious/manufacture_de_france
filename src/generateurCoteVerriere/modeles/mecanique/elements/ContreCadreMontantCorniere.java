package generateurCoteVerriere.modeles.mecanique.elements;

import java.util.HashMap;

import generateurCoteVerriere.ElementGenerique;
import generateurCoteVerriere.dessinProfil.DessinProfil;
import myCustomSvgLibrary.MyCustomSvg;

public class ContreCadreMontantCorniere extends ElementGenerique {
	private String nomFichierDeRendu = "contre_cadre_montant_corniere";
	private final int nbMontants = 2;
	private final double diametreTrous = conf.get("largeurContreCadreMontantCorniere") / 8; // INCORRECT
	private final String valeurDiametreTrous = "Ø9";
	
	public ContreCadreMontantCorniere(HashMap<String, Double> conf, HashMap<String, Object> data) {
		super(conf, data);
	}

	@Override
	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.get("largeurContreCadreMontantCorniere"), conf.get("hauteurContreCadreMontantCorniere"), 13.5);
		profil.setCorniere();
		profil.setLargeurPercage(this.diametreTrous);
		double ordonnee = conf.get("ecartEntreExtremiteEtPremierTrouContreCadreMontantCorniere");
		profil.addPercage(ordonnee);
		ordonnee += conf.get("ecartEntrePremierTrouEtDeuxiemeTrouMontantCorniere");
		for(int i=0;i<conf.get("nbTrousIntermediairesVerticaux")+2;i++) {
			if(i != conf.get("nbTrousIntermediairesVerticaux") + 1) {
				profil.addPercage(ordonnee);
				ordonnee += conf.get("entreAxeMontant");
			}
			else
				profil.addPercage(ordonnee, this.valeurDiametreTrous);
		}
		ordonnee += conf.get("ecartEntrePremierTrouEtDeuxiemeTrouMontantCorniere");
		profil.addPercage(ordonnee);
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
		return this.nbMontants;
	}
}

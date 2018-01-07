package generateurCoteVerriere.modeles.premium.elements;

import java.util.HashMap;

import generateurCoteVerriere.ElementGenerique;
import generateurCoteVerriere.dessinProfil.DessinProfil;
import generateurCoteVerriere.dessinProfil.DessinProfil.Side;
import myCustomSvgLibrary.MyCustomSvg;

public class MontantCorniere extends ElementGenerique {
	private String nomFichierDeRendu = "montant_corniere";
	private final int nbMontants = 2;
	private final String valeurDiametrePercages = "ØM5";
	
	public MontantCorniere(HashMap<String, Double> conf, HashMap<String, Object> data) {
		super(conf, data);
	}

	@Override
	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.get("largeurChampMontantCorniere"), conf.get("longueurMontantCorniere"), 10);
		profil.setChamp(conf.get("epaisseurMontantCorniere"), Side.RIGHT);
		profil.setIsChampCorniere(true);
		profil.setLargeurChamp(conf.get("largeurFaceMontantCorniere"));
		
		double ordonnee = conf.get("ecartEntreExtremiteEtPremierPercageMontantCorniere");
		for(int i=0;i<conf.get("nbPercageMontant");i++) {
			if(i != conf.get("nbPercageMontant") - 1) {
				profil.addPercage(ordonnee);
				ordonnee += conf.get("entreAxePercagesMontant");
			}
			else
				profil.addPercage(ordonnee, this.valeurDiametrePercages);
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

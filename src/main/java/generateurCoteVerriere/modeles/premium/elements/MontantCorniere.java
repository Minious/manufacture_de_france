package generateurCoteVerriere.modeles.premium.elements;

import java.util.HashMap;

import generateurCoteVerriere.ElementGenerique;
import generateurCoteVerriere.DessinProfil;
import generateurCoteVerriere.DessinProfil.Side;
import myCustomSvgLibrary.MyCustomSvg;

public class MontantCorniere extends ElementGenerique {
	private final int nbMontants = 2;
	private final String valeurDiametrePercages = "ØM5";
	
	public MontantCorniere(HashMap<String, Double> conf, HashMap<String, Object> data) {
		super(conf, data);
	}

	@Override
	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.get("largeurChampMontantCorniere"), conf.get("longueurMontantCorniere"), conf.get("demiLargeurGaucheChampMontantCorniere"));
		profil.setChamp(conf.get("epaisseurMontantCorniere"), Side.RIGHT);
		profil.setIsChampCorniere(true);
		profil.setLargeurChamp(conf.get("largeurFaceMontantCorniere"));
		profil.setValeurPercage(this.valeurDiametrePercages);
		
		double ordonnee = conf.get("ecartEntreExtremiteEtPremierPercageMontantCorniere");
		for(int i=0;i<conf.get("nbPercageMontant");i++) {
			if(i < conf.get("nbPercageMontant") - 1) {
				profil.addPercage(ordonnee, false);
				ordonnee += conf.get("entreAxePercagesMontant");
			}
			else
				profil.addPercage(ordonnee, true);
		}
		profil.addCoteDroiteEntrePercages(0, 1, 0);
		
		return profil.render();
	}

	@Override
	public int getNbElements() {
		return this.nbMontants;
	}
}

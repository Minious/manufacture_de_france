package generateurCoteVerriere.modeles.premium.elements;

import java.util.HashMap;

import generateurCoteVerriere.ElementGenerique;
import generateurCoteVerriere.DessinProfil;
import generateurCoteVerriere.DessinProfil.Side;
import myCustomSvgLibrary.MyCustomSvg;

public class MontantIntermediaire extends ElementGenerique {
	private final int nbMontants = (int) (conf.get("nbPartitions") - 1);
	private final String valeurDiametrePercages = "ØM5";
	
	public MontantIntermediaire(HashMap<String, Double> conf, HashMap<String, Object> data) {
		super(conf, data);
	}

	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.get("largeurChampMontantIntermediaire"), conf.get("longueurMontantIntermediaire"), conf.get("demiLargeurGaucheChampMontantIntermediaire"));
		profil.setEpaulement(conf.get("longueurEpaulementMontantIntermediaire"), conf.get("epaisseurEpaulementMontantIntermediaire"), Side.RIGHT);
		profil.setChamp(conf.get("epaisseurMontantIntermedaire"), Side.RIGHT);
		profil.setValeurPercage(this.valeurDiametrePercages);

		double ordonnee = conf.get("ecartEntreExtremiteEtPremierPercageMontantIntermediaire");
		for (int i = 0; i < conf.get("nbPercageMontant"); i++) {
			if (i != conf.get("nbPercageMontant") - 1) {
				profil.addPercage(ordonnee, false);
				ordonnee += conf.get("entreAxePercagesMontant");
			} else
				profil.addPercage(ordonnee, true);
		}
		profil.addCoteDroiteEntrePercages(0, 1, 0);
		profil.addCoteDroiteOrigine(conf.get("longueurEpaulementMontantIntermediaire"), 0);

		return profil.render();
	}

	@Override
	public int getNbElements() {
		return this.nbMontants;
	}
}
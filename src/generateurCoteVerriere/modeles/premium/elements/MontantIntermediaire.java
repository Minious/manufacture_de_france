package generateurCoteVerriere.modeles.premium.elements;

import java.util.HashMap;

import generateurCoteVerriere.ElementGenerique;
import generateurCoteVerriere.dessinProfil.DessinProfil;
import generateurCoteVerriere.dessinProfil.DessinProfil.Side;
import myCustomSvgLibrary.MyCustomSvg;

public class MontantIntermediaire extends ElementGenerique {
	private String nomFichierDeRendu = "montant_intermediaire";
	private final int nbMontants = (int) (conf.get("nbPartitions") - 1);
	private final String valeurDiametrePercages = "ØM5";
	
	public MontantIntermediaire(HashMap<String, Double> conf, HashMap<String, Object> data) {
		super(conf, data);
	}

	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.get("largeurChampMontantIntermediaire"), conf.get("longueurMontantIntermediaire"), 10);
		profil.setEpaulement(conf.get("longueurEpaulementMontantIntermediaire"), conf.get("epaisseurEpaulementMontantIntermediaire"), Side.RIGHT);
		profil.setChamp(conf.get("epaisseurMontantIntermedaire"), Side.RIGHT);
		
		double ordonnee = conf.get("ecartEntreExtremiteEtPremierPercageMontantIntermediaire");
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
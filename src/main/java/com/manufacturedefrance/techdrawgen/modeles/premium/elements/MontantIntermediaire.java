package com.manufacturedefrance.techdrawgen.modeles.premium.elements;

import java.util.Map;

import com.manufacturedefrance.techdrawgen.ElementGenerique;
import com.manufacturedefrance.techdrawgen.DessinProfil;
import com.manufacturedefrance.techdrawgen.DessinProfil.Side;
import com.manufacturedefrance.svgen.MyCustomSvg;

public class MontantIntermediaire extends ElementGenerique {
	private final int nbMontants = (int) (conf.get("nbPartitions") - 1);
	private static final String valeurDiametrePercages = "ØM5";
	
	public MontantIntermediaire(Map<String, Double> conf, Map<String, Object> data) {
		super(conf, data);
	}

	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.get("largeurChampMontantIntermediaire"), conf.get("longueurMontantIntermediaire"), conf.get("demiLargeurGaucheChampMontantIntermediaire"));
		profil.setSideCoteDemiLargeur(Side.RIGHT);
		profil.setEpaulement(conf.get("longueurEpaulementMontantIntermediaire"), conf.get("epaisseurEpaulementMontantIntermediaire"), Side.RIGHT);
		profil.setChamp(conf.get("epaisseurMontantIntermedaire"), Side.RIGHT);
		profil.setValeurPercage(valeurDiametrePercages);

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
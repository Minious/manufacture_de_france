package com.manufacturedefrance.techdrawgen.modeles.premium.elements;

import java.util.Map;

import com.manufacturedefrance.conf.PremiumConf;
import com.manufacturedefrance.techdrawgen.ElementGenerique;
import com.manufacturedefrance.techdrawgen.DessinProfil;
import com.manufacturedefrance.techdrawgen.DessinProfil.Side;
import com.manufacturedefrance.svgen.MyCustomSvg;

public class MontantIntermediaire extends ElementPremium {
	private static final String DIAMETRE_PERCAGES = "Ø5.5";
	
	public MontantIntermediaire(Map<String, Object> data, PremiumConf conf) {
		super(data, conf);
	}

	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.largeurChampMontantIntermediaire(), conf.longueurMontantIntermediaire(), conf.demiLargeurGaucheChampMontantIntermediaire());
		profil.setSideCoteDemiLargeur(Side.RIGHT);
		profil.setEpaulement(conf.longueurEpaulementMontantIntermediaire(), conf.epaisseurEpaulementMontantIntermediaire(), Side.RIGHT);
		profil.setChamp(conf.epaisseurMontantIntermedaire(), Side.RIGHT);
		profil.setValeurPercage(DIAMETRE_PERCAGES);

		double ordonnee = conf.ecartEntreExtremiteEtPremierPercageMontantIntermediaire();
		for (int i = 0; i < conf.nbPercageMontant(); i++) {
			if (i != conf.nbPercageMontant() - 1) {
				profil.addPercage(ordonnee, 0, false);
				ordonnee += conf.entreAxePercagesMontant();
			} else
				profil.addPercage(ordonnee, 0, true);
		}
		profil.addCoteDroiteEntrePercages(0, 1, 0);
		profil.addCoteDroiteOrigine(conf.longueurEpaulementMontantIntermediaire(), 0);

		return profil.render();
	}

	@Override
	public int getNbElements() {
		return conf.getNbPartitions() - 1;
	}
}
package com.manufacturedefrance.techdrawgen.modeles.premium.elements;

import java.util.Map;

import com.manufacturedefrance.conf.PremiumConf;
import com.manufacturedefrance.techdrawgen.DessinProfil;
import com.manufacturedefrance.techdrawgen.DessinProfil.Side;
import com.manufacturedefrance.svgen.MyCustomSvg;

public class MontantCorniere extends ElementPremium {
	private static final int NB_MONTANTS = 2;
	private static final String DIAMETRE_PERCAGES = "ØM5";
	
	public MontantCorniere(Map<String, Object> data, PremiumConf conf) {
		super(data, conf);
	}

	@Override
	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.largeurChampMontantCorniere(), conf.longueurMontantCorniere(), conf.demiLargeurGaucheChampMontantCorniere());
		profil.setSideCoteDemiLargeur(Side.RIGHT);
		profil.setChamp(conf.epaisseurMontantCorniere(), Side.RIGHT);
		profil.setIsChampCorniere(true);
		profil.setLargeurChamp(conf.largeurFaceMontantCorniere());
		profil.setValeurPercage(DIAMETRE_PERCAGES);
		
		double ordonnee = conf.ecartEntreExtremiteEtPremierPercageMontantCorniere();
		for(int i=0;i<conf.nbPercageMontant();i++) {
			if(i < conf.nbPercageMontant() - 1) {
				profil.addPercage(ordonnee, 0, false);
				ordonnee += conf.entreAxePercagesMontant();
			}
			else
				profil.addPercage(ordonnee, 0, true);
		}
		profil.addCoteDroiteEntrePercages(0, 1, 0);
		
		return profil.render();
	}

	@Override
	public int getNbElements() {
		return NB_MONTANTS;
	}
}

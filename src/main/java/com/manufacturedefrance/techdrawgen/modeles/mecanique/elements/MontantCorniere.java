package com.manufacturedefrance.techdrawgen.modeles.mecanique.elements;

import java.util.Map;

import com.manufacturedefrance.conf.MecaniqueConf;
import com.manufacturedefrance.techdrawgen.ElementGenerique;
import com.manufacturedefrance.techdrawgen.DessinProfil;
import com.manufacturedefrance.techdrawgen.DessinProfil.Side;
import com.manufacturedefrance.svgen.MyCustomSvg;

public class MontantCorniere extends ElementMecanique {
	private static final int NB_MONTANTS = 2;
	private static final String DIAMETRE_PERCAGES = "ØM5";
	
	public MontantCorniere(Map<String, Object> data, MecaniqueConf conf) {
		super(data, conf);
	}

	@Override
	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.largeurMontantCorniere(), conf.hauteurMontantCorniere());
		profil.setCorniere();
		profil.setChamp(conf.epaisseurProfil(), Side.RIGHT);
		profil.setValeurPercage(DIAMETRE_PERCAGES);
		double ordonnee = conf.ecartEntreExtremiteEtPremierTrouMontantCorniere();
		profil.addPercage(ordonnee, 0, false);
		ordonnee += conf.ecartEntrePremierTrouEtDeuxiemeTrouMontantCorniere();
		for(int i=0;i<conf.nbTrousIntermediairesVerticaux()+2;i++) {
			if(i != conf.nbTrousIntermediairesVerticaux() + 1) {
				profil.addPercage(ordonnee, i == 0 ? 1 : 0, false);
				ordonnee += conf.entreAxeMontant();
			}
			else
				profil.addPercage(ordonnee, 0, true);
		}
		ordonnee += conf.ecartEntrePremierTrouEtDeuxiemeTrouMontantCorniere();
		profil.addPercage(ordonnee, 1, false);
		profil.addCoteDroiteEntrePercages(0, 1, 0);
		profil.addCoteDroiteEntrePercages(1, 2, 0);
		
		return profil.render();
	}

	@Override
	public int getNbElements() {
		return NB_MONTANTS;
	}
}

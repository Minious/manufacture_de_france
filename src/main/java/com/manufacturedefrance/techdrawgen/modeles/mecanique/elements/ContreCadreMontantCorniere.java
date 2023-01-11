package com.manufacturedefrance.techdrawgen.modeles.mecanique.elements;

import java.util.Map;

import com.manufacturedefrance.conf.MecaniqueConf;
import com.manufacturedefrance.techdrawgen.ElementGenerique;
import com.manufacturedefrance.techdrawgen.DessinProfil;
import com.manufacturedefrance.svgen.MyCustomSvg;

public class ContreCadreMontantCorniere extends ElementMecanique {
	private static final int NB_MONTANTS = 2;
	private static final String DIAMETRE_PERCAGES_EXTREMITE = "Ø9";
	
	public ContreCadreMontantCorniere(Map<String, Object> data, MecaniqueConf conf) {
		super(data, conf);
	}

	@Override
	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.largeurContreCadreMontantCorniere(), conf.hauteurContreCadreMontantCorniere(), 13.5);
		profil.setCorniere();
		profil.setValeurPercage(DIAMETRE_PERCAGES_EXTREMITE);

		double ordonnee = conf.ecartEntreExtremiteEtPremierTrouContreCadreMontantCorniere();
		profil.addPercage(ordonnee, 0, false);
		ordonnee += conf.ecartEntrePremierTrouEtDeuxiemeTrouMontantCorniere();
		profil.setValeurPercage(DIAMETRE_PERCAGES_EXTREMITE);
		for(int i=0;i<conf.nbTrousIntermediairesVerticaux()+2;i++) {
			if(i < conf.nbTrousIntermediairesVerticaux()) {
				profil.addPercage(ordonnee, i == 0 ? 1 : 0, false);
				ordonnee += conf.entreAxeMontant();
			}
			else if(i < conf.nbTrousIntermediairesVerticaux() + 1) {
				profil.addPercage(ordonnee, 0, false);
				ordonnee += conf.entreAxeMontant();
			}
			else {
				profil.setValeurPercage(DIAMETRE_PERCAGES_EXTREMITE);
				profil.addPercage(ordonnee, 0, false);
			}
		}
		ordonnee += conf.ecartEntrePremierTrouEtDeuxiemeTrouMontantCorniere();
		profil.addPercage(ordonnee, 1, true);
		profil.addCoteDroiteEntrePercages(0, 1, 1);
		profil.addCoteDroiteEntrePercages(1, 2, 1);
		
		return profil.render();
	}
	
	@Override
	public int getNbElements() {
		return NB_MONTANTS;
	}
}

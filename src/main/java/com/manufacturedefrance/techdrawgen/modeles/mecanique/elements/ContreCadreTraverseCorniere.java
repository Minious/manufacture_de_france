package com.manufacturedefrance.techdrawgen.modeles.mecanique.elements;

import java.util.Map;

import com.manufacturedefrance.conf.MecaniqueConf;
import com.manufacturedefrance.techdrawgen.ElementGenerique;
import com.manufacturedefrance.techdrawgen.DessinProfil;
import com.manufacturedefrance.svgen.MyCustomSvg;

public class ContreCadreTraverseCorniere extends ElementMecanique {
	private static final int NB_TRAVERSES = 2;
	private static final String DIAMETRE_PERCAGES = "Ø9";

	public ContreCadreTraverseCorniere(Map<String, Object> data, MecaniqueConf conf) {
		super(data, conf);
	}

	@Override
	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.largeurContreCadreTraverseCorniere(), conf.hauteurContreCadreTraverseCorniere(), 13.5);
		profil.setCorniere();
		profil.setValeurPercage(DIAMETRE_PERCAGES);
		double ordonnee = conf.ecartEntreExtremiteEtPremierTrouContreCadreTraverseCorniere();
		profil.addPercage(ordonnee, 0, false);
		ordonnee += conf.ecartEntrePremierTrouEtDeuxiemeTrouTraverseCorniere();
		profil.addPercage(ordonnee, 1, false);
		ordonnee += conf.entreAxeLateralTraverseCorniere();
		profil.addPercage(ordonnee, 0, false);
		
		for(int i=0;i<conf.getNbPartitions() - 2;i++) {
			ordonnee += conf.entreAxeT();
			profil.addPercage(ordonnee, 1, false);
			ordonnee += conf.entreAxeCentralTraverseCorniere();
			profil.addPercage(ordonnee, 0, false);
		}
		
		if(conf.getNbPartitions() >= 2) {
			ordonnee += conf.entreAxeT();
			profil.addPercage(ordonnee, 1, false);
			ordonnee += conf.entreAxeLateralTraverseCorniere();
			profil.addPercage(ordonnee, 0, false);
			profil.addCoteDroiteEntrePercages(2, 3, 0);
		}

		if(conf.getNbPartitions() >= 3)
			profil.addCoteDroiteEntrePercages(3, 4, 0);
		
		ordonnee += conf.ecartEntrePremierTrouEtDeuxiemeTrouTraverseCorniere();
		profil.addPercage(ordonnee, 1, true);
		
		profil.addCoteDroiteEntrePercages(0, 1, 0);
		profil.addCoteDroiteEntrePercages(1, 2, 0);
		
		return profil.render();
	}

	@Override
	public int getNbElements() {
		return NB_TRAVERSES;
	}
}

package com.manufacturedefrance.techdrawgen.modeles.mecanique.elements;

import java.util.Map;

import com.manufacturedefrance.conf.MecaniqueConf;
import com.manufacturedefrance.techdrawgen.ElementGenerique;
import com.manufacturedefrance.techdrawgen.DessinProfil;
import com.manufacturedefrance.svgen.MyCustomSvg;

public class ContreCadreMontantPartition extends ElementMecanique {
	private static final String DIAMETRE_PERCAGES_EXTREMITE = "Ø9";
	private static final String DIAMETRE_PERCAGES_INTERMEDIAIRE = "Ø7";
	
	public ContreCadreMontantPartition(Map<String, Object> data, MecaniqueConf conf) {
		super(data, conf);

	}

	@Override
	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.largeurMontantPartition(), conf.hauteurMontantPartition());
		profil.setValeurPercage(DIAMETRE_PERCAGES_EXTREMITE);
		for(int i=0;i<conf.nbTrousIntermediairesVerticaux()+2;i++) {
			double ordonnee = conf.ecartEntreExtremiteEtPremierTrouMontantPartition() + i * conf.entreAxeMontant();
			if(i == 0 || i == conf.nbTrousIntermediairesVerticaux()+1)
				profil.setValeurPercage(DIAMETRE_PERCAGES_EXTREMITE);
			else
				profil.setValeurPercage(DIAMETRE_PERCAGES_INTERMEDIAIRE);
			profil.addPercage(ordonnee, 0, true);
		}
		profil.addCoteDroiteEntrePercages(0, 1, 1);
		
		return profil.render();
	}

	@Override
	public int getNbElements() {
		return conf.getNbPartitions() - 1;
	}
}

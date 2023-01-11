package com.manufacturedefrance.techdrawgen.modeles.mecanique.elements;

import java.util.Map;

import com.manufacturedefrance.conf.MecaniqueConf;
import com.manufacturedefrance.techdrawgen.ElementGenerique;
import com.manufacturedefrance.techdrawgen.DessinProfil;
import com.manufacturedefrance.svgen.MyCustomSvg;

public class MontantPartition extends ElementMecanique {
	private static final String DIAMTRE_PERCAGES = "ØM5";
	
	public MontantPartition(Map<String, Object> data, MecaniqueConf conf) {
		super(data, conf);
	}

	@Override
	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.largeurMontantPartition(), conf.hauteurMontantPartition());
		profil.setValeurPercage(DIAMTRE_PERCAGES);
		for(int i=0;i<conf.nbTrousIntermediairesVerticaux()+2;i++) {
			double ordonnee = conf.ecartEntreExtremiteEtPremierTrouMontantPartition() + i * conf.entreAxeMontant();
			if(i != conf.nbTrousIntermediairesVerticaux() + 1)
				profil.addPercage(ordonnee, 0, false);
			else
				profil.addPercage(ordonnee, 0, true);
		}
		profil.addCoteDroiteEntrePercages(0, 1, 0);
		
		return profil.render();
	}

	@Override
	public int getNbElements() {
		return conf.getNbPartitions() - 1;
	}
}

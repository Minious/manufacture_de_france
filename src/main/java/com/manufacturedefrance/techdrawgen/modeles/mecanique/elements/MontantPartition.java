package com.manufacturedefrance.techdrawgen.modeles.mecanique.elements;

import java.util.Map;

import com.manufacturedefrance.techdrawgen.ElementGenerique;
import com.manufacturedefrance.techdrawgen.DessinProfil;
import com.manufacturedefrance.svgen.MyCustomSvg;

public class MontantPartition extends ElementGenerique {
	private final int nbMontants = (int) (conf.get("nbPartitions") - 1);
	private static final String DIAMTRE_PERCAGES = "ØM5";
	
	public MontantPartition(Map<String, Double> conf, Map<String, Object> data) {
		super(conf, data);
	}

	@Override
	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.get("largeurMontantPartition"), conf.get("hauteurMontantPartition"));
		profil.setValeurPercage(DIAMTRE_PERCAGES);
		for(int i=0;i<conf.get("nbTrousIntermediairesVerticaux")+2;i++) {
			double ordonnee = conf.get("ecartEntreExtremiteEtPremierTrouMontantPartition") + i * conf.get("entreAxeMontant");
			if(i != conf.get("nbTrousIntermediairesVerticaux") + 1)
				profil.addPercage(ordonnee, false);
			else
				profil.addPercage(ordonnee, true);
		}
		profil.addCoteDroiteEntrePercages(0, 1, 0);
		
		return profil.render();
	}

	@Override
	public int getNbElements() {
		return this.nbMontants;
	}
}

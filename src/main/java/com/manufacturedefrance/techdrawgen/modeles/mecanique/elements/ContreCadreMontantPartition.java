package com.manufacturedefrance.techdrawgen.modeles.mecanique.elements;

import java.util.Map;

import com.manufacturedefrance.techdrawgen.ElementGenerique;
import com.manufacturedefrance.techdrawgen.DessinProfil;
import com.manufacturedefrance.svgen.MyCustomSvg;

public class ContreCadreMontantPartition extends ElementGenerique {
	private final int nbMontants = (int) (conf.get("nbPartitions") - 1);
	private static final String DIAMETRE_PERCAGES_EXTREMITE = "Ø9";
	private static final String DIAMETRE_PERCAGES_INTERMEDIAIRE = "Ø7";
	
	public ContreCadreMontantPartition(Map<String, Double> conf, Map<String, Object> data) {
		super(conf, data);
	}

	@Override
	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.get("largeurMontantPartition"), conf.get("hauteurMontantPartition"));
		profil.setValeurPercage(DIAMETRE_PERCAGES_EXTREMITE);
		for(int i=0;i<conf.get("nbTrousIntermediairesVerticaux")+2;i++) {
			double ordonnee = conf.get("ecartEntreExtremiteEtPremierTrouMontantPartition") + i * conf.get("entreAxeMontant");
			if(i == 0 || i == conf.get("nbTrousIntermediairesVerticaux")+1)
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
		return this.nbMontants;
	}
}

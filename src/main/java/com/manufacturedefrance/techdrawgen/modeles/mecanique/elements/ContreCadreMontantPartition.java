package com.manufacturedefrance.techdrawgen.modeles.mecanique.elements;

import java.util.HashMap;

import com.manufacturedefrance.techdrawgen.ElementGenerique;
import com.manufacturedefrance.techdrawgen.DessinProfil;
import com.manufacturedefrance.svgen.MyCustomSvg;

public class ContreCadreMontantPartition extends ElementGenerique {
	private final int nbMontants = (int) (conf.get("nbPartitions") - 1);
	private final String valeurDiametreTrous = "Ø7";
	
	public ContreCadreMontantPartition(HashMap<String, Double> conf, HashMap<String, Object> data) {
		super(conf, data);
	}

	@Override
	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.get("largeurMontantPartition"), conf.get("hauteurMontantPartition"));
		profil.setValeurPercage(this.valeurDiametreTrous);
		for(int i=0;i<conf.get("nbTrousIntermediairesVerticaux")+2;i++) {
			double ordonnee = conf.get("ecartEntreExtremiteEtPremierTrouMontantPartition") + i * conf.get("entreAxeMontant");
			boolean showCote = i == conf.get("nbTrousIntermediairesVerticaux") + 1;
			profil.addPercage(ordonnee, showCote);
		}
		profil.addCoteDroiteEntrePercages(0, 1, 0);
		
		return profil.render();
	}

	@Override
	public int getNbElements() {
		return this.nbMontants;
	}
}

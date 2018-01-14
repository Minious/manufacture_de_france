package com.manufacturedefrance.techdrawgen.modeles.mecanique.elements;

import java.util.HashMap;

import com.manufacturedefrance.techdrawgen.ElementGenerique;
import com.manufacturedefrance.techdrawgen.DessinProfil;
import com.manufacturedefrance.svgen.MyCustomSvg;

public class ContreCadreMontantCorniere extends ElementGenerique {
	private final int nbMontants = 2;
	private final String valeurDiametreTrous = "Ø9";
	
	public ContreCadreMontantCorniere(HashMap<String, Double> conf, HashMap<String, Object> data) {
		super(conf, data);
	}

	@Override
	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.get("largeurContreCadreMontantCorniere"), conf.get("hauteurContreCadreMontantCorniere"), 13.5);
		profil.setCorniere();
		profil.setValeurPercage(this.valeurDiametreTrous);
		
		double ordonnee = conf.get("ecartEntreExtremiteEtPremierTrouContreCadreMontantCorniere");
		profil.addPercage(ordonnee, false);
		ordonnee += conf.get("ecartEntrePremierTrouEtDeuxiemeTrouMontantCorniere");
		for(int i=0;i<conf.get("nbTrousIntermediairesVerticaux")+2;i++) {
			boolean showCote;
			if(i != conf.get("nbTrousIntermediairesVerticaux") + 1) {
				showCote = false;
				ordonnee += conf.get("entreAxeMontant");
			}
			else
				showCote = true;
			profil.addPercage(ordonnee, showCote);
		}
		ordonnee += conf.get("ecartEntrePremierTrouEtDeuxiemeTrouMontantCorniere");
		profil.addPercage(ordonnee, false);
		profil.addCoteDroiteEntrePercages(0, 1, 0);
		profil.addCoteDroiteEntrePercages(1, 2, 0);
		
		return profil.render();
	}
	
	@Override
	public int getNbElements() {
		return this.nbMontants;
	}
}

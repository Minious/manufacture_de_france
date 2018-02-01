package com.manufacturedefrance.techdrawgen.modeles.mecanique.elements;

import java.util.Map;

import com.manufacturedefrance.techdrawgen.ElementGenerique;
import com.manufacturedefrance.techdrawgen.DessinProfil;
import com.manufacturedefrance.svgen.MyCustomSvg;

public class ContreCadreMontantCorniere extends ElementGenerique {
	private static final int NB_MONTANTS = 2;
	private static final String DIAMETRE_PERCAGES = "Ø9";
	
	public ContreCadreMontantCorniere(Map<String, Double> conf, Map<String, Object> data) {
		super(conf, data);
	}

	@Override
	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.get("largeurContreCadreMontantCorniere"), conf.get("hauteurContreCadreMontantCorniere"), 13.5);
		profil.setCorniere();
		profil.setValeurPercage(DIAMETRE_PERCAGES);
		
		double ordonnee = conf.get("ecartEntreExtremiteEtPremierTrouContreCadreMontantCorniere");
		profil.addPercage(ordonnee, false);
		ordonnee += conf.get("ecartEntrePremierTrouEtDeuxiemeTrouMontantCorniere");
		for(int i=0;i<conf.get("nbTrousIntermediairesVerticaux")+2;i++) {
			if(i != conf.get("nbTrousIntermediairesVerticaux") + 1) {
				profil.addPercage(ordonnee, false);
				ordonnee += conf.get("entreAxeMontant");
			}
			else
				profil.addPercage(ordonnee, true);
		}
		ordonnee += conf.get("ecartEntrePremierTrouEtDeuxiemeTrouMontantCorniere");
		profil.addPercage(ordonnee, false);
		profil.addCoteDroiteEntrePercages(0, 1, 0);
		profil.addCoteDroiteEntrePercages(1, 2, 0);
		
		return profil.render();
	}
	
	@Override
	public int getNbElements() {
		return NB_MONTANTS;
	}
}

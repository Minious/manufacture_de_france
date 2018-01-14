package com.manufacturedefrance.techdrawgen.modeles.mecanique.elements;

import java.util.Map;

import com.manufacturedefrance.techdrawgen.ElementGenerique;
import com.manufacturedefrance.techdrawgen.DessinProfil;
import com.manufacturedefrance.techdrawgen.DessinProfil.Side;
import com.manufacturedefrance.svgen.MyCustomSvg;

public class AttachesTraverseCorniere extends ElementGenerique {
	private static final int NB_TRAVERSES = 2;
	private static final String DIAMETRE_PERCAGES = "ØM5";

	public AttachesTraverseCorniere(Map<String, Double> conf, Map<String, Object> data) {
		super(conf, data);
	}

	@Override
	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.get("largeurTraverseCorniere"), conf.get("hauteurTraverseCorniere"));
		profil.setValeurPercage(DIAMETRE_PERCAGES);
		profil.setChamp(conf.get("epaisseurProfil"), Side.LEFT);
		profil.setIsChampCorniere(true);
		
		double ordonnee = conf.get("ecartEntreExtremiteEtPremierTrouAttachesTraverseCorniere");
		profil.addPercage(ordonnee);
		
		for(int i=0;i<conf.get("nbAttachesIntermediaires")+1;i++) {
			ordonnee += conf.get("entreAxeAttachesTraverseCorniere");
			if(i == conf.get("nbAttachesIntermediaires"))
				profil.addPercage(ordonnee, true);
			else
				profil.addPercage(ordonnee, false);
		}
		
		profil.addCoteDroiteEntrePercages(0, 1, 0);
		
		return profil.render();
	}

	@Override
	public int getNbElements() {
		return NB_TRAVERSES;
	}
}

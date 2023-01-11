package com.manufacturedefrance.techdrawgen.modeles.mecanique.elements;

import java.util.Map;

import com.manufacturedefrance.conf.MecaniqueConf;
import com.manufacturedefrance.techdrawgen.ElementGenerique;
import com.manufacturedefrance.techdrawgen.DessinProfil;
import com.manufacturedefrance.techdrawgen.DessinProfil.Side;
import com.manufacturedefrance.svgen.MyCustomSvg;

public class AttachesTraverseCorniere extends ElementMecanique {
	private static final int NB_TRAVERSES = 2;
	private static final String DIAMETRE_PERCAGES = "Ø5.5";
	private static final String DIAMETRE_PERCAGES_AFFICHE = "Ø5.5 + fr";

	public AttachesTraverseCorniere(Map<String, Object> data, MecaniqueConf conf) {
		super(data, conf);
	}

	@Override
	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.largeurTraverseCorniere(), conf.hauteurTraverseCorniere());
		profil.setValeurPercage(DIAMETRE_PERCAGES);
		profil.setChamp(conf.epaisseurProfil(), Side.LEFT);
		profil.setIsChampCorniere(true);
		
		double ordonnee = conf.ecartEntreExtremiteEtPremierTrouAttachesTraverseCorniere();
		profil.addPercage(ordonnee, 0);
		
		for(int i=0;i<conf.nbAttachesIntermediaires()+1;i++) {
			ordonnee += conf.entreAxeAttachesTraverseCorniere();
			if(i == conf.nbAttachesIntermediaires())
				profil.addPercage(ordonnee, 0, DIAMETRE_PERCAGES, DIAMETRE_PERCAGES_AFFICHE);
			else
				profil.addPercage(ordonnee, 0, false);
		}
		
		profil.addCoteDroiteEntrePercages(0, 1, 0);
		
		return profil.render();
	}

	@Override
	public int getNbElements() {
		return NB_TRAVERSES;
	}
}

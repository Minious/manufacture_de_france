package com.manufacturedefrance.techdrawgen.modeles.premium.elements;

import java.util.Map;

import com.manufacturedefrance.techdrawgen.ElementGenerique;
import com.manufacturedefrance.techdrawgen.DessinProfil;
import com.manufacturedefrance.techdrawgen.DessinProfil.Side;
import com.manufacturedefrance.svgen.MyCustomSvg;

public class MontantCorniere extends ElementGenerique {
	private static final int NB_MONTANTS = 2;
	private static final String DIAMETRE_PERCAGES = "ØM5";
	
	public MontantCorniere(Map<String, Double> conf, Map<String, Object> data) {
		super(conf, data);
	}

	@Override
	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.get("largeurChampMontantCorniere"), conf.get("longueurMontantCorniere"), conf.get("demiLargeurGaucheChampMontantCorniere"));
		profil.setSideCoteDemiLargeur(Side.RIGHT);
		profil.setChamp(conf.get("epaisseurMontantCorniere"), Side.RIGHT);
		profil.setIsChampCorniere(true);
		profil.setLargeurChamp(conf.get("largeurFaceMontantCorniere"));
		profil.setValeurPercage(DIAMETRE_PERCAGES);
		
		double ordonnee = conf.get("ecartEntreExtremiteEtPremierPercageMontantCorniere");
		for(int i=0;i<conf.get("nbPercageMontant");i++) {
			if(i < conf.get("nbPercageMontant") - 1) {
				profil.addPercage(ordonnee, false);
				ordonnee += conf.get("entreAxePercagesMontant");
			}
			else
				profil.addPercage(ordonnee, true);
		}
		profil.addCoteDroiteEntrePercages(0, 1, 0);
		
		return profil.render();
	}

	@Override
	public int getNbElements() {
		return NB_MONTANTS;
	}
}

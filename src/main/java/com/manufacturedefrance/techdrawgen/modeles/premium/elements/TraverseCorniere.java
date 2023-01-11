package com.manufacturedefrance.techdrawgen.modeles.premium.elements;

import java.util.Map;

import com.manufacturedefrance.conf.PremiumConf;
import com.manufacturedefrance.techdrawgen.ElementGenerique;
import com.manufacturedefrance.techdrawgen.DessinProfil;
import com.manufacturedefrance.techdrawgen.DessinProfil.Side;
import com.manufacturedefrance.svgen.MyCustomSvg;

public class TraverseCorniere extends ElementPremium {
	private static final int NB_TRAVERSES = 2;
	private static final String DIAMETRE_PERCAGES_PARCLOSE = "ØM5";
    private static final String DIAMETRE_PERCAGES_FIXATION = "Ø5.5";
    private static final String DIAMETRE_PERCAGES_FIXATION_AFFICHE = "Ø5.5 + fr";
	
	public TraverseCorniere(Map<String, Object> data, PremiumConf conf) {
		super(data, conf);
	}

	@Override
	public MyCustomSvg getDessin() {
		double entreAxePercageMontantEtParcloseTraverseCorniere = conf.entreAxePercageMontantEtParcloseTraverseCorniere();
		double entreAxePercageMontantTraverseCorniere = conf.entreAxePercageMontantTraverseCorniere();
		double longueurTraverseCorniere = conf.longueurTraverseCorniere();

		DessinProfil profil = new DessinProfil(conf.largeurChampTraverseCorniere(), longueurTraverseCorniere, conf.demiLargeurGaucheChampTraverseCorniere());
        profil.setSideCoteDemiLargeur(Side.RIGHT);
        profil.setChamp(conf.epaisseurTraverseCorniere(), Side.RIGHT);
		profil.setIsChampCorniere(true);
		profil.setLargeurChamp(conf.largeurFaceTraverseCorniere());
		
		double ordonnee = 0;
		ordonnee += conf.ecartEntreExtremiteEtPremierPercageParcloseTraverseCorniere();
		profil.addPercage(ordonnee, 0, DIAMETRE_PERCAGES_PARCLOSE, true);
		ordonnee += entreAxePercageMontantTraverseCorniere - entreAxePercageMontantEtParcloseTraverseCorniere;
		for(int i=0;i<conf.getNbPartitions() - 1;i++) {
			profil.addPercage(ordonnee - entreAxePercageMontantTraverseCorniere / 2, 0, DIAMETRE_PERCAGES_FIXATION, DIAMETRE_PERCAGES_FIXATION_AFFICHE);
			profil.addPercage(ordonnee - entreAxePercageMontantEtParcloseTraverseCorniere, 0, DIAMETRE_PERCAGES_PARCLOSE, true);

//			profil.addPercage(ordonnee, DIAMETRE_PERCAGES_MONTANT, true);
			profil.addPercage(ordonnee + entreAxePercageMontantEtParcloseTraverseCorniere, 0, DIAMETRE_PERCAGES_PARCLOSE, true);
			
			ordonnee += entreAxePercageMontantTraverseCorniere;
		}
		
		if(conf.getNbPartitions() == 1) {
			profil.addPercage(conf.ecartEntreExtremiteEtPercageFixationSiUneSeulePartitionTraverseCorniere(), 0, DIAMETRE_PERCAGES_FIXATION, DIAMETRE_PERCAGES_FIXATION_AFFICHE);
			profil.addPercage(longueurTraverseCorniere - conf.ecartEntreExtremiteEtPercageFixationSiUneSeulePartitionTraverseCorniere(), 0, DIAMETRE_PERCAGES_FIXATION, DIAMETRE_PERCAGES_FIXATION_AFFICHE);
		} else {
			profil.addPercage(ordonnee - entreAxePercageMontantTraverseCorniere / 2, 0, DIAMETRE_PERCAGES_FIXATION, DIAMETRE_PERCAGES_FIXATION_AFFICHE);
		}
		profil.addPercage(ordonnee - entreAxePercageMontantEtParcloseTraverseCorniere, 0, DIAMETRE_PERCAGES_PARCLOSE, true);

		if(conf.getNbPartitions() >= 3) {
			profil.addCoteDroiteEntrePercages(0, 4, 2);
			profil.addCoteDroiteEntrePercages(1, 5, 3);
			profil.addCoteDroiteEntrePercages(2, 6, 4);
			profil.addCoteDroiteEntrePercages(3, 7, 5);
		}
		
		return profil.render();
	}
	
	public MyCustomSvg getDessinFace() {
		DessinProfil profil = new DessinProfil(conf.largeurFaceTraverseCorniere(), conf.longueurTraverseCorniere());
		profil.setCorniere(Side.RIGHT);
		profil.setChamp(conf.epaisseurTraverseCorniere(), Side.LEFT);
		
		return profil.render();
	}

	@Override
	public int getNbElements() {
		return NB_TRAVERSES;
	}
}

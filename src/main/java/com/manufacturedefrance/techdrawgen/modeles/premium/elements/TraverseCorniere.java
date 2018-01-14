package com.manufacturedefrance.techdrawgen.modeles.premium.elements;

import java.util.Map;

import com.manufacturedefrance.techdrawgen.ElementGenerique;
import com.manufacturedefrance.techdrawgen.DessinProfil;
import com.manufacturedefrance.techdrawgen.DessinProfil.Side;
import com.manufacturedefrance.svgen.MyCustomSvg;

public class TraverseCorniere extends ElementGenerique {
	private static final int NB_TRAVERSES = 2;
	private static final String DIAMETRE_PERCAGES_MONTANT = "Ø7";
	private static final String DIAMETRE_PERCAGES_PARCLOSE = "ØM5";
    private static final String DIAMETRE_PERCAGES_FIXATION = "Ø5.5";
    private static final String DIAMETRE_PERCAGES_FIXATION_AFFICHE = "Ø5.5 + fr";
	
	public TraverseCorniere(Map<String, Double> conf, Map<String, Object> data) {
		super(conf, data);
	}

	@Override
	public MyCustomSvg getDessin() {
		int nbPartitions = conf.get("nbPartitions").intValue();
		double entreAxePercageMontantEtParcloseTraverseCorniere = conf.get("entreAxePercageMontantEtParcloseTraverseCorniere");
		double entreAxePercageMontantTraverseCorniere = conf.get("entreAxePercageMontantTraverseCorniere");
		double longueurTraverseCorniere = conf.get("longueurTraverseCorniere");

		DessinProfil profil = new DessinProfil(conf.get("largeurChampTraverseCorniere"), longueurTraverseCorniere, conf.get("demiLargeurGaucheChampTraverseCorniere"));
        profil.setSideCoteDemiLargeur(Side.RIGHT);
        profil.setChamp(conf.get("epaisseurTraverseCorniere"), Side.RIGHT);
		profil.setIsChampCorniere(true);
		profil.setLargeurChamp(conf.get("largeurFaceTraverseCorniere"));
		
		double ordonnee = 0;
		ordonnee += conf.get("ecartEntreExtremiteEtPremierPercageParcloseTraverseCorniere");
		profil.addPercage(ordonnee, DIAMETRE_PERCAGES_PARCLOSE, true);
		ordonnee += entreAxePercageMontantTraverseCorniere - entreAxePercageMontantEtParcloseTraverseCorniere;
		for(int i=0;i<nbPartitions - 1;i++) {
			profil.addPercage(ordonnee - entreAxePercageMontantTraverseCorniere / 2, DIAMETRE_PERCAGES_FIXATION, DIAMETRE_PERCAGES_FIXATION_AFFICHE);
			profil.addPercage(ordonnee - entreAxePercageMontantEtParcloseTraverseCorniere, DIAMETRE_PERCAGES_PARCLOSE, true);

			profil.addPercage(ordonnee, DIAMETRE_PERCAGES_MONTANT, true);
			profil.addPercage(ordonnee + entreAxePercageMontantEtParcloseTraverseCorniere, DIAMETRE_PERCAGES_PARCLOSE, true);
			
			ordonnee += entreAxePercageMontantTraverseCorniere;
		}
		
		if(nbPartitions == 1) {
			profil.addPercage(conf.get("ecartEntreExtremiteEtPercageFixationSiUneSeulePartitionTraverseCorniere"), DIAMETRE_PERCAGES_FIXATION, DIAMETRE_PERCAGES_FIXATION_AFFICHE);
			profil.addPercage(longueurTraverseCorniere - conf.get("ecartEntreExtremiteEtPercageFixationSiUneSeulePartitionTraverseCorniere"), DIAMETRE_PERCAGES_FIXATION, DIAMETRE_PERCAGES_FIXATION_AFFICHE);
		} else {
			profil.addPercage(ordonnee - entreAxePercageMontantTraverseCorniere / 2, DIAMETRE_PERCAGES_FIXATION, DIAMETRE_PERCAGES_FIXATION_AFFICHE);
		}
		profil.addPercage(ordonnee - entreAxePercageMontantEtParcloseTraverseCorniere, DIAMETRE_PERCAGES_PARCLOSE, true);

		if(nbPartitions >= 3) {
			profil.addCoteDroiteEntrePercages(0, 4, 2);
			profil.addCoteDroiteEntrePercages(1, 5, 3);
			profil.addCoteDroiteEntrePercages(2, 6, 4);
		}
		
		return profil.render();
	}
	
	public MyCustomSvg getDessinFace() {
		DessinProfil profil = new DessinProfil(conf.get("largeurFaceTraverseCorniere"), conf.get("longueurTraverseCorniere"));
		profil.setCorniere(Side.RIGHT);
		profil.setChamp(conf.get("epaisseurTraverseCorniere"), Side.LEFT);
		
		return profil.render();
	}

	@Override
	public int getNbElements() {
		return NB_TRAVERSES;
	}
}

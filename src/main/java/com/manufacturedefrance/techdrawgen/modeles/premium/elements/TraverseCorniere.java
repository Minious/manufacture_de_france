package com.manufacturedefrance.techdrawgen.modeles.premium.elements;

import java.util.Map;

import com.manufacturedefrance.techdrawgen.ElementGenerique;
import com.manufacturedefrance.techdrawgen.DessinProfil;
import com.manufacturedefrance.techdrawgen.DessinProfil.Side;
import com.manufacturedefrance.svgen.MyCustomSvg;

public class TraverseCorniere extends ElementGenerique {
	private static final int nbTraverses = 2;
	private static final String valeurDiametrePercagesMontant = "Ø7";
	private static final String valeurDiametrePercagesParclose = "ØM5";
    private static final String valeurDiametrePercagesFixation = "Ø5.5";
    private static final String valeurAfficheeDiametrePercagesFixation = "Ø5.5 + fr";
	
	public TraverseCorniere(Map<String, Double> conf, Map<String, Object> data) {
		super(conf, data);
	}

	@Override
	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.get("largeurChampTraverseCorniere"), conf.get("longueurTraverseCorniere"), conf.get("demiLargeurGaucheChampTraverseCorniere"));
        profil.setSideCoteDemiLargeur(Side.RIGHT);
        profil.setChamp(conf.get("epaisseurTraverseCorniere"), Side.RIGHT);
		profil.setIsChampCorniere(true);
		profil.setLargeurChamp(conf.get("largeurFaceTraverseCorniere"));
		
		double ordonnee = 0;
		ordonnee += conf.get("ecartEntreExtremiteEtPremierPercageParcloseTraverseCorniere");
		profil.addPercage(ordonnee, valeurDiametrePercagesParclose, true);
		ordonnee += conf.get("entreAxePercageMontantTraverseCorniere") - conf.get("entreAxePercageMontantEtParcloseTraverseCorniere");
		for(int i=0;i<conf.get("nbPartitions") - 1;i++) {
			profil.addPercage(ordonnee - conf.get("entreAxePercageMontantTraverseCorniere") / 2, valeurDiametrePercagesFixation, valeurAfficheeDiametrePercagesFixation);
			profil.addPercage(ordonnee - conf.get("entreAxePercageMontantEtParcloseTraverseCorniere"), valeurDiametrePercagesParclose, true);

			profil.addPercage(ordonnee, valeurDiametrePercagesMontant, true);
			profil.addPercage(ordonnee + conf.get("entreAxePercageMontantEtParcloseTraverseCorniere"), valeurDiametrePercagesParclose, true);
			
			ordonnee += conf.get("entreAxePercageMontantTraverseCorniere");
		}
		
		if(conf.get("nbPartitions") == 1) {
			profil.addPercage(conf.get("ecartEntreExtremiteEtPercageFixationSiUneSeulePartitionTraverseCorniere"), valeurDiametrePercagesFixation, valeurAfficheeDiametrePercagesFixation);
			profil.addPercage(conf.get("longueurTraverseCorniere") - conf.get("ecartEntreExtremiteEtPercageFixationSiUneSeulePartitionTraverseCorniere"), valeurDiametrePercagesFixation, valeurAfficheeDiametrePercagesFixation);
		} else {
			profil.addPercage(ordonnee - conf.get("entreAxePercageMontantTraverseCorniere") / 2, valeurDiametrePercagesFixation, valeurAfficheeDiametrePercagesFixation);
		}
		profil.addPercage(ordonnee - conf.get("entreAxePercageMontantEtParcloseTraverseCorniere"), valeurDiametrePercagesParclose, true);

		if(conf.get("nbPartitions") >= 3) {
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
		return nbTraverses;
	}
}

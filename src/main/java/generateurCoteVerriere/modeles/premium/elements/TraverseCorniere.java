package generateurCoteVerriere.modeles.premium.elements;

import java.util.HashMap;

import generateurCoteVerriere.ElementGenerique;
import generateurCoteVerriere.DessinProfil;
import generateurCoteVerriere.DessinProfil.Side;
import myCustomSvgLibrary.MyCustomSvg;

public class TraverseCorniere extends ElementGenerique {
	private final int nbTraverses = 2;
	private final String valeurDiametrePercagesMontant = "Ø7";
	private final String valeurDiametrePercagesParclose = "ØM5";
    private final String valeurDiametrePercagesFixation = "Ø5.5";
    private final String valeurAfficheeDiametrePercagesFixation = "Ø5.5 + fr";
	
	public TraverseCorniere(HashMap<String, Double> conf, HashMap<String, Object> data) {
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
		profil.addPercage(ordonnee, this.valeurDiametrePercagesParclose, true);
		ordonnee += conf.get("entreAxePercageMontantTraverseCorniere") - conf.get("entreAxePercageMontantEtParcloseTraverseCorniere");
		for(int i=0;i<conf.get("nbPartitions") - 1;i++) {
			profil.addPercage(ordonnee - conf.get("entreAxePercageMontantTraverseCorniere") / 2, this.valeurDiametrePercagesFixation, this.valeurAfficheeDiametrePercagesFixation);
			profil.addPercage(ordonnee - conf.get("entreAxePercageMontantEtParcloseTraverseCorniere"), this.valeurDiametrePercagesParclose, true);

			//boolean showCote = i == conf.get("nbPartitions") - 2;
			profil.addPercage(ordonnee, this.valeurDiametrePercagesMontant, true);
			profil.addPercage(ordonnee + conf.get("entreAxePercageMontantEtParcloseTraverseCorniere"), this.valeurDiametrePercagesParclose, true);
			
			ordonnee += conf.get("entreAxePercageMontantTraverseCorniere");
		}
		
		if(conf.get("nbPartitions") == 1) {
			profil.addPercage(conf.get("ecartEntreExtremiteEtPercageFixationSiUneSeulePartitionTraverseCorniere"), this.valeurDiametrePercagesFixation, this.valeurAfficheeDiametrePercagesFixation);
			profil.addPercage(conf.get("longueurTraverseCorniere") - conf.get("ecartEntreExtremiteEtPercageFixationSiUneSeulePartitionTraverseCorniere"), this.valeurDiametrePercagesFixation, this.valeurAfficheeDiametrePercagesFixation);
		} else {
			profil.addPercage(ordonnee - conf.get("entreAxePercageMontantTraverseCorniere") / 2, this.valeurDiametrePercagesFixation, this.valeurAfficheeDiametrePercagesFixation);
		}
		profil.addPercage(ordonnee - conf.get("entreAxePercageMontantEtParcloseTraverseCorniere"), this.valeurDiametrePercagesParclose, true);

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
		return this.nbTraverses;
	}
}

package generateurCoteVerriere.modeles.premium.elements;

import java.util.HashMap;

import generateurCoteVerriere.ElementGenerique;
import generateurCoteVerriere.dessinProfil.DessinProfil;
import generateurCoteVerriere.dessinProfil.DessinProfil.Side;
import myCustomSvgLibrary.MyCustomSvg;

public class TraverseCorniere extends ElementGenerique {
	private String nomFichierDeRendu = "traverse_corniere";
	private final int nbTraverses = 2;
	private final String valeurDiametrePercagesMontant = "Ø7";
	private final String valeurDiametrePercagesParclose = "ØM5";
	private final String valeurDiametrePercagesFixation = "Ø5.5 + fraisage";
	
	public TraverseCorniere(HashMap<String, Double> conf, HashMap<String, Object> data) {
		super(conf, data);
	}

	@Override
	public MyCustomSvg getDessin() {
		DessinProfil profil = new DessinProfil(conf.get("largeurChampTraverseCorniere"), conf.get("longueurTraverseCorniere"), 10);
		profil.setChamp(conf.get("epaisseurTraverseCorniere"), Side.RIGHT);
		profil.setIsChampCorniere(true);
		profil.setLargeurChamp(conf.get("largeurFaceTraverseCorniere"));
		
		double ordonnee = 0;
		ordonnee += conf.get("ecartEntreExtremiteEtPremierPercageParcloseTraverseCorniere");
		profil.addPercage(ordonnee);
		ordonnee += conf.get("entreAxePercageMontantTraverseCorniere") - conf.get("entreAxePercageMontantEtParcloseTraverseCorniere");
		for(int i=0;i<conf.get("nbPartitions") - 1;i++) {
			profil.addPercage(ordonnee - conf.get("entreAxePercageMontantTraverseCorniere") / 2);
			profil.addPercage(ordonnee - conf.get("entreAxePercageMontantEtParcloseTraverseCorniere"));
			if(i < conf.get("nbPartitions") - 2) {
				profil.addPercage(ordonnee);
				profil.addPercage(ordonnee + conf.get("entreAxePercageMontantEtParcloseTraverseCorniere"));
			} else {
				profil.addPercage(ordonnee, this.valeurDiametrePercagesMontant);
				profil.addPercage(ordonnee + conf.get("entreAxePercageMontantEtParcloseTraverseCorniere"), this.valeurDiametrePercagesParclose);				
			}
			ordonnee += conf.get("entreAxePercageMontantTraverseCorniere");
		}
		
		if(conf.get("nbPartitions") == 1) {
			profil.addPercage(conf.get("ecartEntreExtremiteEtPercageFixationSiUneSeulePartitionTraverseCorniere"));
			profil.addPercage(conf.get("longueurTraverseCorniere") - conf.get("ecartEntreExtremiteEtPercageFixationSiUneSeulePartitionTraverseCorniere"), this.valeurDiametrePercagesFixation);
		} else {
			profil.addPercage(ordonnee - conf.get("entreAxePercageMontantTraverseCorniere") / 2, this.valeurDiametrePercagesFixation);
		}
		profil.addPercage(ordonnee - conf.get("entreAxePercageMontantEtParcloseTraverseCorniere"), this.valeurDiametrePercagesParclose);

		if(conf.get("nbPartitions") >= 3) {
			profil.addCoteDroiteEntrePercages(0, 4, 0);
			profil.addCoteDroiteEntrePercages(1, 5, 1);
			profil.addCoteDroiteEntrePercages(2, 6, 2);
		}
		
		MyCustomSvg g = profil.render();
		
		return g;
	}
	
	public MyCustomSvg getDessinFace() {
		DessinProfil profil = new DessinProfil(conf.get("largeurFaceTraverseCorniere"), conf.get("longueurTraverseCorniere"));
		profil.setCorniere(Side.RIGHT);
		profil.setChamp(conf.get("epaisseurTraverseCorniere"), Side.LEFT);
		
		MyCustomSvg g = profil.render();
		
		return g;
	}

	@Override
	public String getNomFichierDeRendu() {
		return this.nomFichierDeRendu;
	}

	@Override
	public int getNbElements() {
		return this.nbTraverses;
	}
}

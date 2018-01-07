package generateurCoteVerriere.modeles.premium.elements;

import java.awt.BasicStroke;
import java.util.ArrayList;
import java.util.HashMap;

import generateurCoteVerriere.ElementGenerique;
import generateurCoteVerriere.LignesTexte;
import generateurCoteVerriere.dessinProfil.DessinProfil;
import generateurCoteVerriere.dessinProfil.DessinProfil.Side;
import myCustomSvgLibrary.MyCustomSvg;
import myCustomSvgLibrary.MyHandyLayout;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced;
import myCustomSvgLibraryEnhanced.Point;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced.ShiftMode;

public class TraverseCorniere extends ElementGenerique {
	// Initialisation des paramètres
	private String nomFichierDeRendu = "traverse_corniere";

	// Parametres de la police
	private final int taillePoliceCote = 10;
	private final int curUnderLineGap = 2;

	// Titre traverse Corniere
	private final int nbTraverses = 2;

	// General
	private final double margeEntreTraverseEtPremiereCote = 10; // <--- Empirique

	private final double diametrePercages = conf.get("largeurChampTraverseCorniere") / 8; // INCORRECT
	private final String valeurDiametrePercagesMontant = "Ø7";
	private final String valeurDiametrePercagesParclose = "ØM5";
	private final String valeurDiametrePercagesFixation = "Ø5.5 + fraisage";
	
	public TraverseCorniere(HashMap<String, Double> conf, HashMap<String, Object> data) {
		super(conf, data);
	}

	@Override
	protected MyCustomSvg getDessin() {
		MyHandyLayout l = new MyHandyLayout();
		l.addRow(new MyCustomSvg[] {getDessinOriginal(), getDessinNew()}, ShiftMode.CENTER);
		return l.getSvg();
	}
	
	public MyCustomSvg getDessinNew() {
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
			profil.addCoteDroite(0, 4, 0);
			profil.addCoteDroite(1, 5, 1);
			profil.addCoteDroite(2, 6, 2);
		}
		
		MyCustomSvg g = profil.render();
		
		return g;
	}

	private MyCustomSvg getDessinOriginal() {
		MyCustomSvgEnhanced g = new MyCustomSvgEnhanced();

		g.setUnderLineGap(this.curUnderLineGap);
		g.setFontSize(this.taillePoliceCote);
		
		// Trace le traverse 
		g.setStrokeWidth(1);
		g.drawRect(- conf.get("demiLargeurGaucheChampTraverseCorniere"), 0, conf.get("largeurChampTraverseCorniere"), conf.get("longueurTraverseCorniere"));

		// Trace l'onglet
		g.setStrokeWidth(1);
		g.drawLine(conf.get("demiLargeurDroitChampTraverseCorniere") - conf.get("epaisseurTraverseCorniere"), 0, conf.get("demiLargeurDroitChampTraverseCorniere") - conf.get("epaisseurTraverseCorniere"), conf.get("longueurTraverseCorniere"));

		// Trace l'axe du milieu du traverse 
		g.setStroke(new BasicStroke(0.5f));
		g.setDashArray(new float[] {10, 2, 2, 2});
		g.drawLine(0, 0, 0, conf.get("longueurTraverseCorniere"));
		g.removeDashArray();
		
		// Cote de largeur puis demi largeur de la Corniere avant
 		g.setFontSize(this.taillePoliceCote);
 		Point p1_1 = new Point(- conf.get("demiLargeurGaucheChampTraverseCorniere"), 0);
 		Point p1_2 = new Point(conf.get("demiLargeurDroitChampTraverseCorniere"), 0);
 		g.drawDistanceCote(p1_1, p1_2, margeEntreTraverseEtPremiereCote + curUnderLineGap + taillePoliceCote, 0, ShiftMode.CENTER);
 		
 		Point p2_1 = p1_1;
 		Point p2_2 = new Point(0, 0);
 		g.drawDistanceCote(p2_1, p2_2, margeEntreTraverseEtPremiereCote, - conf.get("demiLargeurGaucheChampTraverseCorniere") / 2 - 4, ShiftMode.RIGHT);
	 	
 		// Cotes latérales
 		int numCurCoteGauche = 0;
 		Point pBasTraverseCornier = new Point(0, conf.get("longueurTraverseCorniere"));
 		Point pHautTraverseCornier = new Point(0, 0);
 		if(conf.get("nbPartitions") <= 2) {
 			Point pParclose1 = new Point(pBasTraverseCornier);
 			pParclose1.move(0, - conf.get("entreAxePercageMontantEtParcloseTraverseCorniere"));
 			g.drawDistanceCote(pBasTraverseCornier, pParclose1, conf.get("demiLargeurGaucheChampTraverseCorniere") + margeEntreTraverseEtPremiereCote + numCurCoteGauche * (curUnderLineGap + taillePoliceCote));
			g.drawCircle(pParclose1, this.diametrePercages);
			numCurCoteGauche++;

			if(conf.get("nbPartitions") == 1) {
	 			Point pFixation1 = new Point(pBasTraverseCornier);
	 			pFixation1.move(0, - conf.get("ecartEntreExtremiteEtPercageFixationSiUneSeulePartitionTraverseCorniere"));
	 			g.drawDistanceCote(pBasTraverseCornier, pFixation1, conf.get("demiLargeurGaucheChampTraverseCorniere") + margeEntreTraverseEtPremiereCote + numCurCoteGauche * (curUnderLineGap + taillePoliceCote));
				g.drawCircle(pFixation1, this.diametrePercages);
	 			numCurCoteGauche++;
	
	 			Point pFixation2 = new Point(pHautTraverseCornier);
	 			pFixation2.move(0, conf.get("ecartEntreExtremiteEtPercageFixationSiUneSeulePartitionTraverseCorniere"));
	 			g.drawDistanceCote(pBasTraverseCornier, pFixation2, conf.get("demiLargeurGaucheChampTraverseCorniere") + margeEntreTraverseEtPremiereCote + numCurCoteGauche * (curUnderLineGap + taillePoliceCote));
				g.drawCircle(pFixation2, this.diametrePercages);
	 			numCurCoteGauche++;
 			}
 			else if(conf.get("nbPartitions") == 2) {
 				Point pMontant = new Point(pBasTraverseCornier);
 				pMontant.move(0, - conf.get("ecartEntreExtremiteEtPremierPercageMontantTraverseCorniere"));

	 			Point pFixation1 = new Point(pMontant);
	 			pFixation1.move(0, conf.get("ecartEntreExtremiteEtPremierPercageMontantTraverseCorniere") / 2);
	 			g.drawDistanceCote(pBasTraverseCornier, pFixation1, conf.get("demiLargeurGaucheChampTraverseCorniere") + margeEntreTraverseEtPremiereCote + numCurCoteGauche * (curUnderLineGap + taillePoliceCote));
				g.drawCircle(pFixation1, this.diametrePercages);
	 			numCurCoteGauche++;
	 			
	 			Point pParclose1_2 = new Point(pMontant);
	 			pParclose1_2.move(0, conf.get("entreAxePercageMontantEtParcloseTraverseCorniere"));
	 			g.drawDistanceCote(pBasTraverseCornier, pParclose1_2, conf.get("demiLargeurGaucheChampTraverseCorniere") + margeEntreTraverseEtPremiereCote + numCurCoteGauche * (curUnderLineGap + taillePoliceCote));
				g.drawCircle(pParclose1_2, this.diametrePercages);
				numCurCoteGauche++;

	 			g.drawDistanceCote(pBasTraverseCornier, pMontant, conf.get("demiLargeurGaucheChampTraverseCorniere") + margeEntreTraverseEtPremiereCote + numCurCoteGauche * (curUnderLineGap + taillePoliceCote));
				g.drawCircle(pMontant, this.diametrePercages);
	 			numCurCoteGauche++;
	 			
	 			Point pParclose2_2 = new Point(pMontant);
	 			pParclose2_2.move(0, - conf.get("entreAxePercageMontantEtParcloseTraverseCorniere"));
	 			g.drawDistanceCote(pBasTraverseCornier, pParclose2_2, conf.get("demiLargeurGaucheChampTraverseCorniere") + margeEntreTraverseEtPremiereCote + numCurCoteGauche * (curUnderLineGap + taillePoliceCote));
				g.drawCircle(pParclose2_2, this.diametrePercages);
				numCurCoteGauche++;
	
	 			Point pFixation2 = new Point(pMontant);
	 			pFixation2.move(0, - conf.get("ecartEntreExtremiteEtPremierPercageMontantTraverseCorniere") / 2);
	 			g.drawDistanceCote(pBasTraverseCornier, pFixation2, conf.get("demiLargeurGaucheChampTraverseCorniere") + margeEntreTraverseEtPremiereCote + numCurCoteGauche * (curUnderLineGap + taillePoliceCote));
				g.drawCircle(pFixation2, this.diametrePercages);
	 			numCurCoteGauche++;
			}

 			Point pParclose2 = new Point(pHautTraverseCornier);
 			pParclose2.move(0, conf.get("entreAxePercageMontantEtParcloseTraverseCorniere"));
 			g.drawDistanceCote(pBasTraverseCornier, pParclose2, conf.get("demiLargeurGaucheChampTraverseCorniere") + margeEntreTraverseEtPremiereCote + numCurCoteGauche * (curUnderLineGap + taillePoliceCote));
			g.drawCircle(pParclose2, this.diametrePercages);
			numCurCoteGauche++;
 		}
 		else {
 			// Cotes gauches (nbPart > 2)
	 		Point pCurPercageMontant = new Point(0, conf.get("longueurTraverseCorniere") - conf.get("ecartEntreExtremiteEtPremierPercageMontantTraverseCorniere"));
	 		for(int i=0;i<conf.get("nbPartitions")-1;i++) {
	 			Point pParclose1 = new Point(pCurPercageMontant);
	 			pParclose1.move(0, conf.get("entreAxePercageMontantTraverseCorniere") - conf.get("entreAxePercageMontantEtParcloseTraverseCorniere"));
	 			g.drawDistanceCote(pBasTraverseCornier, pParclose1, conf.get("demiLargeurGaucheChampTraverseCorniere") + margeEntreTraverseEtPremiereCote + numCurCoteGauche * (curUnderLineGap + taillePoliceCote));
				g.drawCircle(pParclose1, this.diametrePercages);
				numCurCoteGauche++;
	
	 			Point pFixation = new Point(pCurPercageMontant);
	 			pFixation.move(0, conf.get("entreAxePercageMontantTraverseCorniere") / 2);
	 			g.drawDistanceCote(pBasTraverseCornier, pFixation, conf.get("demiLargeurGaucheChampTraverseCorniere") + margeEntreTraverseEtPremiereCote + numCurCoteGauche * (curUnderLineGap + taillePoliceCote));
				g.drawCircle(pFixation, this.diametrePercages);
	 			numCurCoteGauche++;
	
	 			Point pParclose2 = new Point(pCurPercageMontant);
	 			pParclose2.move(0, conf.get("entreAxePercageMontantEtParcloseTraverseCorniere"));
	 			g.drawDistanceCote(pBasTraverseCornier, pParclose2, conf.get("demiLargeurGaucheChampTraverseCorniere") + margeEntreTraverseEtPremiereCote + numCurCoteGauche * (curUnderLineGap + taillePoliceCote));
				g.drawCircle(pParclose2, this.diametrePercages);
	 			numCurCoteGauche++;
	 			
	 			g.drawDistanceCote(pBasTraverseCornier, pCurPercageMontant, conf.get("demiLargeurGaucheChampTraverseCorniere") + margeEntreTraverseEtPremiereCote + numCurCoteGauche * (curUnderLineGap + taillePoliceCote));
	 			g.drawCircle(pCurPercageMontant, this.diametrePercages);
	 			numCurCoteGauche++;
	
	 			if(i != conf.get("nbPartitions") - 2)
	 				pCurPercageMontant.move(0, - conf.get("entreAxePercageMontantTraverseCorniere"));
	 		}
	 		
	 		g.drawDiameterCote(this.valeurDiametrePercagesMontant, pCurPercageMontant, - Math.PI / 4, 40, ShiftMode.LEFT, 5);
			
			Point pLastParclose1 = new Point(pCurPercageMontant);
			pLastParclose1.move(0, - conf.get("entreAxePercageMontantEtParcloseTraverseCorniere"));
			g.drawDistanceCote(pBasTraverseCornier, pLastParclose1, conf.get("demiLargeurGaucheChampTraverseCorniere") + margeEntreTraverseEtPremiereCote + numCurCoteGauche * (curUnderLineGap + taillePoliceCote));
			g.drawCircle(pLastParclose1, this.diametrePercages);
			g.drawDiameterCote(this.valeurDiametrePercagesParclose, pLastParclose1, - Math.PI / 4, 40, ShiftMode.LEFT, 5);
			numCurCoteGauche++;
	
			Point pLastFixation = new Point(pCurPercageMontant);
			pLastFixation.move(0, - conf.get("entreAxePercageMontantTraverseCorniere") / 2);
			g.drawDistanceCote(pBasTraverseCornier, pLastFixation, conf.get("demiLargeurGaucheChampTraverseCorniere") + margeEntreTraverseEtPremiereCote + numCurCoteGauche * (curUnderLineGap + taillePoliceCote));
			g.drawCircle(pLastFixation, this.diametrePercages);
			g.drawDiameterCote(this.valeurDiametrePercagesFixation, pLastFixation, - Math.PI / 4, 40, ShiftMode.LEFT, 5);
			numCurCoteGauche++;
	
			Point pLastParclose2 = new Point(pCurPercageMontant);
			pLastParclose2.move(0, - conf.get("entreAxePercageMontantTraverseCorniere") + conf.get("entreAxePercageMontantEtParcloseTraverseCorniere"));
			g.drawDistanceCote(pBasTraverseCornier, pLastParclose2, conf.get("demiLargeurGaucheChampTraverseCorniere") + margeEntreTraverseEtPremiereCote + numCurCoteGauche * (curUnderLineGap + taillePoliceCote));
			g.drawCircle(pLastParclose2, this.diametrePercages);
			g.drawDiameterCote(this.valeurDiametrePercagesParclose, pLastParclose2, - Math.PI / 4, 40, ShiftMode.LEFT, 5);
			numCurCoteGauche++;
			
			// Cotes droites (nbPart > 2)
			int numCurCoteDroite = 0;
			
			Point pA1 = new Point(pBasTraverseCornier);
			pA1.move(0, - conf.get("ecartEntreExtremiteEtPremierPercageMontantTraverseCorniere") + conf.get("entreAxePercageMontantTraverseCorniere") - conf.get("entreAxePercageMontantEtParcloseTraverseCorniere"));
			g.drawDistanceCote(pA1, pBasTraverseCornier, conf.get("demiLargeurDroitChampTraverseCorniere") + margeEntreTraverseEtPremiereCote + numCurCoteDroite * (curUnderLineGap + taillePoliceCote));
		
			Point pA2 = new Point(pA1);
			pA2.move(0, - conf.get("entreAxePercageMontantTraverseCorniere"));
			g.drawDistanceCote(pA2, pA1, conf.get("demiLargeurDroitChampTraverseCorniere") + margeEntreTraverseEtPremiereCote + numCurCoteDroite * (curUnderLineGap + taillePoliceCote));
		
			numCurCoteDroite++;
			
			Point pB1 = new Point(pBasTraverseCornier);
			pB1.move(0, - conf.get("ecartEntreExtremiteEtPremierPercageMontantTraverseCorniere") + conf.get("entreAxePercageMontantTraverseCorniere") / 2);
			g.drawDistanceCote(pB1, pBasTraverseCornier, conf.get("demiLargeurDroitChampTraverseCorniere") + margeEntreTraverseEtPremiereCote + numCurCoteDroite * (curUnderLineGap + taillePoliceCote));
		
			Point pB2 = new Point(pB1);
			pB2.move(0, - conf.get("entreAxePercageMontantTraverseCorniere"));
			g.drawDistanceCote(pB2, pB1, conf.get("demiLargeurDroitChampTraverseCorniere") + margeEntreTraverseEtPremiereCote + numCurCoteDroite * (curUnderLineGap + taillePoliceCote));
		
			numCurCoteDroite++;
			
			Point pC1 = new Point(pBasTraverseCornier);
			pC1.move(0, - conf.get("ecartEntreExtremiteEtPremierPercageMontantTraverseCorniere") + conf.get("entreAxePercageMontantEtParcloseTraverseCorniere"));
			g.drawDistanceCote(pC1, pBasTraverseCornier, conf.get("demiLargeurDroitChampTraverseCorniere") + margeEntreTraverseEtPremiereCote + numCurCoteDroite * (curUnderLineGap + taillePoliceCote));
		
			Point pC2 = new Point(pC1);
			pC2.move(0, - conf.get("entreAxePercageMontantTraverseCorniere"));
			g.drawDistanceCote(pC2, pC1, conf.get("demiLargeurDroitChampTraverseCorniere") + margeEntreTraverseEtPremiereCote + numCurCoteDroite * (curUnderLineGap + taillePoliceCote));
 		}
 		
 		g.drawDistanceCote(pBasTraverseCornier, pHautTraverseCornier, conf.get("demiLargeurGaucheChampTraverseCorniere") + margeEntreTraverseEtPremiereCote + numCurCoteGauche * (curUnderLineGap + taillePoliceCote));
	
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

	@Override
	protected MyCustomSvg getEntete() {
		ArrayList<String> lignes = new ArrayList<String>();
		lignes.add("TRAVERSE CORNIERE 30x30x3");
		lignes.add("QTE = " + this.nbTraverses);
		return new LignesTexte(lignes, 15, ShiftMode.CENTER);
	}

	@Override
	protected MyCustomSvg getPiedDePage() {
		ArrayList<String> lignes = new ArrayList<String>();
		lignes.add("ARC : " + this.data.get("ARC"));
		lignes.add("Client : " + this.data.get("client"));
		lignes.add("Ref : " + this.data.get("reference"));
		lignes.add("Dimensions vitrage : " + conf.get("largeurVitrage") + " x " + conf.get("hauteurVitrage"));
		return new LignesTexte(lignes);
	}
}

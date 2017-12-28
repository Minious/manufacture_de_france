package generateurCoteVerriere.modeles.premium.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.HashMap;

import generateurCoteVerriere.ElementGenerique;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced;
import myCustomSvgLibraryEnhanced.Point;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced.ShiftMode;

public class TraverseCorniere extends ElementGenerique {
	// Initialisation des paramètres
	private String nomFichierDeRendu = "traverse_corniere";

	// Parametres de la police
	private final int taillePoliceTitre = 12;
	private final int taillePoliceCote = 10;
	private final int curUnderLineGap = 2;

	// Titre traverse Corniere
	private final double margeEntreLignesTitre = 10;
	private final double ordonneePremiereLigneTitre = this.margeEntreLignesTitre + this.taillePoliceCote;
	private final double ordonneeDeuxiemeLigneTitre = this.ordonneePremiereLigneTitre + this.margeEntreLignesTitre + this.taillePoliceCote;
	private final double margeEntreTitreEtDessin = 10;
	private final int nbTraverses = 2;

	// General
	private final double ordonneeHautDessinTraverse = this.ordonneeDeuxiemeLigneTitre + this.margeEntreTitreEtDessin;

	private final double margeInterCote = 0; // 10;
	private final double margeEntreTraverseEtPremiereCote = 10; // <--- Empirique
	
	private final double ordonneeHautTraverse = this.ordonneeHautDessinTraverse + 2 * (this.taillePoliceCote + this.curUnderLineGap + this.margeEntreTraverseEtPremiereCote);
	private final double ordonneeBasTraverse = this.ordonneeHautTraverse + conf.get("longueurTraverseCorniere");

	private final double diametrePercages = conf.get("largeurChampTraverseCorniere") / 8; // INCORRECT
	private final String valeurDiametrePercagesMontant = "Ø7";
	private final String valeurDiametrePercagesParclose = "ØM5";
	private final String valeurDiametrePercagesFixation = "Ø5.5";

	private final double nbCotesAGauche = conf.get("nbPartitions") * 4;
	private final double distanceEntreCentreTraverseEtExtremiteGaucheDessin = conf.get("demiLargeurGaucheChampTraverseCorniere") + this.margeEntreTraverseEtPremiereCote + (this.curUnderLineGap + this.taillePoliceCote + this.margeInterCote) * this.nbCotesAGauche;
	private final double nbCotesADroite = 3;
	private final double distanceEntreCentreTraverseEtExtremiteDroiteDessin = conf.get("demiLargeurDroitChampTraverseCorniere") + this.margeEntreTraverseEtPremiereCote + (this.curUnderLineGap + this.taillePoliceCote + this.margeInterCote) * this.nbCotesADroite;
	
	private final double margeLateraleDessin = 50;
	private final double margeBasDessin = 100;
	
	private final double abscisseAxeTraverse = this.margeLateraleDessin + this.distanceEntreCentreTraverseEtExtremiteGaucheDessin;
	
	private final double largeurImage = this.abscisseAxeTraverse + this.distanceEntreCentreTraverseEtExtremiteDroiteDessin + this.margeLateraleDessin;
	private final double hauteurImage = this.ordonneeBasTraverse + this.margeBasDessin;
	
	public TraverseCorniere(HashMap<String, Double> conf){
		super(conf);
	}
	
	protected void drawImage(MyCustomSvgEnhanced g){
		// Défini la police de caractères par défaut
		g.setFont(this.taillePoliceCote, "Arial");
		g.setUnderLineGap(this.curUnderLineGap);

		this.draw(g);
	}
	
	private void draw(MyCustomSvgEnhanced g){		
		// Affiche les titres du dessin
		g.setColor(Color.BLACK);
		g.setFontSize(this.taillePoliceTitre);
		String titre = "TRAVERSE CORNIERE 30x30x3";
		g.drawString(titre, new Point((double) g.getWidth() / 2, this.ordonneePremiereLigneTitre), 0, ShiftMode.CENTER);
		g.drawString("QTE = " + this.nbTraverses, new Point((double) g.getWidth() / 2, this.ordonneeDeuxiemeLigneTitre), 0, ShiftMode.CENTER);
		
		// Trace le traverse 
		g.setStrokeWidth(1);
		g.drawRect(this.abscisseAxeTraverse - conf.get("demiLargeurGaucheChampTraverseCorniere"), this.ordonneeHautTraverse, conf.get("largeurChampTraverseCorniere"), conf.get("longueurTraverseCorniere"));

		// Trace l'onglet
		g.setStrokeWidth(1);
		g.drawLine(this.abscisseAxeTraverse + conf.get("demiLargeurDroitChampTraverseCorniere") - conf.get("epaisseurTraverseCorniere"), this.ordonneeHautTraverse, this.abscisseAxeTraverse + conf.get("demiLargeurDroitChampTraverseCorniere") - conf.get("epaisseurTraverseCorniere"), this.ordonneeBasTraverse);

		// Trace l'axe du milieu du traverse 
		g.setStroke(new BasicStroke(0.5f));
		g.setDashArray(new float[] {10, 2, 2, 2});
		g.drawLine(this.abscisseAxeTraverse, this.ordonneeHautTraverse, this.abscisseAxeTraverse, this.ordonneeBasTraverse);
		g.removeDashArray();
		
		// Cote de largeur puis demi largeur de la Corniere avant
 		g.setFontSize(this.taillePoliceCote);
 		Point p1_1 = new Point(this.abscisseAxeTraverse - conf.get("demiLargeurGaucheChampTraverseCorniere"), this.ordonneeHautTraverse);
 		Point p1_2 = new Point(this.abscisseAxeTraverse + conf.get("demiLargeurDroitChampTraverseCorniere"), this.ordonneeHautTraverse);
 		g.drawDistanceCote(p1_1, p1_2, margeEntreTraverseEtPremiereCote + curUnderLineGap + taillePoliceCote, 0, ShiftMode.CENTER);
 		
 		Point p2_1 = p1_1;
 		Point p2_2 = new Point(this.abscisseAxeTraverse, this.ordonneeHautTraverse);
 		g.drawDistanceCote(p2_1, p2_2, margeEntreTraverseEtPremiereCote, - conf.get("demiLargeurGaucheChampTraverseCorniere") / 2 - 4, ShiftMode.RIGHT);
	 	
 		// Cotes gauches
 		int numCurCoteGauche = 0;
 		Point pBasTraverseCornier = new Point(this.abscisseAxeTraverse, this.ordonneeBasTraverse);
 		Point pHautTraverseCornier = new Point(this.abscisseAxeTraverse, this.ordonneeHautTraverse);
 		Point pCurPercageMontant = new Point(this.abscisseAxeTraverse, this.ordonneeBasTraverse - conf.get("ecartEntreExtremiteEtPremierPercageMontantTraverseCorniere"));
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
 		
 		System.out.println(this.nbCotesAGauche);
 		
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
		numCurCoteGauche++;
		
		g.drawDistanceCote(pBasTraverseCornier, pHautTraverseCornier, conf.get("demiLargeurGaucheChampTraverseCorniere") + margeEntreTraverseEtPremiereCote + numCurCoteGauche * (curUnderLineGap + taillePoliceCote));
		
		// Cotes droites
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

	@Override
	protected int getLargeurImage() {
		return (int) this.largeurImage;
	}

	@Override
	protected int getHauteurImage() {
		return (int) this.hauteurImage;
	}

	@Override
	public String getNomFichierDeRendu() {
		return this.nomFichierDeRendu;
	}
}

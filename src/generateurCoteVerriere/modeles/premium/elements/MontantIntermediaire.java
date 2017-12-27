package generateurCoteVerriere.modeles.premium.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Path2D;
import java.util.HashMap;

import generateurCoteVerriere.ElementGenerique;
import myCustomSvgLibrary.MyPath;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced;
import myCustomSvgLibraryEnhanced.Point;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced.ShiftMode;

public class MontantIntermediaire extends ElementGenerique {
	// Initialisation des paramètres
	private String nomFichierDeRendu = "montant_intermediaire";

	// Parametres de la police
	private final int taillePoliceTitre = 25;
	private final int taillePoliceCote = 20;
	private final int curUnderLineGap = 5;

	// Titre montant Partition
	private final double margeEntreLignesTitre = 10;
	private final double ordonneePremiereLigneTitre = this.margeEntreLignesTitre + this.taillePoliceTitre;
	private final double ordonneeDeuxiemeLigneTitre = this.ordonneePremiereLigneTitre + this.margeEntreLignesTitre + this.taillePoliceTitre;
	private final double margeEntreTitreEtDessin = 10;
	private final int nbMontants = (int) (conf.get("nbPartitions") - 1);

	// General
	private final double ordonneeHautDessinMontant = this.ordonneeDeuxiemeLigneTitre + this.margeEntreTitreEtDessin;

	private final double margeInterCote = 0; // 10;
	private final double margeEntreMontantEtPremiereCote = conf.get("largeurChampMontantIntermediaire") / 2; // <--- Empirique
	
	private final double ordonneeHautMontant = this.ordonneeHautDessinMontant + 2 * (this.taillePoliceCote + this.curUnderLineGap + this.margeEntreMontantEtPremiereCote);
	private final double ordonneeBasMontant = this.ordonneeHautMontant + conf.get("longueurMontantIntermediaire");

	private final double diametrePercages = conf.get("largeurChampMontantIntermediaire") / 4; // INCORRECT
	private final String valeurDiametrePercages = "ØM5";

	private final double nbCotesAGauche = conf.get("nbPercageMontant") + 1;
	private final double distanceEntreCentreMontantEtExtremiteGaucheDessin = conf.get("demiLargeurGaucheChampMontantIntermediaire") + this.margeEntreMontantEtPremiereCote + (this.curUnderLineGap + this.taillePoliceCote + this.margeInterCote) * this.nbCotesAGauche;
	private final double distanceEntreCentreMontantEtExtremiteDroiteDessin = conf.get("demiLargeurDroitChampMontantIntermediaire") + this.margeEntreMontantEtPremiereCote + this.curUnderLineGap + this.taillePoliceCote;
	
	private final double margeLateraleDessin = 80;
	private final double margeBasDessin = 100;
	
	private final double abscisseAxeMontant = this.margeLateraleDessin + this.distanceEntreCentreMontantEtExtremiteGaucheDessin;
	
	private final double largeurImage = this.abscisseAxeMontant + this.distanceEntreCentreMontantEtExtremiteDroiteDessin + this.margeLateraleDessin;
	private final double hauteurImage = this.ordonneeBasMontant + this.margeBasDessin;
	
	public MontantIntermediaire(HashMap<String, Double> conf){
		super(conf);
	}

	// protected void drawImage(SVGGraphics2D g){
	protected void drawImage(MyCustomSvgEnhanced g){
		g.setUnderLineGap(this.curUnderLineGap);
		
		// Affiche les titres du dessin
		g.setColor(Color.BLACK);
		g.setFontSize(this.taillePoliceTitre);
		String titre = "MONTANT INTERMEDIAIRE 30x30x4";
		g.drawString(titre, new Point((double) g.getWidth() / 2, this.ordonneePremiereLigneTitre), 0, ShiftMode.CENTER);
		g.drawString("QTE = " + this.nbMontants, new Point((double) g.getWidth() / 2, this.ordonneeDeuxiemeLigneTitre), 0, ShiftMode.CENTER);
		
		// Trace le montant 
	    g.setStroke(new BasicStroke(2));
	    Path2D path = new Path2D.Double();
	    path.moveTo(this.abscisseAxeMontant + conf.get("demiLargeurDroitChampMontantIntermediaire"), this.ordonneeHautMontant);
	    path.lineTo(this.abscisseAxeMontant + conf.get("demiLargeurDroitChampMontantIntermediaire"), this.ordonneeBasMontant);
	    path.lineTo(this.abscisseAxeMontant - conf.get("demiLargeurGaucheChampMontantIntermediaire") + conf.get("epaisseurEpaulementMontantIntermediaire"), this.ordonneeBasMontant);
	    path.lineTo(this.abscisseAxeMontant - conf.get("demiLargeurGaucheChampMontantIntermediaire") + conf.get("epaisseurEpaulementMontantIntermediaire"), this.ordonneeBasMontant - conf.get("longueurEpaulementMontantIntermediaire"));
	    path.lineTo(this.abscisseAxeMontant - conf.get("demiLargeurGaucheChampMontantIntermediaire"), this.ordonneeBasMontant - conf.get("longueurEpaulementMontantIntermediaire"));
	    path.lineTo(this.abscisseAxeMontant - conf.get("demiLargeurGaucheChampMontantIntermediaire"), this.ordonneeHautMontant + conf.get("longueurEpaulementMontantIntermediaire"));
	    path.lineTo(this.abscisseAxeMontant - conf.get("demiLargeurGaucheChampMontantIntermediaire") + conf.get("epaisseurEpaulementMontantIntermediaire"), this.ordonneeHautMontant + conf.get("longueurEpaulementMontantIntermediaire"));
	    path.lineTo(this.abscisseAxeMontant - conf.get("demiLargeurGaucheChampMontantIntermediaire") + conf.get("epaisseurEpaulementMontantIntermediaire"), this.ordonneeHautMontant);
	    path.closePath();
	    g.drawPath(path);
	      
	    // Trace l'axe du milieu du montant 
	    g.setStroke(new BasicStroke(1));
	    g.drawLine(this.abscisseAxeMontant, this.ordonneeHautMontant, this.abscisseAxeMontant, this.ordonneeBasMontant);
	    /*
	    // Cote de largeur puis demi largeur de la Corniere avant
		Point p1_1 = new Point(this.abscisseAxeMontant - conf.get("demiLargeurGaucheChampMontantCorniere"), this.ordonneeHautMontant);
		Point p1_2 = new Point(this.abscisseAxeMontant + conf.get("demiLargeurDroitChampMontantCorniere"), this.ordonneeHautMontant);
		g.drawDistanceCote(p1_1, p1_2, margeEntreMontantEtPremiereCote + curUnderLineGap + taillePoliceCote, 0, ShiftMode.CENTER);
		
		Point p2_1 = p1_1;
		Point p2_2 = new Point(this.abscisseAxeMontant, this.ordonneeHautMontant);
		g.drawDistanceCote(p2_1, p2_2, margeEntreMontantEtPremiereCote, - conf.get("demiLargeurGaucheChampMontantCorniere") / 2 - 10, ShiftMode.RIGHT);
		
		// Cote entre les trous et l'extrémité de la Corniere + affichage des trous
		double curDistanceCotesLaterales = conf.get("demiLargeurGaucheChampMontantCorniere") / 2 + this.margeEntreMontantEtPremiereCote;
		
		Point p3_1 = new Point(this.abscisseAxeMontant, this.ordonneeBasMontant);
		Point pTemp = new Point(this.abscisseAxeMontant, this.ordonneeBasMontant - conf.get("ecartEntreExtremiteEtPremierPercageMontantCorniere"));
		Point p3_2 = new Point(this.abscisseAxeMontant, this.ordonneeBasMontant - conf.get("ecartEntreExtremiteEtPremierPercageMontantCorniere") - conf.get("entreAxePercagesMontant"));
		
		g.drawDistanceCote(p3_2, pTemp, conf.get("demiLargeurDroitChampMontantCorniere") + this.margeEntreMontantEtPremiereCote);
		
		for(int i=0;i<conf.get("nbPercageMontant");i++){
			g.drawDistanceCote(p3_1, pTemp, curDistanceCotesLaterales);
			curDistanceCotesLaterales = decalerCote(curDistanceCotesLaterales);
			g.drawCircle(pTemp, this.diametrePercage);
			if(i != conf.get("nbPercageMontant")-1)
				pTemp.move(0, - conf.get("entreAxePercagesMontant"));
		}
		
		g.drawDiameterCote(this.valeurDiametrePercage, pTemp, - Math.PI / 4, 40, ShiftMode.LEFT, 5);
		
		// Cote longueur totale du montant
		g.drawDistanceCote(p3_1, p2_2, curDistanceCotesLaterales);

	    
	    // Trave l'onglet
		Point p4_1 = new Point(this.abscisseAxeMontant, this.ordonneeBasMontant);
		Point p4_2 = new Point(this.abscisseAxeMontant, this.ordonneeBasMontant);
	    g.drawLine(this.abscisseAxeMontant + conf.get("demiLargeurDroitChampMontantCorniere") - conf.get("epaisseurMontantCorniere"), this.ordonneeHautMontant, this.abscisseAxeMontant + conf.get("demiLargeurDroitChampMontantCorniere") - conf.get("epaisseurMontantCorniere"), this.ordonneeBasMontant);
	
		/* OLD
	    // Trace l'axe du milieu du montant 
	    g.setStroke(new BasicStroke(1));
	    g.drawLine(this.abscisseAxeMontant, this.ordonneeHautMontant, this.abscisseAxeMontant, this.ordonneeBasMontant);

		g.setFontSize(this.taillePoliceCote);
		
	    // Cote de largeur puis demi largeur de la Partition avant
		Point p1_1 = new Point(this.abscisseAxeMontant - conf.get("largeurMontantPartition") / 2, this.ordonneeHautMontant);
		Point p1_2 = new Point(this.abscisseAxeMontant + conf.get("largeurMontantPartition") / 2, this.ordonneeHautMontant);
		g.drawDistanceCote(p1_1, p1_2, margeEntreMontantEtPremiereCote + curUnderLineGap + taillePoliceCote, 0, ShiftMode.CENTER);
		
		Point p2_1 = p1_1;
		Point p2_2 = new Point(this.abscisseAxeMontant, this.ordonneeHautMontant);
		g.drawDistanceCote(p2_1, p2_2, margeEntreMontantEtPremiereCote, - conf.get("largeurMontantPartition") / 4 - 10, ShiftMode.RIGHT);
		
		// Cote entre les trous et l'extrémité de la Partition + affichage des trous
	    Point p3_1 = new Point(this.abscisseAxeMontant, this.ordonneeBasMontant);
	    Point p3_2 = new Point(this.abscisseAxeMontant, this.ordonneeBasMontant - conf.get("ecartEntreExtremiteEtPremierTrouMontantPartition"));
		g.drawDistanceCote(p3_1, p3_2, conf.get("largeurMontantPartition") / 2 + this.margeEntreMontantEtPremiereCote, 0, ShiftMode.LEFT);
		g.drawCircle(p3_2, this.diametreTrous);
		
		Point pTemp = new Point(this.abscisseAxeMontant, this.ordonneeBasMontant - conf.get("ecartEntreExtremiteEtPremierTrouMontantPartition") - conf.get("entreAxeMontant"));
		double curDistanceCotesLaterales = conf.get("largeurMontantPartition") / 2 + this.margeEntreMontantEtPremiereCote;
		g.drawDistanceCote(pTemp, p3_2, curDistanceCotesLaterales);
		curDistanceCotesLaterales = decalerCote(curDistanceCotesLaterales);
		
		for(int i = 1 ; i <= conf.get("nbTrousIntermediairesVerticaux") + 1 ; i++){
			g.drawDistanceCote(p3_1, pTemp, curDistanceCotesLaterales);
			curDistanceCotesLaterales = decalerCote(curDistanceCotesLaterales);
			g.drawCircle(pTemp, this.diametreTrous);
			if(i != conf.get("nbTrousIntermediairesVerticaux") + 1)
				pTemp.move(0, - conf.get("entreAxeMontant"));
		}
		g.drawDiameterCote(this.valeurDiametreTrous, pTemp, - Math.PI / 4, 40, ShiftMode.LEFT, 5);

		// Cote longueur totale du montant
		g.drawDistanceCote(p3_1, p2_2, curDistanceCotesLaterales);
		*/
	}
	
	private double decalerCote(double curDistance){
		return curDistance + this.curUnderLineGap + this.taillePoliceCote + this.margeInterCote;
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

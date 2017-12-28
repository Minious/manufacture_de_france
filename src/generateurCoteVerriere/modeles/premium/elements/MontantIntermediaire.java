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
	private final int taillePoliceTitre = 12;
	private final int taillePoliceCote = 10;
	private final int curUnderLineGap = 2;

	// Titre montant Partition
	private final double margeEntreLignesTitre = 4;
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

	private final double diametrePercages = conf.get("largeurChampMontantIntermediaire") / 6; // INCORRECT
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
		String titre = "MONTANT INTERMEDIAIRE T DE 30x30x4";
		g.drawString(titre, new Point((double) g.getWidth() / 2, this.ordonneePremiereLigneTitre), 0, ShiftMode.CENTER);
		g.drawString("QTE = " + this.nbMontants, new Point((double) g.getWidth() / 2, this.ordonneeDeuxiemeLigneTitre), 0, ShiftMode.CENTER);
		
		// Trace le montant 
		g.setFontSize(this.taillePoliceCote);
	    g.setStrokeWidth(1);
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
	    g.setStrokeWidth(0.5f);
	    g.setDashArray(new float[] {10, 2, 2, 2});
	    g.drawLine(this.abscisseAxeMontant, this.ordonneeHautMontant, this.abscisseAxeMontant, this.ordonneeBasMontant);
	    g.removeDashArray();
	    
	    // Trace la face du montant
	    g.setStrokeWidth(1);
	    g.drawLine(this.abscisseAxeMontant - conf.get("demiLargeurGaucheChampMontantIntermediaire") + conf.get("epaisseurEpaulementMontantIntermediaire"), this.ordonneeHautMontant + conf.get("longueurEpaulementMontantIntermediaire"), this.abscisseAxeMontant - conf.get("demiLargeurGaucheChampMontantIntermediaire") + conf.get("epaisseurEpaulementMontantIntermediaire"), this.ordonneeBasMontant - conf.get("longueurEpaulementMontantIntermediaire"));
	    
	    // Cote de largeur puis demi largeur de la Corniere avant
	    g.setStrokeWidth(0.5f);
		Point p1_1 = new Point(this.abscisseAxeMontant - conf.get("demiLargeurGaucheChampMontantIntermediaire"), this.ordonneeHautMontant + conf.get("longueurEpaulementMontantIntermediaire"));
		Point p1_2 = new Point(this.abscisseAxeMontant + conf.get("demiLargeurDroitChampMontantIntermediaire"), this.ordonneeHautMontant + conf.get("longueurEpaulementMontantIntermediaire"));
		g.drawDistanceCote(p1_1, p1_2, conf.get("longueurEpaulementMontantIntermediaire") + margeEntreMontantEtPremiereCote + curUnderLineGap + taillePoliceCote, 0, ShiftMode.CENTER);
		
		Point p2_1 = new Point(this.abscisseAxeMontant - conf.get("demiLargeurGaucheChampMontantIntermediaire"), this.ordonneeHautMontant);
		Point p2_2 = new Point(this.abscisseAxeMontant, this.ordonneeHautMontant);
		g.drawDistanceCote(p2_1, p2_2, margeEntreMontantEtPremiereCote, - conf.get("demiLargeurGaucheChampMontantIntermediaire") / 2 - 4, ShiftMode.RIGHT);
		
		// Cote entre les trous et l'extrémité de la Corniere + affichage des trous
		double curDistanceCotesLaterales = conf.get("demiLargeurGaucheChampMontantIntermediaire") / 2 + this.margeEntreMontantEtPremiereCote;
		
		Point p3_1 = new Point(this.abscisseAxeMontant, this.ordonneeBasMontant);
		Point pTemp = new Point(this.abscisseAxeMontant, this.ordonneeBasMontant - conf.get("ecartEntreExtremiteEtPremierPercageMontantIntermediaire"));
		Point p3_2 = new Point(this.abscisseAxeMontant, this.ordonneeBasMontant - conf.get("ecartEntreExtremiteEtPremierPercageMontantIntermediaire") - conf.get("entreAxePercagesMontant"));
		
		g.drawDistanceCote(p3_2, pTemp, conf.get("demiLargeurDroitChampMontantCorniere") + this.margeEntreMontantEtPremiereCote);
		
		for(int i=0;i<conf.get("nbPercageMontant");i++){
			g.drawDistanceCote(p3_1, pTemp, curDistanceCotesLaterales);
			curDistanceCotesLaterales = decalerCote(curDistanceCotesLaterales);
			g.drawCircle(pTemp, this.diametrePercages);
			if(i != conf.get("nbPercageMontant")-1)
				pTemp.move(0, - conf.get("entreAxePercagesMontant"));
		}
		
		g.drawDiameterCote(this.valeurDiametrePercages, pTemp, - Math.PI / 4, 40, ShiftMode.LEFT, 5);
		
		// Cote longueur totale du montant
		g.drawDistanceCote(p3_1, p2_2, curDistanceCotesLaterales);
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

	@Override
	protected int getNbElements() {
		return this.nbMontants;
	}
}

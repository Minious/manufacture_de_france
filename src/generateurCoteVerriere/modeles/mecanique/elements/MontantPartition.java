package generateurCoteVerriere.modeles.mecanique.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.util.HashMap;

import generateurCoteVerriere.ElementGenerique;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced;
import myCustomSvgLibraryEnhanced.Point;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced.ShiftMode;

public class MontantPartition extends ElementGenerique {
	// Initialisation des paramètres
	private String nomFichierDeRendu = "montant_partition";

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
	private final double margeEntreMontantEtPremiereCote = conf.get("largeurMontantPartition") / 2; // <--- Empirique
	
	private final double ordonneeHautMontant = this.ordonneeHautDessinMontant + 2 * (this.taillePoliceCote + this.curUnderLineGap + this.margeEntreMontantEtPremiereCote);
	private final double ordonneeBasMontant = this.ordonneeHautMontant + conf.get("hauteurMontantPartition");

	private final double diametreTrous = conf.get("largeurMontantPartition") / 4; // INCORRECT
	private final String valeurDiametreTrous = "ØM5";

	private final double nbCotesAGauche = conf.get("nbTrousIntermediairesVerticaux") + 3;
	private final double distanceEntreCentreMontantEtExtremiteGaucheDessin = conf.get("largeurMontantPartition") / 2 + this.margeEntreMontantEtPremiereCote + (this.curUnderLineGap + this.taillePoliceCote + this.margeInterCote) * this.nbCotesAGauche;
	private final double distanceEntreCentreMontantEtExtremiteDroiteDessin = conf.get("largeurMontantPartition") / 2 + this.margeEntreMontantEtPremiereCote + this.curUnderLineGap + this.taillePoliceCote;
	
	private final double margeLateraleDessin = 80;
	private final double margeBasDessin = 100;
	
	private final double abscisseAxeMontant = this.margeLateraleDessin + this.distanceEntreCentreMontantEtExtremiteGaucheDessin;
	
	private final double largeurImage = this.abscisseAxeMontant + this.distanceEntreCentreMontantEtExtremiteDroiteDessin + this.margeLateraleDessin;
	private final double hauteurImage = this.ordonneeBasMontant + this.margeBasDessin;
	
	public MontantPartition(HashMap<String, Double> conf){
		super(conf);
	}

	// protected void drawImage(SVGGraphics2D g){
	protected void drawImage(MyCustomSvgEnhanced g){
		g.setUnderLineGap(this.curUnderLineGap);
		
		// Affiche les titres du dessin
		g.setColor(Color.BLACK);
		g.setFontSize(this.taillePoliceTitre);
		String titre = "PARTITION PLAT 30x3";
		g.drawString(titre, new Point((double) g.getWidth() / 2, this.ordonneePremiereLigneTitre), 0, ShiftMode.CENTER);
		g.drawString("QTE = " + this.nbMontants, new Point((double) g.getWidth() / 2, this.ordonneeDeuxiemeLigneTitre), 0, ShiftMode.CENTER);
		
		// Trace le montant 
	    g.setStroke(new BasicStroke(2));
	    g.drawRect(this.abscisseAxeMontant - conf.get("largeurMontantPartition") / 2, this.ordonneeHautMontant, conf.get("largeurMontantPartition"), conf.get("hauteurMontantPartition"));
	    
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

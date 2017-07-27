package generateurCoteVerriere.modeles.mecanique.elements;

import java.awt.BasicStroke;
import java.awt.Color;

import generateurCoteVerriere.ElementGenerique;
import generateurCoteVerriere.modeles.mecanique.Conf;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced;
import myCustomSvgLibraryEnhanced.Point;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced.ShiftMode;

public class ContreCadreMontantPartition extends ElementGenerique {
	// Initialisation des paramètres
	private String nomFichierDeRendu = "contre_cadre_montant_partition";

	// Parametres de la police
	private final int taillePoliceCote = 25;
	private final int curUnderLineGap = 5;

	// Titre montant Partition
	private final double margeEntreLignesTitre = 10;
	private final double ordonneePremiereLigneTitre = this.margeEntreLignesTitre + this.taillePoliceCote;
	private final double ordonneeDeuxiemeLigneTitre = this.ordonneePremiereLigneTitre + this.margeEntreLignesTitre + this.taillePoliceCote;
	private final double margeEntreTitreEtDessin = 10;
	private final int nbMontants = conf.nbPartitions - 1;

	// General
	private final double ordonneeHautDessinMontant = this.ordonneeDeuxiemeLigneTitre + this.margeEntreTitreEtDessin;

	private final double margeInterCote = 0; // 10;
	private final double margeEntreMontantEtPremiereCote = conf.largeurMontantPartition / 2; // <--- Empirique
	
	private final double ordonneeHautMontant = this.ordonneeHautDessinMontant + 2 * (this.taillePoliceCote + this.curUnderLineGap + this.margeEntreMontantEtPremiereCote);
	private final double ordonneeBasMontant = this.ordonneeHautMontant + conf.hauteurMontantPartition;

	private final double diametreTrous = conf.largeurMontantPartition / 4; // INCORRECT
	private final String valeurDiametreTrous = "Ø7";

	private final double nbCotesAGauche = this.conf.nbTrousIntermediairesVerticaux + 3;
	private final double distanceEntreCentreMontantEtExtremiteGaucheDessin = conf.largeurMontantPartition / 2 + this.margeEntreMontantEtPremiereCote + (this.curUnderLineGap + this.taillePoliceCote + this.margeInterCote) * this.nbCotesAGauche;
	private final double distanceEntreCentreMontantEtExtremiteDroiteDessin = conf.largeurMontantPartition / 2 + this.margeEntreMontantEtPremiereCote + this.curUnderLineGap + this.taillePoliceCote;
	
	private final double margeLateraleDessin = 150;
	private final double margeBasDessin = 100;
	
	private final double abscisseAxeMontant = this.margeLateraleDessin + this.distanceEntreCentreMontantEtExtremiteGaucheDessin;
	
	private final double largeurImage = this.abscisseAxeMontant + this.distanceEntreCentreMontantEtExtremiteDroiteDessin + this.margeLateraleDessin;
	private final double hauteurImage = this.ordonneeBasMontant + this.margeBasDessin;
	
	public ContreCadreMontantPartition(Conf conf){
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
		String titre = "CONTRE CADRE PARTITION PLAT 30x3";
		g.drawString(titre, new Point((double) g.getWidth() / 2, this.ordonneePremiereLigneTitre), 0, ShiftMode.CENTER);
		g.drawString("QTE = " + this.nbMontants, new Point((double) g.getWidth() / 2, this.ordonneeDeuxiemeLigneTitre), 0, ShiftMode.CENTER);

		// Trace le montant 
	    g.setStroke(new BasicStroke(2));
	    g.drawRect(this.abscisseAxeMontant - conf.largeurMontantPartition / 2, this.ordonneeHautMontant, conf.largeurMontantPartition, conf.hauteurMontantPartition);
	    
	    // Trace l'axe du milieu du montant 
	    g.setStroke(new BasicStroke(1));
	    g.drawLine(this.abscisseAxeMontant, this.ordonneeHautMontant, this.abscisseAxeMontant, this.ordonneeBasMontant);
	    
	    // Cote de largeur puis demi largeur de la Partition avant
		Point p1_1 = new Point(this.abscisseAxeMontant - conf.largeurMontantPartition / 2, this.ordonneeHautMontant);
		Point p1_2 = new Point(this.abscisseAxeMontant + conf.largeurMontantPartition / 2, this.ordonneeHautMontant);
		g.drawDistanceCote(p1_1, p1_2, margeEntreMontantEtPremiereCote + curUnderLineGap + taillePoliceCote, 0, ShiftMode.CENTER);
		
		Point p2_1 = p1_1;
		Point p2_2 = new Point(this.abscisseAxeMontant, this.ordonneeHautMontant);
		g.drawDistanceCote(p2_1, p2_2, margeEntreMontantEtPremiereCote, - conf.largeurMontantPartition / 4 - 10, ShiftMode.RIGHT);
		
		// Cote entre les trous et l'extrémité de la Partition + affichage des trous
	    Point p3_1 = new Point(this.abscisseAxeMontant, this.ordonneeBasMontant);
	    Point p3_2 = new Point(this.abscisseAxeMontant, this.ordonneeBasMontant - conf.ecartEntreExtremiteEtPremierTrouMontantPartition);
		g.drawDistanceCote(p3_1, p3_2, conf.largeurMontantPartition / 2 + this.margeEntreMontantEtPremiereCote, 0, ShiftMode.LEFT);
		g.drawCircle(p3_2, this.diametreTrous);
		
		Point pTemp = new Point(this.abscisseAxeMontant, this.ordonneeBasMontant - conf.ecartEntreExtremiteEtPremierTrouMontantPartition - conf.entreAxeMontant);
		double curDistanceCotesLaterales = conf.largeurMontantPartition / 2 + this.margeEntreMontantEtPremiereCote;
		g.drawDistanceCote(pTemp, p3_2, curDistanceCotesLaterales);
		curDistanceCotesLaterales = decalerCote(curDistanceCotesLaterales);
		
		for(int i = 1 ; i <= this.conf.nbTrousIntermediairesVerticaux + 1 ; i++){
			g.drawDistanceCote(p3_1, pTemp, curDistanceCotesLaterales);
			curDistanceCotesLaterales = decalerCote(curDistanceCotesLaterales);
			g.drawCircle(pTemp, this.diametreTrous);
			if(i != this.conf.nbTrousIntermediairesVerticaux + 1)
				pTemp.move(0, - conf.entreAxeMontant);
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
}

package generateurCoteVerriere.modeles.premium.elements;

import java.awt.BasicStroke;
import java.awt.Color;

import generateurCoteVerriere.ElementGenerique;
import generateurCoteVerriere.modeles.premium.Conf;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced;
import myCustomSvgLibraryEnhanced.Point;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced.ShiftMode;

public class MontantCorniere extends ElementGenerique {
	// Initialisation des paramètres
	private String nomFichierDeRendu = "montant_corniere";

	// Parametres de la police
	private final int taillePoliceCote = 25;
	private final int curUnderLineGap = 5;

	// Titre montant Corniere
	private final double margeEntreLignesTitre = 10;
	private final double ordonneePremiereLigneTitre = this.margeEntreLignesTitre + this.taillePoliceCote;
	private final double ordonneeDeuxiemeLigneTitre = this.ordonneePremiereLigneTitre + this.margeEntreLignesTitre + this.taillePoliceCote;
	private final double margeEntreTitreEtDessin = 10;
	private final int nbMontants = 2;

	// General
	private final double ordonneeHautDessinMontant = this.ordonneeDeuxiemeLigneTitre + this.margeEntreTitreEtDessin;

	private final double margeInterCote = 0; // 10;
	private final double margeEntreMontantEtPremiereCote = ((Conf) conf).largeurMontantCorniere / 2; // <--- Empirique
	
	private final double ordonneeHautMontant = this.ordonneeHautDessinMontant + 2 * (this.taillePoliceCote + this.curUnderLineGap + this.margeEntreMontantEtPremiereCote);
	private final double ordonneeBasMontant = this.ordonneeHautMontant + ((Conf) conf).hauteurMontantCorniere;

	private final double diametreTrous = ((Conf) conf).largeurMontantCorniere / 8; // INCORRECT
	private final String valeurDiametreTrous = "ØM5";

	private final double nbCotesAGauche = ((Conf) this.conf).nbTrousIntermediairesVerticaux + 5;
	private final double distanceEntreCentreMontantEtExtremiteGaucheDessin = ((Conf) conf).largeurMontantCorniere / 2 + this.margeEntreMontantEtPremiereCote + (this.curUnderLineGap + this.taillePoliceCote + this.margeInterCote) * this.nbCotesAGauche;
	private final double distanceEntreCentreMontantEtExtremiteDroiteDessin = ((Conf) conf).largeurMontantCorniere / 2 + this.margeEntreMontantEtPremiereCote + this.curUnderLineGap + this.taillePoliceCote;
	
	private final double margeLateraleDessin = 50;
	private final double margeBasDessin = 100;
	
	private final double abscisseAxeMontant = this.margeLateraleDessin + this.distanceEntreCentreMontantEtExtremiteGaucheDessin;
	
	private final double largeurImage = this.abscisseAxeMontant + this.distanceEntreCentreMontantEtExtremiteDroiteDessin + this.margeLateraleDessin;
	private final double hauteurImage = this.ordonneeBasMontant + this.margeBasDessin;
	
	public MontantCorniere(Conf conf){
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
		String titre = "MONTANT CORNIERE 30x30x3";
		g.drawString(titre, new Point((double) g.getWidth() / 2, this.ordonneePremiereLigneTitre), 0, ShiftMode.CENTER);
		g.drawString("QTE = " + this.nbMontants, new Point((double) g.getWidth() / 2, this.ordonneeDeuxiemeLigneTitre), 0, ShiftMode.CENTER);

		// Trace le montant 
	    g.setStroke(new BasicStroke(2));
	    g.drawLine(this.abscisseAxeMontant - ((Conf) conf).largeurMontantCorniere / 2, this.ordonneeHautMontant + ((Conf) conf).largeurMontantCorniere, this.abscisseAxeMontant - ((Conf) conf).largeurMontantCorniere / 2, this.ordonneeBasMontant - ((Conf) conf).largeurMontantCorniere);
	    g.drawLine(this.abscisseAxeMontant + ((Conf) conf).largeurMontantCorniere / 2, this.ordonneeHautMontant, this.abscisseAxeMontant + ((Conf) conf).largeurMontantCorniere / 2, this.ordonneeBasMontant);
	    g.drawLine(this.abscisseAxeMontant - ((Conf) conf).largeurMontantCorniere / 2, this.ordonneeHautMontant + ((Conf) conf).largeurMontantCorniere, this.abscisseAxeMontant + ((Conf) conf).largeurMontantCorniere / 2, this.ordonneeHautMontant);
	    g.drawLine(this.abscisseAxeMontant - ((Conf) conf).largeurMontantCorniere / 2, this.ordonneeBasMontant - ((Conf) conf).largeurMontantCorniere, this.abscisseAxeMontant + ((Conf) conf).largeurMontantCorniere / 2, this.ordonneeBasMontant);

	    // Trace l'axe du milieu du montant 
	    g.setStroke(new BasicStroke(1));
	    g.drawLine(this.abscisseAxeMontant, this.ordonneeHautMontant, this.abscisseAxeMontant, this.ordonneeBasMontant);
	    
	    // Cote de largeur puis demi largeur de la Corniere avant
		Point p1_1 = new Point(this.abscisseAxeMontant - ((Conf) conf).largeurMontantCorniere / 2, this.ordonneeHautMontant);
		Point p1_2 = new Point(this.abscisseAxeMontant + ((Conf) conf).largeurMontantCorniere / 2, this.ordonneeHautMontant);
		g.drawDistanceCote(p1_1, p1_2, margeEntreMontantEtPremiereCote + curUnderLineGap + taillePoliceCote, 0, ShiftMode.CENTER);
		
		Point p2_1 = p1_1;
		Point p2_2 = new Point(this.abscisseAxeMontant, this.ordonneeHautMontant);
		g.drawDistanceCote(p2_1, p2_2, margeEntreMontantEtPremiereCote, - ((Conf) conf).largeurMontantCorniere / 4 - 10, ShiftMode.RIGHT);
		
		// Cote entre les trous et l'extrémité de la Corniere + affichage des trous
		double curDistanceCotesLaterales = ((Conf) conf).largeurMontantPartition / 2 + this.margeEntreMontantEtPremiereCote;
		
		Point p3_1 = new Point(this.abscisseAxeMontant, this.ordonneeBasMontant);
	    Point p3_2 = new Point(this.abscisseAxeMontant, this.ordonneeBasMontant - ((Conf) conf).ecartEntreExtremiteEtPremierTrouMontantCorniere);
		g.drawDistanceCote(p3_1, p3_2, curDistanceCotesLaterales, 0, ShiftMode.LEFT);
		curDistanceCotesLaterales = decalerCote(curDistanceCotesLaterales);
		g.drawCircle(p3_2, this.diametreTrous);
		
		Point p4 = new Point(this.abscisseAxeMontant, this.ordonneeBasMontant - ((Conf) conf).ecartEntreExtremiteEtPremierTrouMontantCorniere - ((Conf) conf).ecartEntrePremierTrouEtDeuxiemeTrouMontantCorniere);
		g.drawDistanceCote(p3_1, p4, curDistanceCotesLaterales, 0, ShiftMode.LEFT);
		curDistanceCotesLaterales = decalerCote(curDistanceCotesLaterales);
		g.drawCircle(p4, this.diametreTrous);
		
		Point pTemp = new Point(this.abscisseAxeMontant, this.ordonneeBasMontant - ((Conf) conf).ecartEntreExtremiteEtPremierTrouMontantCorniere - ((Conf) conf).ecartEntrePremierTrouEtDeuxiemeTrouMontantCorniere - ((Conf) conf).entreAxeMontant);
		g.drawDistanceCote(pTemp, p4, ((Conf) conf).largeurMontantPartition / 2 + this.margeEntreMontantEtPremiereCote);
		
		for(int i = 1 ; i <= ((Conf) conf).nbTrousIntermediairesVerticaux + 1 ; i++){
			g.drawDistanceCote(p3_1, pTemp, curDistanceCotesLaterales);
			curDistanceCotesLaterales = decalerCote(curDistanceCotesLaterales);
			g.drawCircle(pTemp, this.diametreTrous);
			if(i != ((Conf) this.conf).nbTrousIntermediairesVerticaux + 1)
				pTemp.move(0, - ((Conf) conf).entreAxeMontant);
		}
		g.drawDiameterCote(this.valeurDiametreTrous, pTemp, - Math.PI / 4, 40, ShiftMode.LEFT, 5);
		
		pTemp.move(0, - ((Conf) conf).ecartEntrePremierTrouEtDeuxiemeTrouMontantCorniere);
		g.drawDistanceCote(p3_1, pTemp, curDistanceCotesLaterales);
		curDistanceCotesLaterales = decalerCote(curDistanceCotesLaterales);
		g.drawCircle(pTemp, this.diametreTrous);
		
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

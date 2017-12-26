package generateurCoteVerriere.modeles.mecanique.elements;

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
	private final int taillePoliceCote = 25;
	private final int curUnderLineGap = 5;

	// Titre traverse Corniere
	private final double margeEntreLignesTitre = 10;
	private final double ordonneePremiereLigneTitre = this.margeEntreLignesTitre + this.taillePoliceCote;
	private final double ordonneeDeuxiemeLigneTitre = this.ordonneePremiereLigneTitre + this.margeEntreLignesTitre + this.taillePoliceCote;
	private final double margeEntreTitreEtDessin = 10;
	private final int nbTraverses = 2;

	// General
	private final double ordonneeHautDessinTraverse = this.ordonneeDeuxiemeLigneTitre + this.margeEntreTitreEtDessin;

	private final double margeInterCote = 0; // 10;
	private final double margeEntreTraverseEtPremiereCote = conf.get("largeurTraverseCorniere") / 2; // <--- Empirique
	
	private final double ordonneeHautTraverse = this.ordonneeHautDessinTraverse + 2 * (this.taillePoliceCote + this.curUnderLineGap + this.margeEntreTraverseEtPremiereCote);
	private final double ordonneeBasTraverse = this.ordonneeHautTraverse + conf.get("hauteurTraverseCorniere");

	private final double diametreTrous = conf.get("largeurTraverseCorniere") / 8; // INCORRECT
	private final String valeurDiametreTrous = "ØM5";

	private final double nbCotesAGauche = conf.get("nbPairesTrousIntermediairesHorizontaux") * 2 + 5;
	private final double distanceEntreCentreTraverseEtExtremiteGaucheDessin = conf.get("largeurTraverseCorniere") / 2 + this.margeEntreTraverseEtPremiereCote + (this.curUnderLineGap + this.taillePoliceCote + this.margeInterCote) * this.nbCotesAGauche;
	private final double distanceEntreCentreTraverseEtExtremiteDroiteDessin = conf.get("largeurTraverseCorniere") / 2 + this.margeEntreTraverseEtPremiereCote + this.curUnderLineGap + this.taillePoliceCote;
	
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
		String titre = "TRAVERSE CORNIERE 30x30x3";
		g.drawString(titre, new Point((double) g.getWidth() / 2, this.ordonneePremiereLigneTitre), 0, ShiftMode.CENTER);
		g.drawString("QTE = " + this.nbTraverses, new Point((double) g.getWidth() / 2, this.ordonneeDeuxiemeLigneTitre), 0, ShiftMode.CENTER);

		// Trace la traverse corniere
	    g.setStroke(new BasicStroke(2));
	    g.drawLine(this.abscisseAxeTraverse - conf.get("largeurTraverseCorniere") / 2, this.ordonneeHautTraverse + conf.get("largeurTraverseCorniere"), this.abscisseAxeTraverse - conf.get("largeurTraverseCorniere") / 2, this.ordonneeBasTraverse - conf.get("largeurTraverseCorniere"));
	    g.drawLine(this.abscisseAxeTraverse + conf.get("largeurTraverseCorniere") / 2, this.ordonneeHautTraverse, this.abscisseAxeTraverse + conf.get("largeurTraverseCorniere") / 2, this.ordonneeBasTraverse);
	    g.drawLine(this.abscisseAxeTraverse - conf.get("largeurTraverseCorniere") / 2, this.ordonneeHautTraverse + conf.get("largeurTraverseCorniere"), this.abscisseAxeTraverse + conf.get("largeurTraverseCorniere") / 2, this.ordonneeHautTraverse);
	    g.drawLine(this.abscisseAxeTraverse - conf.get("largeurTraverseCorniere") / 2, this.ordonneeBasTraverse - conf.get("largeurTraverseCorniere"), this.abscisseAxeTraverse + conf.get("largeurTraverseCorniere") / 2, this.ordonneeBasTraverse);
	    
	    // Trace l'axe du milieu du traverse 
	    g.setStroke(new BasicStroke(1));
	    g.drawLine(this.abscisseAxeTraverse, this.ordonneeHautTraverse, this.abscisseAxeTraverse, this.ordonneeBasTraverse);
	    
	    // Cote de largeur puis demi largeur de la traverse corniere
		Point p1_1 = new Point(this.abscisseAxeTraverse - conf.get("largeurTraverseCorniere") / 2, this.ordonneeHautTraverse);
		Point p1_2 = new Point(this.abscisseAxeTraverse + conf.get("largeurTraverseCorniere") / 2, this.ordonneeHautTraverse);
		g.drawDistanceCote(p1_1, p1_2, margeEntreTraverseEtPremiereCote + curUnderLineGap + taillePoliceCote, 0, ShiftMode.CENTER);
		
		Point p2_1 = p1_1;
		Point p2_2 = new Point(this.abscisseAxeTraverse, this.ordonneeHautTraverse);
		g.drawDistanceCote(p2_1, p2_2, margeEntreTraverseEtPremiereCote, - conf.get("largeurTraverseCorniere") / 4 - 10, ShiftMode.RIGHT);
		
		// Cote entre les trous et l'extrémité de la traverse corniere + affichage des trous
		Point p3 = new Point(this.abscisseAxeTraverse, this.ordonneeBasTraverse);
		Point pTemp = new Point(p3);
		Point pTemp2 = new Point(pTemp);
		double curDistanceCotesLaterales = conf.get("largeurTraverseCorniere") / 2 + this.margeEntreTraverseEtPremiereCote;
		int nbCotes = (int) (conf.get("nbPairesTrousIntermediairesHorizontaux") + 5);
		for(int i = 1 ; i <= nbCotes ; i++){
			if(i == 1 || i == nbCotes)
				pTemp.move(0, - conf.get("ecartEntreExtremiteEtPremierTrouTraverseCorniere"));
			else if(i == 2 || i == nbCotes - 1){
				pTemp.move(0, - conf.get("ecartEntrePremierTrouEtDeuxiemeTrouTraverseCorniere"));
				if(i == nbCotes - 1)
					g.drawDiameterCote(this.valeurDiametreTrous, pTemp, - Math.PI / 4, 40, ShiftMode.LEFT, 5);
			}
			else if(i == 3 || i == nbCotes - 2){
				pTemp.move(0, - conf.get("entreAxeLateralTraverseCorniere"));
				if(i == 3)
					g.drawDistanceCote(pTemp, pTemp2, conf.get("largeurTraverseCorniere") / 2 + this.margeEntreTraverseEtPremiereCote);
			}
			else{
				pTemp.move(0, - conf.get("entreAxeCentralTraverseCorniere"));
				if(i == 4)
					g.drawDistanceCote(pTemp, pTemp2, conf.get("largeurTraverseCorniere") / 2 + this.margeEntreTraverseEtPremiereCote);
			}
			
			if(i == 1)
				g.drawDistanceCote(p3, pTemp, curDistanceCotesLaterales, 0, ShiftMode.LEFT);
			else
				g.drawDistanceCote(p3, pTemp, curDistanceCotesLaterales);
			curDistanceCotesLaterales = decalerCote(curDistanceCotesLaterales);
			if(i != nbCotes)
				g.drawCircle(pTemp, this.diametreTrous);
			
			pTemp2 = new Point(pTemp);
			
			if(i >= 3 && i <= nbCotes - 3){
				pTemp.move(0, - conf.get("entreAxeT"));
				g.drawDistanceCote(p3, pTemp, curDistanceCotesLaterales);
				curDistanceCotesLaterales = decalerCote(curDistanceCotesLaterales);
				g.drawCircle(pTemp, this.diametreTrous);
				if(i == 3)
					g.drawDistanceCote(pTemp, pTemp2, conf.get("largeurTraverseCorniere") / 2 + this.margeEntreTraverseEtPremiereCote);
				
				pTemp2 = new Point(pTemp);
			}
		}
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

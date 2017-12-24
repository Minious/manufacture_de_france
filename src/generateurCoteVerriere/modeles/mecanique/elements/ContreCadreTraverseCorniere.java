package generateurCoteVerriere.modeles.mecanique.elements;

import java.awt.BasicStroke;
import java.awt.Color;

import generateurCoteVerriere.ElementGenerique;
import generateurCoteVerriere.modeles.mecanique.Conf;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced;
import myCustomSvgLibraryEnhanced.Point;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced.ShiftMode;

public class ContreCadreTraverseCorniere extends ElementGenerique {
	// Initialisation des param�tres
	private String nomFichierDeRendu = "contre_cadre_traverse_corniere";

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
	private final double margeEntreTraverseEtPremiereCote = ((Conf) conf).largeurContreCadreTraverseCorniere / 2; // <--- Empirique
	
	private final double ordonneeHautTraverse = this.ordonneeHautDessinTraverse + 2 * (this.taillePoliceCote + this.curUnderLineGap + this.margeEntreTraverseEtPremiereCote);
	private final double ordonneeBasTraverse = this.ordonneeHautTraverse + ((Conf) conf).hauteurContreCadreTraverseCorniere;

	private final double diametreTrous = ((Conf) conf).largeurContreCadreTraverseCorniere / 8; // INCORRECT
	private final String valeurDiametreTrous = "�9";

	private final double nbCotesAGauche = ((Conf) conf).nbPairesTrousIntermediairesHorizontaux * 2 + 5;
	private final double distanceEntreCentreTraverseEtExtremiteGaucheDessin = ((Conf) conf).demiLargeurGaucheContreCadreTraverseCorniere + this.margeEntreTraverseEtPremiereCote + (this.curUnderLineGap + this.taillePoliceCote + this.margeInterCote) * this.nbCotesAGauche;
	private final double distanceEntreCentreTraverseEtExtremiteDroiteDessin = ((Conf) conf).demiLargeurDroitContreCadreTraverseCorniere + this.margeEntreTraverseEtPremiereCote + this.curUnderLineGap + this.taillePoliceCote;
	
	private final double margeLateraleDessin = 50;
	private final double margeBasDessin = 100;
	
	private final double abscisseAxeTraverse = this.margeLateraleDessin + this.distanceEntreCentreTraverseEtExtremiteGaucheDessin;
	
	private final double largeurImage = this.abscisseAxeTraverse + this.distanceEntreCentreTraverseEtExtremiteDroiteDessin + this.margeLateraleDessin;
	private final double hauteurImage = this.ordonneeBasTraverse + this.margeBasDessin;
	
	public ContreCadreTraverseCorniere(Conf conf){
		super(conf);
	}
	
	protected void drawImage(MyCustomSvgEnhanced g){
		// D�fini la police de caract�res par d�faut
		g.setFont(this.taillePoliceCote, "Arial");
		g.setUnderLineGap(this.curUnderLineGap);

		this.draw(g);
	}
	
	private void draw(MyCustomSvgEnhanced g){		
		// Affiche les titres du dessin
		g.setColor(Color.BLACK);
		String titre = "CONTRE CADRE TRAVERSE PLAT 25x3";
		g.drawString(titre, new Point((double) g.getWidth() / 2, this.ordonneePremiereLigneTitre), 0, ShiftMode.CENTER);
		g.drawString("QTE = " + this.nbTraverses, new Point((double) g.getWidth() / 2, this.ordonneeDeuxiemeLigneTitre), 0, ShiftMode.CENTER);

		// Trace la traverse corniere
	    g.setStroke(new BasicStroke(2));
	    g.drawLine(this.abscisseAxeTraverse - ((Conf) conf).demiLargeurGaucheContreCadreTraverseCorniere, this.ordonneeHautTraverse + ((Conf) conf).largeurContreCadreTraverseCorniere, this.abscisseAxeTraverse - ((Conf) conf).demiLargeurGaucheContreCadreTraverseCorniere, this.ordonneeBasTraverse - ((Conf) conf).largeurContreCadreTraverseCorniere);
	    g.drawLine(this.abscisseAxeTraverse + ((Conf) conf).demiLargeurDroitContreCadreTraverseCorniere, this.ordonneeHautTraverse, this.abscisseAxeTraverse + ((Conf) conf).demiLargeurDroitContreCadreTraverseCorniere, this.ordonneeBasTraverse);
	    g.drawLine(this.abscisseAxeTraverse - ((Conf) conf).demiLargeurGaucheContreCadreTraverseCorniere, this.ordonneeHautTraverse + ((Conf) conf).largeurContreCadreTraverseCorniere, this.abscisseAxeTraverse + ((Conf) conf).demiLargeurDroitContreCadreTraverseCorniere, this.ordonneeHautTraverse);
	    g.drawLine(this.abscisseAxeTraverse - ((Conf) conf).demiLargeurGaucheContreCadreTraverseCorniere, this.ordonneeBasTraverse - ((Conf) conf).largeurContreCadreTraverseCorniere, this.abscisseAxeTraverse + ((Conf) conf).demiLargeurDroitContreCadreTraverseCorniere, this.ordonneeBasTraverse);
	    
	    // Trace l'axe du milieu du traverse 
	    g.setStroke(new BasicStroke(1));
	    g.drawLine(this.abscisseAxeTraverse, this.ordonneeHautTraverse, this.abscisseAxeTraverse, this.ordonneeBasTraverse);
	    
	    // Cote de largeur puis demi largeur de la traverse corniere
		Point p1_1 = new Point(this.abscisseAxeTraverse - ((Conf) conf).demiLargeurGaucheContreCadreTraverseCorniere, this.ordonneeHautTraverse);
		Point p1_2 = new Point(this.abscisseAxeTraverse + ((Conf) conf).demiLargeurDroitContreCadreTraverseCorniere, this.ordonneeHautTraverse);
		g.drawDistanceCote(p1_1, p1_2, margeEntreTraverseEtPremiereCote + curUnderLineGap + taillePoliceCote, 0, ShiftMode.CENTER);
		
		Point p2_1 = p1_1;
		Point p2_2 = new Point(this.abscisseAxeTraverse, this.ordonneeHautTraverse);
		g.drawDistanceCote(p2_1, p2_2, margeEntreTraverseEtPremiereCote, - ((Conf) conf).demiLargeurGaucheContreCadreTraverseCorniere / 2 - 10, ShiftMode.RIGHT);

		Point p3 = new Point(this.abscisseAxeTraverse, this.ordonneeBasTraverse);
		Point pTemp = new Point(p3);
		Point pTemp2 = new Point(pTemp);
		double curDistanceCotesLaterales = ((Conf) conf).demiLargeurGaucheContreCadreTraverseCorniere + this.margeEntreTraverseEtPremiereCote;
		int nbCotes = ((Conf) conf).nbPairesTrousIntermediairesHorizontaux + 5;
		for(int i = 1 ; i <= nbCotes ; i++){
			if(i == 1 || i == nbCotes)
				pTemp.move(0, - ((Conf) conf).ecartEntreExtremiteEtPremierTrouContreCadreTraverseCorniere);
			else if(i == 2 || i == nbCotes - 1){
				pTemp.move(0, - ((Conf) conf).ecartEntrePremierTrouEtDeuxiemeTrouTraverseCorniere);
				if(i == nbCotes - 1)
					g.drawDiameterCote(this.valeurDiametreTrous, pTemp, - Math.PI / 4, 40, ShiftMode.LEFT, 5);
			}
			else if(i == 3 || i == nbCotes - 2){
				pTemp.move(0, - ((Conf) conf).entreAxeLateralTraverseCorniere);
				if(i == 3)
					g.drawDistanceCote(pTemp, pTemp2, ((Conf) conf).demiLargeurDroitContreCadreTraverseCorniere + this.margeEntreTraverseEtPremiereCote);
			}
			else{
				pTemp.move(0, - ((Conf) conf).entreAxeCentralTraverseCorniere);
				if(i == 4)
					g.drawDistanceCote(pTemp, pTemp2, ((Conf) conf).demiLargeurDroitContreCadreTraverseCorniere + this.margeEntreTraverseEtPremiereCote);
			}

			if(i == 1)
				g.drawDistanceCote(p3, pTemp, curDistanceCotesLaterales, - 20, ShiftMode.RIGHT);
			else if(i == 2)
				g.drawDistanceCote(p3, pTemp, curDistanceCotesLaterales, 15, ShiftMode.CENTER);
			else
				g.drawDistanceCote(p3, pTemp, curDistanceCotesLaterales);
			curDistanceCotesLaterales = decalerCote(curDistanceCotesLaterales);
			if(i != nbCotes)
				g.drawCircle(pTemp, this.diametreTrous);
			
			pTemp2 = new Point(pTemp);
			
			if(i >= 3 && i <= nbCotes - 3){
				pTemp.move(0, - ((Conf) conf).entreAxeT);
				g.drawDistanceCote(p3, pTemp, curDistanceCotesLaterales);
				curDistanceCotesLaterales = decalerCote(curDistanceCotesLaterales);
				g.drawCircle(pTemp, this.diametreTrous);
				if(i == 3)
					g.drawDistanceCote(pTemp, pTemp2, ((Conf) conf).demiLargeurDroitContreCadreTraverseCorniere + this.margeEntreTraverseEtPremiereCote);
				
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

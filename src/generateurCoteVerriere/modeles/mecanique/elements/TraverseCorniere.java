package generateurCoteVerriere.modeles.mecanique.elements;

import java.awt.BasicStroke;
import java.util.ArrayList;
import java.util.HashMap;

import generateurCoteVerriere.ElementGenerique;
import generateurCoteVerriere.LignesTexte;
import generateurCoteVerriere.dessinProfil.DessinProfil;
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

	// General
	private final int nbTraverses = 2;
	
	private final double margeInterCote = 0; // 10;
	private final double margeEntreTraverseEtPremiereCote = conf.get("largeurTraverseCorniere") / 2; // <--- Empirique

	private final double diametreTrous = conf.get("largeurTraverseCorniere") / 8; // INCORRECT
	private final String valeurDiametreTrous = "ØM5";
	
	public TraverseCorniere(HashMap<String, Double> conf, HashMap<String, Object> data) {
		super(conf, data);
	}

	@Override
	protected MyCustomSvg getDessin() {	
		MyHandyLayout l = new MyHandyLayout();
		l.addRow(new MyCustomSvg[] {getDessinOriginal(), getDessinNew()}, ShiftMode.CENTER);
		return l.getSvg();
	}
	
	private MyCustomSvg getDessinNew() {
		DessinProfil profil = new DessinProfil(conf.get("largeurTraverseCorniere"), conf.get("hauteurTraverseCorniere"));
		profil.setCorniere();
		profil.setLargeurPercage(this.diametreTrous);
		double ordonnee = conf.get("ecartEntreExtremiteEtPremierTrouTraverseCorniere");
		profil.addPercage(ordonnee);
		ordonnee += conf.get("ecartEntrePremierTrouEtDeuxiemeTrouTraverseCorniere");
		profil.addPercage(ordonnee);
		ordonnee += conf.get("entreAxeLateralTraverseCorniere");
		profil.addPercage(ordonnee);
		
		for(int i=0;i<conf.get("nbPartitions") - 2;i++) {
			ordonnee += conf.get("entreAxeT");
			profil.addPercage(ordonnee);
			ordonnee += conf.get("entreAxeCentralTraverseCorniere");
			profil.addPercage(ordonnee);
		}
		
		if(conf.get("nbPartitions") >= 2) {
			ordonnee += conf.get("entreAxeT");
			profil.addPercage(ordonnee);
			ordonnee += conf.get("entreAxeLateralTraverseCorniere");
			profil.addPercage(ordonnee);
			profil.addCoteDroite(2, 3, 0);
		}

		if(conf.get("nbPartitions") >= 3)
			profil.addCoteDroite(3, 4, 0);
		
		ordonnee += conf.get("ecartEntrePremierTrouEtDeuxiemeTrouTraverseCorniere");
		profil.addPercage(ordonnee, this.valeurDiametreTrous);
		
		profil.addCoteDroite(0, 1, 0);
		profil.addCoteDroite(1, 2, 0);

		MyCustomSvg g = profil.render();
		
		return g;
	}

	private MyCustomSvg getDessinOriginal() {
		MyCustomSvgEnhanced g = new MyCustomSvgEnhanced();

		g.setUnderLineGap(this.curUnderLineGap);
		g.setFontSize(this.taillePoliceCote);
		
		// Trace la traverse corniere
	    g.setStroke(new BasicStroke(1));
	    g.drawLine(- conf.get("largeurTraverseCorniere") / 2, conf.get("largeurTraverseCorniere"), - conf.get("largeurTraverseCorniere") / 2, conf.get("hauteurTraverseCorniere")- conf.get("largeurTraverseCorniere"));
	    g.drawLine(conf.get("largeurTraverseCorniere") / 2, 0, conf.get("largeurTraverseCorniere") / 2, conf.get("hauteurTraverseCorniere"));
	    g.drawLine(- conf.get("largeurTraverseCorniere") / 2, conf.get("largeurTraverseCorniere"), conf.get("largeurTraverseCorniere") / 2, 0);
	    g.drawLine(- conf.get("largeurTraverseCorniere") / 2, conf.get("hauteurTraverseCorniere") - conf.get("largeurTraverseCorniere"), conf.get("largeurTraverseCorniere") / 2, conf.get("hauteurTraverseCorniere"));
	    
	    // Trace l'axe du milieu du traverse 
	    g.setStroke(new BasicStroke(0.5f));
	    g.drawLine(0, 0, 0, conf.get("hauteurTraverseCorniere"));
	    
	    // Cote de largeur puis demi largeur de la traverse corniere
		Point p1_1 = new Point(- conf.get("largeurTraverseCorniere") / 2, 0);
		Point p1_2 = new Point(conf.get("largeurTraverseCorniere") / 2, 0);
		g.drawDistanceCote(p1_1, p1_2, margeEntreTraverseEtPremiereCote + curUnderLineGap + taillePoliceCote, 0, ShiftMode.CENTER);
		
		Point p2_1 = p1_1;
		Point p2_2 = new Point(0, 0);
		g.drawDistanceCote(p2_1, p2_2, margeEntreTraverseEtPremiereCote, - conf.get("largeurTraverseCorniere") / 4 - 10, ShiftMode.RIGHT);
		
		// Cote entre les trous et l'extrémité de la traverse corniere + affichage des trous
		Point p3 = new Point(0, conf.get("hauteurTraverseCorniere"));
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
		
		return g;
	}
	
	private double decalerCote(double curDistance){
		return curDistance + this.curUnderLineGap + this.taillePoliceCote + this.margeInterCote;
	}

	@Override
	public String getNomFichierDeRendu() {
		return this.nomFichierDeRendu;
	}

	@Override
	protected int getNbElements() {
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

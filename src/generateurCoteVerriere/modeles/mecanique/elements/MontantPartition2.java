package generateurCoteVerriere.modeles.mecanique.elements;

import java.awt.BasicStroke;
import java.util.ArrayList;
import java.util.HashMap;

import generateurCoteVerriere.ElementGenerique;
import generateurCoteVerriere.LignesTexte;
import myCustomSvgLibrary.MyCustomSvg;
import myCustomSvgLibrary.MyHandyLayout;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced;
import myCustomSvgLibraryEnhanced.Point;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced.ShiftMode;

public class MontantPartition2 extends ElementGenerique {
	// Initialisation des paramètres
	private String nomFichierDeRendu = "montant_partition";

	// Parametres de la police
	private final int taillePoliceCote = 10;
	private final int curUnderLineGap = 2;

	// General
	private final int nbMontants = (int) (conf.get("nbPartitions") - 1);
	
	private final double margeInterCote = 0; // 10;
	private final double margeEntreMontantEtPremiereCote = conf.get("largeurMontantPartition") / 2; // <--- Empirique

	private final double diametreTrous = conf.get("largeurMontantPartition") / 8; // INCORRECT
	private final String valeurDiametreTrousMontant = "ØM5";
	private final String valeurDiametreTrousContreCadre = "Ø7";
	
	public MontantPartition2(HashMap<String, Double> conf, HashMap<String, Object> data) {
		super(conf, data);
	}

	@Override
	protected MyCustomSvg getDessin() {
		MyHandyLayout l = new MyHandyLayout();
		l.addRow(new MyCustomSvg[] {getDessinMontant(), getDessinContreCadre()}, ShiftMode.CENTER);
		return l.getSvg();
	}
	
	private MyCustomSvg getDessinMontant() {
		MyCustomSvgEnhanced g = new MyCustomSvgEnhanced();

		g.setUnderLineGap(this.curUnderLineGap);
		g.setFontSize(this.taillePoliceCote);
		
		// Trace le montant 
	    g.setStroke(new BasicStroke(1));
	    g.drawRect(- conf.get("largeurMontantPartition") / 2, 0, conf.get("largeurMontantPartition"), conf.get("hauteurMontantPartition"));
	    
	    // Trace l'axe du milieu du montant 
	    g.setStroke(new BasicStroke(0.5f));
	    g.drawLine(0, 0, 0, conf.get("hauteurMontantPartition"));

		g.setFontSize(this.taillePoliceCote);
		
	    // Cote de largeur puis demi largeur de la Partition avant
		Point p1_1 = new Point(- conf.get("largeurMontantPartition") / 2, 0);
		Point p1_2 = new Point(conf.get("largeurMontantPartition") / 2, 0);
		g.drawDistanceCote(p1_1, p1_2, margeEntreMontantEtPremiereCote + curUnderLineGap + taillePoliceCote, 0, ShiftMode.CENTER);
		
		Point p2_1 = p1_1;
		Point p2_2 = new Point(0, 0);
		g.drawDistanceCote(p2_1, p2_2, margeEntreMontantEtPremiereCote, - conf.get("largeurMontantPartition") / 4 - 10, ShiftMode.RIGHT);
		
		// Cote entre les trous et l'extrémité de la Partition + affichage des trous
	    Point p3_1 = new Point(0, conf.get("hauteurMontantPartition"));
	    Point p3_2 = new Point(0, conf.get("hauteurMontantPartition") - conf.get("ecartEntreExtremiteEtPremierTrouMontantPartition"));
		g.drawDistanceCote(p3_1, p3_2, conf.get("largeurMontantPartition") / 2 + this.margeEntreMontantEtPremiereCote, 0, ShiftMode.LEFT);
		g.drawCircle(p3_2, this.diametreTrous);
		
		Point pTemp = new Point(0, conf.get("hauteurMontantPartition") - conf.get("ecartEntreExtremiteEtPremierTrouMontantPartition") - conf.get("entreAxeMontant"));
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
		g.drawDiameterCote(this.valeurDiametreTrousMontant, pTemp, - Math.PI / 4, 40, ShiftMode.LEFT, 5);

		// Cote longueur totale du montant
		g.drawDistanceCote(p3_1, p2_2, curDistanceCotesLaterales);
		
		return g;
	}
	
	private MyCustomSvg getDessinContreCadre() {		
		MyCustomSvgEnhanced g = new MyCustomSvgEnhanced();

		g.setUnderLineGap(this.curUnderLineGap);
		g.setFontSize(this.taillePoliceCote);
		
		// Trace le montant 
	    g.setStroke(new BasicStroke(1));
	    g.drawRect(- conf.get("largeurMontantPartition") / 2, 0, conf.get("largeurMontantPartition"), conf.get("hauteurMontantPartition"));
	    
	    // Trace l'axe du milieu du montant 
	    g.setStroke(new BasicStroke(0.5f));
	    g.drawLine(0, 0, 0, conf.get("hauteurMontantPartition"));
	    
	    // Cote de largeur puis demi largeur de la Partition avant
		Point p1_1 = new Point(- conf.get("largeurMontantPartition") / 2, 0);
		Point p1_2 = new Point(conf.get("largeurMontantPartition") / 2, 0);
		g.drawDistanceCote(p1_1, p1_2, margeEntreMontantEtPremiereCote + curUnderLineGap + taillePoliceCote, 0, ShiftMode.CENTER);
		
		Point p2_1 = p1_1;
		Point p2_2 = new Point(0, 0);
		g.drawDistanceCote(p2_1, p2_2, margeEntreMontantEtPremiereCote, - conf.get("largeurMontantPartition") / 4 - 10, ShiftMode.RIGHT);
		
		// Cote entre les trous et l'extrémité de la Partition + affichage des trous
	    Point p3_1 = new Point(0, conf.get("hauteurMontantPartition"));
	    Point p3_2 = new Point(0, conf.get("hauteurMontantPartition") - conf.get("ecartEntreExtremiteEtPremierTrouMontantPartition"));
		g.drawDistanceCote(p3_1, p3_2, conf.get("largeurMontantPartition") / 2 + this.margeEntreMontantEtPremiereCote, 0, ShiftMode.LEFT);
		g.drawCircle(p3_2, this.diametreTrous);
		
		Point pTemp = new Point(0, conf.get("hauteurMontantPartition") - conf.get("ecartEntreExtremiteEtPremierTrouMontantPartition") - conf.get("entreAxeMontant"));
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
		g.drawDiameterCote(this.valeurDiametreTrousContreCadre, pTemp, - Math.PI / 4, 40, ShiftMode.LEFT, 5);

		// Cote longueur totale du montant
		g.drawDistanceCote(p3_1, p2_2, curDistanceCotesLaterales);
		
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
	public int getNbElements() {
		return this.nbMontants;
	}

	@Override
	protected MyCustomSvg getEntete() {
		ArrayList<String> lignes = new ArrayList<String>();
		lignes.add("PARTITION PLAT 30x3");
		lignes.add("QTE = " + this.nbMontants);
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

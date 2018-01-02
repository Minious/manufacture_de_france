package generateurCoteVerriere.modeles.mecanique.elements;

import java.awt.BasicStroke;
import java.util.ArrayList;
import java.util.HashMap;

import generateurCoteVerriere.ElementGenerique;
import generateurCoteVerriere.LignesTexte;
import myCustomSvgLibrary.MyCustomSvg;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced;
import myCustomSvgLibraryEnhanced.Point;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced.ShiftMode;

public class ContreCadreMontantCorniere extends ElementGenerique {
	// Initialisation des param�tres
	private String nomFichierDeRendu = "contre_cadre_montant_corniere";

	// Parametres de la police
	private final int taillePoliceCote = 10;
	private final int curUnderLineGap = 2;

	// General
	private final int nbMontants = 2;
	
	private final double margeInterCote = 0; // 10;
	private final double margeEntreMontantEtPremiereCote = conf.get("largeurContreCadreMontantCorniere") / 2; // <--- Empirique
	
	private final double diametreTrous = conf.get("largeurContreCadreMontantCorniere") / 8; // INCORRECT
	private final String valeurDiametreTrous = "�9";
	
	public ContreCadreMontantCorniere(HashMap<String, Double> conf, HashMap<String, Object> data) {
		super(conf, data);
	}

	@Override
	protected MyCustomSvg getDessin() {	
		MyCustomSvgEnhanced g = new MyCustomSvgEnhanced();

		g.setUnderLineGap(this.curUnderLineGap);
		g.setFontSize(this.taillePoliceCote);
		
		// Trace le montant 
	    g.setStroke(new BasicStroke(1));
	    g.drawLine(- conf.get("demiLargeurGaucheContreCadreMontantCorniere"), conf.get("largeurContreCadreMontantCorniere"), - conf.get("demiLargeurGaucheContreCadreMontantCorniere"), conf.get("hauteurContreCadreMontantCorniere") - conf.get("largeurContreCadreMontantCorniere"));
	    g.drawLine(conf.get("demiLargeurDroitContreCadreMontantCorniere"), 0, conf.get("demiLargeurDroitContreCadreMontantCorniere"), conf.get("hauteurContreCadreMontantCorniere"));
	    g.drawLine(- conf.get("demiLargeurGaucheContreCadreMontantCorniere"), conf.get("largeurContreCadreMontantCorniere"), conf.get("demiLargeurDroitContreCadreMontantCorniere"), 0);
	    g.drawLine(- conf.get("demiLargeurGaucheContreCadreMontantCorniere"), conf.get("hauteurContreCadreMontantCorniere") - conf.get("largeurContreCadreMontantCorniere"), conf.get("demiLargeurDroitContreCadreMontantCorniere"), conf.get("hauteurContreCadreMontantCorniere"));
	    
	    // Trace l'axe du milieu du contre cadre 
	    g.setStroke(new BasicStroke(0.5f));
	    g.drawLine(0, 0, 0, conf.get("hauteurContreCadreMontantCorniere"));
	    
	    // Cote de largeur puis demi largeur du contre cadre
		Point p1_1 = new Point(- conf.get("demiLargeurGaucheContreCadreMontantCorniere"), 0);
		Point p1_2 = new Point(conf.get("demiLargeurDroitContreCadreMontantCorniere"), 0);
		g.drawDistanceCote(p1_1, p1_2, margeEntreMontantEtPremiereCote + curUnderLineGap + taillePoliceCote, 0, ShiftMode.CENTER);
		
		Point p2_1 = p1_1;
		Point p2_2 = new Point(0, 0);
		g.drawDistanceCote(p2_1, p2_2, margeEntreMontantEtPremiereCote, - conf.get("demiLargeurGaucheContreCadreMontantCorniere") / 2 - 10, ShiftMode.RIGHT);
		
		// Cote entre les trous et l'extr�mit� de la Corniere + affichage des trous
		double curDistanceCotesLaterales = conf.get("demiLargeurGaucheContreCadreMontantCorniere") + this.margeEntreMontantEtPremiereCote;
		
		Point p3_1 = new Point(0, conf.get("hauteurContreCadreMontantCorniere"));
	    Point p3_2 = new Point(0, conf.get("hauteurContreCadreMontantCorniere") - conf.get("ecartEntreExtremiteEtPremierTrouContreCadreMontantCorniere"));
		g.drawDistanceCote(p3_1, p3_2, curDistanceCotesLaterales, - 20	, ShiftMode.RIGHT);
		curDistanceCotesLaterales = decalerCote(curDistanceCotesLaterales);
		g.drawCircle(p3_2, this.diametreTrous);
		
		Point p4 = new Point(0, conf.get("hauteurContreCadreMontantCorniere") - conf.get("ecartEntreExtremiteEtPremierTrouContreCadreMontantCorniere") - conf.get("ecartEntrePremierTrouEtDeuxiemeTrouMontantCorniere"));
		g.drawDistanceCote(p3_1, p4, curDistanceCotesLaterales, 0, ShiftMode.LEFT);
		curDistanceCotesLaterales = decalerCote(curDistanceCotesLaterales);
		g.drawCircle(p4, this.diametreTrous);
		
		Point pTemp = new Point(0, conf.get("hauteurContreCadreMontantCorniere") - conf.get("ecartEntreExtremiteEtPremierTrouContreCadreMontantCorniere") - conf.get("ecartEntrePremierTrouEtDeuxiemeTrouMontantCorniere") - conf.get("entreAxeMontant"));
		g.drawDistanceCote(pTemp, p4, conf.get("demiLargeurDroitContreCadreMontantCorniere") + this.margeEntreMontantEtPremiereCote);
		
		for(int i = 1 ; i <= conf.get("nbTrousIntermediairesVerticaux") + 1 ; i++){
			g.drawDistanceCote(p3_1, pTemp, curDistanceCotesLaterales);
			curDistanceCotesLaterales = decalerCote(curDistanceCotesLaterales);
			g.drawCircle(pTemp, this.diametreTrous);
			if(i != conf.get("nbTrousIntermediairesVerticaux") + 1)
				pTemp.move(0, - conf.get("entreAxeMontant"));
		}
		g.drawDiameterCote(this.valeurDiametreTrous, pTemp, - Math.PI / 4, 40, ShiftMode.LEFT, 5);
		
		pTemp.move(0, - conf.get("ecartEntrePremierTrouEtDeuxiemeTrouMontantCorniere"));
		g.drawDistanceCote(p3_1, pTemp, curDistanceCotesLaterales);
		curDistanceCotesLaterales = decalerCote(curDistanceCotesLaterales);
		g.drawCircle(pTemp, this.diametreTrous);
		
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
	protected int getNbElements() {
		return this.nbMontants;
	}

	@Override
	protected MyCustomSvg getEntete() {
		ArrayList<String> lignes = new ArrayList<String>();
		lignes.add("CONTRE CADRE MONTANT PLAT 25x3");
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

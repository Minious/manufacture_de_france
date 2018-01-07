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

public class MontantCorniere extends ElementGenerique {
	// Initialisation des paramètres
	private String nomFichierDeRendu = "montant_corniere";

	// Parametres de la police
	private final int taillePoliceCote = 10;
	private final int curUnderLineGap = 2;

	// General
	private final int nbMontants = 2;
	
	private final double margeInterCote = 0; // 10;
	private final double margeEntreMontantEtPremiereCote = conf.get("largeurMontantCorniere") / 2; // <--- Empirique
	
	private final double diametreTrous = conf.get("largeurMontantCorniere") / 8; // INCORRECT
	private final String valeurDiametreTrous = "ØM5";
	
	public MontantCorniere(HashMap<String, Double> conf, HashMap<String, Object> data) {
		super(conf, data);
	}

	@Override
	protected MyCustomSvg getDessin() {
		MyHandyLayout l = new MyHandyLayout();
		l.addRow(new MyCustomSvg[] {getDessinOriginal(), getDessinNew()}, ShiftMode.CENTER);
		return l.getSvg();
	}
	
	private MyCustomSvg getDessinNew() {
		DessinProfil profil = new DessinProfil(conf.get("largeurMontantCorniere"), conf.get("hauteurMontantCorniere"));
		profil.setCorniere();
		profil.setLargeurPercage(this.diametreTrous);
		double ordonnee = conf.get("ecartEntreExtremiteEtPremierTrouMontantCorniere");
		profil.addPercage(ordonnee);
		ordonnee += conf.get("ecartEntrePremierTrouEtDeuxiemeTrouMontantCorniere");
		for(int i=0;i<conf.get("nbTrousIntermediairesVerticaux")+2;i++) {
			if(i != conf.get("nbTrousIntermediairesVerticaux") + 1) {
				profil.addPercage(ordonnee);
				ordonnee += conf.get("entreAxeMontant");
			}
			else
				profil.addPercage(ordonnee, this.valeurDiametreTrous);
		}
		ordonnee += conf.get("ecartEntrePremierTrouEtDeuxiemeTrouMontantCorniere");
		profil.addPercage(ordonnee);
		profil.addCoteDroite(0, 1, 0);
		profil.addCoteDroite(1, 2, 0);
		MyCustomSvg g = profil.render();
		
		return g;
	}

	private MyCustomSvg getDessinOriginal() {
		MyCustomSvgEnhanced g = new MyCustomSvgEnhanced();

		g.setUnderLineGap(this.curUnderLineGap);
		g.setFontSize(this.taillePoliceCote);
		
		// Trace le montant 
	    g.setStroke(new BasicStroke(1));
	    g.drawLine(- conf.get("largeurMontantCorniere") / 2, conf.get("largeurMontantCorniere"), - conf.get("largeurMontantCorniere") / 2, conf.get("hauteurMontantCorniere") - conf.get("largeurMontantCorniere"));
	    g.drawLine(conf.get("largeurMontantCorniere") / 2, 0, conf.get("largeurMontantCorniere") / 2, conf.get("hauteurMontantCorniere"));
	    g.drawLine(- conf.get("largeurMontantCorniere") / 2, conf.get("largeurMontantCorniere"), conf.get("largeurMontantCorniere") / 2, 0);
	    g.drawLine(- conf.get("largeurMontantCorniere") / 2, conf.get("hauteurMontantCorniere") - conf.get("largeurMontantCorniere"), conf.get("largeurMontantCorniere") / 2, conf.get("hauteurMontantCorniere"));

	    // Trace l'axe du milieu du montant 
	    g.setStroke(new BasicStroke(0.5f));
	    g.drawLine(0, 0, 0, conf.get("hauteurMontantCorniere"));
	    
	    // Cote de largeur puis demi largeur de la Corniere avant
		Point p1_1 = new Point(- conf.get("largeurMontantCorniere") / 2, 0);
		Point p1_2 = new Point(conf.get("largeurMontantCorniere") / 2, 0);
		g.drawDistanceCote(p1_1, p1_2, margeEntreMontantEtPremiereCote + curUnderLineGap + taillePoliceCote, 0, ShiftMode.CENTER);
		
		Point p2_1 = p1_1;
		Point p2_2 = new Point(0, 0);
		g.drawDistanceCote(p2_1, p2_2, margeEntreMontantEtPremiereCote, - conf.get("largeurMontantCorniere") / 4 - 10, ShiftMode.RIGHT);
		
		// Cote entre les trous et l'extrémité de la Corniere + affichage des trous
		double curDistanceCotesLaterales = conf.get("largeurMontantPartition") / 2 + this.margeEntreMontantEtPremiereCote;
		
		Point p3_1 = new Point(0, conf.get("hauteurMontantCorniere"));
	    Point p3_2 = new Point(0, conf.get("hauteurMontantCorniere") - conf.get("ecartEntreExtremiteEtPremierTrouMontantCorniere"));
		g.drawDistanceCote(p3_1, p3_2, curDistanceCotesLaterales, 0, ShiftMode.LEFT);
		curDistanceCotesLaterales = decalerCote(curDistanceCotesLaterales);
		g.drawCircle(p3_2, this.diametreTrous);
		
		Point p4 = new Point(0, conf.get("hauteurMontantCorniere") - conf.get("ecartEntreExtremiteEtPremierTrouMontantCorniere") - conf.get("ecartEntrePremierTrouEtDeuxiemeTrouMontantCorniere"));
		g.drawDistanceCote(p3_1, p4, curDistanceCotesLaterales, 0, ShiftMode.LEFT);
		curDistanceCotesLaterales = decalerCote(curDistanceCotesLaterales);
		g.drawCircle(p4, this.diametreTrous);
		
		Point pTemp = new Point(0, conf.get("hauteurMontantCorniere") - conf.get("ecartEntreExtremiteEtPremierTrouMontantCorniere") - conf.get("ecartEntrePremierTrouEtDeuxiemeTrouMontantCorniere") - conf.get("entreAxeMontant"));
		g.drawDistanceCote(pTemp, p4, conf.get("largeurMontantPartition") / 2 + this.margeEntreMontantEtPremiereCote);
		
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
	public int getNbElements() {
		return this.nbMontants;
	}

	@Override
	protected MyCustomSvg getEntete() {
		ArrayList<String> lignes = new ArrayList<String>();
		lignes.add("MONTANT CORNIERE 30x30x3");
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

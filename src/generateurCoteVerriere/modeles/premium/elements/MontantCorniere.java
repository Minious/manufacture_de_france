package generateurCoteVerriere.modeles.premium.elements;

import java.awt.BasicStroke;
import java.util.ArrayList;
import java.util.HashMap;

import generateurCoteVerriere.ElementGenerique;
import generateurCoteVerriere.LignesTexte;
import generateurCoteVerriere.dessinProfil.DessinProfil;
import generateurCoteVerriere.dessinProfil.DessinProfil.Side;
import myCustomSvgLibrary.MyCustomSvg;
import myCustomSvgLibrary.MyHandyLayout;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced;
import myCustomSvgLibraryEnhanced.Point;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced.ShiftMode;

public class MontantCorniere extends ElementGenerique {
	// Initialisation des param�tres
	private String nomFichierDeRendu = "montant_corniere";

	// Parametres de la police
	private final int taillePoliceCote = 10;
	private final int curUnderLineGap = 2;

	// Titre montant Corniere
	private final int nbMontants = 2;

	private final double margeInterCote = 0; // 10;
	private final double margeEntreMontantEtPremiereCote = conf.get("largeurChampMontantCorniere") / 2; // <--- Empirique

	private final double diametrePercages = conf.get("largeurChampMontantCorniere") / 8; // INCORRECT
	private final String valeurDiametrePercages = "�M5";
	
	public MontantCorniere(HashMap<String, Double> conf, HashMap<String, Object> data) {
		super(conf, data);
	}

	@Override
	protected MyCustomSvg getDessin() {
		MyHandyLayout l = new MyHandyLayout();
		l.addRow(new MyCustomSvg[] {getDessinOriginal(), getDessinNew()}, ShiftMode.CENTER);
		return l.getSvg();
	}
	
	public MyCustomSvg getDessinNew() {
		DessinProfil profil = new DessinProfil(conf.get("largeurChampMontantCorniere"), conf.get("longueurMontantCorniere"), 10);
		profil.setChamp(conf.get("epaisseurMontantCorniere"), Side.RIGHT);
		
		double ordonnee = conf.get("ecartEntreExtremiteEtPremierPercageMontantCorniere");
		for(int i=0;i<conf.get("nbPercageMontant");i++) {
			if(i != conf.get("nbPercageMontant") - 1) {
				profil.addPercage(ordonnee);
				ordonnee += conf.get("entreAxePercagesMontant");
			}
			else
				profil.addPercage(ordonnee, this.valeurDiametrePercages);
		}
		profil.addCoteDroite(0, 1, 0);
		MyCustomSvg g = profil.render();
		
		return g;
	}

	private MyCustomSvg getDessinOriginal() {
		MyCustomSvgEnhanced g = new MyCustomSvgEnhanced();

		g.setUnderLineGap(this.curUnderLineGap);
		g.setFontSize(this.taillePoliceCote);
		
		// Trace le montant 
	    g.setStrokeWidth(1);
	    g.drawRect(- conf.get("demiLargeurGaucheChampMontantCorniere"), 0, conf.get("largeurChampMontantCorniere"), conf.get("longueurMontantCorniere"));

	    // Trace l'onglet
	    g.setStrokeWidth(1);
	    g.drawLine(conf.get("demiLargeurDroitChampMontantCorniere") - conf.get("epaisseurMontantCorniere"), 0, conf.get("demiLargeurDroitChampMontantCorniere") - conf.get("epaisseurMontantCorniere"), conf.get("longueurMontantCorniere"));
	    
	    // Trace l'axe du milieu du montant 
	    g.setStroke(new BasicStroke(0.5f));
	    g.setDashArray(new float[] {10, 2, 2, 2});
	    g.drawLine(0, 0, 0, conf.get("longueurMontantCorniere"));
	    g.removeDashArray();
	    
	    // Cote de largeur puis demi largeur de la Corniere avant
		g.setFontSize(this.taillePoliceCote);
		Point p1_1 = new Point(- conf.get("demiLargeurGaucheChampMontantCorniere"), 0);
		Point p1_2 = new Point(conf.get("demiLargeurDroitChampMontantCorniere"), 0);
		g.drawDistanceCote(p1_1, p1_2, margeEntreMontantEtPremiereCote + curUnderLineGap + taillePoliceCote, 0, ShiftMode.CENTER);
		
		Point p2_1 = p1_1;
		Point p2_2 = new Point(0, 0);
		g.drawDistanceCote(p2_1, p2_2, margeEntreMontantEtPremiereCote, - conf.get("demiLargeurGaucheChampMontantCorniere") / 2 - 4, ShiftMode.RIGHT);
		
		// Cote entre les trous et l'extr�mit� de la Corniere + affichage des trous
		double curDistanceCotesLaterales = conf.get("demiLargeurGaucheChampMontantCorniere") / 2 + this.margeEntreMontantEtPremiereCote;
		
		Point p3_1 = new Point(0, conf.get("longueurMontantCorniere"));
		Point pTemp = new Point(0, conf.get("longueurMontantCorniere") - conf.get("ecartEntreExtremiteEtPremierPercageMontantCorniere"));
		Point p3_2 = new Point(0, conf.get("longueurMontantCorniere") - conf.get("ecartEntreExtremiteEtPremierPercageMontantCorniere") - conf.get("entreAxePercagesMontant"));
		
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
		lignes.add("MONTANT LAT�RAUX CORNIERE DE 30x20x3");
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

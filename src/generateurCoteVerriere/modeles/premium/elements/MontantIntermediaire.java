package generateurCoteVerriere.modeles.premium.elements;

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
import utils.MyPath2D;

public class MontantIntermediaire extends ElementGenerique {

	// Initialisation des paramètres
	private String nomFichierDeRendu = "montant_intermediaire";

	// Parametres de la police
	private final int taillePoliceCote = 10;
	private final int curUnderLineGap = 2;

	// Titre montant Partition
	private final int nbMontants = (int) (conf.get("nbPartitions") - 1);

	// General
	private final double margeInterCote = 0; // 10;
	private final double margeEntreMontantEtPremiereCote = conf.get("largeurChampMontantIntermediaire") / 2; // <--- Empirique
	
	private final double diametrePercages = conf.get("largeurChampMontantIntermediaire") / 6; // INCORRECT
	private final String valeurDiametrePercages = "ØM5";
	
	public MontantIntermediaire(HashMap<String, Double> conf, HashMap<String, Object> data) {
		super(conf, data);
	}

	protected MyCustomSvg getDessin() {
		MyHandyLayout l = new MyHandyLayout();
		l.addRow(new MyCustomSvg[] {getDessinOriginal(), getDessinNew()}, ShiftMode.CENTER);
		return l.getSvg();
	}
	
	public MyCustomSvg getDessinNew() {
		DessinProfil profil = new DessinProfil(conf.get("largeurChampMontantIntermediaire"), conf.get("longueurMontantIntermediaire"), 10);
		profil.setEpaulement(conf.get("longueurEpaulementMontantIntermediaire"), conf.get("epaisseurEpaulementMontantIntermediaire"), Side.RIGHT);
		profil.setChamp(conf.get("epaisseurMontantIntermedaire"), Side.RIGHT);
		
		double ordonnee = conf.get("ecartEntreExtremiteEtPremierPercageMontantIntermediaire");
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
	    MyPath2D path = new MyPath2D();
	    path.moveTo(conf.get("demiLargeurDroitChampMontantIntermediaire"), 0);
	    /*
	    path.lineTo(this.abscisseAxeMontant + conf.get("demiLargeurDroitChampMontantIntermediaire"), this.ordonneeBasMontant);
	    path.lineTo(this.abscisseAxeMontant - conf.get("demiLargeurGaucheChampMontantIntermediaire") + conf.get("epaisseurEpaulementMontantIntermediaire"), this.ordonneeBasMontant);
	    path.lineTo(this.abscisseAxeMontant - conf.get("demiLargeurGaucheChampMontantIntermediaire") + conf.get("epaisseurEpaulementMontantIntermediaire"), this.ordonneeBasMontant - conf.get("longueurEpaulementMontantIntermediaire"));
	    path.lineTo(this.abscisseAxeMontant - conf.get("demiLargeurGaucheChampMontantIntermediaire"), this.ordonneeBasMontant - conf.get("longueurEpaulementMontantIntermediaire"));
	    path.lineTo(this.abscisseAxeMontant - conf.get("demiLargeurGaucheChampMontantIntermediaire"), this.ordonneeHautMontant + conf.get("longueurEpaulementMontantIntermediaire"));
	    path.lineTo(this.abscisseAxeMontant - conf.get("demiLargeurGaucheChampMontantIntermediaire") + conf.get("epaisseurEpaulementMontantIntermediaire"), this.ordonneeHautMontant + conf.get("longueurEpaulementMontantIntermediaire"));
	    path.lineTo(this.abscisseAxeMontant - conf.get("demiLargeurGaucheChampMontantIntermediaire") + conf.get("epaisseurEpaulementMontantIntermediaire"), this.ordonneeHautMontant);
	    */
	    path.lineToR(0, conf.get("longueurMontantIntermediaire"));
	    path.lineToR(- conf.get("largeurChampMontantIntermediaire") + conf.get("epaisseurEpaulementMontantIntermediaire"), 0);
	    path.lineToR(0, - conf.get("longueurEpaulementMontantIntermediaire"));
	    path.lineToR(- conf.get("epaisseurEpaulementMontantIntermediaire"), 0);
	    path.lineToR(0, - conf.get("longueurMontantIntermediaire") + 2 * conf.get("longueurEpaulementMontantIntermediaire"));
	    path.lineToR(conf.get("epaisseurEpaulementMontantIntermediaire"), 0);
	    path.lineToR(0, - conf.get("longueurEpaulementMontantIntermediaire"));
	    path.closePath();
	    g.drawPath(path);
	      
	    // Trace l'axe du milieu du montant 
	    g.setStrokeWidth(0.5f);
	    g.setDashArray(new float[] {10, 2, 2, 2});
	    g.drawLine(0, 0, 0, conf.get("longueurMontantIntermediaire"));
	    g.removeDashArray();
	    
	    // Trace la face du montant
	    g.setStrokeWidth(1);
	    g.drawLine(- conf.get("demiLargeurGaucheChampMontantIntermediaire") + conf.get("epaisseurEpaulementMontantIntermediaire"), conf.get("longueurEpaulementMontantIntermediaire"), - conf.get("demiLargeurGaucheChampMontantIntermediaire") + conf.get("epaisseurEpaulementMontantIntermediaire"), conf.get("longueurMontantIntermediaire") - conf.get("longueurEpaulementMontantIntermediaire"));
	    
	    // Cote de largeur puis demi largeur de la Corniere avant
	    g.setStrokeWidth(0.5f);
		Point p1_1 = new Point(- conf.get("demiLargeurGaucheChampMontantIntermediaire"), conf.get("longueurEpaulementMontantIntermediaire"));
		Point p1_2 = new Point(conf.get("demiLargeurDroitChampMontantIntermediaire"), conf.get("longueurEpaulementMontantIntermediaire"));
		g.drawDistanceCote(p1_1, p1_2, conf.get("longueurEpaulementMontantIntermediaire") + margeEntreMontantEtPremiereCote + curUnderLineGap + taillePoliceCote, 0, ShiftMode.CENTER);
		
		Point p2_1 = new Point(- conf.get("demiLargeurGaucheChampMontantIntermediaire"), 0);
		Point p2_2 = new Point(0, 0);
		g.drawDistanceCote(p2_1, p2_2, margeEntreMontantEtPremiereCote, - conf.get("demiLargeurGaucheChampMontantIntermediaire") / 2 - 4, ShiftMode.RIGHT);
		
		// Cote entre les trous et l'extrémité de la Corniere + affichage des trous
		double curDistanceCotesLaterales = conf.get("demiLargeurGaucheChampMontantIntermediaire") / 2 + this.margeEntreMontantEtPremiereCote;
		
		Point p3_1 = new Point(0, conf.get("longueurMontantIntermediaire"));
		Point pTemp = new Point(0, conf.get("longueurMontantIntermediaire") - conf.get("ecartEntreExtremiteEtPremierPercageMontantIntermediaire"));
		Point p3_2 = new Point(0, conf.get("longueurMontantIntermediaire") - conf.get("ecartEntreExtremiteEtPremierPercageMontantIntermediaire") - conf.get("entreAxePercagesMontant"));
		
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
		lignes.add("MONTANT INTERMEDIAIRE T DE 30x30x4");
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
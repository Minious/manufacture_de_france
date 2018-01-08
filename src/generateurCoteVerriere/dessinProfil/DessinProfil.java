package generateurCoteVerriere.dessinProfil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import myCustomSvgLibrary.MyCustomSvg;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced.ShiftMode;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced.StyleTrait;
import myCustomSvgLibraryEnhanced.Point;
import utils.MyPath2D;

public class DessinProfil {
	private double longueur;
	private double largeur;
	private double axePercage;

	private ArrayList<Percage> percages;
	private String valeurPercageEnregistree;

	private ArrayList<CoteDroite> coteDroites;

	private boolean isCorniere;
	private Side corniereSide;
	private boolean hasChamp;
	private Side champSide;
	private Face champFace;
	private boolean isChampCorniere;
	private double epaisseurChamp;
	private double largeurChamp;
	private boolean hasEpaulement;
	private Side epaulementSide;
	private double longueurEpaulement;
	private double epaisseurEpaulement;

	private double curUnderLineGap;
	private float taillePoliceCote;
	private float margeEntreProfilEtPremiereCote;
	private double margeInterCote;

	public DessinProfil(double largeur, double longueur) {
		this(largeur, longueur, largeur / 2);
	}

	public DessinProfil(double largeur, double longueur, double axePercage) {
		this.longueur = longueur;
		this.largeur = largeur;
		this.axePercage = axePercage;

		this.percages = new ArrayList<Percage>();
		this.valeurPercageEnregistree = "4";

		this.coteDroites = new ArrayList<CoteDroite>();

		this.isCorniere = false;
		this.corniereSide = Side.LEFT;
		this.hasChamp = false;
		this.champSide = Side.LEFT;
		this.champFace = Face.FRONT;
		this.isChampCorniere = false;
		this.epaisseurChamp = 0;
		this.largeurChamp = this.largeur;
		this.hasEpaulement = false;
		this.epaulementSide = Side.LEFT;
		this.longueurEpaulement = 0;
		this.epaisseurEpaulement = 0;

		this.curUnderLineGap = 5;
		this.taillePoliceCote = 10;
		this.margeEntreProfilEtPremiereCote = 20;
		this.margeInterCote = 2;
	}

	public MyCustomSvg render() {
		MyCustomSvgEnhanced g = new MyCustomSvgEnhanced();

		g.setUnderLineGap(this.curUnderLineGap);
		g.setFontSize(this.taillePoliceCote);

		double demiLargeurGauche = this.axePercage;
		double demiLargeurDroite = this.largeur - this.axePercage;

		// Trace le montant
		g.setStrokeWidth(1);

		MyPath2D path = new MyPath2D();

		if(this.corniereSide == Side.RIGHT || this.epaulementSide == Side.RIGHT) {
			g.translate(0, this.longueur);
			g.rotate(Math.PI);
			path.moveTo(demiLargeurGauche, 0);
		}
		else
			path.moveTo(demiLargeurDroite, 0);
		if (this.isCorniere) {
			path.lineToR(0, this.longueur);
			path.lineToR(-this.largeur, -this.largeur);
			path.lineToR(0, -this.longueur + 2 * this.largeur);
		} else if (this.hasEpaulement) {
			path.lineToR(0, this.longueur);
			path.lineToR(-this.largeur + this.epaisseurEpaulement, 0);
			path.lineToR(0, -this.longueurEpaulement);
			path.lineToR(-this.epaisseurEpaulement, 0);
			path.lineToR(0, -this.longueur + 2 * this.longueurEpaulement);
			path.lineToR(this.epaisseurEpaulement, 0);
			path.lineToR(0, -this.longueurEpaulement);
		} else {
			path.moveTo(demiLargeurDroite, 0);
			path.lineToR(0, this.longueur);
			path.lineToR(-this.largeur, 0);
			path.lineToR(0, -this.longueur);
		}
		
		path.closePath();
		g.drawPath(path);
		g.resetTransform();
		
		// Trace le champ
		double abscisseChamp;
		Point p1Champ, p2Champ;
		if(this.hasChamp) {
			abscisseChamp = (this.champSide == Side.LEFT ? - demiLargeurGauche + this.epaisseurChamp : demiLargeurDroite - this.epaisseurChamp);
			if(this.hasEpaulement && isChampDansEpaulement()) {
				p1Champ = new Point(abscisseChamp, this.longueurEpaulement);
				p2Champ = new Point(abscisseChamp, this.longueur - this.longueurEpaulement);
			} else if(this.isCorniere) {
				if(this.corniereSide == this.champSide) {
					p1Champ = new Point(abscisseChamp, this.largeur - this.epaisseurChamp);
					p2Champ = new Point(abscisseChamp, this.longueur - this.largeur + this.epaisseurChamp);
				} else {
					p1Champ = new Point(abscisseChamp, this.epaisseurChamp);
					p2Champ = new Point(abscisseChamp, this.longueur - this.epaisseurChamp);
				}
			} else if(this.isChampCorniere) {
				p1Champ = new Point(abscisseChamp, this.epaisseurChamp);
				p2Champ = new Point(abscisseChamp, this.longueur - this.epaisseurChamp);
			} else {
				p1Champ = new Point(abscisseChamp, 0);
				p2Champ = new Point(abscisseChamp, this.longueur);
			}
			
			if(this.champFace == Face.BACK)
				g.setDashArray(StyleTrait.INTERROMPU);
			g.drawLine(p1Champ, p2Champ);
			if(this.isChampCorniere) {
				Point p1ChampCorniere11 = new Point(abscisseChamp, this.largeurChamp);
				Point p2ChampCorniere11 = new Point(this.champSide == Side.LEFT ? - demiLargeurGauche : demiLargeurDroite, this.largeurChamp);
				Point p1ChampCorniere12 = new Point(abscisseChamp, this.longueur - this.largeurChamp);
				Point p2ChampCorniere12 = new Point(this.champSide == Side.LEFT ? - demiLargeurGauche : demiLargeurDroite, this.longueur - this.largeurChamp);
				g.drawLine(p1ChampCorniere11, p2ChampCorniere11);
				g.drawLine(p1ChampCorniere12, p2ChampCorniere12);
				
				Point p1ChampCorniere21 = new Point(abscisseChamp, this.epaisseurChamp);
				Point p2ChampCorniere21 = new Point(this.champSide == Side.LEFT ? demiLargeurDroite : - demiLargeurGauche, this.epaisseurChamp);
				Point p1ChampCorniere22 = new Point(abscisseChamp, this.longueur - this.epaisseurChamp);
				Point p2ChampCorniere22 = new Point(this.champSide == Side.LEFT ? demiLargeurDroite : - demiLargeurGauche, this.longueur - this.epaisseurChamp);
				g.drawLine(p1ChampCorniere21, p2ChampCorniere21);
				g.drawLine(p1ChampCorniere22, p2ChampCorniere22);
			}
			g.removeDashArray();
		}

		// Trace l'axe des percages
		g.setStrokeWidth(0.5f);
		if(this.percages.size() > 0) {
			g.setDashArray(StyleTrait.MIXTE);
			g.drawLine(0, 0, 0, this.longueur);
			g.removeDashArray();
		}

		// Cote de largeur puis demi largeur de la Partition avant
		double curDistanceCotesSuperieures = this.margeEntreProfilEtPremiereCote;
		Point origine = new Point(0, 0);
		Point coinSuperieurGaucheProfil = new Point(-demiLargeurGauche, 0);
		Point coinSuperieurDroiteProfil = new Point(demiLargeurDroite, 0);

		if(this.percages.size() > 0) {
			g.drawDistanceCote(coinSuperieurGaucheProfil, origine, curDistanceCotesSuperieures, -demiLargeurGauche / 2 - 10,
					ShiftMode.RIGHT);
			curDistanceCotesSuperieures = decalerCote(curDistanceCotesSuperieures);
		}
		g.drawDistanceCote(coinSuperieurGaucheProfil, coinSuperieurDroiteProfil, curDistanceCotesSuperieures, 0,
				ShiftMode.CENTER);

		// Percages ordonnes de bas en haut
		ArrayList<Percage> sortedPercages = (ArrayList<Percage>) this.percages.clone();
		Collections.sort(sortedPercages, new Comparator<Percage>() {
			@Override
			public int compare(Percage percage1, Percage percage2) {
				return Double.valueOf(percage1.getHauteurPercage()).compareTo(percage2.getHauteurPercage());
			}
		});

		// Trace les cotes gauches + percages + traits d'axe horizontaux
		Point origineCotesGauches = new Point(-demiLargeurGauche, this.longueur);
		double curDistanceCotesGauches = this.margeEntreProfilEtPremiereCote;
		for (Percage percage : sortedPercages) {
			// Cotes gauches
			Point extremiteCote = new Point(-demiLargeurGauche, this.longueur - percage.getHauteurPercage());
			g.drawDistanceCote(origineCotesGauches, extremiteCote, curDistanceCotesGauches);
			curDistanceCotesGauches = decalerCote(curDistanceCotesGauches);

			// Percages
			Point coordPercage = new Point(0, this.longueur - percage.getHauteurPercage());
			if (percage.getShowCote())
				g.drawDiameterCote(percage.getValeurPercage(), coordPercage, - 3 * Math.PI / 4, 50);
			g.drawPercage(percage.getValeurPercage(), coordPercage);

			// Traits d'axe
			Point extremiteTraitDAxe = new Point(demiLargeurDroite, this.longueur - percage.getHauteurPercage());
			g.setDashArray(StyleTrait.MIXTE);
			g.drawLine(extremiteCote, extremiteTraitDAxe);
			g.removeDashArray();
		}

		// Trace la cote de longueur totale
		g.drawDistanceCote(origineCotesGauches, coinSuperieurGaucheProfil, curDistanceCotesGauches);

		// Cotes droites ordonnees de le moins a la plus eloignee
		ArrayList<CoteDroite> sortedCotesDroites = (ArrayList<CoteDroite>) this.coteDroites.clone();
		Collections.sort(sortedCotesDroites, new Comparator<CoteDroite>() {
			@Override
			public int compare(CoteDroite cote1, CoteDroite cote2) {
				return Integer.valueOf(cote1.getEtage()).compareTo(cote2.getEtage());
			}
		});

		// Trace les cotes droites
		for (CoteDroite coteDroite : sortedCotesDroites) {
			double ordonnee1 = coteDroite.getHauteur1();
			double ordonnee2 = coteDroite.getHauteur2();
			double ordonneeBasse = ordonnee1 < ordonnee2 ? ordonnee1 : ordonnee2;
			double ordonneeHaute = ordonnee1 > ordonnee2 ? ordonnee1 : ordonnee2;
			Point extremiteCote1 = new Point(demiLargeurDroite, this.longueur - ordonneeHaute);
			Point extremiteCote2 = new Point(demiLargeurDroite, this.longueur - ordonneeBasse);
			g.drawReversedDistanceCote(extremiteCote1, extremiteCote2, this.margeEntreProfilEtPremiereCote	+ coteDroite.getEtage() * (this.curUnderLineGap + this.taillePoliceCote + this.margeInterCote));
		}

		return g;
	}

	private double decalerCote(double curDistance) {
		return curDistance + this.curUnderLineGap + this.taillePoliceCote + this.margeInterCote;
	}

	public void setCorniere(Side side) {
		this.isCorniere = true;
		this.corniereSide = side;
	}
	
	public void setCorniere() {
		setCorniere(Side.LEFT);
	}
	
	public void setCorniereSide(Side side) {
		this.corniereSide = side;
	}
	
	public void setChamp(double epaisseur, Side side, Face face, boolean isChampCorniere) {
		this.hasChamp = true;
		this.epaisseurChamp = epaisseur;
		this.champSide = side;
		this.champFace = face;
		this.isChampCorniere = isChampCorniere;
	}
	
	public void setChamp(double epaisseur, Side side, Face face) {
		setChamp(epaisseur, side, Face.FRONT, false);
	}
	
	public void setChamp(double epaisseur, Side side) {
		setChamp(epaisseur, side, Face.FRONT);
	}
	
	public void setChamp(double epaisseur) {
		setChamp(epaisseur, Side.LEFT);
	}
	
	public void setSideChamp(Side side) {
		this.champSide = side;
	}
	
	public void setFaceChamp(Face face) {
		this.champFace = face;
	}
	
	public void setIsChampCorniere(boolean isChampCorniere) {
		this.isChampCorniere = isChampCorniere;
	}
	
	public void setLargeurChamp(double largeur) {
		this.largeurChamp = largeur;
	}

	public void setEpaulement(double longueurEpaulement, double epaisseurEpaulement, Side side) {
		this.hasEpaulement = true;
		this.longueurEpaulement = longueurEpaulement;
		this.epaisseurEpaulement = epaisseurEpaulement;
		this.epaulementSide = side;
	}

	public void setEpaulement(double longueurEpaulement, double epaisseurEpaulement) {
		setEpaulement(longueurEpaulement, epaisseurEpaulement, Side.LEFT);
	}

	public void setEpaulementSide(Side side) {
		this.epaulementSide = side;
	}
	
	private boolean isChampDansEpaulement() {
		return (this.champSide == this.epaulementSide && this.epaisseurChamp < this.epaisseurEpaulement) ||
			(this.champSide != this.epaulementSide && this.epaisseurChamp > this.largeur - this.epaisseurEpaulement);
	}

	public void setValeurPercage(String valeurPercage) {
		this.valeurPercageEnregistree = valeurPercage;
	}

	public void addPercage(double hauteurPercage) {
		this.percages.add(new Percage(hauteurPercage, this.valeurPercageEnregistree));
	}

	public void addPercage(double hauteurPercage, boolean showCote) {
		this.percages.add(new Percage(hauteurPercage, this.valeurPercageEnregistree, showCote));
	}

	public void addPercage(double hauteurPercage, String valeurPercage) {
		this.percages.add(new Percage(hauteurPercage, valeurPercage));
	}

	public void addPercage(double hauteurPercage, String valeurPercage, boolean showCote) {
		this.percages.add(new Percage(hauteurPercage, valeurPercage, showCote));
	}

	public void addCoteDroiteEntrePercages(int percage1, int percage2, int etage) {
		addCoteDroite(this.percages.get(percage1).getHauteurPercage(), this.percages.get(percage2).getHauteurPercage(), etage);
	}

	public void addCoteDroiteEntreHauteurEtPercage(double hauteur, int percage, int etage) {
		addCoteDroite(hauteur, this.percages.get(percage).getHauteurPercage(), etage);
	}

	public void addCoteDroiteEntrePercageEtOrigine(int percage, int etage) {
		addCoteDroiteOrigine(this.percages.get(percage).getHauteurPercage(), etage);
	}

	public void addCoteDroiteOrigine(double hauteur, int etage) {
		addCoteDroite(0, hauteur, etage);
	}

	public void addCoteDroite(double hauteur1, double hauteur2, int etage) {
		this.coteDroites.add(new CoteDroite(hauteur1, hauteur2, etage));
	}

	class CoteDroite {
		private double hauteur1;
		private double hauteur2;
		private int etage;

		public CoteDroite(double hauteur1, double hauteur2, int etage) {
			this.hauteur1 = hauteur1;
			this.hauteur2 = hauteur2;
			this.etage = etage;
		}

		public double getHauteur1() {
			return this.hauteur1;
		}

		public double getHauteur2() {
			return this.hauteur2;
		}

		public int getEtage() {
			return this.etage;
		}
	}

	class Percage {
		private double hauteurPercage;
		private String valeurPercage;
		private boolean showCote;

		public Percage(double hauteurPercage, String valeurPercage, boolean showCote) {
			this.hauteurPercage = hauteurPercage;
			this.valeurPercage = valeurPercage;
			this.showCote = showCote;
		}

		public Percage(double hauteurPercage, String valeurPercage) {
			this(hauteurPercage, valeurPercage, false);
		}

		public double getHauteurPercage() {
			return this.hauteurPercage;
		}

		public String getValeurPercage() {
			return this.valeurPercage;
		}
		
		public boolean getShowCote() {
			return this.showCote;
		}
	}

	public enum Side {
		LEFT, RIGHT;
	}

	public enum Face {
		FRONT, BACK;
	}
}

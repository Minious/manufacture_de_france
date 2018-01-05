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
	private double largeurPercageEnregistree;

	private ArrayList<CoteDroite> coteDroites;

	private boolean isCorniere;
	private Side corniereSide;
	private boolean hasChamp;
	private Side champSide;
	private double epaisseurChamp;
	private boolean hasEpaulement;
	private Side epaulementSide;
	private double longueurEpaulement;
	private double epaisseurEpaulement;

	private double curUnderLineGap;
	private float taillePoliceCote;
	private float margeEntreMontantEtPremiereCote;
	private double margeInterCote;

	public DessinProfil(double largeur, double longueur) {
		this(largeur, longueur, largeur / 2);
	}

	public DessinProfil(double largeur, double longueur, double axePercage) {
		this.longueur = longueur;
		this.largeur = largeur;
		this.axePercage = axePercage;

		this.percages = new ArrayList<Percage>();
		this.largeurPercageEnregistree = 4;

		this.coteDroites = new ArrayList<CoteDroite>();

		this.isCorniere = false;
		this.corniereSide = Side.LEFT;
		this.hasChamp = false;
		this.champSide = Side.LEFT;
		this.epaisseurChamp = 0;
		this.hasEpaulement = false;
		this.epaulementSide = Side.LEFT;
		this.longueurEpaulement = 0;
		this.epaisseurEpaulement = 0;

		this.curUnderLineGap = 2;
		this.taillePoliceCote = 10;
		this.margeEntreMontantEtPremiereCote = 15;
		this.margeInterCote = 0;
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
			System.out.println("lol");
			abscisseChamp = (this.champSide == Side.LEFT ? - demiLargeurGauche + this.epaisseurChamp : demiLargeurDroite - this.epaisseurChamp);
			if(this.hasEpaulement && isChampDansEpaulement()) {
				System.out.println("lol");
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
			} else {
				p1Champ = new Point(abscisseChamp, 0);
				p2Champ = new Point(abscisseChamp, this.longueur);
			}
			
			g.setDashArray(StyleTrait.INTERROMPU);
			g.drawLine(p1Champ, p2Champ);
			g.removeDashArray();
		}

		// Trace l'axe des percages
		g.setStrokeWidth(0.5f);
		g.setDashArray(StyleTrait.MIXTE);
		g.drawLine(0, 0, 0, this.longueur);
		g.removeDashArray();

		// Cote de largeur puis demi largeur de la Partition avant
		double curDistanceCotesSuperieures = this.margeEntreMontantEtPremiereCote;
		Point origine = new Point(0, 0);
		Point coinSuperieurGaucheProfil = new Point(-demiLargeurGauche, 0);
		Point coinSuperieurDroiteProfil = new Point(demiLargeurDroite, 0);

		g.drawDistanceCote(coinSuperieurGaucheProfil, origine, curDistanceCotesSuperieures, -demiLargeurGauche / 2 - 10,
				ShiftMode.RIGHT);
		curDistanceCotesSuperieures = decalerCote(curDistanceCotesSuperieures);
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
		double curDistanceCotesGauches = this.margeEntreMontantEtPremiereCote;
		for (Percage percage : sortedPercages) {
			// Cotes gauches
			Point extremiteCote = new Point(-demiLargeurGauche, this.longueur - percage.getHauteurPercage());
			g.drawDistanceCote(origineCotesGauches, extremiteCote, curDistanceCotesGauches);
			curDistanceCotesGauches = decalerCote(curDistanceCotesGauches);

			// Percages
			Point coordPercage = new Point(0, this.longueur - percage.getHauteurPercage());
			g.drawCircle(coordPercage, percage.getLargeurPercage());
			if (percage.hasValeurPercage())
				g.drawDiameterCote(percage.getValeurPercage(), coordPercage, -Math.PI / 4, 40);

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
			double ordonnee1 = coteDroite.getPercage1().getHauteurPercage();
			double ordonnee2 = coteDroite.getPercage2().getHauteurPercage();
			double ordonneeBasse = ordonnee1 < ordonnee2 ? ordonnee1 : ordonnee2;
			double ordonneeHaute = ordonnee1 > ordonnee2 ? ordonnee1 : ordonnee2;
			Point extremiteCote1 = new Point(demiLargeurDroite, this.longueur - ordonneeHaute);
			Point extremiteCote2 = new Point(demiLargeurDroite, this.longueur - ordonneeBasse);
			g.drawDistanceCote(extremiteCote1, extremiteCote2, this.margeEntreMontantEtPremiereCote
					+ coteDroite.getEtage() * (this.curUnderLineGap + this.taillePoliceCote + this.margeInterCote));
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
	
	public void setChamp(double epaisseur, Side side) {
		this.hasChamp = true;
		this.epaisseurChamp = epaisseur;
		this.champSide = side;
	}
	
	public void setChamp(double epaisseur) {
		setChamp(epaisseur, Side.LEFT);
	}
	
	public void setSideChamp(Side side) {
		this.champSide = side;
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

	public void setLargeurPercage(double largeurPercage) {
		this.largeurPercageEnregistree = largeurPercage;
	}

	public void addPercage(double hauteurPercage) {
		this.percages.add(new Percage(hauteurPercage, this.largeurPercageEnregistree));
	}

	public void addPercage(double hauteurPercage, double largeurPercage) {
		this.percages.add(new Percage(hauteurPercage, largeurPercage));
	}

	public void addPercage(double hauteurPercage, String valeurPercage) {
		this.percages.add(new Percage(hauteurPercage, this.largeurPercageEnregistree, valeurPercage));
	}

	public void addPercage(double hauteurPercage, double largeurPercage, String valeurPercage) {
		this.percages.add(new Percage(hauteurPercage, largeurPercage, valeurPercage));
	}

	public void addCoteDroite(int percage1, int percage2, int etage) {
		this.coteDroites.add(new CoteDroite(this.percages.get(percage1), this.percages.get(percage2), etage));
	}

	class CoteDroite {
		private Percage percage1;
		private Percage percage2;
		private int etage;

		public CoteDroite(Percage percage1, Percage percage2, int etage) {
			this.percage1 = percage1;
			this.percage2 = percage2;
			this.etage = etage;
		}

		public Percage getPercage1() {
			return this.percage1;
		}

		public Percage getPercage2() {
			return this.percage2;
		}

		public int getEtage() {
			return this.etage;
		}
	}

	class Percage {
		private double hauteurPercage;
		private double largeurPercage;
		private String valeurPercage;

		public Percage(double hauteurPercage, double largeurPercage, String valeurPercage) {
			this.hauteurPercage = hauteurPercage;
			this.largeurPercage = largeurPercage;
			this.valeurPercage = valeurPercage;
		}

		public Percage(double hauteurPercage, double largeurPercage) {
			this(hauteurPercage, largeurPercage, null);
		}

		public double getHauteurPercage() {
			return this.hauteurPercage;
		}

		public double getLargeurPercage() {
			return this.largeurPercage;
		}

		public boolean hasValeurPercage() {
			return this.valeurPercage != null;
		}

		public String getValeurPercage() {
			return this.valeurPercage;
		}
	}

	public enum Side {
		LEFT, RIGHT;
	}
}

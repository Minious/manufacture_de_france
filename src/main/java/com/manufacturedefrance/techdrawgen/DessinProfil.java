package com.manufacturedefrance.techdrawgen;

import java.util.ArrayList;
import java.util.Comparator;

import com.manufacturedefrance.svgen.MyCustomSvg;
import com.manufacturedefrance.techdrawgen.MyCustomSvgEnhanced.ShiftMode;
import com.manufacturedefrance.techdrawgen.MyCustomSvgEnhanced.StyleTrait;
import com.manufacturedefrance.utils.MyPoint2D;
import com.manufacturedefrance.utils.MyPath2D;

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
	private int taillePoliceCote;
	private Side sideCoteDemiLargeur;
	private float margeEntreProfilEtPremiereCote;
	private double margeInterCote;

	public DessinProfil(double largeur, double longueur) {
		this(largeur, longueur, largeur / 2);
	}

	public DessinProfil(double largeur, double longueur, double axePercage) {
		this.longueur = longueur;
		this.largeur = largeur;
		this.axePercage = axePercage;

		this.percages = new ArrayList<>();
		this.valeurPercageEnregistree = "4";

		this.coteDroites = new ArrayList<>();

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
		this.taillePoliceCote = 16;
		this.sideCoteDemiLargeur = Side.LEFT;
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
		MyPoint2D p1Champ;
		MyPoint2D p2Champ;
		if(this.hasChamp) {
			abscisseChamp = (this.champSide == Side.LEFT ? - demiLargeurGauche + this.epaisseurChamp : demiLargeurDroite - this.epaisseurChamp);
			if(this.hasEpaulement && isChampDansEpaulement()) {
				p1Champ = new MyPoint2D(abscisseChamp, this.longueurEpaulement);
				p2Champ = new MyPoint2D(abscisseChamp, this.longueur - this.longueurEpaulement);
			} else if(this.isCorniere) {
				if(this.corniereSide == this.champSide) {
					p1Champ = new MyPoint2D(abscisseChamp, this.largeur - this.epaisseurChamp);
					p2Champ = new MyPoint2D(abscisseChamp, this.longueur - this.largeur + this.epaisseurChamp);
				} else {
					p1Champ = new MyPoint2D(abscisseChamp, this.epaisseurChamp);
					p2Champ = new MyPoint2D(abscisseChamp, this.longueur - this.epaisseurChamp);
				}
			} else if(this.isChampCorniere) {
				p1Champ = new MyPoint2D(abscisseChamp, this.epaisseurChamp);
				p2Champ = new MyPoint2D(abscisseChamp, this.longueur - this.epaisseurChamp);
			} else {
				p1Champ = new MyPoint2D(abscisseChamp, 0);
				p2Champ = new MyPoint2D(abscisseChamp, this.longueur);
			}
			
			if(this.champFace == Face.BACK)
				g.setDashArray(StyleTrait.INTERROMPU);
			g.drawLine(p1Champ, p2Champ);
			if(this.isChampCorniere) {
				MyPoint2D p1ChampCorniere11 = new MyPoint2D(abscisseChamp, this.largeurChamp);
				MyPoint2D p2ChampCorniere11 = new MyPoint2D(this.champSide == Side.LEFT ? - demiLargeurGauche : demiLargeurDroite, this.largeurChamp);
				MyPoint2D p1ChampCorniere12 = new MyPoint2D(abscisseChamp, this.longueur - this.largeurChamp);
				MyPoint2D p2ChampCorniere12 = new MyPoint2D(this.champSide == Side.LEFT ? - demiLargeurGauche : demiLargeurDroite, this.longueur - this.largeurChamp);
				g.drawLine(p1ChampCorniere11, p2ChampCorniere11);
				g.drawLine(p1ChampCorniere12, p2ChampCorniere12);
				
				MyPoint2D p1ChampCorniere21 = new MyPoint2D(abscisseChamp, this.epaisseurChamp);
				MyPoint2D p2ChampCorniere21 = new MyPoint2D(this.champSide == Side.LEFT ? demiLargeurDroite : - demiLargeurGauche, this.epaisseurChamp);
				MyPoint2D p1ChampCorniere22 = new MyPoint2D(abscisseChamp, this.longueur - this.epaisseurChamp);
				MyPoint2D p2ChampCorniere22 = new MyPoint2D(this.champSide == Side.LEFT ? demiLargeurDroite : - demiLargeurGauche, this.longueur - this.epaisseurChamp);
				g.drawLine(p1ChampCorniere21, p2ChampCorniere21);
				g.drawLine(p1ChampCorniere22, p2ChampCorniere22);
			}
			g.removeDashArray();
		}

		// Trace l'axe des percages
		g.setStrokeWidth(0.5f);
		if(!this.percages.isEmpty()) {
			g.setDashArray(StyleTrait.MIXTE);
			g.drawLine(0, 0, 0, this.longueur);
			g.removeDashArray();
		}

		// Cote de largeur puis demi largeur de la Partition avant
		double curDistanceCotesSuperieures = this.margeEntreProfilEtPremiereCote;
		MyPoint2D origine = new MyPoint2D(0, 0);
		MyPoint2D coinSuperieurGaucheProfil = new MyPoint2D(-demiLargeurGauche, 0);
		MyPoint2D coinSuperieurDroitProfil = new MyPoint2D(demiLargeurDroite, 0);

		if(!this.percages.isEmpty()) {
			if(this.sideCoteDemiLargeur == Side.LEFT)
				g.drawDistanceCote(coinSuperieurGaucheProfil, origine, curDistanceCotesSuperieures, -demiLargeurGauche / 2 - 10, ShiftMode.RIGHT);
			else
				g.drawDistanceCote(origine, coinSuperieurDroitProfil, curDistanceCotesSuperieures, demiLargeurDroite / 2 + 10, ShiftMode.LEFT);
			curDistanceCotesSuperieures = decalerCote(curDistanceCotesSuperieures);
		}
		g.drawDistanceCote(coinSuperieurGaucheProfil, coinSuperieurDroitProfil, curDistanceCotesSuperieures, 0,
				ShiftMode.CENTER);

		// Percages ordonnes de bas en haut
		ArrayList<Percage> sortedPercages = new ArrayList<>(this.percages);
		sortedPercages.sort(Comparator.comparing(Percage::getHauteurPercage));

		// Trace les cotes gauches + percages + traits d'axe horizontaux
		MyPoint2D origineCotesGauches = new MyPoint2D(-demiLargeurGauche, this.longueur);
		double curDistanceCotesGauches;
		int maxNumRangeeCote = 0;
		for (Percage percage : sortedPercages) {
			// Cotes gauches
			MyPoint2D extremiteCote = new MyPoint2D(-demiLargeurGauche, this.longueur - percage.getHauteurPercage());
			//g.drawDistanceCote(origineCotesGauches, extremiteCote, curDistanceCotesGauches);
			if (maxNumRangeeCote < percage.getNumRangeeCote()) {
				maxNumRangeeCote = percage.getNumRangeeCote();
			}
			curDistanceCotesGauches = getDistanceCotePercage(percage.getNumRangeeCote());
			g.drawSimpleDistanceCote(origineCotesGauches, extremiteCote, curDistanceCotesGauches);

			// Percages
			MyPoint2D coordPercage = new MyPoint2D(0, this.longueur - percage.getHauteurPercage());
			if (percage.getShowCote())
				g.drawDiameterCote(percage.getValeurAfficheePercage(), coordPercage, - 3 * Math.PI / 4, 50);
			g.drawPercage(percage.getValeurPercage(), coordPercage);

			// Traits d'axe
			MyPoint2D extremiteTraitDAxe = new MyPoint2D(demiLargeurDroite, this.longueur - percage.getHauteurPercage());
			g.setDashArray(StyleTrait.MIXTE);
			g.drawLine(extremiteCote, extremiteTraitDAxe);
			g.removeDashArray();
		}

		// Trace la cote de longueur totale
		//curDistanceCotesGauches = decalerCote(curDistanceCotesGauches);
		curDistanceCotesGauches = getDistanceCotePercage(maxNumRangeeCote + 1);
		g.drawDistanceCote(origineCotesGauches, coinSuperieurGaucheProfil, curDistanceCotesGauches);

		// Cotes droites ordonnees de le moins a la plus eloignee
		ArrayList<CoteDroite> sortedCotesDroites = new ArrayList<>(this.coteDroites);
		sortedCotesDroites.sort(Comparator.comparing(CoteDroite::getEtage));

		// Trace les cotes droites
		for (CoteDroite coteDroite : sortedCotesDroites) {
			double ordonnee1 = coteDroite.getHauteur1();
			double ordonnee2 = coteDroite.getHauteur2();
			double ordonneeBasse = ordonnee1 < ordonnee2 ? ordonnee1 : ordonnee2;
			double ordonneeHaute = ordonnee1 > ordonnee2 ? ordonnee1 : ordonnee2;
			MyPoint2D extremiteCote1 = new MyPoint2D(demiLargeurDroite, this.longueur - ordonneeHaute);
			MyPoint2D extremiteCote2 = new MyPoint2D(demiLargeurDroite, this.longueur - ordonneeBasse);
			g.drawReversedDistanceCote(extremiteCote1, extremiteCote2, this.margeEntreProfilEtPremiereCote	+ coteDroite.getEtage() * (this.curUnderLineGap + this.taillePoliceCote + this.margeInterCote));
		}

		return g;
	}

	public void setSideCoteDemiLargeur(Side sideCoteDemiLargeur){
	    this.sideCoteDemiLargeur = sideCoteDemiLargeur;
    }

	private double decalerCote(double curDistance) {
		return curDistance + this.curUnderLineGap + this.taillePoliceCote + this.margeInterCote;
	}

	private double getDistanceCotePercage(int numRangeeCote) {
		return this.margeEntreProfilEtPremiereCote + numRangeeCote * (this.curUnderLineGap + this.taillePoliceCote + this.margeInterCote);
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
		setChamp(epaisseur, side, face, false);
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

	public void addPercage(double hauteurPercage, int numRangeeCote) {
		this.percages.add(new Percage(hauteurPercage, numRangeeCote, this.valeurPercageEnregistree));
	}

	public void addPercage(double hauteurPercage, int numRangeeCote, boolean showCote) {
		this.percages.add(new Percage(hauteurPercage, numRangeeCote, this.valeurPercageEnregistree, showCote));
	}

	public void addPercage(double hauteurPercage, int numRangeeCote, String valeurPercage) {
		this.percages.add(new Percage(hauteurPercage, numRangeeCote, valeurPercage));
	}

    public void addPercage(double hauteurPercage, int numRangeeCote, String valeurPercage, boolean showCote) {
        this.percages.add(new Percage(hauteurPercage, numRangeeCote, valeurPercage, showCote));
    }

    public void addPercage(double hauteurPercage, int numRangeeCote, String valeurPercage, String valeurAfficheePercage) {
        this.percages.add(new Percage(hauteurPercage, numRangeeCote, valeurPercage, valeurAfficheePercage));
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

		CoteDroite(double hauteur1, double hauteur2, int etage) {
			this.hauteur1 = hauteur1;
			this.hauteur2 = hauteur2;
			this.etage = etage;
		}

		double getHauteur1() {
			return this.hauteur1;
		}

		double getHauteur2() {
			return this.hauteur2;
		}

		int getEtage() {
			return this.etage;
		}
	}

	class Percage {
		private double hauteurPercage;
		private int numRangeeCote;
		private String valeurPercage;
		private String valeurAfficheePercage;
		private boolean showCote;

        Percage(double hauteurPercage, int numRangeeCote, String valeurPercage, String valeurAfficheePercage) {
            this(hauteurPercage, numRangeeCote, valeurPercage, true, valeurAfficheePercage);
        }

        Percage(double hauteurPercage, int numRangeeCote, String valeurPercage, boolean showCote) {
            this(hauteurPercage, numRangeeCote, valeurPercage, showCote, null);
        }

        private Percage(double hauteurPercage, int numRangeeCote, String valeurPercage, boolean showCote, String valeurAfficheePercage){
			this.hauteurPercage = hauteurPercage;
			this.numRangeeCote = numRangeeCote;
            this.valeurPercage = valeurPercage;
            this.showCote = showCote;
            this.valeurAfficheePercage = valeurAfficheePercage;
        }

		Percage(double hauteurPercage, int numRangeeCote, String valeurPercage) {
			this(hauteurPercage, numRangeeCote, valeurPercage, false);
		}

		double getHauteurPercage() {
			return this.hauteurPercage;
		}

		int getNumRangeeCote() { return this.numRangeeCote; }

        String getValeurPercage() {
            return this.valeurPercage;
        }

        String getValeurAfficheePercage() {
            return this.valeurAfficheePercage != null ? this.valeurAfficheePercage : this.valeurPercage;
        }
		
		boolean getShowCote() {
			return this.showCote;
		}
	}

	public enum Side {
		LEFT, RIGHT
	}

	public enum Face {
		FRONT, BACK
	}
}

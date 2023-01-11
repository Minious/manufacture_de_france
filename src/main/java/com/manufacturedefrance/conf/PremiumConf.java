package com.manufacturedefrance.conf;

public class PremiumConf extends GenericConf {
	private double epaisseurVitrage;

	public PremiumConf(double hauteurVerriere, double largeurVerriere, int nbPartitions, double epaisseurVitrage) {
		super(hauteurVerriere, largeurVerriere, nbPartitions);

		this.epaisseurVitrage = epaisseurVitrage;
	}

	//region Traverse corniere
	public double epaisseurTraverseCorniere() {
		return 3;
	}

	public double longueurTraverseCorniere() {
		return this.largeurVerriere;
	}

	public double largeurChampTraverseCorniere() {
		return 30;
	}

	public double largeurFaceTraverseCorniere() {
		return 20;
	}

	public double demiLargeurDroitChampTraverseCorniere() {
		return epaisseurVitrage == 44.2 ? 23 : 21;
	}

	public double demiLargeurGaucheChampTraverseCorniere() {
		return this.largeurChampTraverseCorniere() - this.demiLargeurDroitChampTraverseCorniere();
	}

	public double ecartEntreExtremiteEtPremierPercageMontantTraverseCorniere() {
		return this.largeurClairVitrage()
			+ this.largeurFaceMontantCorniere()
			+ this.largeurFaceMontantIntermediaire() / 2;
	}

	public double ecartEntreExtremiteEtPremierPercageParcloseTraverseCorniere() {
		return this.largeurFaceMontantCorniere()
			+ this.largeurClairVitrage()
			+ this.largeurFaceMontantIntermediaire() / 2
			+ this.entreAxePercageMontantEtParcloseTraverseCorniere()
			- this.entreAxePercageMontantTraverseCorniere();
	}

	public double ecartEntreExtremiteEtPercageFixationSiUneSeulePartitionTraverseCorniere() {
		return 80;
	}

	public double entreAxePercageMontantTraverseCorniere() {
		return this.largeurClairVitrage() + this.largeurFaceMontantIntermediaire();
	}

	public double entreAxePercageMontantEtParcloseTraverseCorniere() {
		return 50;
	}
	//endregion

	//region Montant generique
	public double longueurADiviserMontant() {
		return this.longueurMontantCorniere()
			- 2 * this.ecartEntreExtremiteEtPremierPercageMontantCorniere();
	}

	public double entreAxePercagesMax() {
		return 600;
	}

	public int nbPercageMontant() {
		return (int) Math.ceil(this.longueurADiviserMontant() / this.entreAxePercagesMax()) + 1;
	}

	public double entreAxePercagesMontant() {
		return this.longueurADiviserMontant() / (this.nbPercageMontant() - 1);
	}
	//endregion

	//region Montant corniere
	public double longueurMontantCorniere() {
		return this.hauteurVerriere;
	}

	public double epaisseurMontantCorniere() {
		return 3;
	}

	public double largeurChampMontantCorniere() {
		return 30;
	}

	public double largeurFaceMontantCorniere() {
		return 20;
	}

	public double demiLargeurDroitChampMontantCorniere() {
		return epaisseurVitrage == 44.2 ? 23 : 21;
	}

	public double demiLargeurGaucheChampMontantCorniere() {
		return this.largeurChampMontantCorniere()
			- this.demiLargeurDroitChampMontantCorniere();
	}

	public double ecartEntreExtremiteEtPremierPercageMontantCorniere() {
		return 70;
	}
	//endregion

	//region Montant intermediaire
	public double epaisseurMontantIntermedaire() {
		return 4;
	}

	public double jeuEpaulement() {
		return 1;
	}

	public double longueurEpaulementMontantIntermediaire() {
		return this.largeurFaceTraverseCorniere()
			- this.epaisseurTraverseCorniere()
			+ this.jeuEpaulement();
	}

	public double epaisseurEpaulementMontantIntermediaire() {
		return this.epaisseurMontantIntermedaire();
	}

	public double longueurMontantIntermediaire() {
		return this.hauteurVerriere
			- 2 * this.epaisseurTraverseCorniere();
	}

	public double largeurFaceMontantIntermediaire() {
		return 30;
	}

	public double largeurChampMontantIntermediaire() {
		return 30;
	}

	public double demiLargeurDroitChampMontantIntermediaire() {
		return epaisseurVitrage == 44.2 ? 23 : 21;
	}

	public double demiLargeurGaucheChampMontantIntermediaire() {
		return this.largeurChampMontantIntermediaire()
			- this.demiLargeurDroitChampMontantIntermediaire();
	}

	public double ecartEntreExtremiteEtPremierPercageMontantIntermediaire() {
		return this.ecartEntreExtremiteEtPremierPercageMontantCorniere()
			- this.epaisseurTraverseCorniere();
	}
	//endregion

	//region Vitrage
	public double jeuVitrage() {
		return 4;
	}

	public int nbVitrage() {
		return this.nbPartitions;
	}
	public double largeurVitrage() {
		return this.entreAxePercageMontantTraverseCorniere()
			- this.epaisseurMontantIntermedaire()
			- 2 * this.jeuVitrage();
	}
	public double hauteurVitrage() {
		return this.hauteurVerriere
			- 2 * this.epaisseurTraverseCorniere()
			- 2 * this.jeuVitrage();
	}
	//endregion

	//region ClairDeVitrage
	public double largeurClairVitrage() {
		return (
			this.largeurVerriere
				- 2 * this.largeurFaceMontantCorniere()
				- (this.nbPartitions - 1) * this.largeurFaceMontantIntermediaire()
		) / this.nbPartitions;
	}

	public double hauteurClairVitrage() {
		return this.hauteurVerriere - 2 * this.largeurFaceTraverseCorniere();
	}
	//endregion

	//region Parclose
	public double jeuParclose() {
		return 1;
	}

	public double hauteurParcloseCorniere() {
		return 16;
	}

	public double hauteurParcloseT() {
		return 12;
	}

	public int nbParcloseTraverseLaterale() {
		return nbPartitions > 1 ? 4 : 2;
	}

	public double longueurParcloseTraverseLaterale() {
		return this.ecartEntreExtremiteEtPremierPercageMontantTraverseCorniere()
			- this.epaisseurMontantCorniere()
			- this.epaisseurMontantIntermedaire() / 2
			- this.jeuParclose();
	}

	public int nbParcloseTraverseCentrale() {
		return Math.max(0, (this.nbPartitions - 2) * 2);
	}

	public double longueurParcloseTraverseCentrale() {
		return this.entreAxePercageMontantTraverseCorniere()
			- this.epaisseurMontantIntermedaire()
			- this.jeuParclose();
	}

	public int nbParcloseMontantCorniere() {
		return 2;
	}

	public int nbParcloseMontantIntermediaire() {
		return (this.nbPartitions - 1) * 2;
	}

	public double longueurParcloseMontant() {
		return this.hauteurVerriere
			- 2 * (this.epaisseurTraverseCorniere() + this.hauteurParcloseCorniere())
			- this.jeuParclose();
	}
	//endregion
}

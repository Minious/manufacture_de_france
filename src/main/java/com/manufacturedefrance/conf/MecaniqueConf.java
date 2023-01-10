package com.manufacturedefrance.conf;

public class MecaniqueConf {
	public double hauteurVerriere;
	public double largeurVerriere;
	public int nbPartitions;

	public MecaniqueConf(double hauteurVerriere, double largeurVerriere, int nbPartitions) {
		this.hauteurVerriere = hauteurVerriere;
		this.largeurVerriere = largeurVerriere;
		this.nbPartitions = nbPartitions;
	}

	//region Nb trous
	public double nbTrousIntermediairesVerticaux() {
		return Math.max(1, Math.floor(this.hauteurVerriere / 500));
	}

	public int nbPairesTrousIntermediairesHorizontaux() {
		return this.nbPartitions - 1;
	}
	//endregion

	//region Jeux
	public double jeuEntreTraverseCorniereEtMontantPartition() {
		return 0.5;
	}
	//endregion

	//region T
	public double entreAxeT() {
		return 44;
	}
	//endregion

	//region Generique
	public double epaisseurProfil() {
		return 3;
	}
	//endregion

	//region Traverse corniere
	public double hauteurTraverseCorniere() {
		return this.largeurVerriere;
	}

	public double largeurTraverseCorniere() {
		return 30;
	}

	public double ecartEntreExtremiteEtPremierTrouTraverseCorniere() {
		return 29; // 21.5
	}

	public double ecartEntrePremierTrouEtDeuxiemeTrouTraverseCorniere() {
		return 23;
	}

	public double ecartEntreExtremiteEtDeuxiemeTrouTraverseCorniere() {
		return this.ecartEntreExtremiteEtPremierTrouTraverseCorniere()
			+ this.ecartEntrePremierTrouEtDeuxiemeTrouTraverseCorniere();
	}

	public double entreAxeLateralTraverseCorniere() {
		if(nbPartitions == 1)
			return this.hauteurTraverseCorniere() -
				2 * this.ecartEntreExtremiteEtDeuxiemeTrouTraverseCorniere();
		else
			return this.largeurClairDeVitrage()
				+ this.largeurMontantPartition() / 2
				- this.entreAxeT() / 2
				+ this.largeurMontantCorniere()
				- this.ecartEntreExtremiteEtDeuxiemeTrouTraverseCorniere();
	}

	public double entreAxeCentralTraverseCorniere() {
		return this.largeurClairDeVitrage()
			+ this.largeurMontantPartition() / 2 * 2
			- this.entreAxeT() / 2 * 2;
	}
	//endregion

	//region Contre cadre traverse corniere
	public double ecartEntreExtremiteEtPremierTrouContreCadreTraverseCorniere() {
		return 24.09;
	}

	public double ecartEntreExtremiteEtDeuxiemeTrouContreCadreTraverseCorniere() {
		return this.ecartEntreExtremiteEtPremierTrouTraverseCorniere()
			- this.ecartEntreExtremiteEtPremierTrouContreCadreTraverseCorniere();
	}

	public double hauteurContreCadreTraverseCorniere() {
		return this.hauteurTraverseCorniere()
			- 2 * this.ecartEntreExtremiteEtDeuxiemeTrouContreCadreTraverseCorniere();
	}

	public double largeurContreCadreTraverseCorniere() {
		return 25;
	}

	public double demiLargeurDroitContreCadreTraverseCorniere() {
		return 11.5;
	}

	public double demiLargeurGaucheContreCadreTraverseCorniere() {
		return this.largeurContreCadreTraverseCorniere()
			- demiLargeurDroitContreCadreTraverseCorniere();
	}
	//endregion

	//region Attaches traverse corniere
	public double ecartEntreExtremiteEtPremierTrouAttachesTraverseCorniere() {
		return 80;
	}

	public double entreAxeAttachesSouhaite() {
		return 500;
	}

	public double longueurADiviser() {
		return this.hauteurTraverseCorniere()
			- 2 * this.ecartEntreExtremiteEtPremierTrouAttachesTraverseCorniere();
	}

	public int nbAttachesIntermediaires() {
		int nbAttaches = (int) Math.floor(this.longueurADiviser() / this.entreAxeAttachesSouhaite());
		double entreAxeAttaches1 = this.longueurADiviser() / nbAttaches;
		double entreAxeAttaches2 = this.longueurADiviser() / (1 + nbAttaches);
		double ecartEntreSouhaiteEtReel1 = Math.abs(this.entreAxeAttachesSouhaite() - entreAxeAttaches1);
		double ecartEntreSouhaiteEtReel2 = Math.abs(this.entreAxeAttachesSouhaite() - entreAxeAttaches2);

		return ecartEntreSouhaiteEtReel1 < ecartEntreSouhaiteEtReel2 ? nbAttaches - 1 : nbAttaches;
	}

	public double entreAxeAttachesTraverseCorniere() {
		return this.longueurADiviser() / (this.nbAttachesIntermediaires() + 1);
	}
	//endregion

	//region Montant generique
	public double entreAxeMontant() {
		return (
			this.hauteurMontantPartition()
				- 2 * this.ecartEntreExtremiteEtPremierTrouMontantPartition()
		) / (this.nbTrousIntermediairesVerticaux() + 1);
	}
	//endregion

	//region Montant partition
	public double hauteurMontantPartition() {
		return this.hauteurVerriere
			- 2 * this.jeuEntreTraverseCorniereEtMontantPartition()
			- 2 * this.largeurTraverseCorniere();
	}

	public double ecartEntreExtremiteEtPremierTrouMontantPartition() {
		return 21.5;
	}

	public double largeurMontantPartition() {
		return 30;
	}
	//endregion

	//region Contre cadre montant corniere
	public double hauteurMontantCorniere() {
		return this.hauteurVerriere;
	}

	public double ecartEntreExtremiteEtPremierTrouMontantCorniere() {
		return 29;
	}

	public double ecartEntrePremierTrouEtDeuxiemeTrouMontantCorniere() {
		return 23;
	}

	public double largeurMontantCorniere() {
		return 30;
	}
	//endregion

	//region Contre cadre montant corniere
	public double largeurContreCadreMontantCorniere() {
		return 25;
	}

	public double demiLargeurDroitContreCadreMontantCorniere() {
		return 11.5;
	}

	public double demiLargeurGaucheContreCadreMontantCorniere() {
		return this.largeurContreCadreMontantCorniere()
			- this.demiLargeurDroitContreCadreMontantCorniere();
	}

	public double ecartEntreExtremiteEtPremierTrouContreCadreMontantCorniere() {
		return 24.09;
	}

	public double hauteurContreCadreMontantCorniere() {
		return this.hauteurMontantCorniere()
			- 2 * (
				this.ecartEntreExtremiteEtPremierTrouMontantCorniere()
				- this.ecartEntreExtremiteEtPremierTrouContreCadreMontantCorniere()
		);
	}
	//endregion

	//region Vitrage
	public double penetrationVitrageGaucheDroite() {
		return 8;
	}

	public double penetrationVitrageHautBas() {
		return 9;
	}

	public double largeurVitrage() {
		return this.largeurClairDeVitrage()
			+ 2 * this.penetrationVitrageGaucheDroite();
	}

	public double hauteurVitrage() {
		return this.hauteurClairDeVitrage()
			+ 2 * this.penetrationVitrageHautBas();
	}

	public int nbVitrage() {
		return this.nbPartitions;
	}
	//endregion

	//region ClairDeVitrage
	public double largeurClairDeVitrage() {
		return (
			this.hauteurTraverseCorniere()
				- 2 * this.largeurMontantCorniere()
				- this.nbPairesTrousIntermediairesHorizontaux() * this.largeurMontantPartition()
		) / this.nbPartitions;
	}

	public double hauteurClairDeVitrage() {
		return this.hauteurVerriere
			- 2 * this.largeurTraverseCorniere();
	}
	//endregion
}

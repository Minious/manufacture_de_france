package generateurCoteVerriere.modeles.mecanique;

import generateurCoteVerriere.ConfGenerique;
import generateurCoteVerriere.Utils;

public class Conf extends ConfGenerique{
	// Paramètres utilisateur
	public final double hauteurVerriere;
	public final double largeurVerriere;
	public final int nbPartitions;
	
	// Nb trous
	public final int nbTrousIntermediairesVerticaux;
	public final int nbPairesTrousIntermediairesHorizontaux;
	
	// Jeux
	public final double jeuEntreTraverseCorniereEtMontantPartition;
	
	// T
	public final double entreAxeT;
	
	// Clair de vitrage
	public final double largeurClairDeVitrage;
	public final double hauteurClairDeVitrage;
	
	// Traverse corniere
	public final double hauteurTraverseCorniere;
	public final double largeurTraverseCorniere;
	public final double ecartEntreExtremiteEtPremierTrouTraverseCorniere;
	public final double ecartEntrePremierTrouEtDeuxiemeTrouTraverseCorniere;
	public final double entreAxeLateralTraverseCorniere;
	public final double entreAxeCentralTraverseCorniere;

	// Contre cadre traverse corniere
	public final double largeurContreCadreTraverseCorniere;
	public final double hauteurContreCadreTraverseCorniere;
	public final double demiLargeurDroitContreCadreTraverseCorniere;
	public final double demiLargeurGaucheContreCadreTraverseCorniere;
	public final double ecartEntreExtremiteEtPremierTrouContreCadreTraverseCorniere;
	
	// Montant partition
	public final double hauteurMontantPartition;
	public final double ecartEntreExtremiteEtPremierTrouMontantPartition;
	public final double largeurMontantPartition;
	
	// Montant cornière
	public final double hauteurMontantCorniere;
	public final double ecartEntreExtremiteEtPremierTrouMontantCorniere;
	public final double ecartEntrePremierTrouEtDeuxiemeTrouMontantCorniere;
	public final double largeurMontantCorniere;

	// Contre cadre montant corniere
	public final double largeurContreCadreMontantCorniere;
	public final double demiLargeurDroitContreCadreMontantCorniere;
	public final double demiLargeurGaucheContreCadreMontantCorniere;
	public final double ecartEntreExtremiteEtPremierTrouContreCadreMontantCorniere;
	public final double hauteurContreCadreMontantCorniere;
	
	// Montant générique
	public final double entreAxeMontant;
	
	// Vitrage
	public final double penetrationVitrageGaucheDroite;
	public final double penetrationVitrageHautBas;
	public final double largeurVitrage;
	public final double hauteurVitrage;
	
	// Attaches traverse corniere
	public final double ecartEntreExtremiteEtPremierTrouAttachesTraverseCorniere;
	public final double entreAxeAttachesTraverseCorniere;
	public final int nbAttachesIntermediaires;
	
	public Conf(String ARC, String client, String reference, double hauteurVerriere, double largeurVerriere, int nbPartitions){
		super(ARC, client, reference);
		
		// this.savePath = savePath;
		
		this.ARC = ARC;
		this.reference = reference;
		
		// Paramètres utilisateur
		this.hauteurVerriere = hauteurVerriere;
		this.largeurVerriere = largeurVerriere;
		this.nbPartitions = nbPartitions;
		
		// Nb trous
		this.nbTrousIntermediairesVerticaux = Utils.getNbTrousVerticauxMecanique(this.hauteurVerriere);
		this.nbPairesTrousIntermediairesHorizontaux = this.nbPartitions - 1;
		
		// Jeux
		this.jeuEntreTraverseCorniereEtMontantPartition = 0.5;
		
		// T
		this.entreAxeT = 44;
		
		// Traverse cornière
		this.hauteurTraverseCorniere = this.largeurVerriere;
		this.ecartEntreExtremiteEtPremierTrouTraverseCorniere = 29; // 21.5; ???
		this.ecartEntrePremierTrouEtDeuxiemeTrouTraverseCorniere = 23;
		this.largeurTraverseCorniere = 30;
		
		// Contre cadre traverse cornière
		this.ecartEntreExtremiteEtPremierTrouContreCadreTraverseCorniere = 24.09;
		this.hauteurContreCadreTraverseCorniere = this.hauteurTraverseCorniere - 2 * (this.ecartEntreExtremiteEtPremierTrouTraverseCorniere - this.ecartEntreExtremiteEtPremierTrouContreCadreTraverseCorniere);
		this.largeurContreCadreTraverseCorniere = 25;
		this.demiLargeurDroitContreCadreTraverseCorniere = 11.5;
		this.demiLargeurGaucheContreCadreTraverseCorniere = this.largeurContreCadreTraverseCorniere - this.demiLargeurDroitContreCadreTraverseCorniere;
		
		// Montant partition
		this.hauteurMontantPartition = this.hauteurVerriere - 2 * this.jeuEntreTraverseCorniereEtMontantPartition - 2 * this.largeurTraverseCorniere;
		this.ecartEntreExtremiteEtPremierTrouMontantPartition = 21.5;
		this.largeurMontantPartition = 30;
		
		// Montant cornière
		this.hauteurMontantCorniere = this.hauteurVerriere;
		this.ecartEntreExtremiteEtPremierTrouMontantCorniere = 29;
		this.ecartEntrePremierTrouEtDeuxiemeTrouMontantCorniere = 23;
		this.largeurMontantCorniere = 30;
		
		// ClairDeVitrage
		this.largeurClairDeVitrage = (this.hauteurTraverseCorniere - 2 * this.largeurMontantCorniere - this.nbPairesTrousIntermediairesHorizontaux * this.largeurMontantPartition) / this.nbPartitions;
		this.hauteurClairDeVitrage = this.hauteurVerriere - 2 * this.largeurTraverseCorniere;
		
		// Traverse cornière suite
		if(this.nbPartitions == 1)
			this.entreAxeLateralTraverseCorniere = this.hauteurTraverseCorniere - 2 * (this.ecartEntreExtremiteEtPremierTrouTraverseCorniere + this.ecartEntrePremierTrouEtDeuxiemeTrouTraverseCorniere);
		else
			this.entreAxeLateralTraverseCorniere = this.largeurClairDeVitrage + this.largeurMontantPartition / 2 - this.entreAxeT / 2 + this.largeurMontantCorniere - this.ecartEntreExtremiteEtPremierTrouTraverseCorniere - this.ecartEntrePremierTrouEtDeuxiemeTrouTraverseCorniere;
		this.entreAxeCentralTraverseCorniere = this.largeurClairDeVitrage + this.largeurMontantPartition / 2 * 2 - this.entreAxeT / 2 * 2;
		
		// Contre cadre montant corniere
		this.largeurContreCadreMontantCorniere = 25;
		this.demiLargeurDroitContreCadreMontantCorniere = 11.5;
		this.demiLargeurGaucheContreCadreMontantCorniere = this.largeurContreCadreMontantCorniere - this.demiLargeurDroitContreCadreMontantCorniere;
		this.ecartEntreExtremiteEtPremierTrouContreCadreMontantCorniere = 24.09;
		this.hauteurContreCadreMontantCorniere = this.hauteurMontantCorniere - 2 * (this.ecartEntreExtremiteEtPremierTrouMontantCorniere - this.ecartEntreExtremiteEtPremierTrouContreCadreMontantCorniere);
		
		// Montant générique
		this.entreAxeMontant = (this.hauteurMontantPartition - 2 * this.ecartEntreExtremiteEtPremierTrouMontantPartition) / (this.nbTrousIntermediairesVerticaux + 1);
	
		// Vitrage
		this.penetrationVitrageGaucheDroite = 8;
		this.penetrationVitrageHautBas = 9;
		this.largeurVitrage = this.largeurClairDeVitrage + 2 * this.penetrationVitrageGaucheDroite;
		this.hauteurVitrage = this.hauteurClairDeVitrage + 2 * this.penetrationVitrageHautBas;
		
		// Attaches traverse corniere
		this.ecartEntreExtremiteEtPremierTrouAttachesTraverseCorniere = 80;
		ValeursAttaches valeursAttaches = calculEntreAxeAttachesTraverseCorniere();
		this.entreAxeAttachesTraverseCorniere = valeursAttaches.getEntreAxeAttachesTraverseCorniere();
		this.nbAttachesIntermediaires = valeursAttaches.getNbAttachesintermediaires();
	}
	
	class ValeursAttaches{
		private double entreAxeAttachesTraverseCorniere;
		private int nbAttachesintermediaires;
		
		public ValeursAttaches(double entreAxeAttachesTraverseCorniere, int nbAttachesintermediaires){
			this.entreAxeAttachesTraverseCorniere = entreAxeAttachesTraverseCorniere;
			this.nbAttachesintermediaires = nbAttachesintermediaires;
		}
		
		public double getEntreAxeAttachesTraverseCorniere(){
			return this.entreAxeAttachesTraverseCorniere;
		}
		
		public int getNbAttachesintermediaires(){
			return this.nbAttachesintermediaires;
		}
	}
	
	private ValeursAttaches calculEntreAxeAttachesTraverseCorniere(){
		double ecartEntreAttachesSouhaite = 500;
		double temp1 = this.hauteurTraverseCorniere - 2 * this.ecartEntreExtremiteEtPremierTrouAttachesTraverseCorniere;
		double temp2 = Math.floor((this.hauteurTraverseCorniere - 2 * this.ecartEntreExtremiteEtPremierTrouAttachesTraverseCorniere) / ecartEntreAttachesSouhaite);
		double value1 = temp1 / temp2;
		double value2 = temp1 / (1 + temp2);
		double ecart1 = Math.abs(ecartEntreAttachesSouhaite - value1);
		double ecart2 = Math.abs(ecartEntreAttachesSouhaite - value2);
		
		if(ecart1 < ecart2)
			return new ValeursAttaches(value1, (int) temp2 - 1);
		else
			return new ValeursAttaches(value2, (int) temp2);
	}
}

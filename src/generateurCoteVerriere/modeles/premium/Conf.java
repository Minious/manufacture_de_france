package generateurCoteVerriere.modeles.premium;

import generateurCoteVerriere.ConfGenerique;

public class Conf extends ConfGenerique{
	// Paramètres utilisateur
	public final double hauteurVerriere;
	public final double largeurVerriere;
	public final int nbPartitions;
	
	// Nb trous
	public final int nbTrousIntermediairesVerticaux;
	public final int nbTrousIntermediairesHorizontaux;
	
	// Jeux
	//////////////
	
	// Clair de vitrage
	public final double largeurClairDeVitrage;
	public final double hauteurClairDeVitrage;
	
	// Traverse corniere
	public final double longueurTraverseCorniere;
	public final double largeurFaceATraverseCorniere;
	public final double largeurFaceBTraverseCorniere;
	public final double epaisseurTraverseCorniere;
	public final double ecartEntreExtremiteEtPremierTrouTraverseCorniere;
	public final double ecartEntrePremierTrouEtDeuxiemeTrouTraverseCorniere;
	public final double entreAxeTraverseCorniere;
	
	// Montant générique
	public final double entreAxeMontant;
	
	// Montant partition
	public final double longueurMontantPartition;
	public final double largeurFaceAMontantPartition;
	public final double largeurFaceBMontantPartition;
	public final double epaisseurMontantPartition;
	public final double ecartEntreExtremiteEtPremierTrouMontantPartition;
	
	// Montant cornière
	public final double longueurMontantCorniere;
	public final double largeurFaceAMontantCorniere;
	public final double largeurFaceBMontantCorniere;
	public final double ecartEntreExtremiteEtPremierTrouMontantCorniere;
	
	// Vitrage
	/*
	public final double penetrationVitrageGaucheDroite;
	public final double penetrationVitrageHautBas;
	public final double largeurVitrage;
	public final double hauteurVitrage;
	*/
	
	// Attaches traverse corniere
	/*
	public final double ecartEntreExtremiteEtPremierTrouAttachesTraverseCorniere;
	public final double entreAxeAttachesTraverseCorniere;
	*/
	
	public Conf(String ARC, String client, String reference, double hauteurVerriere, double largeurVerriere, int nbPartitions){
		super(ARC, client, reference);
		
		// this.savePath = savePath;
		
		this.ARC = ARC;
		this.reference = reference;
		
		// Paramètres utilisateur
		this.hauteurVerriere = hauteurVerriere;
		this.largeurVerriere = largeurVerriere;
		this.nbPartitions = nbPartitions;

		// Traverse cornière
		this.longueurTraverseCorniere = this.largeurVerriere;
		this.largeurFaceATraverseCorniere = 20;
		this.largeurFaceBTraverseCorniere = 30;
		this.epaisseurTraverseCorniere = 3;
		
		// Montant partition
		this.longueurMontantPartition = this.hauteurVerriere - 2 * this.epaisseurTraverseCorniere;
		this.largeurFaceAMontantPartition = 30;
		this.largeurFaceBMontantPartition = 30;
		this.epaisseurMontantPartition = 4;
		this.ecartEntreExtremiteEtPremierTrouMontantPartition = 70;
		
		// Nb trous
		this.nbTrousIntermediairesVerticaux = (int) Math.floor((this.hauteurVerriere - 2 * this.ecartEntreExtremiteEtPremierTrouMontantPartition) / 500);
		this.nbTrousIntermediairesHorizontaux = this.nbPartitions - 1;
		
		// Montant cornière
		this.longueurMontantCorniere = this.hauteurVerriere;
		this.largeurFaceAMontantCorniere = 20;
		this.largeurFaceBMontantCorniere = 30;
		this.ecartEntreExtremiteEtPremierTrouMontantCorniere = 70;
		
		// Jeux
		//////////////
		
		// ClairDeVitrage
		this.largeurClairDeVitrage = (this.largeurVerriere - 2 * this.largeurFaceAMontantCorniere - this.nbTrousIntermediairesHorizontaux * this.largeurFaceAMontantPartition) / this.nbPartitions;
		this.hauteurClairDeVitrage = this.hauteurVerriere - 2 * this.largeurFaceATraverseCorniere;
				
		// Traverse cornière (suite)
		this.ecartEntreExtremiteEtPremierTrouTraverseCorniere = 50;
		this.ecartEntrePremierTrouEtDeuxiemeTrouTraverseCorniere = this.largeurClairDeVitrage + this.largeurFaceAMontantPartition + this.largeurFaceAMontantPartition / 2 - this.ecartEntreExtremiteEtPremierTrouTraverseCorniere;
		
		// Traverse cornière suite
		if(this.nbPartitions == 1)
			this.entreAxeTraverseCorniere = this.longueurTraverseCorniere - 2 * (this.ecartEntreExtremiteEtPremierTrouTraverseCorniere + this.ecartEntrePremierTrouEtDeuxiemeTrouTraverseCorniere);
		else
			this.entreAxeTraverseCorniere = (this.longueurTraverseCorniere - 2 * this.ecartEntreExtremiteEtPremierTrouTraverseCorniere - 2 * this.ecartEntrePremierTrouEtDeuxiemeTrouTraverseCorniere) / (this.nbTrousIntermediairesHorizontaux - 2);
			//this.entreAxeTraverseCorniere = this.largeurClairDeVitrage + this.largeurMontantPartition / 2 - this.entreAxeT / 2 + this.largeurMontantCorniere - this.ecartEntreExtremiteEtPremierTrouTraverseCorniere - this.ecartEntrePremierTrouEtDeuxiemeTrouTraverseCorniere;
		
		// Montant générique
		this.entreAxeMontant = (this.longueurMontantPartition - 2 * this.ecartEntreExtremiteEtPremierTrouMontantPartition) / this.nbTrousIntermediairesVerticaux;
	
		// Vitrage
		/*
		this.penetrationVitrageGaucheDroite = 8;
		this.penetrationVitrageHautBas = 9;
		this.largeurVitrage = this.largeurClairDeVitrage + 2 * this.penetrationVitrageGaucheDroite;
		this.hauteurVitrage = this.hauteurClairDeVitrage + 2 * this.penetrationVitrageHautBas;
		*/
		
		// Attaches traverse corniere
		/*
		this.ecartEntreExtremiteEtPremierTrouAttachesTraverseCorniere = 80;
		ValeursAttaches valeursAttaches = calculEntreAxeAttachesTraverseCorniere();
		this.entreAxeAttachesTraverseCorniere = valeursAttaches.getEntreAxeAttachesTraverseCorniere();
		this.nbAttachesIntermediaires = valeursAttaches.getNbAttachesintermediaires();
		*/
	}
	
	/*
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
		double temp1 = this.longueurTraverseCorniere - 2 * this.ecartEntreExtremiteEtPremierTrouAttachesTraverseCorniere;
		double temp2 = Math.floor((this.longueurTraverseCorniere - 2 * this.ecartEntreExtremiteEtPremierTrouAttachesTraverseCorniere) / ecartEntreAttachesSouhaite);
		double value1 = temp1 / temp2;
		double value2 = temp1 / (1 + temp2);
		double ecart1 = Math.abs(ecartEntreAttachesSouhaite - value1);
		double ecart2 = Math.abs(ecartEntreAttachesSouhaite - value2);
		
		if(ecart1 < ecart2)
			return new ValeursAttaches(value1, (int) temp2 - 1);
		else
			return new ValeursAttaches(value2, (int) temp2);
	}
	*/
}

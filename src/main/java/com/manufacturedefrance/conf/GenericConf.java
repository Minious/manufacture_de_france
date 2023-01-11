package com.manufacturedefrance.conf;

public abstract class GenericConf {
	protected double hauteurVerriere;
	protected double largeurVerriere;
	protected int nbPartitions;

	public GenericConf(double hauteurVerriere, double largeurVerriere, int nbPartitions) {
		this.hauteurVerriere = hauteurVerriere;
		this.largeurVerriere = largeurVerriere;
		this.nbPartitions = nbPartitions;
	}

	public double getHauteurVerriere() {
		return hauteurVerriere;
	}

	public double getLargeurVerriere() {
		return largeurVerriere;
	}

	public int getNbPartitions() {
		return nbPartitions;
	}
}

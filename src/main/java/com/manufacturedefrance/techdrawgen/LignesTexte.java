package com.manufacturedefrance.techdrawgen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.manufacturedefrance.utils.MyPoint2D;

public class LignesTexte extends MyCustomSvgEnhanced {
	private static final int TAILLE_POLICE_PAR_DEFAUT = 15;
	private static final double MARGE_ENTRE_LIGNES_PAR_DEFAUT = 2;

	private ArrayList<String> lignes;
	private int taillePoliceDonnees;
	private ShiftMode shiftMode;
	private double margeEntreLignes;

	public LignesTexte() {
		this(Arrays.asList());
	}

	public LignesTexte(String ligne) {
		this(Arrays.asList(ligne));
	}
	
	public LignesTexte(String ligne, int taillePoliceDonnees) {
		this(Arrays.asList(ligne), taillePoliceDonnees);
	}
	
	public LignesTexte(String ligne, int taillePoliceDonnees, boolean isBold) {
		this(Arrays.asList(ligne), taillePoliceDonnees, isBold);
	}
	
	public LignesTexte(String ligne, int taillePoliceDonnees, boolean isBold, ShiftMode shiftMode) {
		this(Arrays.asList(ligne), taillePoliceDonnees, isBold, shiftMode);
	}
	
	public LignesTexte(List<String> lignes) {
		this(lignes, TAILLE_POLICE_PAR_DEFAUT);
	}
	
	public LignesTexte(List<String> lignes, int taillePoliceDonnees) {
		this(lignes, taillePoliceDonnees, false);
	}
	
	public LignesTexte(List<String> lignes, int taillePoliceDonnees, boolean isBold) {
		this(lignes, taillePoliceDonnees, isBold, ShiftMode.LEFT);
	}
	
	public LignesTexte(List<String> lignes, int taillePoliceDonnees, boolean isBold, ShiftMode shiftMode) {
		this(lignes, taillePoliceDonnees, isBold, shiftMode, MARGE_ENTRE_LIGNES_PAR_DEFAUT);
	}
	
	public LignesTexte(List<String> lignes, int taillePoliceDonnees, boolean isBold, ShiftMode shiftMode, double margeEntreLignes) {
		this.lignes = new ArrayList<>();
		this.lignes.addAll(lignes);
		this.taillePoliceDonnees = taillePoliceDonnees;
		this.shiftMode = shiftMode;
		this.margeEntreLignes = margeEntreLignes;
		this.setFontSize(taillePoliceDonnees);
		this.setFontBold(isBold);
		
		render();
	}
	
	public void addLigne(String ligne) {
		addLignes(Arrays.asList(ligne));
	}
	
	public void addLignes(List<String> lignes) {
		this.lignes.addAll(lignes);
		render();
	}
	
	private void render() {
		this.clear();
		for(int i=0;i<this.lignes.size();i++)
			this.drawString(this.lignes.get(i), new MyPoint2D(0, i * (taillePoliceDonnees + margeEntreLignes)), 0, shiftMode);
	}
}

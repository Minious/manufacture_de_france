package generateurCoteVerriere;

import java.util.Arrays;
import java.util.List;

import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced;
import myCustomSvgLibraryEnhanced.Point;

public class LignesTexte extends MyCustomSvgEnhanced {
	private final static int TAILLE_POLICE_PAR_DEFAUT = 15;
	private final static double MARGE_ENTRE_LIGNES_PAR_DEFAUT = 2;
	
	public LignesTexte(String ligne) {
		this(Arrays.asList(new String[] {ligne}));
	}
	
	public LignesTexte(String ligne, int taillePoliceDonnees) {
		this(Arrays.asList(new String[] {ligne}), taillePoliceDonnees);
	}
	
	public LignesTexte(String ligne, int taillePoliceDonnees, ShiftMode shiftMode) {
		this(Arrays.asList(new String[] {ligne}), taillePoliceDonnees, shiftMode);
	}
	
	public LignesTexte(List<String> lignes) {
		this(lignes, TAILLE_POLICE_PAR_DEFAUT);
	}
	
	public LignesTexte(List<String> lignes, int taillePoliceDonnees) {
		this(lignes, taillePoliceDonnees, ShiftMode.LEFT);
	}
	
	public LignesTexte(List<String> lignes, int taillePoliceDonnees, ShiftMode shiftMode) {
		this(lignes, taillePoliceDonnees, shiftMode, MARGE_ENTRE_LIGNES_PAR_DEFAUT);
	}
	
	public LignesTexte(List<String> lignes, int taillePoliceDonnees, ShiftMode shiftMode, double margeEntreLignes) {
		this.setFontSize(taillePoliceDonnees);
		for(int i=0;i<lignes.size();i++)
			this.drawString(lignes.get(i), new Point(0, i * (taillePoliceDonnees + margeEntreLignes)), 0, shiftMode);
	}
}

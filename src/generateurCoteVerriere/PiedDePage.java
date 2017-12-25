package generateurCoteVerriere;

import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced;
import myCustomSvgLibraryEnhanced.Point;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced.ShiftMode;

public class PiedDePage {
	private final int taillePoliceDonnees = 15;
	private final double margeEntreDonnees = 2;
	private final double margeInferieureDonnees = 10;
	
	public void drawImage(MyCustomSvgEnhanced g, String ARC, String client, String reference) {
		g.setFontSize(this.taillePoliceDonnees);
		g.drawString("ARC : " + ARC, new Point(0, (double) g.getHeight() - 3 * (taillePoliceDonnees + margeEntreDonnees) - margeInferieureDonnees), 10, ShiftMode.LEFT);
		g.drawString("Client : " + client, new Point(0, (double) g.getHeight() - 2 * (taillePoliceDonnees + margeEntreDonnees) - margeInferieureDonnees), 10, ShiftMode.LEFT);
		g.drawString("Ref : " + reference, new Point(0, (double) g.getHeight() - (taillePoliceDonnees + margeEntreDonnees) - margeInferieureDonnees), 10, ShiftMode.LEFT);
		//g.drawString("Dimensions vitrage : " + conf.largeurVitrage + " x " + conf.hauteurVitrage, new Point(0, (double) g.getHeight() - margeInferieureDonnees), 10, ShiftMode.LEFT);
	}
}

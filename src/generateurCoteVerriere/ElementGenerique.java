package generateurCoteVerriere;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import generateurCoteVerriere.modeles.mecanique.Conf;
import myCustomSvgLibrary.MyCustomSvg;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced;
import myCustomSvgLibraryEnhanced.Point;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced.ShiftMode;

public abstract class ElementGenerique {
	protected Conf conf;

	protected final int taillePoliceDonnees = 15;
	protected final String font = "Arial";
	
	public ElementGenerique(Conf conf){
		this.conf = conf;
	}
	
	public void renderImage(Path savePath) throws IOException{
		// Création d'une l'image vierge
		MyCustomSvgEnhanced g = new MyCustomSvgEnhanced(getLargeurImage(), getHauteurImage());
		
		// Initialisation des paramètres de rendu
		
		// Remplie l'image avec un fond blanc
		/*
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, g.getWidth(), g.getHeight());
		*/
		g.setColor(Color.BLACK);
		//g.drawRect(0, 0, g.getWidth(), g.getHeight());
		
		drawImage(g);
		
		double margeEntreDonnees = 2;
		double margeInferieureDonnees = 10;
		g.setFont(this.taillePoliceDonnees, font);
		g.drawString("ARC : " + conf.getARC(), new Point(0, (double) g.getHeight() - 3 * (taillePoliceDonnees + margeEntreDonnees) - margeInferieureDonnees), 10, ShiftMode.LEFT);
		g.drawString("Client : " + conf.getClient(), new Point(0, (double) g.getHeight() - 2 * (taillePoliceDonnees + margeEntreDonnees) - margeInferieureDonnees), 10, ShiftMode.LEFT);
		g.drawString("Ref : " + conf.getReference(), new Point(0, (double) g.getHeight() - (taillePoliceDonnees + margeEntreDonnees) - margeInferieureDonnees), 10, ShiftMode.LEFT);
		g.drawString("Dimensions vitrage : " + conf.largeurVitrage + " x " + conf.hauteurVitrage, new Point(0, (double) g.getHeight() - margeInferieureDonnees), 10, ShiftMode.LEFT);

		Path outputFilePath = savePath.resolve(getNomFichierDeRendu() + ".svg");
		g.writeToSVG(outputFilePath);
	}

	protected abstract void drawImage(MyCustomSvgEnhanced g);
	protected abstract int getLargeurImage();
	protected abstract int getHauteurImage();
	public abstract String getNomFichierDeRendu();
}

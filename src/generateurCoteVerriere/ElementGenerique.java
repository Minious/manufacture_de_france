package generateurCoteVerriere;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

import myCustomSvgLibrary.MyCustomSvg;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced.ShiftMode;

public abstract class ElementGenerique {
	protected HashMap<String, Double> conf;
	protected HashMap<String, Object> data;
	
	public ElementGenerique(HashMap<String, Double> conf, HashMap<String, Object> data){
		this.conf = conf;
		this.data = data;
	}
	
	public void renderImage(Path savePath) throws IOException{
		// Création d'une l'image vierge
		MyCustomSvgEnhanced g = new MyCustomSvgEnhanced();

		g.drawSvg(this.getEntete(), 0, 0, ShiftMode.CENTER);
		g.drawSvg(this.getDessin(), 0, 150, ShiftMode.CENTER);
		g.drawSvg(this.getPiedDePage(), 0, 800, ShiftMode.CENTER);
		
		/*
		double margeEntreDonnees = 2;
		double margeInferieureDonnees = 10;
		g.setFont(this.taillePoliceDonnees, this.font);
		g.drawString("ARC : " + conf.getARC(), new Point(0, (double) g.getHeight() - 3 * (taillePoliceDonnees + margeEntreDonnees) - margeInferieureDonnees), 10, ShiftMode.LEFT);
		g.drawString("Client : " + conf.getClient(), new Point(0, (double) g.getHeight() - 2 * (taillePoliceDonnees + margeEntreDonnees) - margeInferieureDonnees), 10, ShiftMode.LEFT);
		g.drawString("Ref : " + conf.getReference(), new Point(0, (double) g.getHeight() - (taillePoliceDonnees + margeEntreDonnees) - margeInferieureDonnees), 10, ShiftMode.LEFT);
		g.drawString("Dimensions vitrage : " + conf.largeurVitrage + " x " + conf.hauteurVitrage, new Point(0, (double) g.getHeight() - margeInferieureDonnees), 10, ShiftMode.LEFT);
		*/
		
		Path outputFilePath = savePath.resolve(getNomFichierDeRendu() + ".svg");
		g.writeToSVG(outputFilePath);
	}

	protected abstract int getNbElements();
	protected abstract MyCustomSvg getEntete();
	protected abstract MyCustomSvg getDessin();
	protected abstract MyCustomSvg getPiedDePage();
	public abstract String getNomFichierDeRendu();
}

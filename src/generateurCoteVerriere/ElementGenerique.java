package generateurCoteVerriere;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

import myCustomSvgLibrary.MyCustomSvg;
import myCustomSvgLibrary.MyHandyLayout;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced.ShiftMode;

public abstract class ElementGenerique {
	protected HashMap<String, Double> conf;
	protected HashMap<String, Object> data;
	
	public ElementGenerique(HashMap<String, Double> conf, HashMap<String, Object> data){
		this.conf = conf;
		this.data = data;
	}
	
	public void renderImage(Path savePath) throws IOException{
		MyCustomSvg entete = this.getEntete();
		MyCustomSvg dessin = this.getDessin();
		MyCustomSvg piedDePage = this.getPiedDePage();
		
		MyHandyLayout l = new MyHandyLayout();
		l.addRow(entete, ShiftMode.CENTER);
		l.addRow(dessin, ShiftMode.CENTER);
		//l.addRow(new MyCustomSvg[] {dessin, dessin}, ShiftMode.CENTER);
		l.addRow(piedDePage, ShiftMode.LEFT);
		MyCustomSvg g = l.getSvg();
		
		Path outputFilePath = savePath.resolve(getNomFichierDeRendu() + ".svg");
		g.writeToSVG(outputFilePath);
	}

	protected abstract int getNbElements();
	protected abstract MyCustomSvg getEntete();
	protected abstract MyCustomSvg getDessin();
	protected abstract MyCustomSvg getPiedDePage();
	public abstract String getNomFichierDeRendu();
}

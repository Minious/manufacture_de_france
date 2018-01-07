package generateurCoteVerriere;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

import myCustomSvgLibrary.MyCustomSvg;
import myCustomSvgLibrary.Padding;

public abstract class ElementGenerique {
	protected HashMap<String, Double> conf;
	protected HashMap<String, Object> data;
	
	public ElementGenerique(HashMap<String, Double> conf, HashMap<String, Object> data){
		this.conf = conf;
		this.data = data;
	}
	
	public void renderImage(Path savePath) throws IOException{
		MyCustomSvg dessin = this.getDessin();
		dessin.setPadding(new Padding(10));
		
		Path outputFilePath = savePath.resolve(getNomFichierDeRendu() + ".svg");
		dessin.writeToSVG(outputFilePath);
	}

	public abstract int getNbElements();
	public abstract MyCustomSvg getDessin();
	public abstract String getNomFichierDeRendu();
}

package generateurCoteVerriere;

import java.util.HashMap;

import myCustomSvgLibrary.MyCustomSvg;

public abstract class ElementGenerique {
	protected HashMap<String, Double> conf;
	protected HashMap<String, Object> data;
	
	public ElementGenerique(HashMap<String, Double> conf, HashMap<String, Object> data){
		this.conf = conf;
		this.data = data;
	}

	public abstract int getNbElements();
	public abstract MyCustomSvg getDessin();
}

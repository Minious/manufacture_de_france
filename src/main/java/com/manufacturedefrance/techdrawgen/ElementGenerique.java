package com.manufacturedefrance.techdrawgen;

import java.util.Map;

import com.manufacturedefrance.svgen.MyCustomSvg;

public abstract class ElementGenerique {
	protected Map<String, Double> conf;
	protected Map<String, Object> data;
	
	public ElementGenerique(Map<String, Double> conf, Map<String, Object> data){
		this.conf = conf;
		this.data = data;
	}

	public abstract int getNbElements();
	public abstract MyCustomSvg getDessin();
}

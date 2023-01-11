package com.manufacturedefrance.techdrawgen;

import java.util.Map;

import com.manufacturedefrance.svgen.MyCustomSvg;

public abstract class ElementGenerique {
	protected Map<String, Object> data;
	
	public ElementGenerique(Map<String, Object> data){
		this.data = data;
	}

	public abstract int getNbElements();
	public abstract MyCustomSvg getDessin();
}

package com.manufacturedefrance.techdrawgen.modeles.mecanique.elements;

import com.manufacturedefrance.conf.MecaniqueConf;
import com.manufacturedefrance.techdrawgen.ElementGenerique;

import java.util.Map;

public abstract class ElementMecanique extends ElementGenerique {
	protected MecaniqueConf conf;

	public ElementMecanique(Map<String, Object> data, MecaniqueConf conf) {
		super(data);
		this.conf = conf;
	}
}

package com.manufacturedefrance.techdrawgen.modeles.premium.elements;

import com.manufacturedefrance.conf.PremiumConf;
import com.manufacturedefrance.techdrawgen.ElementGenerique;

import java.util.Map;

public abstract class ElementPremium extends ElementGenerique {
	protected PremiumConf conf;

	public ElementPremium(Map<String, Object> data, PremiumConf conf) {
		super(data);
		this.conf = conf;
	}
}

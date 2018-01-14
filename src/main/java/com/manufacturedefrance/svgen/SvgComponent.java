package com.manufacturedefrance.svgen;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public abstract class SvgComponent {
	protected StyleContext sc;

	public static final DecimalFormat DOUBLE_FORMAT = new DecimalFormat("###.##", new DecimalFormatSymbols(Locale.ENGLISH));
	
	SvgComponent(StyleContext sc) {
		this.sc = sc.clone();
	}

	public abstract String renderTag();
	public abstract SvgComponent duplicate();
}

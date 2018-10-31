package com.manufacturedefrance.svgen;

import com.manufacturedefrance.svgen.styling.StyleContext;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public abstract class SvgComponent {
	protected StyleContext sc;

	public static final DecimalFormat DOUBLE_FORMAT = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.ENGLISH));
	
	SvgComponent(StyleContext sc) {
		this.sc = new StyleContext(sc);
	}

	public abstract String renderTag();
	abstract SvgComponent duplicate();
}

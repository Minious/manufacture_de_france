package com.manufacturedefrance.svgen;

import com.manufacturedefrance.svgen.styling.StyleContext;
import com.manufacturedefrance.svgen.tags.TextTag;

public class StrSVG extends SvgComponent {
	private String str;
	private double x;
	private double y;
	
	StrSVG(String str, double x, double y, StyleContext sc) {
		super(sc);
		this.str = str;
		this.x = x;
		this.y = y;
	}
	
	public String renderTag() {
		TextTag tag = new TextTag();
		tag.x(this.x);
		tag.y(this.y);
		tag.style(this.sc.getFontStyle());
		tag.translate(this.sc.getTranslateX(), this.sc.getTranslateY());
		tag.rotate(this.sc.getRotation());

		return tag.render(this.str);
	}

	@Override
	SvgComponent duplicate() {
		return new StrSVG(this.str, this.x, this.y, this.sc);
	}
}

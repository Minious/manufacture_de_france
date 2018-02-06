package com.manufacturedefrance.svgen;

import com.manufacturedefrance.svgen.styling.StyleContext;
import com.manufacturedefrance.svgen.tags.LineTag;

public class LineSVG extends SvgComponent {
	private double x1;
	private double y1;
	private double x2;
	private double y2;
	
	LineSVG(double x1, double y1, double x2, double y2, StyleContext sc) {
		super(sc);
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public String renderTag() {
		LineTag tag = new LineTag();
		tag.x1(x1);
		tag.y1(y1);
		tag.x2(x2);
		tag.y2(y2);
		tag.stroke(this.sc.getStroke(), this.sc.getStrokeColor());
		tag.translate(this.sc.getTranslateX(), this.sc.getTranslateY());
		tag.rotate(this.sc.getRotation());

		return tag.render();
	}

	@Override
	SvgComponent duplicate() {
		return new LineSVG(x1, y1, x2, y2, sc);
	}
}

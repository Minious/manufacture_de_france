package com.manufacturedefrance.svgen;

import com.manufacturedefrance.svgen.styling.StyleContext;
import com.manufacturedefrance.svgen.tags.RectTag;

public class RectSVG extends SvgComponent {
	private double x;
	private double y;
	private double width;
	private double height;
	
	RectSVG(double x, double y, double width, double height, StyleContext sc) {
		super(sc);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public String renderTag() {
		RectTag tag = new RectTag();
		tag.x(this.x);
		tag.y(this.y);
		tag.width(this.width);
		tag.height(this.height);
		tag.stroke(this.sc.getStroke(), this.sc.getStrokeColor());
		tag.shape(this.sc.getFillColor());
		tag.translate(this.sc.getTranslateX(), this.sc.getTranslateY());
		tag.rotate(this.sc.getRotation());

		return tag.render();
	}

	@Override
	SvgComponent duplicate() {
		return new RectSVG(this.x, this.y, this.width, this.height, this.sc);
	}
}

package com.manufacturedefrance.svgen;

import com.manufacturedefrance.svgen.styling.StyleContext;
import com.manufacturedefrance.svgen.tags.EllipseTag;

public class EllipseSVG extends SvgComponent {
	private double x;
	private double y;
	private double width;
	private double height;
	
	EllipseSVG(double x, double y, double width, double height, StyleContext sc) {
		super(sc);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public String renderTag() {
		double rx = this.width / 2;
		double ry = this.height / 2;
		double cx = this.x + rx;
		double cy = this.y + ry;

		EllipseTag tag = new EllipseTag();
		tag.cx(cx);
		tag.cy(cy);
		tag.rx(rx);
		tag.ry(ry);
		tag.stroke(this.sc.getStroke(), this.sc.getStrokeColor());
		tag.shape(this.sc.getFillColor());
		tag.translate(this.sc.getTranslateX(), this.sc.getTranslateY());
		tag.rotate(this.sc.getRotation());

		return tag.render();
	}

	@Override
	SvgComponent duplicate() {
		return new EllipseSVG(this.x, this.y, this.width, this.height, this.sc);
	}
}

package com.manufacturedefrance.svgen;

import java.text.DecimalFormat;

import com.manufacturedefrance.svgen.styling.StyleContext;
import com.manufacturedefrance.techdrawgen.Utils;
import com.manufacturedefrance.svgen.tags.PathTag;

public class EllipticalArcSVG extends SvgComponent {
	private double x;
	private double y;
	private double width;
	private double height;
	private double start;
	private double end;
	
	EllipticalArcSVG(double x, double y, double width, double height, double start, double end, StyleContext sc) {
		super(sc);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.start = start;
		this.end = end;
	}
	
	public String renderTag() {
		double xStart = this.x + Math.cos(start) * width / 2;
		double yStart = this.y - Math.sin(start) * width / 2;
		double xEnd = this.x + Math.cos(end) * height / 2;
		double yEnd = this.y - Math.sin(end) * height / 2;
		
		DecimalFormat df = new DecimalFormat("#.#");
        df.setMaximumFractionDigits(8);

        double simplifiedStart = Utils.positiveModulo(this.start, 2 * Math.PI);
        double simplifiedEnd = Utils.positiveModulo(this.end, 2 * Math.PI);
		boolean largeArcFlag = (simplifiedEnd - simplifiedStart + (Math.PI * 2)) % (Math.PI * 2) > Math.PI;
		boolean sweepFlag = false;

		PathTag tag = new PathTag();
		tag.moveto(xStart, yStart);
		tag.arcto(this.width / 2, this.height / 2, largeArcFlag, sweepFlag, xEnd, yEnd);
		tag.style(this.sc.getStrokeStyle() + " " + this.sc.getShapeStyle());
		tag.translate(this.sc.getTranslateX(), this.sc.getTranslateY());
		tag.rotate(this.sc.getRotation());

		return tag.render();
	}

	@Override
	SvgComponent duplicate() {
		return new EllipticalArcSVG(this.x, this.y, this.width, this.height, this.start, this.end, this.sc);
	}
}

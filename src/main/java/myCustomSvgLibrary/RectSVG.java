package myCustomSvgLibrary;

import myCustomSvgLibrary.tags.RectTag;

public class RectSVG extends SvgComponent {
	private double x, y, width, height;
	
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
		tag.style(this.sc.getStrokeStyle() + " " + this.sc.getShapeStyle());
		tag.translate(this.sc.getTranslateX(), this.sc.getTranslateY());
		tag.rotate(this.sc.getRotation());

		return tag.render();
	}

	@Override
	public SvgComponent clone() {
		return new RectSVG(this.x, this.y, this.width, this.height, this.sc);
	}
}

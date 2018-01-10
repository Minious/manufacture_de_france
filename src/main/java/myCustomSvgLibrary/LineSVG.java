package myCustomSvgLibrary;

import myCustomSvgLibrary.tags.LineTag;

public class LineSVG extends SvgComponent {
	private double x1, y1, x2, y2;
	
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
		tag.style(this.sc.getStrokeStyle());
		tag.translate(this.sc.getTranslateX(), this.sc.getTranslateY());
		tag.rotate(this.sc.getRotation());

		return tag.render();
	}

	@Override
	public SvgComponent clone() {
		return new LineSVG(x1, y1, x2, y2, sc);
	}
}

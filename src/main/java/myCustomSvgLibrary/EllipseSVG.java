package myCustomSvgLibrary;

public class EllipseSVG extends SvgComponent {
	private double x, y, width, height;
	
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

		String outputStr = "";
		outputStr += "<ellipse ";
		outputStr += "cx=\"" + cx + "\" ";
		outputStr += "cy=\"" + cy + "\" ";
		outputStr += "rx=\"" + rx + "\" ";
		outputStr += "ry=\"" + ry + "\" ";
		outputStr += "style=\"" + this.sc.getStrokeStyle() + " " + this.sc.getShapeStyle() + "\" ";
		if(!this.sc.isTranformIdentity())
			outputStr += "transform=\"" + this.sc.getTransformSvgNotation() + "\" ";
		outputStr += "/>";
		
		return outputStr;
	}

	@Override
	public SvgComponent clone() {
		return new EllipseSVG(this.x, this.y, this.width, this.height, this.sc);
	}
}

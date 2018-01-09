package myCustomSvgLibrary;

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
		String outputStr = "";
		outputStr += "<rect ";
		outputStr += "x=\"" + this.x + "\" ";
		outputStr += "y=\"" + this.y + "\" ";
		outputStr += "width=\"" + this.width + "\" ";
		outputStr += "height=\"" + this.height + "\" ";
		outputStr += "style=\"" + this.sc.getStrokeStyle() + " " + this.sc.getShapeStyle() + "\" ";
		if(!this.sc.isTranformIdentity())
			outputStr += "transform=\"" + this.sc.getTransformSvgNotation() + "\" ";
		outputStr += "/>";
		
		return outputStr;
	}

	@Override
	public SvgComponent clone() {
		return new RectSVG(this.x, this.y, this.width, this.height, this.sc);
	}
}

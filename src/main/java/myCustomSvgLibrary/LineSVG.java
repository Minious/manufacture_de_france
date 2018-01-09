package myCustomSvgLibrary;

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
		String outputStr = "";
		outputStr += "<line ";
		outputStr += "x1=\"" + this.x1 + "\" ";
		outputStr += "y1=\"" + this.y1 + "\" ";
		outputStr += "x2=\"" + this.x2 + "\" ";
		outputStr += "y2=\"" + this.y2 + "\" ";
		outputStr += "style=\"" + this.sc.getStrokeStyle() + "\" ";
		if(!this.sc.isTranformIdentity())
			outputStr += "transform=\"" + this.sc.getTransformSvgNotation() + "\" ";
		outputStr += "/>";
		
		return outputStr;
	}

	@Override
	public SvgComponent clone() {
		return new LineSVG(x1, y1, x2, y2, sc);
	}
}

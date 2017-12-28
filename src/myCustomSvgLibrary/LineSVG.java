package myCustomSvgLibrary;

public class LineSVG extends SvgComponent {
	private double x1, y1, x2, y2;
	
	public LineSVG(double x1, double y1, double x2, double y2, StyleContext sc) {
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
		outputStr += "transform=\"" + this.sc.getTransformMatrix() + "\" ";
		outputStr += "/>";
		
		return outputStr;
	}
}

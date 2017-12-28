package myCustomSvgLibrary;

public class RectSVG extends SvgComponent {
	private double x, y, width, height;
	
	public RectSVG(double x, double y, double width, double height, StyleContext sc) {
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
		outputStr += "style=\"" + this.sc.getStrokeStyle() + "fill: none;\" ";
		outputStr += "transform=\"" + this.sc.getTransformMatrix() + "\" ";
		outputStr += "/>";
		
		return outputStr;
	}
}

package myCustomSvgLibrary;

public class Rect extends SvgComponent {
	private double x, y, width, height;
	
	public Rect(double x, double y, double width, double height, StyleContext sc) {
		super(sc);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public String renderTag() {
		double tx = this.sc.getTransform().getTranslateX();
		double ty = this.sc.getTransform().getTranslateY();
		double rot = Math.atan2(this.sc.getTransform().getShearY(), this.sc.getTransform().getScaleY());
		
		String outputStr = "";
		outputStr += "<rect ";
		outputStr += "x=\"" + this.x + "\" ";
		outputStr += "y=\"" + this.y + "\" ";
		outputStr += "width=\"" + this.width + "\" ";
		outputStr += "height=\"" + this.height + "\" ";
		outputStr += "style=\"" + this.sc.getStrokeStyle() + "fill: none;\" ";
		//outputStr += "transform=\"translate(" + tx + " " + ty + ") rotate(" + rot + ")\" ";
		outputStr += "transform=\"" + this.sc.getTransformMatrix() + "\" ";
		outputStr += "/>";
		
		return outputStr;
	}
}

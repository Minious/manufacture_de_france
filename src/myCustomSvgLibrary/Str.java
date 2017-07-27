package myCustomSvgLibrary;

public class Str extends SvgComponent {
	private String str;
	private double x, y;
	
	public Str(String str, double x, double y, StyleContext sc) {
		super(sc);
		this.str = str;
		this.x = x;
		this.y = y;
	}
	
	public String renderTag() {
		double tx = this.sc.getTransform().getTranslateX();
		double ty = this.sc.getTransform().getTranslateY();
		double rot = Math.atan2(this.sc.getTransform().getShearY(), this.sc.getTransform().getScaleY());
		
		String rgbColorStyle = "rgb(" + this.sc.getColor().getRed() + "," + this.sc.getColor().getGreen() + "," + this.sc.getColor().getBlue() + ")";
		
		String outputStr = "";
		outputStr += "<text ";
		outputStr += "x=\"" + this.x + "\" ";
		outputStr += "y=\"" + this.y + "\" ";
		outputStr += "style=\"" + this.sc.getFontStyle() + "\" ";
		//outputStr += "transform=\"translate(" + tx + " " + ty + ") rotate(" + rot + ")\" ";
		outputStr += "transform=\"" + this.sc.getTransformMatrix() + "\" ";
		outputStr += ">";
		outputStr += this.str;
		outputStr += "</text>";
		
		return outputStr;
	}
}

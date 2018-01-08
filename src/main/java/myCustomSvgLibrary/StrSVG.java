package myCustomSvgLibrary;

public class StrSVG extends SvgComponent {
	private String str;
	private double x, y;
	
	StrSVG(String str, double x, double y, StyleContext sc) {
		super(sc);
		this.str = str;
		this.x = x;
		this.y = y;
	}
	
	public String renderTag() {
		//String rgbColorStyle = "rgb(" + this.sc.getColor().getRed() + "," + this.sc.getColor().getGreen() + "," + this.sc.getColor().getBlue() + ")";
		
		String outputStr = "";
		outputStr += "<text ";
		outputStr += "x=\"" + this.x + "\" ";
		outputStr += "y=\"" + this.y + "\" ";
		//outputStr += "textLength=\"" + this.width + "\" ";
		//outputStr += "lengthAdjust=\"spacingAndGlyphs\" ";
		outputStr += "style=\"" + this.sc.getFontStyle() + "\" ";
		outputStr += "transform=\"" + this.sc.getTransformMatrix() + "\" ";
		outputStr += ">";
		outputStr += this.str;
		outputStr += "</text>";
		
		return outputStr;
	}

	@Override
	public SvgComponent clone() {
		return new StrSVG(this.str, this.x, this.y, this.sc);
	}
}
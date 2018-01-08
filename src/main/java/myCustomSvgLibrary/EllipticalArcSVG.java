package myCustomSvgLibrary;

import java.text.DecimalFormat;

import generateurCoteVerriere.Utils;

public class EllipticalArcSVG extends SvgComponent {
	private double x, y, width, height, start, end;
	
	EllipticalArcSVG(double x, double y, double width, double height, double start, double end, StyleContext sc) {
		super(sc);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.start = start;
		this.end = end;
	}
	
	public String renderTag() {
		double xStart = this.x + Math.cos(start) * width / 2;
		double yStart = this.y - Math.sin(start) * width / 2;
		double xEnd = this.x + Math.cos(end) * height / 2;
		double yEnd = this.y - Math.sin(end) * height / 2;
		
		DecimalFormat df = new DecimalFormat("#.#");
        df.setMaximumFractionDigits(8);

        double simplifiedStart = Utils.positiveModulo(this.start, 2 * Math.PI);
        double simplifiedEnd = Utils.positiveModulo(this.end, 2 * Math.PI);
		int largeArcFlag = (simplifiedEnd - simplifiedStart + (Math.PI * 2)) % (Math.PI * 2) > Math.PI ? 1 : 0;
		int sweepFlag = 0;
		
		String outputStr = "";
		outputStr += "<path d=\"";
		outputStr += "M " +  xStart + " " + yStart + " ";
		outputStr += "A " + this.width / 2 + " " + this.height / 2 + " 0 " + largeArcFlag + " " + sweepFlag + " " + String.valueOf(xEnd) + " " + yEnd + "\" ";
		outputStr += "style=\"" + this.sc.getShapeStyle() + "\" ";
		outputStr += "transform=\"" + this.sc.getTransformMatrix() + "\" ";
		outputStr += "/>";
		
		return outputStr;
	}

	@Override
	public SvgComponent clone() {
		return new EllipticalArcSVG(this.x, this.y, this.width, this.height, this.start, this.end, this.sc.clone());
	}
}

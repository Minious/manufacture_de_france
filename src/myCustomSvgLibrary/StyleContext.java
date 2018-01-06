package myCustomSvgLibrary;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;

public class StyleContext {
	private AffineTransform curTransform;
	private Color curStrokeColor;
	private Color curFillColor;
	private Font curFont;
	private BasicStroke curStroke;

	public StyleContext() {
		this(new AffineTransform(), Color.BLACK, null, new Font("Arial", Font.PLAIN, 12), new BasicStroke(1));
	}

	public StyleContext(AffineTransform t, Color strokeC, Color fillC, Font f, BasicStroke s) {
		this.curTransform = t;
		this.curStrokeColor = strokeC;
		this.curFillColor = fillC;
		this.curFont = f;
		this.curStroke = s;
	}
	
	public StyleContext clone() {
		AffineTransform t = (AffineTransform) this.curTransform.clone(); // new AffineTransform(t);
		Color strokeC = this.curStrokeColor; // new Color(c.getRGB());
		Color fillC = this.curFillColor; // new Color(c.getRGB());
		Font f = this.curFont;
		BasicStroke s = this.curStroke;
		
		return new StyleContext(t, strokeC, fillC, f, s);
	}
	
	public Font getFont() {
		return this.curFont;
	}
	
	public Color getStrokeColor() {
		return this.curStrokeColor;
	}
	
	public Color getFillColor() {
		return this.curFillColor;
	}
	
	public AffineTransform getTransform() {
		return this.curTransform;
	}
	
	public void translate(double tx, double ty) {
		this.curTransform = (AffineTransform) this.curTransform.clone();
		this.curTransform.translate(tx, ty);
	}
	
	public void rotate(double theta) {
		this.curTransform = (AffineTransform) this.curTransform.clone();
		this.curTransform.rotate(theta);
	}
	
	public void resetTransform() {
		this.curTransform = new AffineTransform();
	}
	
	public void setStrokeColor(Color c) {
		this.curStrokeColor = c;
	}
	
	public void setFillColor(Color c) {
		this.curFillColor = c;
	}
	
	public void setFont(Font f) {
		this.curFont = f;
	}
	
	public void setFontSize(float size) {
		this.curFont = this.curFont.deriveFont(size);
	}
	
	public void setStroke(BasicStroke s) {
		this.curStroke = s;
	}
	
	public void setTransform(AffineTransform t) {
		this.curTransform = t;
	}
	
	public void setStrokeWidth(float w) {
		this.curStroke = new BasicStroke(
				w,
				this.curStroke.getEndCap(),
				this.curStroke.getLineJoin(),
				this.curStroke.getMiterLimit(),
				this.curStroke.getDashArray(),
				this.curStroke.getDashPhase()
			);
	}
	
	public void setDashArray(float[] dashArray) {
		this.curStroke = new BasicStroke(
			this.curStroke.getLineWidth(),
			this.curStroke.getEndCap(),
			this.curStroke.getLineJoin(),
			this.curStroke.getMiterLimit(),
			dashArray,
			0
		);
	}
	
	public void removeDashArray() {
		this.curStroke = new BasicStroke(
			this.curStroke.getLineWidth(),
			this.curStroke.getEndCap(),
			this.curStroke.getLineJoin(),
			this.curStroke.getMiterLimit()
		);
	}
	
	public String getShapeStyle() {
		float[] dashArray = this.curStroke.getDashArray();
		
		String outputStr = "";
		outputStr += "stroke-width: " + this.curStroke.getLineWidth() + "; ";
		outputStr += "stroke: rgb(" + this.curStrokeColor.getRed() + "," + this.curStrokeColor.getGreen() + "," + this.curStrokeColor.getBlue() + "); ";
		outputStr += "stroke-opacity: 1.0; ";
		outputStr += "stroke-linecap: butt; ";
		if(dashArray != null) {
			String dashArrayStr = "";
			dashArrayStr += dashArray[0];
			for(int i=1;i<dashArray.length;i++)
				dashArrayStr += ","+dashArray[i];
			outputStr += "stroke-dasharray: "+dashArrayStr+"; ";
		}
		if(this.curFillColor == null)
			outputStr += "fill: none; ";
		else
			outputStr += "fill: rgb(" + this.curFillColor.getRed() + "," + this.curFillColor.getGreen() + "," + this.curFillColor.getBlue() + "); ";
		return outputStr;
	}
	
	public String getFontStyle() {
		String outputStr = "";
		outputStr += "fill: rgb(" + this.curStrokeColor.getRed() + "," + this.curStrokeColor.getGreen() + "," + this.curStrokeColor.getBlue() + "); ";
		outputStr += "fill-opacity: 1.0; ";
		outputStr += "font-family: " + this.curFont.getFontName() + "; ";
		outputStr += "font-size: " + this.curFont.getSize() + "px; ";
		
		return outputStr;
	}
	
	public String getTransformMatrix() {
		double[] matrix = new double[6];
		this.curTransform.getMatrix(matrix);
		String outputStr = "";
		outputStr += "matrix(";
		outputStr += matrix[0] + ",";
		outputStr += matrix[1] + ",";
		outputStr += matrix[2] + ",";
		outputStr += matrix[3] + ",";
		outputStr += matrix[4] + ",";
		outputStr += matrix[5] + ")";
		return outputStr;
	}
}

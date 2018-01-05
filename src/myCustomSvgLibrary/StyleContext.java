package myCustomSvgLibrary;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;

public class StyleContext {
	private AffineTransform curTransform = new AffineTransform();
	private Color curColor = Color.BLACK;
	private Font curFont = new Font("Arial", Font.PLAIN, 12);
	private BasicStroke curStroke = new BasicStroke(1);

	public StyleContext(AffineTransform t, Color c, Font f, BasicStroke s) {
		this.curTransform = t;
		this.curColor = c;
		this.curFont = f;
		this.curStroke = s;
	}
	
	public StyleContext clone() {
		AffineTransform t = (AffineTransform) this.curTransform.clone(); // new AffineTransform(t);
		Color c = this.curColor; // new Color(c.getRGB());
		Font f = this.curFont;
		BasicStroke s = this.curStroke;
		
		return new StyleContext(t, c, f, s);
	}
	
	public Font getFont() {
		return this.curFont;
	}
	
	public Color getColor() {
		return this.curColor;
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
	
	public void setColor(Color c) {
		this.curColor = c;
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
	
	public String getStrokeStyle() {
		float[] dashArray = this.curStroke.getDashArray();
		
		String outputStr = "";
		outputStr += "stroke-width: " + this.curStroke.getLineWidth() + "; ";
		outputStr += "stroke: rgb(" + this.curColor.getRed() + "," + this.curColor.getGreen() + "," + this.curColor.getBlue() + "); ";
		outputStr += "stroke-opacity: 1.0; ";
		outputStr += "stroke-linecap: butt; ";
		if(dashArray != null) {
			String dashArrayStr = "";
			dashArrayStr += dashArray[0];
			for(int i=1;i<dashArray.length;i++)
				dashArrayStr += ","+dashArray[i];
			outputStr += "stroke-dasharray: "+dashArrayStr+"; ";
		}
		return outputStr;
	}
	
	public String getFontStyle() {
		String outputStr = "";
		outputStr += "fill: rgb(" + this.curColor.getRed() + "," + this.curColor.getGreen() + "," + this.curColor.getBlue() + "); ";
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

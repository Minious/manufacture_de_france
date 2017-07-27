package myCustomSvgLibrary;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.util.Objects;

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
	
	public void setColor(Color c) {
		this.curColor = c;
	}
	
	public void setFont(Font f) {
		this.curFont = f;
	}
	
	public void setStroke(BasicStroke s) {
		this.curStroke = s;
	}
	
	public void setTransform(AffineTransform t) {
		this.curTransform = t;
	}
	
	public String getStrokeStyle() {
		String outputStr = "";
		outputStr += "stroke-width: " + this.curStroke.getLineWidth() + "; ";
		outputStr += "stroke: rgb(" + this.curColor.getRed() + "," + this.curColor.getGreen() + "," + this.curColor.getBlue() + "); ";
		outputStr += "stroke-opacity: 1.0; ";
		outputStr += "stroke-linecap: square; ";
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

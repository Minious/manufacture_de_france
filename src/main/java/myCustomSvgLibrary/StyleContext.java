package myCustomSvgLibrary;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;
import java.util.Arrays;

public class StyleContext {
	private AffineTransform curTransform;
	private Color curStrokeColor;
	private Color curFillColor;
	private Color curFontColor;
	private Font curFont;
	private BasicStroke curStroke;

	StyleContext() {
		this(new AffineTransform(), Color.BLACK, null, Color.BLACK, new Font("Century Gothic", Font.PLAIN, 12), new BasicStroke(1));
	}

	StyleContext(AffineTransform t, Color strokeC, Color fillC, Color fontC, Font f, BasicStroke s) {
		this.curTransform = t;
		this.curStrokeColor = strokeC;
		this.curFillColor = fillC;
		this.curFontColor = fontC;
		this.curFont = f;
		this.curStroke = s;
	}
	
	public StyleContext clone() {
		AffineTransform t = (AffineTransform) this.curTransform.clone();
		Color strokeC = this.curStrokeColor;
		Color fillC = this.curFillColor;
		Color fontC = this.curFontColor;
		Font f = this.curFont;
		BasicStroke s = this.curStroke;
		
		return new StyleContext(t, strokeC, fillC, fontC, f, s);
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
	
	public Color getFontColor() {
		return this.curFontColor;
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
	
	public void setFontColor(Color c) {
		this.curFontColor = c;
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
		outputStr += "fill: rgb(" + this.curFontColor.getRed() + "," + this.curFontColor.getGreen() + "," + this.curFontColor.getBlue() + "); ";
		outputStr += "fill-opacity: 1.0; ";
		outputStr += "font-family: " + this.curFont.getFontName() + "; ";
		outputStr += "font-size: " + this.curFont.getSize() + "px; ";
		
		return outputStr;
	}
	
	public String getTransformMatrix() {
		String outputStr = "";

		int type = this.curTransform.getType();
		boolean isIdentity = type == AffineTransform.TYPE_IDENTITY;
		boolean isQuadrantRotation = (type & AffineTransform.TYPE_QUADRANT_ROTATION) == AffineTransform.TYPE_QUADRANT_ROTATION;
		boolean isRotation = (type & AffineTransform.TYPE_GENERAL_ROTATION) == AffineTransform.TYPE_GENERAL_ROTATION;
		boolean isTranslation = (type & AffineTransform.TYPE_TRANSLATION) == AffineTransform.TYPE_TRANSLATION;

		if (!isIdentity) {
			if(isTranslation) {
				double translateX = this.curTransform.getTranslateX();
				double translateY = this.curTransform.getTranslateY();
				outputStr += "translate(" + translateX + " " + translateY + ") ";
			}
			if(isQuadrantRotation || isRotation) {
				double rotation = Math.toDegrees(Math.atan2(this.curTransform.getShearY(), this.curTransform.getScaleY()));
				outputStr += "rotate(" + rotation + ") ";
			}
		}

		return outputStr;
	}

	public boolean isTranformIdentity(){
		return this.curTransform.isIdentity();
	}
}

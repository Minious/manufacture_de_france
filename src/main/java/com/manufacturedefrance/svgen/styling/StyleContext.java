package com.manufacturedefrance.svgen.styling;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;

public class StyleContext {
	private AffineTransform curTransform;
	private Color curStrokeColor;
	private Color curFillColor;
	private Color curFontColor;
	private Font curFont;
	private Stroke stroke;

	private static AffineTransform DEFAULT_TRANSFORM = new AffineTransform();
	private static Color DEFAULT_STROKE_COLOR = Color.BLACK;
	private static Color DEFAULT_FILL_COLOR = null;
	private static Color DEFAULT_FONT_COLOR = Color.BLACK;
	private static int DEFAULT_FONT_SIZE = 12;
	private static Font DEFAULT_FONT = new Font("Century Gothic", Font.PLAIN, DEFAULT_FONT_SIZE);
	private static Stroke DEFAULT_STROKE = new Stroke();

	public StyleContext(StyleContext styleContext) {
		this((AffineTransform) styleContext.curTransform.clone(), styleContext.curStrokeColor, styleContext.curFillColor, styleContext.curFontColor, styleContext.curFont, new Stroke(styleContext.stroke));
	}

	public StyleContext(AffineTransform t, Color strokeC, Color fillC, Color fontC, Font f, Stroke stroke) {
		this.curTransform = t;
		this.curStrokeColor = strokeC;
		this.curFillColor = fillC;
		this.curFontColor = fontC;
		this.curFont = f;
		this.stroke = stroke;
	}

	public StyleContext() {
		this(DEFAULT_TRANSFORM, DEFAULT_STROKE_COLOR, DEFAULT_FILL_COLOR, DEFAULT_FONT_COLOR, DEFAULT_FONT, DEFAULT_STROKE);
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
	
	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}
	
	public void setTransform(AffineTransform t) {
		this.curTransform = t;
	}
	
	public void setStrokeWidth(double strokeWidth) {
		this.stroke.setWidth(strokeWidth);
	}
	
	public void setDashArray(double[] dashArray) {
		this.stroke.setDashArray(dashArray);
	}
	
	public void removeDashArray() {
		this.stroke.removeDashArray();
	}

	public String getStrokeStyle() {
		String capStr = this.getCapStr(this.stroke.getCap());
		String joinStr = this.getJoinStr(this.stroke.getJoin());

		StringBuilder dashArraySb = new StringBuilder();
		double[] dashArray = this.stroke.getDashArray();
		if(dashArray != null) {
			for(int i=0;i<dashArray.length;i++) {
				dashArraySb.append(dashArray[i]);
				if(i<dashArray.length-1)
					dashArraySb.append(",");
			}
		}

		StringBuilder outputStr = new StringBuilder();
		outputStr.append("stroke-width: " + this.stroke.getWidth() + "; ");
		outputStr.append("stroke: rgb(" + this.curStrokeColor.getRed() + "," + this.curStrokeColor.getGreen() + "," + this.curStrokeColor.getBlue() + "); ");
		outputStr.append("stroke-linecap: " + capStr + "; ");
		outputStr.append("stroke-linejoin: " + joinStr + "; ");
		outputStr.append("stroke-dasharray: "+ dashArraySb.toString() +";");

		return outputStr.toString();
	}

	public String getShapeStyle() {
		String outputStr = "";
		if(this.curFillColor == null)
			outputStr += "fill: none;";
		else
			outputStr += "fill: rgb(" + this.curFillColor.getRed() + "," + this.curFillColor.getGreen() + "," + this.curFillColor.getBlue() + ");";
		return outputStr;
	}
	
	public String getFontStyle() {
		String outputStr = "";
		outputStr += "fill: rgb(" + this.curFontColor.getRed() + "," + this.curFontColor.getGreen() + "," + this.curFontColor.getBlue() + "); ";
		outputStr += "font-family: " + this.curFont.getFontName() + "; ";
		outputStr += "font-size: " + this.curFont.getSize() + "px; ";
		
		return outputStr;
	}
	
	public String getTransformSvgNotation() {
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

	public double getTranslateX(){
		return this.curTransform.getTranslateX();
	}

	public double getTranslateY(){
		return this.curTransform.getTranslateY();
	}

	public double getRotation(){
		return Math.toDegrees(Math.atan2(this.curTransform.getShearY(), this.curTransform.getScaleY()));
	}

	public boolean isTranformIdentity(){
		return this.curTransform.isIdentity();
	}

	public String getJoinStr(Stroke.JOIN join) {
		switch(join){
			case ROUND:
				return "round";
			case BEVEL:
				return "bevel";
			case MITER:
				return "miter";
			default:
				throw new IllegalArgumentException();
		}
	}

	public String getCapStr(Stroke.CAP cap) {
		switch(cap){
			case BUTT:
				return "butt";
			case ROUND:
				return "round";
			case SQUARE:
				return "square";
			default:
				throw new IllegalArgumentException();
		}
	}
}

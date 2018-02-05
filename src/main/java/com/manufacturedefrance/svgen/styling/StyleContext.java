package com.manufacturedefrance.svgen.styling;

import java.awt.geom.AffineTransform;

public class StyleContext {
	private AffineTransform transform;
	private Color strokeColor;
	private Color fillColor;
	private Color fontColor;
	private Font font;
	private Stroke stroke;

	private static AffineTransform DEFAULT_TRANSFORM = new AffineTransform();
	private static Color DEFAULT_STROKE_COLOR = Color.BLACK;
	private static Color DEFAULT_FILL_COLOR = null;
	private static Color DEFAULT_FONT_COLOR = Color.BLACK;
	private static int DEFAULT_FONT_SIZE = 18;
	private static Font DEFAULT_FONT = new Font("Century Gothic", DEFAULT_FONT_SIZE);

	public StyleContext(StyleContext styleContext) {
		this(
			(AffineTransform) styleContext.transform.clone(),
			styleContext.strokeColor,
			styleContext.fillColor,
			styleContext.fontColor,
			new Font(styleContext.font),
			new Stroke(styleContext.stroke)
		);
	}

	public StyleContext(AffineTransform transform, Color strokeColor, Color fillColor, Color fontColor, Font font, Stroke stroke) {
		this.transform = transform;
		this.strokeColor = strokeColor;
		this.fillColor = fillColor;
		this.fontColor = fontColor;
		this.font = font;
		this.stroke = stroke;
	}

	public StyleContext() {
		this(
			DEFAULT_TRANSFORM,
			DEFAULT_STROKE_COLOR,
			DEFAULT_FILL_COLOR,
			DEFAULT_FONT_COLOR,
			DEFAULT_FONT,
			new Stroke()
		);
	}
	
	public Font getFont() {
		return this.font;
	}
	
	public AffineTransform getTransform() {
		return this.transform;
	}
	
	public void translate(double tx, double ty) {
		this.transform = (AffineTransform) this.transform.clone();
		this.transform.translate(tx, ty);
	}
	
	public void rotate(double theta) {
		this.transform = (AffineTransform) this.transform.clone();
		this.transform.rotate(theta);
	}
	
	public void resetTransform() {
		this.transform = new AffineTransform();
	}
	
	public void setStrokeColor(Color strokeColor) {
		this.strokeColor = strokeColor;
	}
	
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
	
	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}
	
	public void setFont(Font f) {
		this.font = f;
	}
	
	public void setFontSize(int fontSize) {
		this.font.setSize(fontSize);
	}
	
	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}
	
	public void setTransform(AffineTransform t) {
		this.transform = t;
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
		if(this.stroke.isDashed()) {
			double[] dashArray = this.stroke.getDashArray();
			for(int i=0;i<dashArray.length;i++) {
				dashArraySb.append(dashArray[i]);
				if(i<dashArray.length-1)
					dashArraySb.append(" ");
			}
		}

		StringBuilder outputStr = new StringBuilder();
		outputStr.append("stroke-width: " + this.stroke.getWidth() + "; ");
		outputStr.append("stroke: " + this.strokeColor.getHex() + "; ");
		outputStr.append("stroke-linecap: " + capStr + "; ");
		outputStr.append("stroke-linejoin: " + joinStr + "; ");
		outputStr.append("stroke-dasharray: "+ dashArraySb.toString() +";");

		return outputStr.toString();
	}

	public String getShapeStyle() {
		String outputStr = "";
		if(this.fillColor == null)
			outputStr += "fill: none;";
		else
			outputStr += "fill: " + this.fillColor.getHex() + ";";
		return outputStr;
	}
	
	public String getFontStyle() {
		String outputStr = "";
		outputStr += "fill: " + this.fontColor.getHex() + ";";
		outputStr += "font-family: " + this.font.getName() + "; ";
		outputStr += "font-size: " + this.font.getSize() + "px; ";
		
		return outputStr;
	}
	
	public String getTransformSvgNotation() {
		String outputStr = "";

		int type = this.transform.getType();
		boolean isIdentity = type == AffineTransform.TYPE_IDENTITY;
		boolean isQuadrantRotation = (type & AffineTransform.TYPE_QUADRANT_ROTATION) == AffineTransform.TYPE_QUADRANT_ROTATION;
		boolean isRotation = (type & AffineTransform.TYPE_GENERAL_ROTATION) == AffineTransform.TYPE_GENERAL_ROTATION;
		boolean isTranslation = (type & AffineTransform.TYPE_TRANSLATION) == AffineTransform.TYPE_TRANSLATION;

		if (!isIdentity) {
			if(isTranslation) {
				double translateX = this.transform.getTranslateX();
				double translateY = this.transform.getTranslateY();
				outputStr += "translate(" + translateX + " " + translateY + ") ";
			}
			if(isQuadrantRotation || isRotation) {
				double rotation = Math.toDegrees(Math.atan2(this.transform.getShearY(), this.transform.getScaleY()));
				outputStr += "rotate(" + rotation + ") ";
			}
		}

		return outputStr;
	}

	public double getTranslateX(){
		return this.transform.getTranslateX();
	}

	public double getTranslateY(){
		return this.transform.getTranslateY();
	}

	public double getRotation(){
		return Math.toDegrees(Math.atan2(this.transform.getShearY(), this.transform.getScaleY()));
	}

	public boolean isTranformIdentity(){
		return this.transform.isIdentity();
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

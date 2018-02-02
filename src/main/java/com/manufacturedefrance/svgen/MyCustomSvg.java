package com.manufacturedefrance.svgen;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.manufacturedefrance.svgen.tags.GTag;
import com.manufacturedefrance.svgen.tags.RectTag;
import com.manufacturedefrance.svgen.tags.SvgTag;
import com.manufacturedefrance.utils.MyPath2D;

public class MyCustomSvg extends SvgComponent{
	private double x;
	private double y;
	private Rectangle2D bounds;
	private ArrayList<SvgComponent> svgTree;
	private Padding padding;
	private boolean hasBorders;
	private StyleContext borderSc;
	private StyleContext rememberedSc;
	
	public MyCustomSvg() {
		super(new StyleContext());
		this.bounds = null;
		this.padding = new Padding(0);
		this.x = 0;
		this.y = 0;
		this.svgTree = new ArrayList<>();
		this.hasBorders = false;
		this.borderSc = null;
		this.rememberedSc = new StyleContext(this.sc);
	}
	
	public MyCustomSvg(MyCustomSvg g) {
		super(g.sc);
		this.bounds = (Rectangle2D) g.bounds.clone();
		this.padding = new Padding(g.padding);
		this.x = g.x;
		this.y = g.y;
		this.svgTree = new ArrayList<>();
		for(SvgComponent c : g.svgTree)
			this.svgTree.add(c.duplicate());
		this.hasBorders = g.hasBorders;
		this.borderSc = g.borderSc != null ? new StyleContext(g.borderSc) : null;
		this.rememberedSc = new StyleContext(g.rememberedSc);
	}	
	
	public void setPadding(Padding padding) {
		this.padding = padding;
	}

	public void setBorders(boolean hasBorders) {
		this.hasBorders = hasBorders;
		this.borderSc = new StyleContext(this.rememberedSc);
	}
	
	public double getWidth() {
		return this.bounds.getWidth() + this.padding.getHorizontalPadding();
	}

	public double getHeight() {
		return this.bounds.getHeight() + this.padding.getVerticalPadding();
	}
	
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public FontMetrics getFontMetrics() {
		Canvas c = new Canvas();
		return c.getFontMetrics(this.rememberedSc.getFont());
	}
	
	public AffineTransform getTransform() {
		return this.rememberedSc.getTransform();
	}
	
	public void translate(double tx, double ty) {
		this.rememberedSc.translate(tx, ty);
	}
	
	public void rotate(double theta) {
		this.rememberedSc.rotate(theta);
	}
	
	public void resetTransform() {
		this.rememberedSc.resetTransform();
	}
	
	public void setFontColor(Color c) {
		this.rememberedSc.setFontColor(c);
	}
	
	public void setStrokeColor(Color c) {
		this.rememberedSc.setStrokeColor(c);
	}
	
	public void setFillColor(Color c) {
		this.rememberedSc.setFillColor(c);
	}
	
	public void removeFillColor() {
		this.rememberedSc.setFillColor(null);
	}
	
	public void setFont(Font f) {
		this.rememberedSc.setFont(f);
	}
	
	public void setFontSize(float size) {
		this.rememberedSc.setFontSize(size);
	}
	
	public void setStroke(BasicStroke s) {
		this.rememberedSc.setStroke(s);
	}
	
	public void setStrokeWidth(float w) {
		this.rememberedSc.setStrokeWidth(w);
	}
	
	public void setDashArray(float[] dashArray) {
		this.rememberedSc.setDashArray(dashArray);
	}
	
	public void removeDashArray() {
		this.rememberedSc.removeDashArray();
	}
	
	public void setTransform(AffineTransform t) {
		this.rememberedSc.setTransform(t);
	}
	
	public void drawPath(MyPath2D path) {
		svgTree.add(new PathSVG(path, this.rememberedSc));
		this.enlargeBounds(path.getPath2D());
	}
	
	public void drawRect(double x, double y, double width, double height) {
		RectSVG rect = new RectSVG(x, y, width, height, this.rememberedSc);
		svgTree.add(rect);
		this.enlargeBounds(new Rectangle2D.Double(x, y, width, height));
	}
	
	public void drawRect(Rectangle2D rect) {
		this.drawRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
	}
	
	public void drawEllipse(double x, double y, double width, double height) {
		EllipseSVG el = new EllipseSVG(x, y, width, height, this.rememberedSc);
		svgTree.add(el);
		this.enlargeBounds(new Ellipse2D.Double(x, y, width, height));
	}
	
	public void drawEllipticalArc(double x, double y, double width, double height, double start, double end) {
		EllipticalArcSVG elArc = new EllipticalArcSVG(x, y, width, height, start, end, this.rememberedSc);
		svgTree.add(elArc);
		this.enlargeBounds(new Ellipse2D.Double(x - width / 2, y - height / 2, width, height));
	}
	
	public void drawLine(double x1, double y1, double x2, double y2) {
		LineSVG line = new LineSVG(x1, y1, x2, y2, this.rememberedSc);
		svgTree.add(line);
		this.enlargeBounds(new Line2D.Double(x1, y1, x2, y2));
	}
	
	public void drawString(String text, double x, double y) {
		Rectangle2D strBoundsH = this.rememberedSc.getFont().getStringBounds(text, new FontRenderContext(null, true, true));
		Rectangle2D strBoundsV = this.rememberedSc.getFont().createGlyphVector(this.getFontMetrics().getFontRenderContext(), text).getVisualBounds();
		Rectangle2D strBounds = new Rectangle2D.Double(strBoundsH.getX() + x, strBoundsV.getY() + y, strBoundsH.getWidth(), strBoundsV.getHeight());
		
		StrSVG str = new StrSVG(text, x, y, this.rememberedSc);
		svgTree.add(str);

		this.enlargeBounds(strBounds);
	}
	
	public void drawSvg(MyCustomSvg svg, double x, double y) {
		this.drawSvg(svg, x, y, ShapeMode.CORNER);
	}
	
	public void drawSvg(MyCustomSvg svg, double x, double y, ShapeMode mode) {
		double actualX = 0;
		double actualY = 0;
		if(mode == ShapeMode.CENTER) {
			actualX = x - svg.getWidth() / 2;
			actualY = y - svg.getHeight() / 2;
		} else if(mode == ShapeMode.CORNER) {
			actualX = x;
			actualY = y;
		}

		MyCustomSvg svgClone = svg.duplicate();
		svgClone.sc = new StyleContext(this.rememberedSc);
		svgTree.add(svgClone);
		svgClone.setPosition(actualX, actualY);
		Rectangle2D svgBounds = new Rectangle2D.Double(actualX, actualY, svgClone.getWidth(), svgClone.getHeight());
		this.enlargeBounds(svgBounds);
	}
	
	public String renderTag() {
		double curX = - this.bounds.getX() + this.x;
		double curY = - this.bounds.getY() + this.y;

		StringBuilder balisesIntermediaires = new StringBuilder();
		for(SvgComponent svgComponent : this.svgTree)
			balisesIntermediaires.append(svgComponent.renderTag() + "\n");

		GTag gTagPadding = new GTag();
		gTagPadding.translate(this.padding.getLeftPadding(), this.padding.getTopPadding());
		String gPadding = gTagPadding.render(balisesIntermediaires.toString());

		if(this.hasBorders) {
			double curW = this.bounds.getWidth() + this.padding.getHorizontalPadding();
			double curH = this.bounds.getHeight() + this.padding.getVerticalPadding();

			RectTag rectTag = new RectTag();
			rectTag.x(this.bounds.getX());
			rectTag.y(this.bounds.getY());
			rectTag.width(curW);
			rectTag.height(curH);
			rectTag.style(this.borderSc.getStrokeStyle() + " " + this.borderSc.getShapeStyle());

			gPadding = rectTag.render() + gPadding;
		}

		GTag gTagTransform = new GTag();
		gTagTransform.translate(this.sc.getTranslateX(), this.sc.getTranslateY());
		gTagTransform.rotate(this.sc.getRotation());
		gTagTransform.translate(curX, curY);

		return gTagTransform.render(gPadding);
	}
	
	public void clear() {
		this.svgTree.clear();
	}
	
	public void writeToSVG(Path outputFilePath) {
		double w = this.bounds.getWidth() + this.padding.getHorizontalPadding();
		double h = this.bounds.getHeight() + this.padding.getVerticalPadding();

		SvgTag svgTag = new SvgTag();
		svgTag.width(w);
		svgTag.height(h);
		String output = svgTag.render(this.renderTag());

		try {
			Files.createDirectories(outputFilePath.getParent());
			Files.write(outputFilePath, output.getBytes(Charset.forName("UTF-8")));
		} catch (IOException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, e.toString());
		}
	}
	
	private void enlargeBounds(Shape shape) {
		Shape transformedShape = this.rememberedSc.getTransform().createTransformedShape(shape);
		Rectangle2D curBounds = transformedShape.getBounds2D();
		if(this.bounds == null)
			this.bounds = curBounds;
		else
			this.bounds.add(curBounds);
	}
	
	MyCustomSvg duplicate() {
		return new MyCustomSvg(this);
	}

	public enum ShapeMode{
		CENTER,
		CORNER
	}
}

package myCustomSvgLibrary;

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

import myCustomSvgLibrary.tags.GTag;
import myCustomSvgLibrary.tags.SvgTag;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced.ShiftMode;
import utils.MyPath2D;

public class MyCustomSvg extends SvgComponent{
	private double x, y;
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
		this.svgTree = new ArrayList<SvgComponent>();
		this.hasBorders = false;
		this.borderSc = null;
		this.rememberedSc = this.sc.clone();
	}
	
	public MyCustomSvg(MyCustomSvg g) {
		super(g.sc);
		this.bounds = (Rectangle2D) g.bounds.clone();
		this.padding = g.padding.clone();
		this.x = g.x;
		this.y = g.y;
		this.svgTree = new ArrayList<SvgComponent>();
		for(SvgComponent c : g.svgTree)
			this.svgTree.add(c.clone());
		this.hasBorders = g.hasBorders;
		this.borderSc = g.borderSc != null ? g.borderSc.clone() : null;
		this.rememberedSc = g.rememberedSc.clone();
	}	
	
	public void setPadding(Padding padding) {
		this.padding = padding;
	}

	public void setBorders(boolean hasBorders) {
		this.hasBorders = hasBorders;
		this.borderSc = this.rememberedSc.clone();
	}
	
	public double getWidth() {
		//return this.bounds.getWidth();
		return this.bounds.getWidth() + this.padding.getHorizontalPadding();
	}

	public double getHeight() {
		//return this.bounds.getHeight();
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
		Rectangle2D boundsH = this.rememberedSc.getFont().getStringBounds(text, new FontRenderContext(null, true, true));
		Rectangle2D boundsV = this.rememberedSc.getFont().createGlyphVector(this.getFontMetrics().getFontRenderContext(), text).getVisualBounds();
		Rectangle2D bounds = new Rectangle2D.Double(boundsH.getX() + x, boundsV.getY() + y, boundsH.getWidth(), boundsV.getHeight());
		
		StrSVG str = new StrSVG(text, x, y, this.rememberedSc);
		svgTree.add(str);
		
		this.enlargeBounds(bounds);
		
		///// DEBUG
		/*
		this.setColor(Color.RED);
		this.drawRect(bounds);
		this.setColor(Color.BLACK);
		*/
		/////
	}
	
	public void drawSvg(MyCustomSvg svg, double x, double y) {
		this.drawSvg(svg, x, y, ShiftMode.LEFT);
	}
	
	public void drawSvg(MyCustomSvg svg, double x, double y, ShiftMode hShift) {
		this.drawSvg(svg, x, y, hShift, ShiftMode.TOP);
	}
	
	public void drawSvg(MyCustomSvg svg, double x, double y, ShiftMode hShift, ShiftMode vShift) {
		double actualX, actualY;
		switch(hShift) {
			case CENTER:
				actualX = x - svg.getWidth() / 2;
				break;
			case RIGHT:
				actualX = x - svg.getWidth();
				break;
			default: // or LEFT
				actualX = x;
				break;
		}
			switch(vShift) {
			case CENTER:
				actualY = y - svg.getHeight() / 2;
				break;
			case BOTTOM:
				actualY = y - svg.getHeight();
				break;
			default: // or TOP
				actualY = y;
				break;
		}

		MyCustomSvg svgClone = (MyCustomSvg) svg.clone();
		svgClone.sc = this.rememberedSc.clone();
		svgTree.add(svgClone);
		svgClone.setPosition(actualX, actualY);
		Rectangle2D bounds = new Rectangle2D.Double(actualX, actualY, svgClone.getWidth(), svgClone.getHeight());
		this.enlargeBounds(bounds);
	}
	
	public String renderTag() {
		double curX, curY;
		curX = - this.bounds.getX() + this.x;
		curY = - this.bounds.getY() + this.y;

		String balisesIntermediaires = "";
		for(SvgComponent svgComponent : this.svgTree)
			balisesIntermediaires += svgComponent.renderTag() + "\n";

		GTag gTagPadding = new GTag();
		gTagPadding.translate(this.padding.getLeftPadding(), this.padding.getTopPadding());
		String gPadding = gTagPadding.render(balisesIntermediaires);

		if(this.hasBorders) {
			double curW, curH;
			curW = this.bounds.getWidth() + this.padding.getHorizontalPadding();
			curH = this.bounds.getHeight() + this.padding.getVerticalPadding();

			String gBorder = "";
			gBorder += "<rect ";
			gBorder += "x=\"" + this.bounds.getX() + "\" ";
			gBorder += "y=\"" + this.bounds.getY() + "\" ";
			gBorder += "width=\"" + curW + "\" ";
			gBorder += "height=\"" + curH + "\" ";
			gBorder += "style=\"" + this.borderSc.getStrokeStyle() + " " + this.borderSc.getShapeStyle() + "\" ";
			gBorder += "/>\n";

			gPadding = gBorder += gPadding;
		}

		GTag gTagTransform = new GTag();
		gTagTransform.translate(this.sc.getTranslateX(), this.sc.getTranslateY());
		gTagTransform.rotate(this.sc.getRotation());
		gTagTransform.translate(curX, curY);

		return gTagTransform.render(gPadding);
	}

	private String tabuler(String input){
		String output = "";
		for(String line : input.split("\n"))
			output += "\t" + line + "\n";
		return output;
	}
	
	public void clear() {
		this.svgTree.clear();
	}
	
	public void writeToSVG(Path outputFilePath) {
		double w = this.bounds.getWidth() + this.padding.getHorizontalPadding();
		double h = this.bounds.getHeight() + this.padding.getVerticalPadding();

		/*
		String output = "";
		output += "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"" + w + "\" height=\"" + h + "\" >\n";
		output += tabuler(this.renderTag());
		output += "</svg>";
		*/

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
		Rectangle2D bounds = transformedShape.getBounds2D();
		if(this.bounds == null)
			this.bounds = bounds;
		else
			this.bounds.add(bounds);
	}
	
	public MyCustomSvg clone() {
		return new MyCustomSvg(this);
	}
}

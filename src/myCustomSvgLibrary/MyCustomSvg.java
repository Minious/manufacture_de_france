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

import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced.ShiftMode;
import utils.MyPath2D;

public class MyCustomSvg extends SvgComponent{
	private int width, height;
	private double x, y;
	private Rectangle2D bounds;
	private ArrayList<SvgComponent> svgTree = new ArrayList<SvgComponent>();
	//private Padding padding;
	
	public MyCustomSvg(int width, int height) {
		super(new StyleContext(
				new AffineTransform(),
				Color.BLACK,
				new Font("Arial", Font.PLAIN, 12),
				new BasicStroke(1)
		));
		this.width = width;
		this.height = height;
		this.bounds = null;
		//this.padding = new Padding(100);
		this.x = 0;
		this.y = 0;
	}

	public double getWidth() {
		//return this.bounds.getWidth();
		return this.width;
		// this.bounds.getWidth() + this.padding.getHorizontalPadding();
	}

	public double getHeight() {
		//return this.bounds.getHeight();
		return this.height;
		// this.bounds.getHeight() + this.padding.getVerticalPadding();
	}
	
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public FontMetrics getFontMetrics() {
		Canvas c = new Canvas();
		return c.getFontMetrics(this.sc.getFont());
	}
	
	public AffineTransform getTransform() {
		return this.sc.getTransform();
	}
	
	public void translate(double tx, double ty) {
		this.sc.translate(tx, ty);
	}
	
	public void rotate(double theta) {
		this.sc.rotate(theta);
	}
	
	public void setColor(Color c) {
		this.sc.setColor(c);
	}
	
	public void setFont(Font f) {
		this.sc.setFont(f);
	}
	
	public void setFontSize(float size) {
		this.sc.setFontSize(size);
	}
	
	public void setStroke(BasicStroke s) {
		this.sc.setStroke(s);
	}
	
	public void setStrokeWidth(float w) {
		this.sc.setStrokeWidth(w);
	}
	
	public void setDashArray(float[] dashArray) {
		this.sc.setDashArray(dashArray);
	}
	
	public void removeDashArray() {
		this.sc.removeDashArray();
	}
	
	public void setTransform(AffineTransform t) {
		this.sc.setTransform(t);
	}
	
	public void drawPath(MyPath2D path) {
		svgTree.add(new PathSVG(path, sc));
		this.enlargeBounds(path.getPath2D());
	}
	
	public void drawRect(double x, double y, double width, double height) {
		RectSVG rect = new RectSVG(x, y, width, height, sc);
		svgTree.add(rect);
		this.enlargeBounds(new Rectangle2D.Double(x, y, width, height));
	}
	
	public void drawRect(Rectangle2D rect) {
		this.drawRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
	}
	
	public void drawEllipse(double x, double y, double width, double height) {
		EllipseSVG el = new EllipseSVG(x, y, width, height, this.sc);
		svgTree.add(el);
		this.enlargeBounds(new Ellipse2D.Double(x, y, width, height));
	}
	
	public void drawLine(double x1, double y1, double x2, double y2) {
		LineSVG line = new LineSVG(x1, y1, x2, y2, this.sc);
		svgTree.add(line);
		this.enlargeBounds(new Line2D.Double(x1, y1, x2, y2));
	}
	
	public void drawString(String text, double x, double y) {
		Rectangle2D boundsH = this.sc.getFont().getStringBounds(text, new FontRenderContext(null, true, true));
		Rectangle2D boundsV = this.sc.getFont().createGlyphVector(this.getFontMetrics().getFontRenderContext(), text).getVisualBounds();
		Rectangle2D bounds = new Rectangle2D.Double(boundsH.getX() + x, boundsV.getY() + y, boundsH.getWidth(), boundsV.getHeight());
		
		StrSVG str = new StrSVG(text, x, y, this.sc);
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
		
		svgTree.add(svg);
		svg.setPosition(actualX, actualY);
		Rectangle2D bounds = new Rectangle2D.Double(actualX, actualY, svg.getWidth(), svg.getHeight());
		this.enlargeBounds(bounds);
	}
	
	public String renderTag() {
		//System.out.println("fixed dimensions : "+this.width+"x"+this.height);
		System.out.println("dynamic dimensions : "+this.bounds.getWidth()+"x"+this.bounds.getHeight());
		
		double curX, curY;
		curX = - this.bounds.getX() + this.x;
		curY = - this.bounds.getY() + this.y;
		
		String output = "";
		output += "<svg x=\"" + curX + "\" y=\"" + curY + "\" style=\"overflow:visible;\" >\n";
		//output += "<svg x=\""+this.x+"\" y=\""+this.y+"\" style=\"overflow:visible;\" >\n";
		for(SvgComponent svgComponent : this.svgTree) {
			output += svgComponent.renderTag() + "\n";
		}
		output += "</svg>\n";
		
		return output;
	}
	
	public void writeToSVG(Path outputFilePath) {
		double w, h;
		/*
		w = this.width;
		h = this.height;
		*/
		w = this.bounds.getWidth();
		h = this.bounds.getHeight();
		
		double curX, curY;
		curX = - this.bounds.getX();
		curY = - this.bounds.getY();
		
		// TEMP //
		/*
		w = Math.max(this.width, this.bounds.getWidth());
		h = Math.max(this.getHeight(),  this.bounds.getHeight());
		*/
		//////////
		
		///// DEBUG
		this.setColor(Color.RED);
		this.drawRect(this.bounds.getX(), this.bounds.getY(), this.bounds.getWidth(), this.bounds.getHeight());
		/////
		
		System.out.println(outputFilePath);
		
		String output = "";
		//output += "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"" + w + "\" height=\"" + h + "\">\n";
		output += "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"" + w + "\" height=\"" + h + "\" style=\"overflow:visible;\" >\n";
		//output += "<g transform=\"translate(" + curX + " " + curY + ")\" >\n";
		//output += "<g x=\"" + curX + "\" y=\"" + curY + "\" >\n";
		//output += "<svg x=\"" + curX + "\" y=\"" + curY + "\" style=\"overflow:visible;\" >\n";
		output += this.renderTag();
		//output += "</g>\n";
		//output += "</svg>\n";
		output += "</svg>\n";
		
		try {
			Files.createDirectories(outputFilePath.getParent());
			Files.write(outputFilePath, output.getBytes(Charset.forName("UTF-8")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(this.bounds.getX()+" "+this.bounds.getMinX()+" "+this.bounds.getWidth()+" "+(this.bounds.getX()+this.bounds.getWidth()));
		System.out.println(this.bounds.getY()+" "+this.bounds.getMinY()+" "+this.bounds.getHeight()+" "+(this.bounds.getY()+this.bounds.getHeight()));
	}
	
	private void enlargeBounds(Shape shape) {
		Shape transformedShape = this.sc.getTransform().createTransformedShape(shape);
		Rectangle2D bounds = transformedShape.getBounds2D();
		if(this.bounds == null)
			this.bounds = bounds;
		else
			this.bounds.add(bounds);
	}
}

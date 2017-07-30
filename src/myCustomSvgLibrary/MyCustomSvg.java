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

public class MyCustomSvg extends SvgComponent{
	private int width, height;
	private Rectangle2D bounds;
	private ArrayList<SvgComponent> svgTree = new ArrayList<SvgComponent>();
	private Padding padding;
	
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
		this.padding = new Padding(100);
	}

	public double getWidth() {
		return this.width; // this.bounds.getWidth() + this.padding.getHorizontalPadding();
	}

	public double getHeight() {
		return this.height; // this.bounds.getHeight() + this.padding.getVerticalPadding();
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
	
	public void setTransform(AffineTransform t) {
		this.sc.setTransform(t);
	}
	
	public void drawRect(double x, double y, double width, double height) {
		Rect rect = new Rect(x, y, width, height, sc);
		svgTree.add(rect);
		this.enlargeBounds(new Rectangle2D.Double(x, y, width, height));
	}
	
	public void drawRect(Rectangle2D rect) {
		this.drawRect(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
	}
	
	public void drawEllipse(double x, double y, double width, double height) {
		Ellipse el = new Ellipse(x, y, width, height, this.sc);
		svgTree.add(el);
		this.enlargeBounds(new Ellipse2D.Double(x, y, width, height));
	}
	
	public void drawLine(double x1, double y1, double x2, double y2) {
		Line line = new Line(x1, y1, x2, y2, this.sc);
		svgTree.add(line);
		this.enlargeBounds(new Line2D.Double(x1, y1, x2, y2));
	}
	
	public void drawString(String text, double x, double y) {
		Rectangle2D boundsH = this.sc.getFont().getStringBounds(text, new FontRenderContext(null, true, true));
		Rectangle2D boundsV = this.sc.getFont().createGlyphVector(this.getFontMetrics().getFontRenderContext(), text).getVisualBounds();
		Rectangle2D bounds = new Rectangle2D.Double(boundsH.getX() + x, boundsV.getY() + y, boundsH.getWidth(), boundsV.getHeight());
		
		Str str = new Str(text, x, y, width, this.sc);
		svgTree.add(str);
		
		this.enlargeBounds(bounds);
		
		///// DEBUG
		this.setColor(Color.RED);
		this.drawRect(bounds);
		this.setColor(Color.BLACK);
		/////
	}
	
	public String renderTag() {
		String svgOpeningTag = "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"" + this.width + "\" height=\"" + this.height + "\">";
		String svgClosingTag = "</svg>";
		
		ArrayList<String> svgTreeTags = new ArrayList<String>();
		svgTreeTags.add(svgOpeningTag);
		for(SvgComponent svgComponent : this.svgTree) {
			svgTreeTags.add(svgComponent.renderTag());
			//System.out.println(svgComponent.renderTag());
		}

		System.out.println("fixed dimensions : "+this.width+"x"+this.height);
		System.out.println("dynamic dimensions : "+this.bounds.getWidth()+"x"+this.bounds.getHeight());

		svgTreeTags.add(svgClosingTag);
		
		String output = "";
		for(String currentTag : svgTreeTags)
			output += currentTag + "\n";
		
		return output;
	}
	
	public void writeToSVG(Path outputFilePath) {
		///// DEBUG
		this.setColor(Color.RED);
		this.drawRect(this.bounds.getX(), this.bounds.getY(), this.bounds.getWidth(), this.bounds.getHeight());
		/////
		
		System.out.println(outputFilePath);
		
		String tag = this.renderTag();
		
		
		try {
			Files.createDirectories(outputFilePath.getParent());
			Files.write(outputFilePath, tag.getBytes(Charset.forName("UTF-8")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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

package myCustomSvgLibrary;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MyCustomSvg {
	private int width, height;
	private ArrayList<SvgComponent> svgTree = new ArrayList<SvgComponent>();
	private StyleContext curSc = new StyleContext(
		new AffineTransform(),
		Color.BLACK,
		new Font("Arial", Font.PLAIN, 12),
		new BasicStroke(1)
	);
	
	public MyCustomSvg(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public FontMetrics getFontMetrics() {
		Canvas c = new Canvas();
		return c.getFontMetrics(this.curSc.getFont());
	}
	
	public AffineTransform getTransform() {
		return this.curSc.getTransform();
	}
	
	public void translate(double tx, double ty) {
		this.curSc.translate(tx, ty);
	}
	
	public void rotate(double theta) {
		this.curSc.rotate(theta);
	}
	
	public void setColor(Color c) {
		this.curSc.setColor(c);
	}
	
	public void setFont(Font f) {
		this.curSc.setFont(f);
	}
	
	public void setStroke(BasicStroke s) {
		this.curSc.setStroke(s);
	}
	
	public void setTransform(AffineTransform t) {
		this.curSc.setTransform(t);
	}
	
	public void drawRect(double x, double y, double width, double height) {
		Rect rect = new Rect(x, y, width, height, curSc);
		svgTree.add(rect);
	}
	
	public void drawEllipse(double x, double y, double width, double height) {
		Ellipse el = new Ellipse(x, y, width, height, curSc);
		svgTree.add(el);
	}
	
	public void drawLine(double x1, double y1, double x2, double y2) {
		Line line = new Line(x1, y1, x2, y2, curSc);
		svgTree.add(line);
	}
	
	public void drawString(String text, double x, double y) {
		Str str = new Str(text, x, y, curSc);
		svgTree.add(str);
	}
	
	public void draw(SvgComponent c) {
		svgTree.add(c);
	}
	
	public void writeToSVG(Path outputFilePath) {
		System.out.println(outputFilePath);
		
		String svgOpeningTag = "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"" + this.width + "\" height=\"" + this.height + "\">";
		String svgClosingTag = "</svg>";
		
		ArrayList<String> svgTreeTags = new ArrayList<String>();
		svgTreeTags.add(svgOpeningTag);
		for(SvgComponent svgComponent : this.svgTree) {
			svgTreeTags.add(svgComponent.renderTag());
			//System.out.println(svgComponent.renderTag());
		}
		svgTreeTags.add(svgClosingTag);

		try {
			Files.createDirectories(outputFilePath.getParent());
			Files.write(outputFilePath, svgTreeTags, Charset.forName("UTF-8"));
		} catch (IOException e) {System.out.println(e);}
	}
}

package myCustomSvgLibrary;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;

public class MyPath2D {
	private Path2D path;
	private double x, y;
	
	public MyPath2D() {
		this.path = new Path2D.Double();
		this.x = 0;
		this.y = 0;
	}
	
	public void moveTo(double x, double y) {
		this.path.moveTo(x, y);
		this.x = x;
		this.y = y;
	}
	
	public void moveToR(double x, double y) {
		this.path.moveTo(this.x + x, this.y + y);
		this.x += x;
		this.y += y;
	}
	
	public void lineTo(double x, double y) {
		this.path.lineTo(x, y);
		this.x = x;
		this.y = y;
	}
	
	public void lineToR(double x, double y) {
		this.path.lineTo(this.x + x, this.y + y);
		this.x += x;
		this.y += y;
	}
	
	public void closePath() {
		this.path.closePath();
	}
	
	public PathIterator getPathIterator() {
		return this.path.getPathIterator(null);
	}
	
	public Path2D getPath2D() {
		return this.path;
	}
}

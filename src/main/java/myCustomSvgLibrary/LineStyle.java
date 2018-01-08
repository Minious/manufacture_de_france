package myCustomSvgLibrary;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import generateurCoteVerriere.Utils;
import myCustomSvgLibraryEnhanced.Point;

public class LineStyle {
	@SuppressWarnings("unused")
	private double style[];
	
	public LineStyle(double style[]){
		this.style = style;
	}
	
	public static void traceStylishLine(Graphics2D g, Point p1, Point p2, double style[]){
		double distanceMax = Point.distance(p1, p2);
		
		AffineTransform orig = g.getTransform();
		
		g.translate(p1.x, p1.y);
		g.rotate(- Utils.getAngle(p1, p2));
		
		double distance = 0;
		double curLength = 0;
		for(int i=0;;i++){
			curLength = style[i%style.length];
			if(i % 2 == 0)
				g.drawLine(0, 0, 0, (int) curLength);
			g.translate(0, curLength);
			distance += curLength;
			if(distance > distanceMax)
				break;
		}
		
		g.setTransform(orig);
	}
}

package myCustomSvgLibraryEnhanced;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;

import generateurCoteVerriere.Utils;
import myCustomSvgLibrary.MyCustomSvg;

public class MyCustomSvgEnhanced extends MyCustomSvg {
	public double underLineGap = 0;

	public MyCustomSvgEnhanced(int width, int height) {
		super(width, height);
	}
	
	public void setUnderLineGap(double u) {
		this.underLineGap = u;
	}

	public void setFont(int size, String font){
		Font f = new Font(font, Font.PLAIN, size);
		this.setFont(f);
	}
	
	public void drawDiameterCote(String displayedCote, Point p1, Point p2, int shift, ShiftMode shiftMode, int underLineGap){
		if(p1.x != p2.x || p1.y != p2.y)
			drawDiameterCote(displayedCote, p1, - Utils.getAngle(p1, p2), shift, shiftMode, underLineGap);
	}
	
	public void drawDiameterCote(String displayedCote, Point p1, double angle, int shift, ShiftMode shiftMode, int customUnderLineGap){
		AffineTransform orig = this.getTransform();
		
		FontMetrics metrics = this.getFontMetrics();
		int coteStringWidth = metrics.stringWidth(displayedCote);

		this.translate(p1.x, p1.y);
		this.rotate(angle);
		
		if(angle <= 0){
			double lineLength = shift;
			
			if(shiftMode == ShiftMode.LEFT)
				lineLength += coteStringWidth;
			if(shiftMode == ShiftMode.CENTER)
				lineLength += coteStringWidth / 2;
			if(shiftMode == ShiftMode.RIGHT)
				lineLength += 0;
			
			this.drawLine(0, 0, 0, (int) lineLength);

			this.translate(0, lineLength - coteStringWidth);
			this.translate(customUnderLineGap, 0);
			this.rotate(Math.PI / 2);

			this.drawString(displayedCote, 0, 0);
		}
		else{
			double lineLength = shift;
			
			if(shiftMode == ShiftMode.LEFT)
				lineLength += 0;
			if(shiftMode == ShiftMode.CENTER)
				lineLength += coteStringWidth / 2;
			if(shiftMode == ShiftMode.RIGHT)
				lineLength += coteStringWidth;
			
			this.drawLine(0, 0, 0, (int) lineLength);

			this.translate(0, lineLength);
			this.translate(- customUnderLineGap, 0);
			this.rotate(- Math.PI / 2);

			this.drawString(displayedCote, 0, 0);
		}

		this.setTransform(orig);
	}
	
	public void drawString(String displayedStr, Point p, double shift, ShiftMode shiftMode){
		FontMetrics metrics = this.getFontMetrics();
		double stringWidth = metrics.stringWidth(displayedStr);
		
		double abscisse = p.x + shift;
		if(shiftMode == ShiftMode.LEFT)
			abscisse += 0;
		if(shiftMode == ShiftMode.CENTER)
			abscisse += - stringWidth / 2;
		if(shiftMode == ShiftMode.RIGHT)
			abscisse += - stringWidth;

		this.drawString(displayedStr, abscisse, p.y);
	}

	public void drawDistanceCote(Point p1, Point p2, double offset, double shift, ShiftMode shiftMode, double underLineGap){
		if(p1.x != p2.x || p1.y != p2.y){
			AffineTransform orig = this.getTransform();

			DecimalFormat myFormatter = new DecimalFormat("#.##");
			String formatedCote = myFormatter.format(Point.distance(p1, p2));
			
			this.translate(p1.x, p1.y);
			this.rotate(- Utils.getAngle(p1, p2));
			
			double distance = Point.distance(p1, p2);

			FontMetrics metrics = this.getFontMetrics();
			int coteStringWidth = metrics.stringWidth(formatedCote);

			this.drawLine(0, 0, offset, 0);
			
			this.translate(0, distance);

			this.drawLine(0, 0, offset, 0);
			
			this.translate(offset, - distance / 2);
			
			double lowerBound = shift, upperBound = shift;
			
			if(shiftMode == ShiftMode.LEFT)
				lowerBound += 0;
			if(shiftMode == ShiftMode.CENTER)
				lowerBound += - coteStringWidth / 2;
			if(shiftMode == ShiftMode.RIGHT)
				lowerBound += - coteStringWidth;
			
			if(shiftMode == ShiftMode.LEFT)
				upperBound += coteStringWidth;
			if(shiftMode == ShiftMode.CENTER)
				upperBound += coteStringWidth / 2;
			if(shiftMode == ShiftMode.RIGHT)
				upperBound += 0;
			
			this.drawLine(0, lowerBound < - distance / 2 ? lowerBound : - distance / 2, 0, upperBound > distance / 2 ? upperBound : distance / 2);
	
			this.translate(underLineGap, 0);
			
			if(shiftMode == ShiftMode.LEFT)
				this.translate(0, shift);
			if(shiftMode == ShiftMode.CENTER)
				this.translate(0, - coteStringWidth / 2 + shift);
			if(shiftMode == ShiftMode.RIGHT)
				this.translate(0, - coteStringWidth + shift);
			this.rotate(Math.PI / 2);
			
			this.drawString(formatedCote, 0, 0);
			
			this.setTransform(orig);
		}
	}
	
	public void drawDistanceCote(Point p1, Point p2, double offset, double shift, ShiftMode shiftMode){
		this.drawDistanceCote(p1, p2, offset, shift, shiftMode, underLineGap);
	}
	
	public void drawDistanceCote(Point p1, Point p2, double offset, double shift){
		this.drawDistanceCote(p1, p2, offset, shift, ShiftMode.CENTER);
	}
	
	public void drawDistanceCote(Point p1, Point p2, double offset){
		this.drawDistanceCote(p1, p2, offset, 0);
	}
	
	public void drawDistanceCote(Point p1, Point p2){
		this.drawDistanceCote(p1, p2, 10);
	}
	
	public void drawOval(Point center, Point dimensions){
		this.drawEllipse(center.x - dimensions.x / 2, center.y - dimensions.y / 2, dimensions.x, dimensions.y);
	}
	
	public void drawCircle(Point center, double diameter){
		this.drawOval(center, new Point(diameter, diameter));
	}
	
	public enum ShiftMode{
		CENTER,
		LEFT,
		RIGHT;
	}
}

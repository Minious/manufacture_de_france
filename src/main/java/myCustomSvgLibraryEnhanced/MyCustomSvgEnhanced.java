package myCustomSvgLibraryEnhanced;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;

import generateurCoteVerriere.Utils;
import myCustomSvgLibrary.MyCustomSvg;
import utils.MyPath2D;

public class MyCustomSvgEnhanced extends MyCustomSvg {
	private double underLineGap = 0;
	private double distanceCoteGap = 4;
	
	public void setUnderLineGap(double underLineGap) {
		this.underLineGap = underLineGap;
	}

	public void setFont(int size, String font){
		Font f = new Font(font, Font.PLAIN, size);
		this.setFont(f);
	}
	
	public void drawDiameterCote(String displayedCote, Point p1, Point p2, int shift, ShiftMode shiftMode, double customUnderLineGap){
		if(p1.x != p2.x || p1.y != p2.y)
			drawDiameterCote(displayedCote, p1, - Utils.getAngle(p1, p2), shift, shiftMode, customUnderLineGap);
	}
	
	public void drawDiameterCote(String displayedCote, Point p, double angle, int shift){
		drawDiameterCote(displayedCote, p, angle, shift, ShiftMode.LEFT);
	}
	
	public void drawDiameterCote(String displayedCote, Point p, double angle, int shift, ShiftMode shiftMode){
		drawDiameterCote(displayedCote, p, angle, shift, shiftMode, this.underLineGap);
	}
		
	public void drawDiameterCote(String displayedCote, Point p, double angle, int shift, ShiftMode shiftMode, double customUnderLineGap){
		AffineTransform orig = this.getTransform();
		
		FontMetrics metrics = this.getFontMetrics();
		int coteStringWidth = metrics.stringWidth(displayedCote);

		this.translate(p.x, p.y);
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
	
	public void drawPercage(String valeurPercage, Point p){
		if(valeurPercage.charAt(0) == 'Ø') {
			String actualValeurPercage = valeurPercage.substring(1);
			double diametrePercage;
			if(actualValeurPercage.charAt(0) == 'M') {
				int percageNumericValue =  Integer.parseInt(actualValeurPercage.substring(1));
				double diametrePercageTaraudage = getDiametrePercageTaraudage(percageNumericValue);
				double pas = getPasPercageTaraudage(percageNumericValue);
				diametrePercage = diametrePercageTaraudage + pas;
				this.drawEllipticalArc(p.x, p.y, diametrePercage - pas, diametrePercage - pas, Math.PI * 3 / 2, Math.PI);
			} else
				diametrePercage = Double.parseDouble(actualValeurPercage);
			this.drawCircle(p, diametrePercage);
		}
	}
	
	public void drawPercage(String valeurPercage, Point p, double angle, int shift, ShiftMode shiftMode, double customUnderLineGap){
		this.drawPercage(valeurPercage, p);
		this.drawDiameterCote(valeurPercage, p, angle, shift, shiftMode, customUnderLineGap);
	}
	
	private double getDiametrePercageTaraudage(int valeur) {
		switch(valeur) {
			case 2: return 1.60;
			case 3: return 2.50;
			case 4: return 3.30;
			case 5: return 4.20;
			case 6: return 5.00;
			case 8: return 6.80;
			case 10: return 8.50;
			case 12: return 10.20;
		}
		return Double.NaN;
	}
	
	private double getPasPercageTaraudage(int valeur) {
		switch(valeur) {
			case 2: return 0.40;
			case 3: return 0.50;
			case 4: return 0.70;
			case 5: return 0.80;
			case 6: return 1.00;
			case 8: return 1.25;
			case 10: return 1.50;
			case 12: return 1.75;
		}
		return Double.NaN;
	}
		
	public void drawString(String displayedStr, Point p){
		this.drawString(displayedStr, p, 0, ShiftMode.LEFT);
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

	public void drawDistanceCote(Point p1, Point p2, double offset, double shift, ShiftMode shiftMode, double underLineGap, boolean reversed){
		if(p1.x != p2.x || p1.y != p2.y){
			AffineTransform orig = this.getTransform();

			DecimalFormat myFormatter = new DecimalFormat("#.##");
			String formatedCote = myFormatter.format(Point.distance(p1, p2));
			
			this.translate(p1.x, p1.y);
			this.rotate(- Utils.getAngle(p1, p2));
			
			double distance = Point.distance(p1, p2);

			FontMetrics metrics = this.getFontMetrics();
			int coteStringWidth = metrics.stringWidth(formatedCote);

			this.drawLine(this.distanceCoteGap, 0, offset + this.distanceCoteGap, 0);
			
			this.translate(0, distance);

			this.drawLine(this.distanceCoteGap, 0, offset + this.distanceCoteGap, 0);
			
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
			
			double actualLowerBound = lowerBound < - distance / 2 ? lowerBound : - distance / 2;
			double actualUpperBound = upperBound > distance / 2 ? upperBound : distance / 2;
			
			this.drawLine(0, actualLowerBound, 0, actualUpperBound);
			
			double arrowWidth = 10, arrowHeight = 3;
			MyPath2D arrowPath = new MyPath2D();
			arrowPath.moveTo(0, 0);
			arrowPath.lineTo(- arrowWidth, arrowHeight / 2);
			arrowPath.lineTo(- arrowWidth, - arrowHeight / 2);
			arrowPath.closePath();

			this.rotate(Math.PI / 2);
			if(reversed)
				this.rotate(Math.PI);

			this.setFillColor(Color.black);
			this.translate(- distance / 2, 0);
			if(distance > 2 * arrowWidth) {
				this.rotate(Math.PI);
				this.drawPath(arrowPath);
				this.rotate(Math.PI);
			} else {
				this.drawPath(arrowPath);
			}
			this.translate(distance, 0);
			if(distance > 2 * arrowWidth) {
				this.drawPath(arrowPath);
			} else {
				this.rotate(Math.PI);
				this.drawPath(arrowPath);
				this.rotate(Math.PI);
			}
			this.translate(- distance / 2, 0);
			this.removeFillColor();
			
			this.translate(0, - underLineGap);
			if(shiftMode == ShiftMode.LEFT)
				this.translate(shift, 0);
			if(shiftMode == ShiftMode.CENTER)
				this.translate(- coteStringWidth / 2 + shift, 0);
			if(shiftMode == ShiftMode.RIGHT)
				this.translate(- coteStringWidth + shift, 0);
			
			this.drawString(formatedCote, 0, 0);
			
			this.setTransform(orig);
		}
	}
	
	public void drawDistanceCote(Point p1, Point p2, double offset, double shift, ShiftMode shiftMode, double underLineGap){
		this.drawDistanceCote(p1, p2, offset, shift, shiftMode, underLineGap, false);
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
	
	public void drawReversedDistanceCote(Point p1, Point p2, double offset, double shift, ShiftMode shiftMode, double underLineGap){
		this.drawDistanceCote(p1, p2, offset, shift, shiftMode, underLineGap, true);
	}
	
	public void drawReversedDistanceCote(Point p1, Point p2, double offset, double shift, ShiftMode shiftMode){
		this.drawReversedDistanceCote(p1, p2, offset, shift, shiftMode, underLineGap);
	}
	
	public void drawReversedDistanceCote(Point p1, Point p2, double offset, double shift){
		this.drawReversedDistanceCote(p1, p2, offset, shift, ShiftMode.CENTER);
	}
	
	public void drawReversedDistanceCote(Point p1, Point p2, double offset){
		this.drawReversedDistanceCote(p1, p2, offset, 0);
	}
	
	public void drawReversedDistanceCote(Point p1, Point p2){
		this.drawReversedDistanceCote(p1, p2, 10);
	}
	
	public void drawLine(Point p1, Point p2){
		this.drawLine(p1.x, p1.y, p2.x, p2.y);
	}
	
	public void drawOval(Point center, Point dimensions){
		this.drawEllipse(center.x - dimensions.x / 2, center.y - dimensions.y / 2, dimensions.x, dimensions.y);
	}
	
	public void drawCircle(Point center, double diameter){
		this.drawOval(center, new Point(diameter, diameter));
	}
	
	public void setDashArray(StyleTrait styleTrait) {
		switch(styleTrait) {
			case MIXTE:
				this.setDashArray(new float[]{10, 2, 2, 2});
				break;
			case INTERROMPU:
				this.setDashArray(new float[]{4, 1});
				break;
		}
	}
	
	public enum ShiftMode{
		CENTER,
		LEFT,
		RIGHT,
		TOP,
		BOTTOM
	}
	
	public enum StyleTrait {
		MIXTE,
		INTERROMPU
	}
}
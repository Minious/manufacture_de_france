package com.manufacturedefrance.techdrawgen;

import java.awt.FontMetrics;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

import com.manufacturedefrance.svgen.MyCustomSvg;
import com.manufacturedefrance.svgen.SvgComponent;
import com.manufacturedefrance.svgen.styling.Color;
import com.manufacturedefrance.svgen.styling.Font;
import com.manufacturedefrance.utils.MyPath2D;
import com.manufacturedefrance.utils.MyPoint2D;

public class MyCustomSvgEnhanced extends MyCustomSvg {
	private double rememberedUnderLineGap = 0;
	private double distanceCoteGap = 4;

	private static final double COTE_ARROW_WIDTH = 10;
	private static final double COTE_ARROW_HEIGHT = 3;
	private static final MyPath2D ARROW_PATH = getArrowPath();
	
	public void setUnderLineGap(double underLineGap) {
		this.rememberedUnderLineGap = underLineGap;
	}

	public void setFont(int size, String name, boolean bold){
		Font font = new Font(name, size, bold);
		this.setFont(font);
	}

	public void setFont(int size, String name){
		Font font = new Font(name, size);
		this.setFont(font);
	}
	
	public void drawDiameterCote(String displayedCote, MyPoint2D p1, MyPoint2D p2, int shift, ShiftMode shiftMode, double customUnderLineGap){
		if(p1.x != p2.x || p1.y != p2.y)
			drawDiameterCote(displayedCote, p1, Utils.getAngle(p1, p2), shift, shiftMode, customUnderLineGap);
	}
	
	public void drawDiameterCote(String displayedCote, MyPoint2D p, double angle, int shift){
		drawDiameterCote(displayedCote, p, angle, shift, ShiftMode.LEFT);
	}
	
	public void drawDiameterCote(String displayedCote, MyPoint2D p, double angle, int shift, ShiftMode shiftMode){
		drawDiameterCote(displayedCote, p, angle, shift, shiftMode, this.rememberedUnderLineGap);
	}
		
	public void drawDiameterCote(String displayedCote, MyPoint2D p, double angle, int shift, ShiftMode shiftMode, double customUnderLineGap){
		AffineTransform orig = this.getTransform();
		
		FontMetrics metrics = this.getFontMetrics();
		double coteStringWidth = metrics.stringWidth(displayedCote);

		this.translate(p.x, p.y);
		this.rotate(angle);
		
		if(angle <= 0){
			double lineLength = shift;
			
			switch(shiftMode) {
				case LEFT:
					lineLength += coteStringWidth;
					break;
				case CENTER:
					lineLength += coteStringWidth / 2;
					break;
				case RIGHT:
					lineLength += 0;
					break;
			}
			
			this.drawLine(0, 0, 0, (int) lineLength);

			this.translate(0, lineLength - coteStringWidth);
			this.translate(customUnderLineGap, 0);
			this.rotate(Math.PI / 2);

			this.drawString(displayedCote, 0, 0);
		}
		else{
			double lineLength = shift;
			
			switch(shiftMode) {
				case LEFT:
					lineLength += 0;
					break;
				case CENTER:
					lineLength += coteStringWidth / 2;
					break;
				case RIGHT:
					lineLength += coteStringWidth;
					break;
			}
			
			this.drawLine(0, 0, 0, (int) lineLength);

			this.translate(0, lineLength);
			this.translate(- customUnderLineGap, 0);
			this.rotate(- Math.PI / 2);

			this.drawString(displayedCote, 0, 0);
		}

		this.setTransform(orig);
	}
	
	public void drawPercage(String valeurPercage, MyPoint2D p){
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
	
	public void drawPercage(String valeurPercage, MyPoint2D p, double angle, int shift, ShiftMode shiftMode, double customUnderLineGap){
		this.drawPercage(valeurPercage, p);
		this.drawDiameterCote(valeurPercage, p, angle, shift, shiftMode, customUnderLineGap);
	}
	
	private double getDiametrePercageTaraudage(int valeur) {
		switch(valeur) {
			case 2:
				return 1.60;
			case 3:
				return 2.50;
			case 4:
				return 3.30;
			case 5:
				return 4.20;
			case 6:
				return 5.00;
			case 8:
				return 6.80;
			case 10:
				return 8.50;
			case 12:
				return 10.20;
			default:
				return Double.NaN;
		}
	}
	
	private double getPasPercageTaraudage(int valeur) {
		switch(valeur) {
			case 2:
				return 0.40;
			case 3:
				return 0.50;
			case 4:
				return 0.70;
			case 5:
				return 0.80;
			case 6:
				return 1.00;
			case 8:
				return 1.25;
			case 10:
				return 1.50;
			case 12:
				return 1.75;
			default:
				return Double.NaN;
		}
	}
		
	public void drawString(String displayedStr, MyPoint2D p){
		this.drawString(displayedStr, p, 0, ShiftMode.LEFT);
	}
	
	public void drawString(String displayedStr, MyPoint2D p, double shift, ShiftMode shiftMode){
		Rectangle2D strBounds = getStringBounds(displayedStr);
		double stringWidth = strBounds.getWidth();
		
		double abscisse = p.x + shift;
		if(shiftMode == ShiftMode.LEFT)
			abscisse += 0;
		if(shiftMode == ShiftMode.CENTER)
			abscisse += - stringWidth / 2;
		if(shiftMode == ShiftMode.RIGHT)
			abscisse += - stringWidth;

		this.drawString(displayedStr, abscisse, p.y);
	}

	public void drawSimpleDistanceCote(MyPoint2D p1, MyPoint2D p2, double offset) {
		this.drawSimpleDistanceCote(p1, p2, offset, 0, ShiftMode.RIGHT, this.rememberedUnderLineGap, false);
	}

	public void drawSimpleDistanceCote(MyPoint2D p1, MyPoint2D p2, double offset, double shift, ShiftMode shiftMode, double customUnderLineGap, boolean reversed){
		if(p1.equals(p2))
			throw new IllegalStateException("Impossible de tracer cote : Ancres identiques");

		AffineTransform orig = this.getTransform();

		double distance = MyPoint2D.distance(p1, p2);
		String formatedCote = SvgComponent.DOUBLE_FORMAT.format(distance);

		this.translate(p2.x, p2.y);
		this.rotate(Utils.getAngle(p1, p2));

		Rectangle2D strBounds = getStringBounds(formatedCote);
		double coteStringWidth = strBounds.getWidth();

		this.drawLine(0, - this.distanceCoteGap, 0, - offset);

		this.translate(0, - offset);

		double lowerBound = shift;
		double upperBound = shift;

		if (shiftMode == ShiftMode.LEFT) {
			lowerBound += 0;
			upperBound += coteStringWidth;
		} else if(shiftMode == ShiftMode.CENTER) {
			lowerBound -= coteStringWidth / 2;
			upperBound += coteStringWidth / 2;
		} else if(shiftMode == ShiftMode.RIGHT) {
			lowerBound -= coteStringWidth;
			upperBound += 0;
		}

		double actualLowerBound = Math.min(lowerBound, 0);
		double actualUpperBound = Math.max(upperBound, 0);

		this.drawLine(actualLowerBound, 0, actualUpperBound, 0);

		if(reversed)
			this.rotate(Math.PI);

		this.translate(0, - customUnderLineGap);
		if(shiftMode == ShiftMode.LEFT)
			this.translate(shift, 0);
		if(shiftMode == ShiftMode.CENTER)
			this.translate(- coteStringWidth / 2 + shift, 0);
		if(shiftMode == ShiftMode.RIGHT)
			this.translate(- coteStringWidth + shift, 0);

		this.drawString(formatedCote, 0, 0);

		this.setTransform(orig);
	}

	public void drawDistanceCote(MyPoint2D p1, MyPoint2D p2, double offset, double shift, ShiftMode shiftMode, double customUnderLineGap, boolean reversed){
		if(p1.equals(p2))
			throw new IllegalStateException("Impossible de tracer cote : Ancres identiques");

		AffineTransform orig = this.getTransform();

		double distance = MyPoint2D.distance(p1, p2);
		String formatedCote = SvgComponent.DOUBLE_FORMAT.format(distance);

		this.translate(p1.x, p1.y);
		this.rotate(Utils.getAngle(p1, p2));

		Rectangle2D strBounds = getStringBounds(formatedCote);
		double coteStringWidth = strBounds.getWidth();

		this.drawLine(0, - this.distanceCoteGap, 0,  - (offset + this.distanceCoteGap));

		this.translate(distance, 0);

		this.drawLine(0, - this.distanceCoteGap, 0,  - (offset + this.distanceCoteGap));

		this.translate(- distance / 2, - offset);

		double lowerBound = shift;
		double upperBound = shift;

		if (shiftMode == ShiftMode.LEFT) {
			lowerBound += 0;
			upperBound += coteStringWidth;
		} else if(shiftMode == ShiftMode.CENTER) {
			lowerBound -= coteStringWidth / 2;
			upperBound += coteStringWidth / 2;
		} else if(shiftMode == ShiftMode.RIGHT) {
			lowerBound -= coteStringWidth;
			upperBound += 0;
		}

		double actualLowerBound = Math.min(lowerBound, - distance / 2);
		double actualUpperBound = Math.max(upperBound, distance / 2);

		this.drawLine(actualLowerBound, 0, actualUpperBound, 0);

		if(reversed)
			this.rotate(Math.PI);

		this.setFillColor(Color.BLACK);
		this.translate(- distance / 2, 0);
		if(distance > 2.5 * COTE_ARROW_WIDTH) {
			this.rotate(Math.PI);
			this.drawPath(ARROW_PATH);
			this.rotate(Math.PI);
		} else {
			this.drawPath(ARROW_PATH);
		}
		this.translate(distance, 0);
		if(distance > 2.5 * COTE_ARROW_WIDTH) {
			this.drawPath(ARROW_PATH);
		} else {
			this.rotate(Math.PI);
			this.drawPath(ARROW_PATH);
			this.rotate(Math.PI);
		}
		this.translate(- distance / 2, 0);
		this.removeFillColor();

		this.translate(0, - customUnderLineGap);
		if(shiftMode == ShiftMode.LEFT)
			this.translate(shift, 0);
		if(shiftMode == ShiftMode.CENTER)
			this.translate(- coteStringWidth / 2 + shift, 0);
		if(shiftMode == ShiftMode.RIGHT)
			this.translate(- coteStringWidth + shift, 0);

		this.drawString(formatedCote, 0, 0);

		this.setTransform(orig);
	}
	
	public void drawDistanceCote(MyPoint2D p1, MyPoint2D p2, double offset, double shift, ShiftMode shiftMode, double customUnderLineGap){
		this.drawDistanceCote(p1, p2, offset, shift, shiftMode, customUnderLineGap, false);
	}
	
	public void drawDistanceCote(MyPoint2D p1, MyPoint2D p2, double offset, double shift, ShiftMode shiftMode){
		this.drawDistanceCote(p1, p2, offset, shift, shiftMode, this.rememberedUnderLineGap);
	}
	
	public void drawDistanceCote(MyPoint2D p1, MyPoint2D p2, double offset, double shift){
		this.drawDistanceCote(p1, p2, offset, shift, ShiftMode.CENTER);
	}
	
	public void drawDistanceCote(MyPoint2D p1, MyPoint2D p2, double offset){
		this.drawDistanceCote(p1, p2, offset, 0);
	}
	
	public void drawReversedDistanceCote(MyPoint2D p1, MyPoint2D p2, double offset, double shift, ShiftMode shiftMode, double customUnderLineGap){
		this.drawDistanceCote(p1, p2, offset, shift, shiftMode, customUnderLineGap, true);
	}
	
	public void drawReversedDistanceCote(MyPoint2D p1, MyPoint2D p2, double offset, double shift, ShiftMode shiftMode){
		this.drawReversedDistanceCote(p1, p2, offset, shift, shiftMode, this.rememberedUnderLineGap);
	}
	
	public void drawReversedDistanceCote(MyPoint2D p1, MyPoint2D p2, double offset, double shift){
		this.drawReversedDistanceCote(p1, p2, offset, shift, ShiftMode.CENTER);
	}
	
	public void drawReversedDistanceCote(MyPoint2D p1, MyPoint2D p2, double offset){
		this.drawReversedDistanceCote(p1, p2, offset, 0);
	}
	
	public void drawLine(MyPoint2D p1, MyPoint2D p2){
		this.drawLine(p1.x, p1.y, p2.x, p2.y);
	}
	
	public void drawOval(MyPoint2D center, MyPoint2D dimensions){
		this.drawEllipse(center.x - dimensions.x / 2, center.y - dimensions.y / 2, dimensions.x, dimensions.y);
	}
	
	public void drawCircle(MyPoint2D center, double diameter){
		this.drawOval(center, new MyPoint2D(diameter, diameter));
	}

	private static MyPath2D getArrowPath(){
		MyPath2D arrowPath = new MyPath2D();
		arrowPath.moveTo(0, 0);
		arrowPath.lineTo(- COTE_ARROW_WIDTH, COTE_ARROW_HEIGHT / 2);
		arrowPath.lineTo(- COTE_ARROW_WIDTH, - COTE_ARROW_HEIGHT / 2);
		arrowPath.closePath();

		return arrowPath;
	}
	
	public void setDashArray(StyleTrait styleTrait) {
		switch(styleTrait) {
			case MIXTE:
				this.setDashArray(new double[]{10, 2, 2, 2});
				break;
			case INTERROMPU:
				this.setDashArray(new double[]{4, 1});
				break;
			case CONTINU:
				this.removeDashArray();
				break;
		}
	}
	
	public enum StyleTrait {
		MIXTE,
		INTERROMPU,
		CONTINU
	}

	public enum ShiftMode{
		CENTER,
		LEFT,
		RIGHT
	}
}

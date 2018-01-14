package com.manufacturedefrance.svgen;

import java.util.ArrayList;

import com.manufacturedefrance.techdrawgen.MyCustomSvgEnhanced;
import com.manufacturedefrance.techdrawgen.MyCustomSvgEnhanced.ShiftMode;

public class MyHandyLayout {

	private static final double DEFAULT_MARGE = 20;

	private double marge;
	private ArrayList<MyCustomSvg[]> gRows;
	private ArrayList<ShiftMode> shiftModes;

	public MyHandyLayout() {
		this(MyHandyLayout.DEFAULT_MARGE);
	}

	public MyHandyLayout(double marge) {
		this.marge = marge;
		this.gRows = new ArrayList<>();
		this.shiftModes = new ArrayList<>();
	}
	
	public void addRow(MyCustomSvg g, ShiftMode shiftMode) {
		this.addRow(new MyCustomSvg[] {g}, shiftMode);
	}
	
	public void addRow(MyCustomSvg[] gRow, ShiftMode shiftMode) {
		this.gRows.add(gRow);
		this.shiftModes.add(shiftMode);
	}
	
	public MyCustomSvg getSvg() {
		ArrayList<Double> widths = new ArrayList<>();
		ArrayList<Double> heights = new ArrayList<>();

		double maxWidth = 0;
		for(MyCustomSvg[] gRow : this.gRows) {
			double curWidth = 0;
			double maxHeight = 0;
			for(MyCustomSvg g : gRow) {
				curWidth += g.getWidth();
				curWidth += marge;
				
				if(g.getHeight() > maxHeight)
					maxHeight = g.getHeight();
			}
			curWidth -= marge;

			widths.add(curWidth);
			heights.add(maxHeight);
			
			if(curWidth > maxWidth)
				maxWidth = curWidth;
		}
		
		MyCustomSvg gFinal = new MyCustomSvgEnhanced();
		double curY = 0;
		for(int i=0;i<this.gRows.size();i++) {
			MyCustomSvg[] gRow = this.gRows.get(i);
			double curWidth = widths.get(i);
			double curHeight = heights.get(i);
			ShiftMode curShiftMode = this.shiftModes.get(i);
			
			double startX;
			switch(curShiftMode) {
				case LEFT:
					startX = - maxWidth / 2;
					break;
				case RIGHT:
					startX = maxWidth / 2 - curWidth;
					break;
				default: // or CENTER
					startX = - curWidth / 2;
					break;
			}
			
			double curX = startX;
			for(MyCustomSvg g : gRow) {
				gFinal.drawSvg(g, curX, curY);
				curX += g.getWidth() + marge;
			}
			
			curY += curHeight + marge;
		}
		
		return gFinal;
	}
}

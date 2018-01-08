package myCustomSvgLibrary;

import java.util.ArrayList;

import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced.ShiftMode;

public class MyHandyLayout {

	private double marge = 20;
	private ArrayList<MyCustomSvg[]> gs;
	private ArrayList<ShiftMode> shiftModes;
	
	public MyHandyLayout() {
		this.gs = new ArrayList<MyCustomSvg[]>();
		this.shiftModes = new ArrayList<ShiftMode>();
	}
	
	public void addRow(MyCustomSvg g, ShiftMode shiftMode) {
		this.addRow(new MyCustomSvg[] {g}, shiftMode);
	}
	
	public void addRow(MyCustomSvg[] gs, ShiftMode shiftMode) {
		this.gs.add(gs);
		this.shiftModes.add(shiftMode);
	}
	
	public MyCustomSvg getSvg() {
		ArrayList<Double> widths = new ArrayList<Double>();
		ArrayList<Double> heights = new ArrayList<Double>();

		double maxWidth = 0;
		for(MyCustomSvg[] gs : this.gs) {
			double curWidth = 0;
			double maxHeight = 0;
			for(MyCustomSvg g : gs) {
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
		for(int i=0;i<this.gs.size();i++) {
			MyCustomSvg[] gs = this.gs.get(i);
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
			for(MyCustomSvg g : gs) {
				gFinal.drawSvg(g, curX, curY);
				curX += g.getWidth() + marge;
			}
			
			curY += curHeight + marge;
		}
		
		return gFinal;
	}
}

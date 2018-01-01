package myCustomSvgLibrary;

import java.nio.file.Paths;

import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced.ShiftMode;
import myCustomSvgLibraryEnhanced.Point;

public class Test {

	public static void main(String[] args) {
		MyCustomSvgEnhanced g1 = new MyCustomSvgEnhanced();
		g1.drawCircle(new Point(50, 50), 80);
		
		MyCustomSvgEnhanced g2 = new MyCustomSvgEnhanced();
		g2.drawRect(0, 30, 90, 30);
		
		MyCustomSvgEnhanced g3 = new MyCustomSvgEnhanced();
		g3.drawString("COUCOU", 50, 50);
		
		MyCustomSvgEnhanced g = new MyCustomSvgEnhanced();
		g.drawSvg(g1, 0, 0, ShiftMode.CENTER, ShiftMode.CENTER);
		g.drawSvg(g2, 0, 0, ShiftMode.CENTER, ShiftMode.CENTER);
		g.drawSvg(g3, 0, 0, ShiftMode.CENTER, ShiftMode.CENTER);
		
		g.writeToSVG(Paths.get("").toAbsolutePath().resolve("test.svg"));
	}

}

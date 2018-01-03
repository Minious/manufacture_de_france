package myCustomSvgLibrary;

import java.nio.file.Paths;

import generateurCoteVerriere.dessinProfil.DessinProfil;
import generateurCoteVerriere.dessinProfil.DessinProfilException;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced;
import myCustomSvgLibraryEnhanced.MyCustomSvgEnhanced.ShiftMode;
import myCustomSvgLibraryEnhanced.Point;

public class Test {

	public static void main(String[] args) {
		//test1();
		//test2();
		//test3();
		//test4();
		test5();
	}
	
	private static void test1() {
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

	private static void test2() {
		MyCustomSvgEnhanced g = new MyCustomSvgEnhanced();
		g.drawRect(0, 0, 180, 80);
		
		MyCustomSvgEnhanced gbis = new MyCustomSvgEnhanced();
		gbis.drawRect(0, 0, 160, 60);
		
		MyCustomSvgEnhanced gter = new MyCustomSvgEnhanced();
		gter.drawRect(0, 0, 140, 40);
		
		gbis.drawSvg(gter, 10, 10);
		
		g.drawSvg(gbis, 10, 10);
		
		g.writeToSVG(Paths.get("").toAbsolutePath().resolve("test.svg"));
	}

	private static void test3() {
		MyCustomSvgEnhanced g = new MyCustomSvgEnhanced();
		g.setPadding(new Padding(10));
		g.setBorders(true);
		g.drawRect(0, 0, 100, 100);
		
		g.writeToSVG(Paths.get("").toAbsolutePath().resolve("test.svg"));
	}

	private static void test4() {
		MyCustomSvgEnhanced g = new MyCustomSvgEnhanced();
		g.drawRect(0, 0, 180, 80);
		g.setPadding(new Padding(50));
		g.setBorders(true);
		
		MyCustomSvgEnhanced gbis = new MyCustomSvgEnhanced();
		gbis.drawRect(0, 0, 160, 60);
		gbis.setPadding(new Padding(20));
		gbis.setBorders(true);
		
		MyCustomSvgEnhanced gter = new MyCustomSvgEnhanced();
		gter.drawRect(0, 0, 140, 40);
		gter.setPadding(new Padding(10));
		gter.setBorders(true);
		
		gbis.drawSvg(gter, 10, 10);
		
		g.drawSvg(gbis, 10, 10);
		
		g.writeToSVG(Paths.get("").toAbsolutePath().resolve("test.svg"));
	}

	private static void test5() {
		DessinProfil profil = new DessinProfil(400, 30);
		try {
			profil.setEpaulement(20, 3);
			//profil.isCorniere();
			profil.setLargeurPercage(4);
			profil.addPercage(25);
			profil.addPercage(120);
			profil.addPercage(200);
			profil.addPercage(230);
			profil.addPercage(270);
			profil.addPercage(370, "CACA");
			profil.addCoteDroite(0, 1, 0);
			profil.addCoteDroite(3, 2, 0);
			profil.addCoteDroite(3, 0, 1);
		} catch (DessinProfilException e) {
			e.printStackTrace();
		}
		MyCustomSvg g = profil.render();
		g.setPadding(new Padding(10));
		
		g.writeToSVG(Paths.get("").toAbsolutePath().resolve("test.svg"));
	}
}

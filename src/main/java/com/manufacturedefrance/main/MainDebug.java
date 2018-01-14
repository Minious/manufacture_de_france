package com.manufacturedefrance.main;

import java.nio.file.Paths;
import java.util.HashMap;

import com.manufacturedefrance.techdrawgen.Renderer;

public class MainDebug {
	public static void main(String[] args) {
		double hauteurVerriere = 1297d;
		double largeurVerriere = 1465d;
		int nbPartitions = 4;
		
		HashMap<String, Object> myArgs = new HashMap<>();
		myArgs.put("ARC", "190000310");
		myArgs.put("client", "M. Dupont");
		myArgs.put("reference", "61140089");
		myArgs.put("hauteurVerriere", hauteurVerriere);
		myArgs.put("largeurVerriere", largeurVerriere);
		myArgs.put("nbPartitions", nbPartitions);
		
		Renderer.render("Premium", Paths.get("").toAbsolutePath(), myArgs);
	}
}

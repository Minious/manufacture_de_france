package com.manufacturedefrance.main;

import java.nio.file.Paths;
import java.util.HashMap;

import com.manufacturedefrance.techdrawgen.Renderer;

public class MainDebug {
	public static void main(String[] args) {
		double hauteurVerriere = 1200d;
		double largeurVerriere = 1400d;
		int nbPartitions = 5;
		String natureVitrage = "44.2 opal";
		
		HashMap<String, Object> myArgs = new HashMap<>();
		myArgs.put("ARC", "190000310");
		myArgs.put("client", "M. Dupont");
		myArgs.put("reference", "61140089");
		myArgs.put("hauteurVerriere", hauteurVerriere);
		myArgs.put("largeurVerriere", largeurVerriere);
		myArgs.put("nbPartitions", nbPartitions);
		myArgs.put("natureVitrage", natureVitrage);
		
		Renderer.render("Mecanica", Paths.get("").toAbsolutePath(), myArgs);
	}
}

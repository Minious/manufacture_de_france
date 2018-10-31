package com.manufacturedefrance.main;

import java.nio.file.Paths;
import java.util.HashMap;

import com.manufacturedefrance.techdrawgen.Renderer;

public class MainDebug {
	public static void main(String[] args) {
		double hauteurVerriere = 1200d;
		double largeurVerriere = 1403d;
		int nbPartitions = 3;
		double epaisseurVitrage = 44.2;
		String natureVitrage = "opal";
		String finition = "Noir 2100 sablé";
		
		HashMap<String, Object> myArgs = new HashMap<>();
		myArgs.put("ARC", "190000310");
		myArgs.put("client", "M. Dupont");
		myArgs.put("reference", "61140089");
		myArgs.put("hauteurVerriere", hauteurVerriere);
		myArgs.put("largeurVerriere", largeurVerriere);
		myArgs.put("nbPartitions", nbPartitions);
		myArgs.put("epaisseurVitrage", epaisseurVitrage);
		myArgs.put("natureVitrage", natureVitrage);
		myArgs.put("finition", finition);

		Renderer.render("Mecanica", Paths.get("").toAbsolutePath(), myArgs);
	}
}

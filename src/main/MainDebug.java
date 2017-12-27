package main;

import java.nio.file.Paths;
import java.util.HashMap;

import generateurCoteVerriere.Renderer;

public class MainDebug {
	public static void main(String[] args) {
		/*
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Entrez la hauteur de la verrière : ");
		double hauteurVerriere = sc.nextDouble();
		System.out.print("Entrez la largeur de la verrière : ");
		double largeurVerriere = sc.nextDouble();
		System.out.print("Entrez le nombre de partitions : ");
		int nbPartitions = sc.nextInt();
		
		sc.close();
		*/
		
		HashMap<String, Object> myArgs = new HashMap<String, Object>();
		myArgs.put("ARC", "190000310");
		myArgs.put("client", "M. Dupont");
		myArgs.put("reference", "61140089");
		myArgs.put("hauteurVerriere", 1150d);
		myArgs.put("largeurVerriere", 1550d);
		myArgs.put("nbPartitions", 4);
		
		Renderer.render("Premium", Paths.get("").toAbsolutePath(), myArgs);
	}
}

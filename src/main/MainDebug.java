package main;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

import generateurCoteVerriere.Renderer;

public class MainDebug {
	public static void main(String[] args) {
		double hauteurVerriere;
		double largeurVerriere;
		int nbPartitions;
		
		/*
		Scanner sc = new Scanner(System.in);
		System.out.print("Entrez la hauteur de la verrière : ");
		hauteurVerriere = sc.nextDouble();
		System.out.print("Entrez la largeur de la verrière : ");
		largeurVerriere = sc.nextDouble();
		System.out.print("Entrez le nombre de partitions : ");
		nbPartitions = sc.nextInt();
		sc.close();
		*/
		
		hauteurVerriere = 490d;
		largeurVerriere = 1150d;
		nbPartitions = 1;
		
		HashMap<String, Object> myArgs = new HashMap<String, Object>();
		myArgs.put("ARC", "190000310");
		myArgs.put("client", "M. Dupont");
		myArgs.put("reference", "61140089");
		myArgs.put("hauteurVerriere", hauteurVerriere);
		myArgs.put("largeurVerriere", largeurVerriere);
		myArgs.put("nbPartitions", nbPartitions);
		
		Renderer.render("Premium", Paths.get("").toAbsolutePath(), myArgs);
	}
}

package main;

import java.nio.file.Paths;

import generateurCoteVerriere.modeles.mecanique.Mecanique;

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
		
		double hauteurVerriere = 1100;
		double largeurVerriere = 1550;
		int nbPartitions = 4;
		
		// Conf conf = new Conf(Paths.get("").toAbsolutePath(), "190000310", "M. Dupont", "61140089", hauteurVerriere, largeurVerriere, nbPartitions);

		new Mecanique("190000310", "M. Dupont", "61140089", hauteurVerriere, largeurVerriere, nbPartitions).generate(Paths.get("").toAbsolutePath().resolve("test_output"));
	}
}

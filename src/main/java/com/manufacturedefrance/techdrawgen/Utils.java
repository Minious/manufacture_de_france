package com.manufacturedefrance.techdrawgen;

import com.manufacturedefrance.utils.MyVec2d;

public class Utils{

	private Utils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Retourne l'angle orienté entre le vecteur vertical orienté vers le bas et le vecteur (p2.x - p1.x, p2.y - p1.y) (compris entre -PI et PI)
	 * Exemple : 
	 * @param p1 MyVec2d 1
	 * @param p2 MyVec2d 2
	 * @return Double représentant l'angle orienté compris entre -PI et PI
	 */
	public static double getAngle(MyVec2d p1, MyVec2d p2){
		double angle = Math.asin((p1.x - p2.x) / MyVec2d.distance(p1, p2));
		if(p1.y < p2.y)
			angle = - angle;
		else{
			if(p1.x < p2.x)
				angle += Math.PI;
			else
				angle -= Math.PI;
		}
		return angle;
	}
	
	public static double positiveModulo(double value, double modulo) {
		return ((value % modulo) + modulo) % modulo;
	}
}

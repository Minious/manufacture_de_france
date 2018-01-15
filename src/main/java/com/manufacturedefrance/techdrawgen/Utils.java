package com.manufacturedefrance.techdrawgen;

import com.manufacturedefrance.utils.MyPoint2D;

public class Utils{

	private Utils() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Retourne l'angle orienté entre le vecteur vertical orienté vers le bas et le vecteur (p2.x - p1.x, p2.y - p1.y) (compris entre -PI et PI)
	 * Exemple : 
	 * @param p1 MyPoint2D 1
	 * @param p2 MyPoint2D 2
	 * @return Double représentant l'angle orienté compris entre -PI et PI
	 */
	public static double getAngle(MyPoint2D p1, MyPoint2D p2){
		double angle = Math.asin((p1.x - p2.x) / MyPoint2D.distance(p1, p2));
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

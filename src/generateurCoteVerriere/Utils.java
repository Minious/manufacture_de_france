package generateurCoteVerriere;

import myCustomSvgLibraryEnhanced.Point;

public class Utils{
	/**
	 * Retourne l'angle orienté entre le vecteur vertical orienté vers le bas et le vecteur (p2.x - p1.x, p2.y - p1.y) (compris entre -PI et PI)
	 * Exemple : 
	 * @param p1 Point 1
	 * @param p2 Point 2
	 * @return Double représentant l'angle orienté compris entre -PI et PI
	 */
	public static double getAngle(Point p1, Point p2){
		double angle = Math.asin((p1.x - p2.x) / Point.distance(p1, p2));
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
	
	public static int getNbTrousVerticaux(double hauteurVerriere){
		if(hauteurVerriere < 0)
			return -1;
		if(hauteurVerriere < 500)
			return 1;
		else
			return (int) Math.floor(hauteurVerriere / 500);
	}
}

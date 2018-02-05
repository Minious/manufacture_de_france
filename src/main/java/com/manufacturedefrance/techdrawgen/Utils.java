package com.manufacturedefrance.techdrawgen;

import com.manufacturedefrance.utils.MyPoint2D;

public class Utils{

	private Utils() {
		throw new IllegalStateException("Utility class");
	}

	public static double getAngle(MyPoint2D p1, MyPoint2D p2){
		MyPoint2D vect = new MyPoint2D(p2.x - p1.x, p2.y - p1.y);
		return Math.atan2(vect.y, vect.x);
	}
	
	public static double positiveModulo(double value, double modulo) {
		return ((value % modulo) + modulo) % modulo;
	}
}

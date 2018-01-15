package com.manufacturedefrance.utils;

import java.awt.geom.Point2D;

public class MyPoint2D extends Point2D.Double{

	public MyPoint2D(double x, double y){
		super(x, y);
	}

	public static double distance(MyPoint2D v1, MyPoint2D v2){
		return MyPoint2D.distance(v1.x, v1.y, v2.x, v2.y);
	}
}

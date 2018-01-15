package com.manufacturedefrance.utils;

import com.sun.javafx.geom.Vec2d;

public class MyVec2d extends Vec2d{

	public MyVec2d(double x, double y){
		super(x, y);
	}

	public static double distance(MyVec2d v1, MyVec2d v2){
		return MyVec2d.distance(v1.x, v1.y, v2.x, v2.y);
	}
}

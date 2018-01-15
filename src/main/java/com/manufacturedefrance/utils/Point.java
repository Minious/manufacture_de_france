package com.manufacturedefrance.utils;

public class Point{
	private double x;
	private double y;
	
	public Point(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Point(Point p){
		this.x = p.x;
		this.y = p.y;
	}
	
	public Point move(double x, double y){
		this.x += x;
		this.y += y;
		return this;
	}
	
	public static double distance(Point p1, Point p2){
		return Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
	}

	public boolean equals(Point p){
		return this.x == p.x && this.y == p.y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}
}

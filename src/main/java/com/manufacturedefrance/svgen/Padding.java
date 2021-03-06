package com.manufacturedefrance.svgen;

public class Padding {
	private double leftPadding;
	private double rightPadding;
	private double topPadding;
	private double bottomPadding;
	
	public Padding(double padding) {
		this(padding, padding);
	}
	
	public Padding(double horizontalPadding, double verticalPadding) {
		this(horizontalPadding, horizontalPadding, verticalPadding, verticalPadding);
	}

	public Padding(double leftPadding, double rightPadding, double topPadding, double bottomPadding) {
		this.leftPadding = leftPadding;
		this.rightPadding = rightPadding;
		this.topPadding = topPadding;
		this.bottomPadding = bottomPadding;
	}

	public Padding(Padding padding) {
		this(padding.leftPadding, padding.rightPadding, padding.topPadding, padding.bottomPadding);
	}
	
	public double getLeftPadding() {
		return this.leftPadding;
	}
	
	public double getRightPadding() {
		return this.rightPadding;
	}
	
	public double getTopPadding() {
		return this.topPadding;
	}
	
	public double getBottomPadding() {
		return this.bottomPadding;
	}
	
	public double getHorizontalPadding() {
		return this.leftPadding + this.rightPadding;
	}
	
	public double getVerticalPadding() {
		return this.topPadding + this.bottomPadding;
	}
}

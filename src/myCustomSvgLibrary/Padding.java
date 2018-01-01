package myCustomSvgLibrary;

public class Padding {
	private double leftPadding, rightPadding, topPadding, bottomPadding;
	
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
	
	public double getHorizontalPadding() {
		return this.leftPadding + this.rightPadding;
	}
	
	public double getVerticalPadding() {
		return this.topPadding + this.bottomPadding;
	}
}

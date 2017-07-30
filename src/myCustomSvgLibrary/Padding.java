package myCustomSvgLibrary;

public class Padding {
	private double leftPadding, rightPadding, topPadding, bottomPadding;
	
	public Padding(double padding) {
		this.leftPadding = padding;
		this.rightPadding = padding;
		this.topPadding = padding;
		this.bottomPadding = padding;
	}
	
	public Padding(double horizontalPadding, double verticalPadding) {
		this.leftPadding = horizontalPadding;
		this.rightPadding = horizontalPadding;
		this.topPadding = verticalPadding;
		this.bottomPadding = verticalPadding;
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

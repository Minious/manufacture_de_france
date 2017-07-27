package myCustomSvgLibrary;

public abstract class SvgComponent {
	protected StyleContext sc;
	
	protected SvgComponent(StyleContext sc) {
		this.sc = sc.clone();
	}
	
	public abstract String renderTag();
}

package myCustomSvgLibrary;

public abstract class SvgComponent {
	protected StyleContext sc;
	
	SvgComponent(StyleContext sc) {
		this.sc = sc.clone();
	}

	public abstract String renderTag();
	public abstract SvgComponent clone();
}

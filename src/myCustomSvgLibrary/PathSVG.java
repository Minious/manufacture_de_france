package myCustomSvgLibrary;

import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;

public class PathSVG extends SvgComponent {
	private Path2D path;
	
	public PathSVG(Path2D path, StyleContext sc) {
		super(sc);
		this.path = path;
	}
	
	public String renderTag() {
		PathIterator pathIterator = path.getPathIterator(null);
		double[] coords = new double[6];
		int state;
		
		String outputStr = "";
		outputStr += "<path ";
		outputStr += "d=\"";
		state = pathIterator.currentSegment(coords);
		outputStr += "M"+coords[0]+" "+coords[1]+" ";
		pathIterator.next();
		while(!pathIterator.isDone()) {
			state = pathIterator.currentSegment(coords);
			outputStr += "L"+coords[0]+" "+coords[1]+" ";
			pathIterator.next();
		}
		if(state == PathIterator.SEG_CLOSE)
			outputStr += "Z ";
		outputStr += "\" ";
		outputStr += "style=\"" + this.sc.getStrokeStyle() + "fill: none;\" ";
		outputStr += "transform=\"" + this.sc.getTransformMatrix() + "\" ";
		outputStr += "/>";
		
		return outputStr;
	}
}

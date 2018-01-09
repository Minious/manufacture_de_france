package myCustomSvgLibrary;

import java.awt.geom.PathIterator;

import utils.MyPath2D;

public class PathSVG extends SvgComponent {
	private MyPath2D path;
	
	PathSVG(MyPath2D path2, StyleContext sc) {
		super(sc);
		this.path = path2;
	}
	
	public String renderTag() {
		PathIterator pathIterator = path.getPathIterator();
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
		outputStr += "style=\"" + this.sc.getStrokeStyle() + " " + this.sc.getShapeStyle() + "\" ";
		if(!this.sc.isTranformIdentity())
			outputStr += "transform=\"" + this.sc.getTransformSvgNotation() + "\" ";
		outputStr += "/>";
		
		return outputStr;
	}

	@Override
	public SvgComponent clone() {
		return new PathSVG(this.path.clone(), sc);
	}
}

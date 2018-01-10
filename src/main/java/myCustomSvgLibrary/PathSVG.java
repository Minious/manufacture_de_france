package myCustomSvgLibrary;

import java.awt.geom.PathIterator;

import myCustomSvgLibrary.tags.PathTag;
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

		state = pathIterator.currentSegment(coords);
		String path = "M"+coords[0]+" "+coords[1]+" ";
		pathIterator.next();
		while(!pathIterator.isDone()) {
			state = pathIterator.currentSegment(coords);
			path += "L"+coords[0]+" "+coords[1]+" ";
			pathIterator.next();
		}
		if(state == PathIterator.SEG_CLOSE)
			path += "Z ";

		PathTag tag = new PathTag();
		tag.d(path);
		tag.style(this.sc.getStrokeStyle() + " " + this.sc.getShapeStyle());
		tag.translate(this.sc.getTranslateX(), this.sc.getTranslateY());
		tag.rotate(this.sc.getRotation());

		return tag.render();
	}

	@Override
	public SvgComponent clone() {
		return new PathSVG(this.path.clone(), sc);
	}
}

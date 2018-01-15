package com.manufacturedefrance.svgen;

import java.awt.geom.PathIterator;

import com.manufacturedefrance.svgen.tags.PathTag;
import com.manufacturedefrance.utils.MyPath2D;

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

		PathTag tag = new PathTag();
		state = pathIterator.currentSegment(coords);
		tag.moveto(coords[0], coords[1]);
		pathIterator.next();
		while(!pathIterator.isDone()) {
			state = pathIterator.currentSegment(coords);
			tag.lineto(coords[0], coords[1]);
			pathIterator.next();
		}
		if(state == PathIterator.SEG_CLOSE)
			tag.closePath();
		tag.style(this.sc.getStrokeStyle() + " " + this.sc.getShapeStyle());
		tag.translate(this.sc.getTranslateX(), this.sc.getTranslateY());
		tag.rotate(this.sc.getRotation());

		return tag.render();
	}

	@Override
	SvgComponent duplicate() {
		return new PathSVG(new MyPath2D(path), sc);
	}
}

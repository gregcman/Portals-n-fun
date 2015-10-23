package world.portal.data;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import math.curve.BezierCurve;

public class Portal_Bezier extends Portal {

	public Portal_Bezier(Point2D.Double[] m) {
		ArrayList<Point2D.Double> ps = new ArrayList<Point2D.Double>();
		for (Point2D.Double mpoint : m) {
			ps.add(mpoint);
		}
		curve = new BezierCurve(ps);
	}

	Portal_Bezier(BezierCurve b) {
		curve = b;
	}

	public void revaluate() {
		((BezierCurve)curve).generatePoints(10);
	}
}
package world.portal.data;


import java.util.ArrayList;

import math.Complex;
import math.curve.BezierCurve;

public class Portal_Bezier extends Portal {

	public Portal_Bezier(Complex[] m) {
		ArrayList<Complex> ps = new ArrayList<Complex>();
		for (Complex mpoint : m) {
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
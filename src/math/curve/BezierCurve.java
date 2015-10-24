package math.curve;


import java.util.ArrayList;

import math.Complex;

public class BezierCurve extends Curve {
	public ArrayList<Complex> controlPoints = new ArrayList<>(); // Control
	// points
	ArrayList<Complex> tangentPoints = new ArrayList<>(); // Derivative
																				// point;

	public BezierCurve() {
	}

	public BezierCurve(ArrayList<Complex> ps) {
		controlPoints = ps;
	}

	/** gives a t value for an arclength */
	@Override
	public double arcgive(double arclength) {
		if (arclength >= ARCLENGTH) {
			return (1);
		}
		if (arclength <= 0) {
			return (0);
		}
		int j = savedPoints.size();
		int repeat = j - 1;

		double vnext, vnow;
		vnext = savedPoints.get(0).getArcLength();

		for (int i = 0; i < repeat; i++) {
			vnow = vnext;
			vnext = savedPoints.get(i + 1).getArcLength();
			if (vnow <= arclength && vnext > arclength) {
				double ratio = (arclength - vnow) / (vnext - vnow);
				return (1 - ratio) * (savedPoints.get(i).getT()) + (ratio)
						* (savedPoints.get(i + 1).getT());
			}
		}
		return (-1);
	}

	/** returns the derivative which is the tangent */
	@Override
	public Complex derivative(double t) {
		gendpts();
		return getPoint(tangentPoints, t).get(0);
	}

	/** Adds the sets up the control points for the tangent points */
	public void gendpts() {
		tangentPoints.clear();
		Complex p0 = controlPoints.get(0);
		for (int i = 0; i < controlPoints.size() - 1; i++) {

			Complex p1 = controlPoints.get(i + 1);
			Complex p = new Complex((p1.x - p0.x)
					* (controlPoints.size() - 1), (p1.y - p0.y)
					* (controlPoints.size() - 1));
			tangentPoints.add(p);
			p0 = p1;
		}
	}


	public ArrayList<Complex> getControlPoints() {
		return controlPoints;
	}

	/*------------------------------------------------------------------------------------------------*/


	public ArrayList<Complex> getTangentPoints() {
		return tangentPoints;
	}

	public ArrayList<Complex> giveCoor() {
		return controlPoints;
	}

	@Override
	public Complex normal(double t) // returns the normal, rotates the
											// tangent 90 degrees
	{
		gendpts();
		Complex p = derivative(t);
		p.times_to(new Complex (Math.PI / 2, 1));
		return p;
	}

	/** returns the point on the curve for given t */
	@Override
	public Complex parametric(double t) {
		return getPoint(controlPoints, t).get(0);
	}

	public void setControlPoints(ArrayList<Complex> controlPoints) {
		this.controlPoints = controlPoints;
	}

	public void setTangentPoints(ArrayList<Complex> tangentPoints) {
		this.tangentPoints = tangentPoints;
	}

	/** Gives arclength for a t value */
	@Override
	public double tgive(double t) {
		if (t >= 1) {
			return (ARCLENGTH);
		}
		if (t <= 0) {
			return (0);
		}
		int j = savedPoints.size();
		int repeat = j - 1;

		double pnext, pnow;
		pnext = savedPoints.get(0).getT();

		for (int i = 0; i < repeat; i++) {
			pnow = pnext;
			pnext = savedPoints.get(i + 1).getT();
			if (pnow <= t && pnext > t) {
				double ratio = (t - pnow) / (pnext - pnow);
				return (1 - ratio) * (savedPoints.get(i).getArcLength())
						+ (ratio) * (savedPoints.get(i + 1).getArcLength());
			}
		}
		return (-1);
	}

	/*---------------------Black box stuff to work with beziert-------------------------*/
	/** Loops the list until value of the curve is found */
	private ArrayList<Complex> getPoint(ArrayList<Complex> plist,double t) {
		int size = plist.size() - 1;
		Complex oldpoint = new Complex(plist.get(0).x,
				plist.get(0).y);
		ArrayList<Complex> newpoints = new ArrayList<Complex>();
		for (int i = 0; i < size; i++) {
			Complex newpoint = plist.get(i + 1);
			newpoints.add(linearbez(t, oldpoint, newpoint));
			oldpoint = newpoint;
		}

		if (newpoints.size() > 1) {
			return getPoint(newpoints, t);
		}
		return newpoints;
	}

	/** Goes with getLines. Linearly interpolates the points together */
	private Complex linearbez(double t, Complex p0, Complex p1) {
		double help = 1 - t;
		return new Complex(p0.x * (help) + p1.x * t, p0.y * (help) + p1.y* t);
	}

	public void generatePoints(double resolution) {
		gendpts();
		savedPoints.clear();
		Complex current = new Complex(0, 0);
		ARCLENGTH = 0;
		for (int i = 0; i < resolution+1; i++) {
			
			Complex pt = parametric(i / resolution);

			ARCLENGTH += current.minus(pt).mod();
			
			savedPoints.add(new PointOnCurve(i/ resolution, ARCLENGTH,   pt));
			
			current.setNumber(pt);
		}

	}
}

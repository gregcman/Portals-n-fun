package math.curve;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import math.Calculation;

public class BezierCurve extends Curve {
	public ArrayList<Point2D.Double> controlPoints = new ArrayList<Point2D.Double>(); // Control
	// points
	ArrayList<Point2D.Double> tangentPoints = new ArrayList<Point2D.Double>(); // Derivative
																				// point;

	public BezierCurve() {
	}

	public BezierCurve(ArrayList<Point2D.Double> ps) {
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
	public Point2D.Double derivative(double t) {
		gendpts();
		return getPoint(tangentPoints, t).get(0);
	}

	/** Adds the sets up the control points for the tangent points */
	public void gendpts() {
		tangentPoints.clear();
		Point2D.Double p0 = controlPoints.get(0);
		for (int i = 0; i < controlPoints.size() - 1; i++) {

			Point2D.Double p1 = controlPoints.get(i + 1);
			Point2D.Double p = new Point2D.Double((p1.x - p0.x)
					* (controlPoints.size() - 1), (p1.y - p0.y)
					* (controlPoints.size() - 1));
			tangentPoints.add(p);
			p0 = p1;
		}
	}


	public ArrayList<Point2D.Double> getControlPoints() {
		return controlPoints;
	}

	/*------------------------------------------------------------------------------------------------*/


	public ArrayList<Point2D.Double> getTangentPoints() {
		return tangentPoints;
	}

	public ArrayList<Point2D.Double> giveCoor() {
		return controlPoints;
	}

	@Override
	public Point2D.Double normal(double t) // returns the normal, rotates the
											// tangent 90 degrees
	{
		gendpts();
		Point2D.Double p = derivative(t);
		Calculation.rotatePoint(p, (Double) Math.PI / 2);
		return p;
	}

	/** returns the point on the curve for given t */
	@Override
	public Point2D.Double parametric(double t) {
		return getPoint(controlPoints, t).get(0);
	}

	public void setControlPoints(ArrayList<Point2D.Double> controlPoints) {
		this.controlPoints = controlPoints;
	}

	public void setTangentPoints(ArrayList<Point2D.Double> tangentPoints) {
		this.tangentPoints = tangentPoints;
	}

	/** Gives arclength for a t value */
	@Override
	public double tgive(double t) {
		if (t >= 1) {
			return ((Double) ARCLENGTH);
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
	private ArrayList<Point2D.Double> getPoint(ArrayList<Point2D.Double> plist,double t) {
		int size = plist.size() - 1;
		Point2D.Double oldpoint = new Point2D.Double(plist.get(0).x,
				plist.get(0).y);
		ArrayList<Point2D.Double> newpoints = new ArrayList<Point2D.Double>();
		for (int i = 0; i < size; i++) {
			Point2D.Double newpoint = plist.get(i + 1);
			newpoints.add(linearbez(t, oldpoint, newpoint));
			oldpoint = newpoint;
		}

		if (newpoints.size() > 1) {
			return getPoint(newpoints, t);
		}
		return newpoints;
	}

	/** Goes with getLines. Linearly interpolates the points together */
	private Point2D.Double linearbez(double t, Point2D.Double p0, Point2D.Double p1) {
		double help = 1 - t;
		return new Point2D.Double(p0.x * (help) + p1.x * t, p0.y * (help) + p1.y* t);
	}

	public void generatePoints(double resolution) {
		gendpts();
		savedPoints.clear();
		Point2D.Double current = new Point2D.Double();
		ARCLENGTH = 0;
		for (int i = 0; i < resolution+1; i++) {
			
			Point2D.Double pt = parametric(i / resolution);

			ARCLENGTH += current.distance(pt);
			
			savedPoints.add(new PointOnCurve(i/ resolution, ARCLENGTH,   pt));
			
			current.setLocation(pt);
		}

	}
}

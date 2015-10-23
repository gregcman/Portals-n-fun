package math.curve;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public abstract class Curve {
	public class PointOnCurve {
		private Point2D.Double coords;
		private double t;
		private double arcLength;

		public PointOnCurve() {
		}

		public PointOnCurve(double tvalue, double arc, Point2D.Double point) {
			coords = point;
			t = tvalue;
			arcLength = arc;
		}

		public double getArcLength() {
			return arcLength;
		}

		public Point2D.Double getCoords() {
			return coords;
		}

		public double getT() {
			return t;
		}

		public void setArcLength(double arcLength) {
			this.arcLength = arcLength;
		}

		public void setCoords(Point2D.Double coords) {
			this.coords = coords;
		}

		public void setT(double t) {
			this.t = t;
		}
	}

	protected double ARCLENGTH; // Length of the arc
	protected ArrayList<PointOnCurve> savedPoints = new ArrayList<PointOnCurve>();

	public abstract double arcgive(double f);

	public abstract Point2D.Double derivative(double f); // outputs the normal

	public double getARCLENGTH() {
		return ARCLENGTH;
	}

	public ArrayList<PointOnCurve> getSavedPoints() {
		return savedPoints;
	}

	public abstract Point2D.Double normal(double f); // outputs the normal

	public abstract Point2D.Double parametric(double f); // outputs a point for a
														// given t value

	public abstract double tgive(double f);

}

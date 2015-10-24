package math.curve;

import math.Complex;
import java.util.ArrayList;

public abstract class Curve {
	public class PointOnCurve {
		private Complex coords;
		private double t;
		private double arcLength;

		public PointOnCurve() {
		}

		public PointOnCurve(double tvalue, double arc, Complex point) {
			coords = point;
			t = tvalue;
			arcLength = arc;
		}

		public double getArcLength() {
			return arcLength;
		}

		public Complex getCoords() {
			return coords;
		}

		public double getT() {
			return t;
		}

		public void setArcLength(double arcLength) {
			this.arcLength = arcLength;
		}

		public void setCoords(Complex coords) {
			this.coords = coords;
		}

		public void setT(double t) {
			this.t = t;
		}
	}

	protected double ARCLENGTH; // Length of the arc
	protected ArrayList<PointOnCurve> savedPoints = new ArrayList<PointOnCurve>();

	public abstract double arcgive(double f);

	public abstract Complex derivative(double f); // outputs the normal

	public double getARCLENGTH() {
		return ARCLENGTH;
	}

	public ArrayList<PointOnCurve> getSavedPoints() {
		return savedPoints;
	}

	public abstract Complex normal(double f); // outputs the normal

	public abstract Complex parametric(double f); // outputs a point for a
														// given t value

	public abstract double tgive(double f);

}

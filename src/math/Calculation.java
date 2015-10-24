package math;

import java.awt.geom.Point2D;

public class Calculation {

	public static Complex dilatePoint(Complex var, double damping) {
		return new Complex(var.x * damping, var.y * damping);
	}

	public static double getAngle(Complex p) {
		return (double) Math.atan2(p.y, p.x);
	}

	public static double getMagnitude(Complex p) {
		return (double) Math.sqrt(p.x * p.x + p.y * p.y);
	}

	public static double interpolate(double a, double b, double t) {
		return (1 - t) * a + t * b;
	}

	public static boolean isThereLinearIntersect(Complex p0,
			Complex p1, Complex p2, Complex p3) {
		double s1_x, s1_y, s2_x, s2_y;
		s1_x = p1.x - p0.x;
		s1_y = p1.y - p0.y;
		s2_x = p3.x - p2.x;
		s2_y = p3.y - p2.y;

		double s, t;
		s = (-s1_y * (p0.x - p2.x) + s1_x * (p0.y - p2.y))
				/ (-s2_x * s1_y + s1_x * s2_y);
		t = (s2_x * (p0.y - p2.y) - s2_y * (p0.x - p2.x))
				/ (-s2_x * s1_y + s1_x * s2_y);

		return (s >= 0 && s <= 1 && t >= 0 && t <= 1) ? true : false; // No
																		// collision
	}

	// code greg found on the internet
	public static Complex linearIntersect(Complex p0,
			Complex p1, Complex p2, Complex p3) {
		double s1_x, s1_y, s2_x, s2_y;
		s1_x = p1.x - p0.x;
		s1_y = p1.y - p0.y;
		s2_x = p3.x - p2.x;
		s2_y = p3.y - p2.y;

		double s, t;
		s = (-s1_y * (p0.x - p2.x) + s1_x * (p0.y - p2.y))
				/ (-s2_x * s1_y + s1_x * s2_y);
		t = (s2_x * (p0.y - p2.y) - s2_y * (p0.x - p2.x))
				/ (-s2_x * s1_y + s1_x * s2_y);

		if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
			// Collision detected
			return new Complex(p0.x + (t * s1_x), p0.y + (t * s1_y));
		}

		return null; // No collision
	}

	public static Complex rectToPolar(Complex p)
	{
		return new Complex(getAngle(p), getMagnitude(p));
	}
}

package math;

import java.awt.geom.Point2D;

public class Calculation {

	public static void dilate(Point2D.Double var, double damping) {
		var.x *= damping;
		var.y *= damping;
	}

	public static Point2D.Double dilatePoint(Point2D.Double var, double damping) {
		return new Point2D.Double(var.x * damping, var.y * damping);
	}

	public static double getAngle(Point2D.Double p) {
		return (double) Math.atan2(p.y, p.x);
	}

	public static double getMagnitude(Point2D.Double p) {
		return (double) Math.sqrt(p.x * p.x + p.y * p.y);
	}

	public static double interpolate(double a, double b, double t) {
		return (1 - t) * a + t * b;
	}

	public static boolean isThereLinearIntersect(Point2D.Double p0,
			Point2D.Double p1, Point2D.Double p2, Point2D.Double p3) {
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
	public static Point2D.Double linearIntersect(Point2D.Double p0,
			Point2D.Double p1, Point2D.Double p2, Point2D.Double p3) {
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
			return new Point2D.Double(p0.x + (t * s1_x), p0.y + (t * s1_y));
		}

		return null; // No collision
	}

	public static Point2D.Double rectToPolar(Point2D.Double p) // converts
																// rectangular
																// form to polar
																// form. the x
																// is the angle
																// and the y is
																// the magnitude
	{
		return new Point2D.Double(getAngle(p), getMagnitude(p));
	}

	public static void rotatePoint(Point2D.Double p, double rot) {
		double msin = Math.sin(rot);
		double mcos = Math.cos(rot);
		p.setLocation(p.x * mcos - p.y * msin, p.x * msin + p.y * mcos);
	}

	public static void translate(Point2D.Double var, double deltax, double deltay) {
		var.x += deltax;
		var.y += deltay;
	}

	public static void translate(Point2D.Double var, Point2D.Double delta) {
		var.x += delta.x;
		var.y += delta.y;
	}
}

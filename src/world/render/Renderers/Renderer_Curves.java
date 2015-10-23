package world.render.Renderers;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import math.Calculation;
import math.curve.Curve;

public class Renderer_Curves {
	/** Displays normals and other things of interest */

	public static void display(Curve curve) {
		drawBez(curve);
		dispModes(curve);
	}

	/** Draws tangent and normal */
	public static void drawCrossHatch(double t, Curve curve) {
		Point2D.Double Pt = curve.parametric(t);
		Point2D.Double Pte = curve.normal(t);
		double ang = Calculation.getAngle(Pte);
		Renderer.getFill().set(50, 40, 200, 100);
		Renderer_Shapes.drawCircle(Pt.x, Pt.y, 5, 10);
		Renderer.getStroke().set(200, 200, 100);
		double sinhelp = (double) (15 * Math.sin(ang));
		double coshelp = (double) (15 * Math.cos(ang));
		Renderer_Shapes.drawLine(Pt.x + sinhelp, Pt.y - coshelp,
				Pt.x - sinhelp, Pt.y + coshelp);
		Renderer.getStroke().set(50, 60, 70);
		Renderer_Shapes.drawLine(Pt.x, Pt.y, Pt.x + coshelp, Pt.y + sinhelp);
	}

	/** Displays various things on the curve */
	private static void dispModes(Curve curve) {
		int mark = 0;
		switch (mark) {
		case 0:
			double steps = 16;
			for (double i = 0; i <= steps; i++) {
				drawCrossHatch(i / steps, curve);
			}
			break;

		case 1:
			double k = curve.getARCLENGTH() / 40;
			for (int i = 0; i < 40; i++) {
				drawCrossHatch(curve.arcgive((double) (k * i)), curve);
			}
			break;
		}
	}

	/** Draws the curve from given points */
	private static void drawBez(Curve curve) {
		int i = 0;
		Renderer.getStroke().set(250, 200, 100);
		ArrayList<Curve.PointOnCurve> list = curve.getSavedPoints();
		while (i < curve.getSavedPoints().size()-1) {
			Renderer_Shapes.drawLine(list.get(i).getCoords(), list.get(i + 1).getCoords());
			i += 1;
		}
	}
}

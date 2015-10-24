package world.portal.link;

import java.util.ArrayList;

import math.Projectile;
import world.portal.data.Portal;
import math.Calculation;
import math.curve.BezierCurve;
import math.Complex;

public class PortalLinker_Bezier extends PortalLinker{
	public class Intersected {
		public Complex point;
		public boolean intersected = false;

		public Intersected() {
		}

		public Intersected(boolean b) {
			intersected = b;
		}
	}

	public class Intersection {
		public Complex closestPoint;
		public double smallDistance;
		public double tval;

		public Intersection() {
		}

	}

	private double arcRatio;

	private int chiral;

	public PortalLinker_Bezier(Portal a, Portal b, int c) {
		A = a;
		B = b;
		chiral = c;
		arcRatio = B.getCurve().getARCLENGTH() / A.getCurve().getARCLENGTH();
	}

	public double arcCorrespond(double t) {
		double shitdick = B.getCurve().arcgive((double) (A.getCurve().tgive(t) * arcRatio));
		return shitdick;
	}

	public Portal getA() {
		return A;
	}

	public double getArcRatio() {
		return arcRatio;
	}

	public Portal getB() {
		return B;
	}

	public int getChiral() {
		return chiral;
	}


	/**
	 * Function that finds where a projectile might hit the curve returns the
	 * closest point, the distance to the point, and the tvalue at that point
	 */
	public Intersection intersect(Projectile P) {
		ArrayList<math.curve.Curve.PointOnCurve> list = A.getCurve().getSavedPoints();

		int repeat = list.size();
		Intersection is = new Intersection();

		is.closestPoint = P.getIdealNextCoords();
		is.tval = -1;

		Complex pnow = new Complex(0, 0);
		Complex pnext = new Complex(0, 0);
		pnext.setNumber(list.get(0).getCoords());

		for (int i = 0; i < repeat-1; i++) {
			pnow.setNumber(pnext);
			pnext.setNumber(list.get(i + 1).getCoords());
			
			Complex phelp = P.getCoords().getPositionVector();

            Complex inters = Calculation.linearIntersect(pnow, pnext,P.getIdealNextCoords(), phelp);
			if (inters != null) {
				double olddist = phelp.minus(is.closestPoint).mod();
				double newdist = phelp.minus(inters).mod();
				if (newdist < olddist) {
					is.closestPoint = inters;
					is.smallDistance = newdist;
					double ratio = pnow.minus(inters).mod() / pnow.minus(pnext).mod();
					is.tval = (Calculation.interpolate(list.get(i).getT(), list.get(i + 1).getT(), ratio));
				}
			}
		}
		return is;
	}

	public boolean intersectCurve(BezierCurve beziercurve, Projectile p) {
		ArrayList<Complex> list = beziercurve.controlPoints;
		int repeat = list.size();
		for (int i = 0; i < repeat; i++) {
			if (Calculation.isThereLinearIntersect(list.get(i),
					list.get(i + 1), p.getIdealNextCoords(), p.getCoords().getPositionVector()) == true) {
				return true;
			}
		}
		return false;
	}

	public Intersected projectileWork(Projectile p) {

		Intersection cross = intersect(p);

		if (cross.tval != -1) {
			Complex newvel = new Complex(0, 0);
			newvel.setNumber(p.getVelocity());

			double portalANormal = Calculation.getAngle(A.getCurve().normal(cross.tval));

			Complex makeup = Calculation.rectToPolar(newvel);
			double distanceLeft = makeup.y - cross.smallDistance;

			// Makes the projectile teleport
			double tvalue = arcCorrespond(cross.tval);
			Complex newCoord = B.getCurve().parametric(tvalue);
			p.getCoords().getPositionVector().setNumber(newCoord);
            double portalBNormal = Calculation.getAngle(B.getCurve().normal(cross.tval));
			double angleChange = portalBNormal - portalANormal;
			arcRatio = B.getCurve().getARCLENGTH() / A.getCurve().getARCLENGTH();
			newvel.times_to(Complex.polar(angleChange, 1));
			newvel.scale(arcRatio);
			distanceLeft *= arcRatio;
			p.getCoords().setSize((double) (p.getCoords().getSize() * arcRatio));
			p.getCoords().setRotation(p.getCoords().getRotation() + angleChange);

			Complex shrink = new Complex(0, 0);
			shrink.setNumber(newvel);
			shrink.scale(distanceLeft / makeup.y);
			p.getVelocity().setNumber(shrink);

			//Intersected inpt = new Intersected();

			// if (0<iterations){inpt.intersected = projectileWork(p,
			// iterations-1).intersected;}
			// if (!inpt.intersected){
			p.getCoords().getPositionVector().plus_to(shrink);
			p.getVelocity().setNumber(newvel);
			// }

		
			return new Intersected(true);
		}
		return new Intersected();
	}

	public void setChiral(int chiral) {
		this.chiral = chiral;
	}

	public double tCorrespond(double t) {
		return t;
	}

	public void update() {
		arcRatio = B.getCurve().getARCLENGTH() / A.getCurve().getARCLENGTH();
	}
}

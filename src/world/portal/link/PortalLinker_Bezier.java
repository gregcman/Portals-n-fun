package world.portal.link;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.util.ArrayList;

import world.physic.Projectile;
import world.portal.data.Portal;
import math.Calculation;
import math.curve.BezierCurve;

public class PortalLinker_Bezier extends PortalLinker{
	public class Intersected {
		public Double point;
		public boolean intersected = false;

		public Intersected() {
		}

		public Intersected(boolean b) {
			intersected = b;
		}
	}

	public class Intersection {
		public Double closestPoint;
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

		Double pnow = new Double();
		Double pnext = new Double();
		pnext.setLocation(list.get(0).getCoords());

		for (int i = 0; i < repeat-1; i++) {
			pnow.setLocation(pnext);
			pnext.setLocation(list.get(i+1).getCoords());
			
			Double phelp = P.getCoords().getCoord().getPosition();

			Double inters = Calculation.linearIntersect(pnow, pnext,P.getIdealNextCoords(), phelp);
			if (inters != null) {
				double olddist = phelp.distance(is.closestPoint);
				double newdist = phelp.distance(inters);
				if (newdist < olddist) {
					is.closestPoint = inters;
					is.smallDistance = newdist;
					double ratio = pnow.distance(inters) / pnow.distance(pnext);
					is.tval = (Calculation.interpolate(list.get(i).getT(), list.get(i + 1).getT(), ratio));
				}
			}
		}
		return is;
	}

	public boolean intersectCurve(BezierCurve beziercurve, Projectile p) {
		ArrayList<Double> list = beziercurve.controlPoints;
		int repeat = list.size();
		for (int i = 0; i < repeat; i++) {
			if (Calculation.isThereLinearIntersect(list.get(i),
					list.get(i + 1), p.getIdealNextCoords(), p.getCoords().getCoord().getPosition()) == true) {
				return true;
			}
		}
		return false;
	}

	public Intersected projectileWork(Projectile p) {

		Intersection cross = intersect(p);

		if (cross.tval != -1) {
			Double newvel = new Double();
			newvel.setLocation(p.getVelocity());

			double portalANormal = Calculation.getAngle(A.getCurve().normal(cross.tval));

			Double makeup = Calculation.rectToPolar(newvel);
			double distanceLeft = makeup.y - cross.smallDistance;

			// Makes the projectile teleport
			double tvalue = arcCorrespond(cross.tval);
			Double newCoord = B.getCurve().parametric(tvalue);
			p.getCoords().getCoord().getPosition().setLocation(newCoord);
			double portalBNormal = Calculation.getAngle(B.getCurve().normal(cross.tval));
			double angleChange = portalBNormal - portalANormal;
			arcRatio = B.getCurve().getARCLENGTH() / A.getCurve().getARCLENGTH();
			Calculation.rotatePoint(newvel, angleChange);
			Calculation.dilate(newvel, (double) (arcRatio));
			distanceLeft *= arcRatio;
			p.getCoords().setSize((double) (p.getCoords().getSize() * arcRatio));
			p.getCoords().setRotation(p.getCoords().getRotation() + angleChange);

			Double shrink = new Double();
			shrink.setLocation(newvel);
			Calculation.dilate(shrink, distanceLeft / makeup.y);
			p.getVelocity().setLocation(shrink);

			//Intersected inpt = new Intersected();

			// if (0<iterations){inpt.intersected = projectileWork(p,
			// iterations-1).intersected;}
			// if (!inpt.intersected){
			Calculation.translate(p.getCoords().getCoord().getPosition(), shrink);
			p.getVelocity().setLocation(newvel);
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

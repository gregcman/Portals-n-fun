package world.things;

import java.awt.geom.Point2D;

import org.lwjgl.input.Mouse;

import controlCenter.Controller;

public class MovablePoint {

	public static Point2D.Double withMouse;

	public static boolean notVacant;

	/** Creates the movable point and moves it accordingly */
	public static void act(Point2D.Double me, double grabDistance) {
		if (Mouse.isButtonDown(0)
				&& inpoint(me, Controller.getWhereMouse(), grabDistance)
				&& !notVacant) {
			withMouse = me;
			notVacant = true;
		}
		if (withMouse == me) {
			me.setLocation(Controller.getWhereMouse());
			if (!Mouse.isButtonDown(0)) {
				withMouse = null;
				notVacant = false;
			}
		}

	}

	public static boolean inpoint(Point2D.Double me, Point2D.Double point,double grabDistance) {
		/** Tells you if a given point is in the movablePoint */
		return me.distance(point) < grabDistance ? true : false;
	}

	public static boolean isNotVacant() {
		return notVacant;
	}
}

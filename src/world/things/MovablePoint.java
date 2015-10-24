package world.things;

import controlCenter.UserInterface;
import org.lwjgl.input.Mouse;
import math.Complex;


public class MovablePoint {

	public static Complex withMouse;

	public static boolean notVacant;

	/** Creates the movable point and moves it accordingly */
	public static void act(Complex me, double grabDistance) {
		if (Mouse.isButtonDown(0)
				&& inpoint(me, UserInterface.MouseHandler.getWhereMouse(), grabDistance)
				&& !notVacant) {
			withMouse = me;
			notVacant = true;
		}
		if (withMouse == me) {
			me.setNumber(UserInterface.MouseHandler.getWhereMouse());
			if (!Mouse.isButtonDown(0)) {
				withMouse = null;
				notVacant = false;
			}
		}

	}

	public static boolean inpoint(Complex me, Complex point,double grabDistance) {
		/** Tells you if a given point is in the movablePoint */
		return me.minus(point).mod() < grabDistance ? true : false;
	}

	public static boolean isNotVacant() {
		return notVacant;
	}
}

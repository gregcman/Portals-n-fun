package world.portal.data;

import math.curve.Curve;

public abstract class Portal {
	protected Curve curve;

	public Curve getCurve() {
		return curve;
	}

	public void setCurve(Curve curve) {
		this.curve = curve;
	}

}

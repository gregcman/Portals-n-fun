package world.portal.link;

import world.portal.data.Portal;

public abstract class PortalLinker {
	protected Portal A;
	protected Portal B;
	public Portal getA() {
		return A;
	}
	public void setA(Portal a) {
		A = a;
	}
	public Portal getB() {
		return B;
	}
	public void setB(Portal b) {
		B = b;
	}
}

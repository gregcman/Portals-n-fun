package world.physic;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
public class Projectile {

	private Particle coord = new Particle();
	private Double velocity = new Double();

	public Projectile() {

	}

	public Projectile(Particle place) {
		coord = place;
	}

	public Projectile(Double position, Double c) {
		coord.getCoord().setPosition(position);
		velocity.setLocation(position.x+c.x, position.y+c.y);
	}

	public Double getVelocity() {
		return velocity;
	}
	public void setVelocity(Double p) {
		velocity = p;
	}

	public Particle getCoords() {
		return coord;
	}

	public void setCoords(Particle coords) {
		this.coord = coords;
	}
	
	public Double getIdealNextCoords(){
		return new Double(coord.getCoord().getPosition().x+velocity.x,coord.getCoord().getPosition().y+velocity.y);
	}
}

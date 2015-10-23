package world.things;

import java.awt.geom.Point2D;

import math.Calculation;
import world.physic.Particle;
import world.physic.Projectile;
import world.render.ImageLibrary;

public class Player {

	private Particle place = new Particle();
	private Projectile location = new Projectile(place);
	private Point2D.Double acceleration = new Point2D.Double(0f, 0f);
	private double friction = 0.95f;
	private Entity sprite;
	
	{place.setSize(25);}

	public Player(Point2D.Double C) {
		location.getCoords().getCoord().getPosition().setLocation(C);
		sprite = new Entity(ImageLibrary.get("smile"), C);
	}

	public void act() {
		sprite.getParticle().setSize(location.getCoords().getSize());
		move();
		sprite.getParticle().getCoord().getPosition().setLocation(location.getCoords().getCoord().getPosition());
	}

	public Point2D.Double getAcceleration() {
		return acceleration;
	}

	public double getFriction() {
		return friction;
	}

	public Projectile getLocation() {
		return location;
	}

	public Entity getSprite() {
		return sprite;
	}

	public void move() {
		Calculation.translate(location.getVelocity(), Calculation.dilatePoint(acceleration, sprite.getParticle().getSize() / 25));
		Calculation.translate(location.getCoords().getCoord().getPosition(), location.getVelocity());
		Calculation.dilate(location.getVelocity(), friction);
	}

	public void setAcceleration(Point2D.Double acceleration) {
		this.acceleration = acceleration;
	}

	public void setFriction(double friction) {
		this.friction = friction;
	}


	public void setSprite(Entity sprite) {
		this.sprite = sprite;
	}

	public Particle getPlace() {
		return place;
	}

	public void setPlace(Particle place) {
		this.place = place;
	}

}

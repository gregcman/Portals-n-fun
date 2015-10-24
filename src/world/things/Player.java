package world.things;


import math.Complex;
import math.Calculation;
import math.Projectile;
import world.render.ImageLibrary;

public class Player {

	private Particle place = new Particle();
	private Projectile location = new Projectile(place);
	private Complex acceleration = new Complex(0f, 0f);
	private double friction = 0.95f;
	private Entity sprite;
	
	{place.setSize(25);}

	public Player(Complex C) {
		location.getCoords().getPositionVector().setNumber(C);
		sprite = new Entity(ImageLibrary.get("smile"), C);
	}

	public void act() {
		sprite.getParticle().setSize(location.getCoords().getSize());
		move();
		sprite.getParticle().getPositionVector().setNumber(location.getCoords().getPositionVector());
	}

	public Complex getAcceleration() {
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
		location.getVelocity().plus_to(Calculation.dilatePoint(acceleration, sprite.getParticle().getSize() / 25));
		location.getCoords().getPositionVector().plus_to(location.getVelocity());
		location.getVelocity().scale(friction);
	}

	public void setAcceleration(Complex acceleration) {
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

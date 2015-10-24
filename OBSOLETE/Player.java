package controlCenter;


import math.Complex;
import math.Calculation;
import world.render.ImageLibrary;
import world.things.Entity;
import world.things.Particle;

public class Player {

	private double friction = 0.95f;
	private Entity sprite;
	
	{sprite.getParticle().setSize(25);}

	public Player(Complex C) {
        sprite.getParticle().getPositionVector().setNumber(C);
		sprite = new Entity(ImageLibrary.get("smile"), C);
	}

	public void act() {
		sprite.getParticle().setSize(sprite.getParticle().getSize());
		move();
		sprite.getParticle().getPositionVector().setNumber(sprite.getParticle().getPositionVector());
	}

	public Complex getAcceleration() {
		return sprite.getParticle().getAccelerationVector();
	}

	public double getFriction() {
		return friction;
	}

	public Entity getSprite() {
		return sprite;
	}

	public void move() {
        sprite.getParticle().getVelocityVector().plus_to(Calculation.dilatePoint(sprite.getParticle().getAccelerationVector(), sprite.getParticle().getSize() / 25));
        sprite.getParticle().getPositionVector().plus_to(sprite.getParticle().getVelocityVector());
        sprite.getParticle().getVelocityVector().scale_to(friction);
	}

    public void setAcceleration(Complex acceleration) {
		sprite.getParticle().setAccelerationVector(acceleration);
	}

	public void setFriction(double friction) {
		this.friction = friction;
	}


	public void setSprite(Entity sprite) {
		this.sprite = sprite;
	}

	public Particle getPlace() {
		return sprite.getParticle();
	}

	public void setPlace(Particle place) {
		sprite.setParticle(place);
	}

}

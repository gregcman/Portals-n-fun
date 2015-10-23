package world.things;

import java.awt.geom.Point2D;

import org.newdawn.slick.opengl.Texture;

import world.physic.Particle;

public class Entity {
	private Texture image;
	private Particle particle = new Particle();

	public Entity() {
	}

	public Entity(double x, double y, double siz, Texture t) {
		image = t;
		particle.setSize(siz);
		particle.getCoord().setPosition(new Point2D.Double(x, y));
	}

	public Entity(Texture t, double rot, double siz) {
		image = t;
		particle.setRotation(rot);
		particle.setSize(siz);
	}

	public Entity(Texture t, double rot, double siz, double x, double y) {
		image = t;
		particle.setRotation(rot);
		particle.setSize(siz);
		particle.getCoord().setPosition(new Point2D.Double(x, y));
	}

	public Entity(Texture t, double rot, double siz, Point2D.Double p) {
		image = t;
		particle.setRotation(rot);
		particle.setSize(siz);
		particle.getCoord().setPosition(new Point2D.Double(p.x, p.y));
	}

	public Entity(Texture t, Point2D.Double p) {
		image = t;
		particle.getCoord().setPosition(new Point2D.Double(p.x, p.y));
	}

	public Texture getImage() {
		return image;
	}

	public Particle getParticle() {
		return this.particle;
	}

	public void setImage(Texture image) {
		this.image = image;
	}

	public void setParticle(Particle p) {
		particle = p;
	}
}

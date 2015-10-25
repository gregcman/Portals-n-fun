package world.things;

import math.Complex;
import org.newdawn.slick.opengl.Texture;

public class Entity {
    private Texture image;
    private Particle particle = new Particle();

    public Entity() {
    }

    public Entity(double x, double y, double siz, Texture t) {
        image = t;
        particle.setSize(siz);
        particle.getPositionVector().setNumber(new Complex(x, y));
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
        particle.getPositionVector().setNumber(new Complex(x, y));
    }

    public Entity(Texture t, double rot, double siz, Complex p) {
        image = t;
        particle.setRotation(rot);
        particle.setSize(siz);
        particle.getPositionVector().setNumber(new Complex(p.x, p.y));
    }

    public Entity(Texture t, Complex p) {
        image = t;
        particle.getPositionVector().setNumber(new Complex(p.x, p.y));
    }

    public Texture getImage() {
        return image;
    }

    public void setImage(Texture image) {
        this.image = image;
    }

    public Particle getParticle() {
        return this.particle;
    }

    public void setParticle(Particle p) {
        particle = p;
    }
}

package math;

import world.things.Particle;

public class Projectile {

    private Particle coord = new Particle();
    private Complex velocity = new Complex(0, 0);

    public Projectile() {

    }

    public Projectile(Particle place) {
        coord = place;
    }

    public Projectile(Complex position, Complex c) {
        coord.getPositionVector().setNumber(c);
        velocity = new Complex(position.x + c.x, position.y + c.y);
    }

    public Complex getVelocity() {
        return velocity;
    }

    public void setVelocity(Complex p) {
        velocity = p;
    }

    public Particle getCoords() {
        return coord;
    }

    public void setCoords(Particle coords) {
        this.coord = coords;
    }

    public Complex getIdealNextCoords() {
        return new Complex(coord.getPositionVector().x + velocity.x, coord.getPositionVector().y + velocity.y);
    }
}

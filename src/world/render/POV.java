package world.render;

import math.Complex;

import org.lwjgl.opengl.Display;
import world.things.Particle;

public class POV {
    Particle particle = new Particle();
	private double zoom = 1;

	public void followParticle(Particle p){
        particle.getPositionVector().setNumber(p.getPositionVector());
        particle.setSize(32f / p.size);
        particle.setRotation(-(p.angular_position));
    }

	public double correctx(double x) {
		return (x - particle.getPositionVector().x) * particle.getSize() * zoom+ Display.getWidth() / 2;
	}

	public double correctx(double x, double depth) {
		return (x -particle.getPositionVector().x / depth) * particle.getSize()* zoom + Display.getWidth() / 2;
	}

	public double correcty(double y) {
		return (y - particle.getPositionVector().y) * particle.getSize() * zoom+ Display.getHeight() / 2;
	}

	public double correcty(double y, double depth) {
		return (y - particle.getPositionVector().y / depth) * particle.getSize()* zoom + Display.getHeight() / 2;
	}
	
	public Complex getCoordinateIn(double x, double y) {
        Complex undo = new Complex(x, y);
		undo.plus_to(new Complex(-Display.getWidth() / 2, -Display.getHeight() / 2));
		undo.times_to(Complex.polar(-particle.getRotation(), 1));
		undo.plus_to(new Complex(Display.getWidth() / 2, Display.getHeight() / 2));
		Complex newPos = new Complex(0, 0);
		newPos.x = (undo.x - (Display.getWidth() >> 1))/ (particle.getSize() * zoom) + particle.getPositionVector().x;
		newPos.y = (undo.y - (Display.getHeight() >> 1))/ (particle.getSize() * zoom) + particle.getPositionVector().y;
		return newPos;
	}
		
		

		public Particle getParticle() {
			return particle;
		}

		public double getZoom() {
			return zoom;
		}

		public void setParticle(Particle particle) {
			this.particle = particle;
		}

		public void setZoom(double zoom) {
			this.zoom = zoom;
		}
}

package world.render;

import java.awt.geom.Point2D;

import org.lwjgl.opengl.Display;
import world.physic.Particle;

public class POV {
	Particle particle = new Particle();
	private double zoom = 1;
	
	public double correctx(double x) {
		return (x - particle.getCoord().getPosition().x) * particle.getSize() * zoom+ Display.getWidth() / 2;
	}

	public double correctx(double x, double depth) {
		return (x -particle.getCoord().getPosition().x / depth) * particle.getSize()* zoom + Display.getWidth() / 2;
	}

	public double correcty(double y) {
		return (y - particle.getCoord().getPosition().y) * particle.getSize() * zoom+ Display.getHeight() / 2;
	}

	public double correcty(double y, double depth) {
		return (y - particle.getCoord().getPosition().y / depth) * particle.getSize()* zoom + Display.getHeight() / 2;
	}
	
	public Point2D.Double getCoordinateIn(double x, double y) {
		Point2D.Double undo = new Point2D.Double(x, y);
		math.Calculation.translate(undo, new Point2D.Double(-Display.getWidth() / 2, -Display.getHeight() / 2));
		math.Calculation.rotatePoint(undo, (double) (-particle.getRotation()* Math.PI / 180));
		math.Calculation.translate(undo, new Point2D.Double(Display.getWidth() / 2, Display.getHeight() / 2));
		Point2D.Double newPos = new Point2D.Double();
		newPos.x = (undo.x - (Display.getWidth() >> 1))/ (particle.getSize() * zoom) + particle.getCoord().getPosition().x;
		newPos.y = (undo.y - (Display.getHeight() >> 1))/ (particle.getSize() * zoom) + particle.getCoord().getPosition().y;
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

package world.physic;

import java.awt.geom.Point2D;

public class Coordinate {
	private World world;
	private Point2D.Double position = new Point2D.Double();
	
	public Coordinate(){}

	public Point2D.Double getPosition() {
		return position;
	}

	public World getWorld() {
		return world;
	}

	public void setPosition(Point2D.Double p) {
		position = p;
	}

	public void setWorld(World w) {
		world = w;
	}
}

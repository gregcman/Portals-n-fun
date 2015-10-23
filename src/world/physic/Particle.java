package world.physic;

public class Particle {
	private double rotation;
	private double size;
	private Coordinate coord = new Coordinate();
	
	public Particle(){
		
	}

	public Coordinate getCoord() {
		return coord;
	}

	public double getRotation() {
		return rotation;
	}

	public double getSize() {
		return size;
	}

	public void setCoordinate(Coordinate newCoord) {
		coord = newCoord;
	}

	public void setRotation(double newRot) {
		rotation = newRot;
	}

	public void setSize(double newSize) {
		size = newSize;
	}
}

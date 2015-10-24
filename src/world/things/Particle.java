package world.things;

import math.Complex;

public class Particle {
	public double rotation;
	public double size;
	public Complex position_vector = new Complex(0, 0);
	public Complex velocity_vector = new Complex(0, 0);
	public Complex acceleration_vector = new Complex(0, 0);
	
	public Particle(){
		
	}



	public void setPositionVector(Complex newPositionVector) {
		position_vector = newPositionVector;
	}

	public void setVelocityVector(Complex newVelocityVector) {
		velocity_vector = newVelocityVector;
	}

	public void setAccelerationVector(Complex newAccelerationVector) {
		acceleration_vector = newAccelerationVector;
	}

	public void setRotation(double newRot) {
		rotation = newRot;
	}

	public void setSize(double newSize) {
		size = newSize;
	}

	public Complex getPositionVector(){
		return position_vector;
	}

	public Complex getVelocityVector(){
		return velocity_vector;
	}

	public Complex getAccelerationVector(){
		return acceleration_vector;
	}

	public double getRotation() {
		return rotation;
	}

	public double getSize() {
		return size;
	}
}

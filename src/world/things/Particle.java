package world.things;

import math.Complex;

public class Particle {
    public double angular_position;
    public double angular_velocity;
    public double size;
    public Complex position_vector = new Complex(0, 0);
    public Complex velocity_vector = new Complex(0, 0);
    public Complex acceleration_vector = new Complex(0, 0);

    public Particle() {

    }

    public void update() {
        velocity_vector.plus_to(acceleration_vector);
        position_vector.plus_to(velocity_vector);
        angular_position += angular_velocity;
    }

    public void setAngularVelocity(double newAngRot) {
        angular_velocity = newAngRot;
    }

    public Complex getPositionVector() {
        return position_vector;
    }

    public void setPositionVector(Complex newPositionVector) {
        position_vector = newPositionVector;
    }

    public Complex getVelocityVector() {
        return velocity_vector;
    }

    public void setVelocityVector(Complex newVelocityVector) {
        velocity_vector = newVelocityVector;
    }

    public Complex getAccelerationVector() {
        return acceleration_vector;
    }

    public void setAccelerationVector(Complex newAccelerationVector) {
        acceleration_vector = newAccelerationVector;
    }

    public double getRotation() {
        return angular_position;
    }

    public void setRotation(double newRot) {
        angular_position = newRot;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double newSize) {
        size = newSize;
    }
}

package world.render;

import math.Complex;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.ReadableColor;
import world.World;
import world.things.Entity;
import world.things.MovablePoint;
import world.things.Particle;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class POV {

    Complex position_Vector;
    public double orientation;
    public int width;
    public int height;
    public World world;
    private double zoom;

    public POV(Complex pos, int w, int h, double rotation, double z, World worldy){
        position_Vector = pos;
        orientation = rotation;
        width = w;
        height = h;
        zoom = z;
        world = worldy;
    }

    public void followParticle(Particle p) {
        position_Vector.setNumber(p.getPositionVector());
        orientation = -p.angular_position;
    }

    public static Complex middle =  new Complex(Display.getWidth() / 2, Display.getHeight() / 2);

    public Complex getCoordinateIn(double x, double y) {
        Complex undo = new Complex(x, y);
        undo.minus_to(middle);
        undo.times_to(Complex.polar(-orientation, 1));
        undo.scale_to(1f/zoom);
        undo.plus_to(position_Vector);
        return undo;
    }

    public double getZoom() {
        return zoom;
    }

    public void setZoom(double zoom) {
        this.zoom = zoom;
    }

    public void setDimensions(int w, int h){
        width = w;
        height = h;
    }

    public void render() {
        Render.getFill().setColor(ReadableColor.WHITE);
        glClear(GL_COLOR_BUFFER_BIT);
        for (Entity x : world.Entity_HashMap.values()) {
            WorldShapes.draw(x);
        }

    }

    public void entityDrag(){
        for (Entity x : world.Entity_HashMap.values()) {
            MovablePoint.act(x.getParticle().getPositionVector(), x.getParticle().size,this);
        }
    }

    public Complex whereMouse;

    public Complex getWhereMouse() {
        return whereMouse;
    }

    public void iterate() {
        whereMouse = WorldShapes.getCamera().getCoordinateIn(Mouse.getX(), Mouse.getY());
    }


}

package world.render;

import math.Complex;
import org.newdawn.slick.opengl.Texture;
import world.things.Entity;
import world.things.Particle;

import static org.lwjgl.opengl.GL11.*;

//This class handles all the rendering done. 
public class WorldShapes extends Render {

    protected static POV camera = new POV();

    public static POV getCamera() {
        return camera;
    }

    public static void draw(Entity sprite) {
        Particle p = sprite.getParticle();
        drawQuad(p.getPositionVector().x, p.getPositionVector().y, p.getSize(), p.getSize(), p.getRotation(), sprite.getImage());
    }


    private static void camMatrix() {
        glTranslated(texture_dimensions_width >> 1, texture_dimensions_height >> 1, 0);
        glRotated(camera.getParticle().getRotation() * 180 / Math.PI, 0, 0, 1);
        glTranslated(-texture_dimensions_width >> 1, -texture_dimensions_height >> 1, 0);
    }

    public static void drawCircle(double cx, double cy, double r, int num_segments) {
        glDisable(GL_TEXTURE_2D);
        double theta = 2 * 3.1415926 / num_segments;
        double c = Math.cos(theta);// precalculate the sine and cosine
        double s = Math.sin(theta);
        double t;
        double x = r;// we start at angle = 0
        double y = 0;
        glPushMatrix();
        camMatrix();

        glBegin(GL_POLYGON);
        for (int ii = 0; ii < num_segments; ii++) {
            glVertex2d(camera.correctx(x + cx), camera.correcty(y + cy));// output vertex

            // apply the rotation matrix
            t = x;
            x = c * x - s * y;
            y = s * t + c * y;
        }
        glEnd();
        glPopMatrix();
        glEnable(GL_TEXTURE_2D);
    }

    public static void drawLine(double x1, double y1, double x2, double y2) {
        glDisable(GL_TEXTURE_2D);

        glPushMatrix();
        camMatrix();

        glBegin(GL_LINE_STRIP);
        glVertex2d(camera.correctx(x1), camera.correcty(y1));
        glVertex2d(camera.correctx(x2), camera.correcty(y2));
        glEnd();
        glPopMatrix();

        glEnable(GL_TEXTURE_2D);
    }

    public static void drawLine(Complex p1, Complex p2) {
        drawLine(p1.x, p1.y, p2.x, p2.y);
    }


    //This guy draws quads with textures on them

    public static void drawQuad(double x, double y, double l1, double l2, double rot, Texture t) {
        t.bind();

        // draw quad
        glPushMatrix();
        x *= camera.getParticle().getSize() * camera.getZoom();
        y *= camera.getParticle().getSize() * camera.getZoom();

        camMatrix();

        glTranslated(x, y, 0);

        double sinHelp = Math.sin(rot + Math.PI / 4);
        double cosHelp = Math.cos(rot + Math.PI / 4);

        glBegin(GL_QUADS);
        glTexCoord2d(0, 1);
        glVertex2d(camera.correctx(-l1 * cosHelp), camera.correcty(-l2 * sinHelp));

        glTexCoord2d(0, 0);
        glVertex2d(camera.correctx(-l1 * sinHelp), camera.correcty(+l2 * cosHelp));

        glTexCoord2d(1, 0);
        glVertex2d(camera.correctx(+l1 * cosHelp), camera.correcty(+l2 * sinHelp));

        glTexCoord2d(1, 1);
        glVertex2d(camera.correctx(+l1 * sinHelp), camera.correcty(-l2 * cosHelp));
        glEnd();

        glPopMatrix();
    }


    public static float max(float a, float b) {
        if (a < b) {
            return b;
        } else return a;
    }

    public static void setRenderingDimensions(int x, int y) {
        texture_dimensions_width = x;
        texture_dimensions_height = y;
    }
}
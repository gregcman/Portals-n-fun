package world.render;

import math.Complex;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;
import world.things.Entity;
import world.things.Particle;

import static org.lwjgl.opengl.GL11.*;

//This class handles all the rendering done. 
public class WorldShapes extends Render {

    public static POV camera;

    public static POV getCamera() {
        return camera;
    }

    public static void setCamera(POV cam){camera = cam;}

    public static void draw(Entity sprite) {
        Particle p = sprite.getParticle();
        drawQuad(p.getPositionVector().x, p.getPositionVector().y, p.getSize(), p.getSize(), p.getRotation(), sprite.getImage());
    }


    private static void camMatrix() {
        glTranslated(camera.width >> 1, camera.height >> 1, 0);
        glRotated(camera.orientation * 180 / Math.PI, 0, 0, 1);
        glTranslated(-camera.width >> 1, -camera.height >> 1, 0);
        glTranslated(Display.getWidth() / 2, Display.getHeight() / 2,0);
        glScaled(camera.getZoom(), camera.getZoom(), 0);
        glTranslated(- camera.position_Vector.x, -camera.position_Vector.y,0);
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
            glVertex2d(x, y);// output vertex

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
        glVertex2d(x1, y1);
        glVertex2d(x2, y2);
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
        camMatrix();

        glTranslated(x, y, 0);
        glRotated(rot * 180/Math.PI, 0, 0, 69);

        glBegin(GL_QUADS);
        glTexCoord2d(0, 1);
        glVertex2d(-l1, -l2);

        glTexCoord2d(0, 0);
        glVertex2d(-l1, +l2);

        glTexCoord2d(1, 0);
        glVertex2d(+l1, +l2);

        glTexCoord2d(1, 1);
        glVertex2d(+l1, -l2);
        glEnd();

        glPopMatrix();
    }
}
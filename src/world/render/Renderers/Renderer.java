package world.render.Renderers;

import math.Complex;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.Color;

import org.lwjgl.util.ReadableColor;
import org.newdawn.slick.opengl.Texture;
import world.render.POV;
import world.render.OpenglInitializer;
import world.things.Entity;
import world.things.Particle;

import static org.lwjgl.opengl.GL11.*;

//This class handles all the rendering done. 
public class Renderer {

	protected static POV camera = new POV();

	public static Color stroke = new Color();

	public static Color fill = new Color();

	public static Color getFill() {
		return fill;
	}

	public static Color getStroke() {
		return stroke;
	}

    public static int texture_dimensions_width;
    public static int texture_dimensions_height;

    public static void setFill(Color fill) {
		Renderer.fill = fill;
	}

	public static void setStroke(Color stroke) {
		Renderer.stroke = stroke;
	}


	public static void init() {
		// OpenGL
		OpenglInitializer.initGL();
		OpenglInitializer.resizeGL();
	}
	
	public static void testResize() {
		if (Display.wasResized()) {
			OpenglInitializer.resizeGL();
		}
	}

	public static POV getCamera() {
		return camera;
	}

	public static class Renderer_Entity extends Renderer {
        public static void draw(Entity sprite) {
            Particle p = sprite.getParticle();
            Renderer_TexturedShapes.drawQuad(p.getPositionVector().x, p.getPositionVector().y,p.getSize(), p.getSize(), p.getRotation(), sprite.getImage());
        }
    }

    public static void graphicsLibraryColor(){
        glColor4f(fill.getRed() >> 8, fill.getGreen() >> 8, fill.getBlue() >> 8, fill.getAlpha() >> 8);
    }

	public static class Renderer_Shapes extends Renderer {
        public static void drawCircle(double cx, double cy, double r, int num_segments) {
            graphicsLibraryColor();
            glDisable(GL_TEXTURE_2D);
            double theta = 2 * 3.1415926 / num_segments;
            double c =  Math.cos(theta);// precalculate the sine and cosine
            double s = Math.sin(theta);
            double t;
            double x = r;// we start at angle = 0
            double y = 0;
            glPushMatrix();
            glTranslated(texture_dimensions_width >> 1, texture_dimensions_height >> 1, 0);
            glRotated(camera.getParticle().getRotation() * 180/Math.PI, 0, 0, 7);
            glTranslated(-texture_dimensions_width >> 1, -texture_dimensions_height >> 1, 0);

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

        public static void drawRect(double x1, double y1, double x2, double y2){
            graphicsLibraryColor();
            glDisable(GL_TEXTURE_2D);

            glBegin(GL_QUADS);
            glVertex2d(x1, y1);
            glVertex2d(x1, y2);
            glVertex2d(x2, y2);
            glVertex2d(x2, y1);
            glEnd();
            glEnable(GL_TEXTURE_2D);
        }

        public static void drawLine(double x1, double y1, double x2, double y2) {
            glDisable(GL_TEXTURE_2D);

            graphicsLibraryColor();
            glPushMatrix();
            glTranslated(texture_dimensions_width / 2, texture_dimensions_height / 2, 0);
            glRotated(camera.getParticle().getRotation() * 180/Math.PI, 0, 0, 1);
            glTranslated(-texture_dimensions_width / 2, -texture_dimensions_height / 2, 0);

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
    }

    //This guy draws quads with textures on them
	public static class Renderer_TexturedShapes extends Renderer{
        public static void drawQuad(double x, double y, double l1, double l2,double rot, Texture t) {
            t.bind();
            // set the color of the quad (R,G,B,A)
            glColor4d(fill.getRed() / 255f, fill.getGreen() / 255f,fill.getBlue() / 255f, fill.getAlpha() / 255f);

            // draw quad
            glPushMatrix();
            x *= camera.getParticle().getSize() * camera.getZoom();
            y *= camera.getParticle().getSize() * camera.getZoom();

            glTranslated(texture_dimensions_width / 2, texture_dimensions_height / 2, 0);
            glRotated(camera.getParticle().getRotation() * 180/ Math.PI, 0, 0, 1);
            glTranslated(-texture_dimensions_width / 2, -texture_dimensions_height / 2, 0);

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
    }

    public static float max(float a, float b){
        if (a < b){
            return b;
        }
        else return a;
    }

    public static class Renderer_FrameBuffer extends Renderer{
        public static void drawFrameBuffer(Texture texture) {
            texture.bind();

            glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
            glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);

            float texture_width = texture.getTextureWidth();
            float texture_height = texture.getTextureHeight();

            Renderer.getFill().setColor(Color.GREEN);
            Renderer_Shapes.drawRect(0,0,texture_dimensions_width,texture_dimensions_height);

            float x1 = 0;
            float y1 = 0;
            float x2 = texture_dimensions_width/texture_width;
            float y2 = texture_dimensions_height/texture_height;

            glColor4d(1, 1, 1, 1f);
            glPushMatrix();
            glBegin(GL_QUADS);
            glTexCoord2d(x1, y1);
            glVertex2d(0f, 0f);
            glTexCoord2d(x1, y2);
            glVertex2d(0f, texture_dimensions_height);
            glTexCoord2d(x2, y2);
            glVertex2d(texture_dimensions_width, texture_dimensions_height);
            glTexCoord2d(x2, y1);
            glVertex2d(texture_dimensions_width, 0f);
            glEnd();
            glPopMatrix();


        }
    }

    public static void setRenderingDimensions(int x, int y){
        texture_dimensions_width = x;
        texture_dimensions_height = y;
    }
}
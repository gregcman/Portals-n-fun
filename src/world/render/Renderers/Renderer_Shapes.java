package world.render.Renderers;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex2d;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import org.lwjgl.opengl.Display;

public class Renderer_Shapes extends Renderer {
	public static void drawCircle(double cx, double cy, double r, int num_segments) {
		glColor4f(fill.getRed() >> 8, fill.getGreen() >> 8,
				fill.getBlue() >> 8, fill.getAlpha() >> 8);
		glDisable(GL_TEXTURE_2D);
		double theta = (double) (2 * 3.1415926 / (num_segments));
		double c = (double) Math.cos(theta);// precalculate the sine and cosine
		double s = (double) Math.sin(theta);
		double t;
		double x = r;// we start at angle = 0
		double y = 0;
		glPushMatrix();
		glTranslated(Display.getWidth() >> 1, Display.getHeight() >> 1, 0);
		glRotated(camera.getParticle().getRotation(), 0, 0, 7);
		glTranslated(-Display.getWidth() >> 1, -Display.getHeight() >> 1, 0);

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

		glColor4f(stroke.getRed() / 255f, stroke.getGreen() / 255f,
				stroke.getBlue() / 255f, stroke.getAlpha() / 255f);
		glPushMatrix();
		glTranslated(Display.getWidth() / 2, Display.getHeight() / 2, 0);
		glRotated(camera.getParticle().getRotation(), 0, 0, 1);
		glTranslated(-Display.getWidth() / 2, -Display.getHeight() / 2, 0);

		glBegin(GL_LINE_STRIP);
		glVertex2d(camera.correctx(x1), camera.correcty(y1));
		glVertex2d(camera.correctx(x2), camera.correcty(y2));
		glEnd();
		glPopMatrix();

		glEnable(GL_TEXTURE_2D);
	}

	public static void drawLine(Line2D line) {
		drawLine(line.getP1(), line.getP2());
	}

	public static void drawLine(Point2D p1, Point2D p2) {
		drawLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}
}

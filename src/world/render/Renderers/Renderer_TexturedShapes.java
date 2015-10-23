package world.render.Renderers;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4d;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex2d;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

public class Renderer_TexturedShapes extends Renderer{
	public static void drawQuad(double x, double y, double l1, double l2,double rot, Texture t) {
		t.bind();
		// set the color of the quad (R,G,B,A)
		glColor4d(fill.getRed() / 255f, fill.getGreen() / 255f,fill.getBlue() / 255f, fill.getAlpha() / 255f);

		// draw quad
		glPushMatrix();
		x *= camera.getParticle().getSize() * camera.getZoom();
		y *= camera.getParticle().getSize() * camera.getZoom();

		glTranslated(Display.getWidth() / 2, Display.getHeight() / 2, 0);
		glRotated(camera.getParticle().getRotation(), 0, 0, 1);
		glTranslated(-Display.getWidth() / 2, -Display.getHeight() / 2, 0);

		glTranslated(x, y, 0);
		rot *= Math.PI / 180;

		double sinHelp = (double) Math.sin(rot + Math.PI / 4);
		double cosHelp = (double) Math.cos(rot + Math.PI / 4);

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

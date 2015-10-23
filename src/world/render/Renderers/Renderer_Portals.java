package world.render.Renderers;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_QUAD_STRIP;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex2d;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;

import java.awt.geom.Point2D;

import math.Calculation;
import math.curve.Curve;

import org.lwjgl.opengl.Display;

import world.portal.link.PortalLinker;
import world.render.FrameBufferHandler;
import world.portal.link.*;

public class Renderer_Portals extends Renderer {
	
	public static int resx = 4; //

	public static int resy = 4; // resolution of the trace function
	
	public static void draw(PortalLinker[] plinks) {

		glColor4f(1, 1, 1, 1f);
		FrameBufferHandler.getFrame().bind();

		glPushMatrix();
		glTranslated(Display.getWidth() >> 1, Display.getHeight() >> 1, 0);
		glRotated(camera.getParticle().getRotation(), 0, 0, 1);
		glTranslated(-Display.getWidth() >> 1, -Display.getHeight() >> 1, 0);

		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

		for (PortalLinker p :plinks){
		drawPortal(p);
		}
	
		glPopMatrix();
	}

	public static void drawPortal(PortalLinker plink) {
		glBegin(GL_QUAD_STRIP);
		Curve bez = plink.getA().getCurve();
		Curve bez2 = plink.getB().getCurve();
		double res = bez.getARCLENGTH();
		for (double i = 0; i < res; i++) {
			double foo = bez.arcgive((bez.getARCLENGTH() * bez2.tgive(i/ res)));
			Point2D.Double point = bez.parametric(foo);

			Calculation.translate(point, -camera.getParticle().getCoord().getPosition().x,-camera.getParticle().getCoord().getPosition().y);
			Calculation.rotatePoint(point, (camera.getParticle().getRotation()* Math.PI / 180));
			Calculation.translate(point, camera.getParticle().getCoord().getPosition().x,camera.getParticle().getCoord().getPosition().y);

			Point2D.Double point2 = bez2.parametric(foo);

			glTexCoord2d(camera.correctx(point.x) / Display.getWidth(),camera.correcty(point.y) / Display.getHeight());
			glVertex2d(camera.correctx(point2.x), camera.correcty(point2.y));

			double foo2 = 1 - bez
					.arcgive((double) (bez2.getARCLENGTH() * (i / res)));
			Point2D.Double point3 = bez.parametric(foo2);
			Point2D.Double delt = bez.normal(foo2);

			double angle = (double) ((Math.atan2(point2.y - camera.getParticle().getCoord().getPosition().y,point2.x - camera.getParticle().getCoord().getPosition().x)) - Calculation.getAngle(delt));

			Calculation.rotatePoint(delt, angle);

			double arcRatio = (double) (bez.getARCLENGTH() / bez2.getARCLENGTH());

			Calculation.dilate(delt,(arcRatio) * (Calculation.getMagnitude(delt)));
			Calculation.translate(point3, delt);

			Calculation.translate(point3, -camera.getParticle().getCoord().getPosition().x,-camera.getParticle().getCoord().getPosition().y);
			Calculation.rotatePoint(point3, (double) (camera.getParticle().getRotation()* Math.PI / 180));
			Calculation.translate(point3, camera.getParticle().getCoord().getPosition().x,camera.getParticle().getCoord().getPosition().y);

			glTexCoord2d(camera.correctx(point3.x) / Display.getWidth(),camera.correcty(point3.y) / Display.getHeight());

			Point2D.Double point4 = bez2.parametric(foo2);
			Point2D.Double delt2 = bez2.normal(foo2);
			Calculation.rotatePoint(delt2, angle);

			Calculation.dilate(delt2,(arcRatio) * (Calculation.getMagnitude(delt2)));
			Calculation.translate(point4, delt);

			glVertex2d(camera.correctx(point4.x), camera.correcty(point4.y));
		}
		glEnd();
	}

	// This method traces light from the player to every point on the screen. It
	// does not work well and will not be used. Pixel by pixel shaders are dumb
	// anyway.
	public static void drawTrace(PortalLinker[] plinks) {
		glColor4f(1, 1, 1, 1);
		FrameBufferHandler.getFrame().bind();

		for (double i = 0; i < Display.getWidth(); i += resx) {
			for (double j = 0; j < Display.getHeight(); j += resy) {

				world.physic.Projectile proj = new world.physic.Projectile(camera.getParticle().getCoord().getPosition(), camera.getCoordinateIn(i, j));
				
				boolean d = false;
				
				for (PortalLinker p: plinks){

				d ^=((PortalLinker_Bezier)p).projectileWork(proj).intersected;
				}
			

				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S,GL_CLAMP_TO_EDGE);
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T,GL_CLAMP_TO_EDGE);
				
				Point2D.Double cam = camera.getParticle().getCoord().getPosition();
				Point2D.Double pro = proj.getCoords().getCoord().getPosition();

				Calculation.translate(pro, -cam.x,-cam.y);
				Calculation.rotatePoint(pro,(camera.getParticle().getRotation() * Math.PI / 180));
				Calculation.translate(pro, cam.x,cam.y);
				
				if (d)
				{
				glBegin(GL_QUADS);
				Point2D.Double phelp = proj.getCoords().getCoord().getPosition();

					glTexCoord2d(camera.correctx(phelp.x) / Display.getWidth(),camera.correcty(phelp.y) / Display.getHeight());
					glVertex2d(i, j);
					glVertex2d(i + resx, j);
					glVertex2d(i + resx, j + resy);
					glVertex2d(i, j + resy);
	
				glEnd();
				}
			}
		}

	}
}

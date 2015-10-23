package world.render;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_VIEWPORT_BIT;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor4d;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopAttrib;
import static org.lwjgl.opengl.GL11.glPushAttrib;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glVertex2d;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

public class FrameBufferHandler {
	
	private static Texture frame;

	private static int myFBOId;
	
	public static void initFrameBuffer() {

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ImageIO.write(
					new BufferedImage(Display.getWidth(), Display.getHeight(),BufferedImage.TYPE_INT_ARGB), "png", os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		InputStream is = new ByteArrayInputStream(os.toByteArray());

		try {
			frame = TextureLoader.getTexture("PNG", is);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// boolean FBOEnabled =
		// GLContext.getCapabilities().GL_EXT_framebuffer_object; -returns true

		IntBuffer buffer = ByteBuffer.allocateDirect(1 * 4)
				.order(ByteOrder.nativeOrder()).asIntBuffer(); // allocate a 1
		// int byte
		// buffer
		EXTFramebufferObject.glGenFramebuffersEXT(buffer); // generate
		myFBOId = buffer.get();

		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, myFBOId);
		EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT, GL_TEXTURE_2D,frame.getTextureID(), 0);

		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, myFBOId);
	}
	
	public static void endFrameBuffer() {
		glPopAttrib();
		EXTFramebufferObject.glBindFramebufferEXT(
				EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
	}
	
	public static void beginFrameBuffer() {
		// *frame buffer below
		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, myFBOId);
		glPushAttrib(GL_VIEWPORT_BIT);
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	public static int getMyFBOId() {
		return myFBOId;
	}
	
	public static void setFrame(Texture frameu) {
		frame = frameu;
	}

	public static void setMyFBOId(int myFBOIdu) {
		myFBOId = myFBOIdu;
	}
	
	public static Texture getFrame() {
		return frame;
	}
	public static void drawFrameBuffer() {
		FrameBufferHandler.getFrame().bind();
		glColor4d(1, 1, 1, 1f);

		glBegin(GL_QUADS);
		glTexCoord2d(0, 0);
		glVertex2d(0, 0);
		glTexCoord2d(0, 1);
		glVertex2d(0, Display.getHeight());
		glTexCoord2d(1, 1);
		glVertex2d(Display.getWidth(), Display.getHeight());
		glTexCoord2d(1, 0);
		glVertex2d(Display.getWidth(), 0);
		glEnd();
	}
}

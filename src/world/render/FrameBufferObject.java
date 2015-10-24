package world.render;

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
import static org.lwjgl.opengl.GL11.*;


public class FrameBufferObject {
	
	public Texture texture;

	public int FrameBufferIdentifier;
	
	public FrameBufferObject() {

        // boolean FBOEnabled = GLContext.getCapabilities().GL_EXT_framebuffer_object; -returns true

        int width = roundToPowTwo(Display.getWidth());
        int height = roundToPowTwo(Display.getHeight());

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try { ImageIO.write( new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB), "png", os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		InputStream is = new ByteArrayInputStream(os.toByteArray());
		try { texture = TextureLoader.getTexture("PNG", is);
		} catch (IOException e) {
			e.printStackTrace();
		}

		IntBuffer buffer = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer(); // allocate a 1 int byte buffer
		EXTFramebufferObject.glGenFramebuffersEXT(buffer); // generate
		FrameBufferIdentifier = buffer.get();

		EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, FrameBufferIdentifier);
		EXTFramebufferObject.glFramebufferTexture2DEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT, GL_TEXTURE_2D, texture.getTextureID(), 0);

	}

    public static int roundToPowTwo(int x){
        return (int)Math.pow(2, Math.ceil(Math.log(x)/Math.log(2)));
    }

    public static void setFrameBuffer(int BufferIdentifier){
        EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, BufferIdentifier);
        glPushAttrib(GL_VIEWPORT_BIT);
    }

    public static void changeFrameBuffer(int BufferIdentifier){
        glPopAttrib();
        EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, BufferIdentifier);
        glPushAttrib(GL_VIEWPORT_BIT);
    }

    public static void removeFrameBuffer(){
        glPopAttrib();
        EXTFramebufferObject.glBindFramebufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);
    }

	public int getFrameBufferIdentifier() {
		return FrameBufferIdentifier;
	}
	
	public Texture getTexture() {
		return texture;
	}
}

package world.render.Renderers;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.Color;

import world.render.POV;
import world.render.FrameBufferHandler;
import world.render.OpenglInitializer;

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
		FrameBufferHandler.initFrameBuffer();

	}
	
	public static void testResize() {
		if (Display.wasResized()) {
			OpenglInitializer.resizeGL();
			FrameBufferHandler.initFrameBuffer();
		}
	}

	public static POV getCamera() {
		return camera;
	}

}
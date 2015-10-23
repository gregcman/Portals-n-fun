package controlCenter;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.ImageIOImageData;

import world.render.Renderers.Renderer;

public class DisplayHandler {

	private static String title = "Space N fun";
	private static int ticksPerSecond = 60;

	public static void act(String s) {
		Display.setTitle(s);
		Display.sync(ticksPerSecond);
	}

	public static void createDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(1024,1024));
			Display.setResizable(false);
			Display.setFullscreen(false);
			Display.setTitle(title);
			Display.setVSyncEnabled(true);

			//setIcon("/res/defcon.png", "/res/warning.png");
			Display.create();
		} catch (LWJGLException ex) {
			Logger.getLogger(Renderer.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}

	public static void createStuff() {
		try {
			createDisplay();

			// Keyboard
			Keyboard.create();

			// Mouse
			Mouse.setGrabbed(false);
			Mouse.create();
		} catch (LWJGLException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void destroyDisplay() {
		// Methods already check if created before destroying.
		Mouse.destroy();
		Keyboard.destroy();
		Display.destroy();
	}

	public static String getTitle() {
		return title;
	}

	public static void setIcon(String bigpic, String smallpic) {
		try {
			Display.setIcon(new ByteBuffer[] {
					new ImageIOImageData().imageToByteBuffer(ImageIO.read(new File(bigpic)), false, false, null),
					new ImageIOImageData().imageToByteBuffer(ImageIO.read(new File(smallpic)), false, false, null) });
		} catch (IOException ex) {
			Logger.getLogger(Renderer.class.getName()).log(Level.SEVERE, null,
					ex);
		}
	}
}

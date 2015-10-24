package controlCenter;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.ImageIOImageData;

import world.render.Renderers.Renderer;

public class DisplayHandler {

	private static String title = "Fun and sun";
	private static int ticksPerSecond = 60;

	public static void act(String s) {
		Display.setTitle(s);
		Display.sync(ticksPerSecond);
	}

	public static void createDisplay() {
		try {

            setDisplayMode(1280, 720, false);

            Display.setResizable(false);
			Display.setTitle(title);
			Display.setVSyncEnabled(true);

			//setIcon("/res/defcon.png", "/res/warning.png");
			Display.create();
		} catch (LWJGLException ex) {
			Logger.getLogger(Renderer.class.getName()).log(Level.SEVERE, null, ex);
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

	/**
	 * Set the display mode to be used
	 *
	 * @param width The width of the display required
	 * @param height The height of the display required
	 * @param fullscreen True if we want fullscreen mode
	 */
	public static void setDisplayMode(int width, int height, boolean fullscreen) {

		// return if requested DisplayMode is already set
		if ((Display.getDisplayMode().getWidth() == width) &&
				(Display.getDisplayMode().getHeight() == height) &&
				(Display.isFullscreen() == fullscreen)) {
			return;
		}

		try {
			DisplayMode targetDisplayMode = null;

			if (fullscreen) {
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;

				for (int i=0;i<modes.length;i++) {
					DisplayMode current = modes[i];

					if ((current.getWidth() == width) && (current.getHeight() == height)) {
						if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
							if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
								targetDisplayMode = current;
								freq = targetDisplayMode.getFrequency();
							}
						}

						// if we've found a match for bpp and frequence against the
						// original display mode then it's probably best to go for this one
						// since it's most likely compatible with the monitor
						if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) &&
								(current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
							targetDisplayMode = current;
							break;
						}
					}
				}
			} else {
				targetDisplayMode = new DisplayMode(width,height);
			}

			if (targetDisplayMode == null) {
				System.out.println("Failed to find value mode: "+width+"x"+height+" fs="+fullscreen);
				return;
			}

			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(fullscreen);

		} catch (LWJGLException e) {
			System.out.println("Unable to setup mode "+width+"x"+height+" fullscreen="+fullscreen + e);
		}
	}

	public static class FrameRateMeasurer {

        private long lastFrame; // time at last frame

        private int fps; // frames per second

        private long lastFPS; // last fps time

        private int saved_fps;

        public int getDelta() {
            long time = getTime();
            int delta = (int) (time - lastFrame);
            lastFrame = time;
            return delta;
        }

        // Get the time in milliseconds
        // @return The system time in milliseconds
        public long getTime() {
            return (Sys.getTime() * 1000) / Sys.getTimerResolution();
        }

        public void startFPS() {
            getDelta();
            lastFPS = getTime();
        }

        // Calculate the FPS
        public int updateFPS() {
            int i = -1;
            if (getTime() - lastFPS > 1000) {
                i = fps;
                saved_fps = fps;
                fps = 0;
                lastFPS += 1000;
            }
            fps++;
            if(!(i==-1)){
            return i;}
            else {return saved_fps;}
        }

    }
}

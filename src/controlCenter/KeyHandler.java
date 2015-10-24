package controlCenter;

import math.Calculation;
import math.Complex;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import world.render.Renderers.Renderer_Portals;

public class KeyHandler {
	public static boolean asciiLib[] = new boolean[257];
	public static void keyboardWork() {

		if (Display.isCloseRequested()
				|| Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			Main.exit = true;
		}

		if (onceKey(Keyboard.KEY_MINUS)&&Renderer_Portals.resx!=1 &&Renderer_Portals.resy!=1) {
				Renderer_Portals.resx--;
				Renderer_Portals.resy--;
		}

		if (onceKey(Keyboard.KEY_EQUALS)){
				Renderer_Portals.resx++;
				Renderer_Portals.resy++;
			}


		Complex addvel = new Complex(0, 0);
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)
				|| Keyboard.isKeyDown(Keyboard.KEY_W)) {
			addvel.y += 1;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)
				|| Keyboard.isKeyDown(Keyboard.KEY_S)) {
			addvel.y -= 1;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)
				|| Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			addvel.x -= 1;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)
				|| Keyboard.isKeyDown(Keyboard.KEY_E)) {
			addvel.x += 1;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			Controller.getPlayer().getPlace().setRotation((Controller.getPlayer().getPlace().getRotation() + 0.03));
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			Controller.getPlayer().getPlace().setRotation((Controller.getPlayer().getPlace().getRotation() - 0.03));
		}

		double foo = addvel.mod();
		if (foo != 0) {
			addvel.scale((Controller.getPlayer().getPlace().getSize() / 50f) / foo);
        }

        addvel.times_to(Complex.polar(Controller.getPlayer().getPlace().getRotation(), 1));
		Controller.getPlayer().getLocation().getVelocity().plus_to(addvel);
	}
	
	public static boolean onceKey(int key){			
		return Keyboard.isKeyDown(key)? (asciiLib[key]?!(asciiLib[key] = true):(asciiLib[key] = true)) :(asciiLib[key] = false);
	}
}

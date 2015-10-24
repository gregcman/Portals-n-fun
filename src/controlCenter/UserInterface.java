package controlCenter;

import math.Complex;
import org.lwjgl.input.Mouse;
import world.render.Renderers.Renderer;
import world.things.Entity;
import world.things.Particle;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;


public class UserInterface {


	public static boolean asciiLib[] = new boolean[257];


	public static void iterate() {

        while (Keyboard.next()) {
            asciiLib[Keyboard.getEventKey()] = Keyboard.getEventKeyState();
        }

    }

    public void update(Entity player_entity) {
        Particle player = player_entity.getParticle();

        if (Display.isCloseRequested() || asciiLib[Keyboard.KEY_ESCAPE]) {
            Main.exit = true;
        }

        Complex addvel = new Complex(0, 0);
        if (asciiLib[Keyboard.KEY_UP] || asciiLib[Keyboard.KEY_W]) {
            addvel.y += 1;
        }

        if (asciiLib[Keyboard.KEY_DOWN] || asciiLib[Keyboard.KEY_S]) {
            addvel.y -= 1;
        }

        if (asciiLib[Keyboard.KEY_LEFT] || asciiLib[Keyboard.KEY_Q]) {
            addvel.x -= 1;
        }
        if (asciiLib[Keyboard.KEY_RIGHT] || asciiLib[Keyboard.KEY_E]) {
            addvel.x += 1;
        }
        if (asciiLib[Keyboard.KEY_A]) {
            player.setRotation((player.getRotation() + 0.03));
        }
        if (asciiLib[Keyboard.KEY_D]) {
            player.setRotation(player.getRotation() - 0.03);
        }

        double foo = addvel.mod();
        if (foo != 0) {
            addvel.scale_to((player.getSize() / 50f) / foo);
        }

        addvel.rotate_to(player.getRotation());

        player.getVelocityVector().plus_to(addvel);
    }

    public static class MouseHandler {
        public static Complex getWhereMouse() {
            return whereMouse;
        }
        public static Complex whereMouse;

        public static void iterate(){
            whereMouse = Renderer.getCamera().getCoordinateIn(Mouse.getX(), Mouse.getY());
        }

    }
}

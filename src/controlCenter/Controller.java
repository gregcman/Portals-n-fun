package controlCenter;


import java.util.HashMap;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.ReadableColor;

import world.things.Particle;
import world.render.FrameBufferObject;
import world.render.ImageLibrary;
import world.render.POV;
import world.render.Renderers.Renderer;
import world.things.Entity;
import world.things.MovablePoint;

import static org.lwjgl.opengl.GL11.*;

public class Controller {

	private static HashMap<String, Entity> Entity_HashMap = new HashMap<String, Entity>();

    public static FrameBufferObject extra_frame = new FrameBufferObject();

	public static void iterate() {

        UserInterface.MouseHandler.iterate();
        UserInterface.iterate();

        UserInterface soul = new UserInterface();
        soul.update(getPlayerEntity());

        for(Entity x : Entity_HashMap.values()){
            MovablePoint.act(x.getParticle().getPositionVector(), x.getParticle().size);
            x.getParticle().update();
        }

        Entity player = Entity_HashMap.get("player");
        Particle player_physics = player.getParticle();

		POV camera = Renderer.getCamera();
        camera.followParticle(player_physics);
		camera.setZoom((camera.getZoom() * Math.pow(1.001, Mouse.getDWheel())));

        Renderer.setRenderingDimensions(Display.getWidth(), Display.getHeight());

		Renderer.getFill().setColor(ReadableColor.WHITE);
        Renderer.testResize();

        glClear(GL_COLOR_BUFFER_BIT);

        // *frame buffer below

        FrameBufferObject.setFrameBuffer(extra_frame.getFrameBufferIdentifier());
        glClear(GL_COLOR_BUFFER_BIT);
		display();
        FrameBufferObject.removeFrameBuffer();

		Renderer.Renderer_FrameBuffer.drawFrameBuffer(extra_frame.getTexture());

		Display.update();
	}

	public static void display() {
		for (Entity x : Entity_HashMap.values()) {
			Renderer.Renderer_Entity.draw(x);
		}

	}

	public static void init() {

		Renderer.init();
		ImageLibrary.init();

        ImageLibrary.addImage("texture", "gooby");
        ImageLibrary.addImage("texel", "sanic");
        ImageLibrary.addImage("smile", "spoder");
        ImageLibrary.addImage("glass", "sanic");
        ImageLibrary.addImage("zen", "sky");
        ImageLibrary.addImage("stripes", "sky");


		Entity_HashMap.put("00", new Entity(-160, -40, 20, ImageLibrary.get("texture")));
		Entity_HashMap.put("01", new Entity(0, -40, 30, ImageLibrary.get("zen")));
		Entity_HashMap.put("02", new Entity(160, -40, 40, ImageLibrary.get("texture")));
		Entity_HashMap.put("10", new Entity(-160, 50, 30, ImageLibrary.get("glass")));
		Entity_HashMap.put("11", new Entity(0, 50, 20, ImageLibrary.get("texel")));
		Entity_HashMap.put("12", new Entity(160, 50, 40, ImageLibrary.get("zen")));

        for (int i = 0; i < 100; i++) {
            Entity_HashMap.put( rand(0,1000) + "",generateRandomEntity());
        }

        Entity_HashMap.put("player", new Entity(12, 12, 12, ImageLibrary.get("smile")));

	}

    public static String names[] = {"texture", "zen", "glass", "texel", "smile"};

    public static double rand(float start, float end){
        return (Math.random() * (end - start) + start);
    }

    public static Entity generateRandomEntity(){
        return new Entity(rand(-1000, 1000), rand(-1000, 1000), rand(4, 256), ImageLibrary.get(names[(int)rand(0, names.length-1)]));
    }

    public static Entity getPlayerEntity(){
        return Entity_HashMap.get("player");
    }
}

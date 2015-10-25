package controlCenter;


import math.Calculation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import world.World;
import world.render.*;
import world.things.Entity;
import math.Complex;


public class Controller {

    public static FrameBufferObject extra_frame = new FrameBufferObject();
    public static POV mainCamera;
    public static World worldOne;

    public static void iterate() {
        //Dealing with the user input

        UserInterface.iterate();
        UserInterface soul = new UserInterface();
        soul.update(getPlayerEntity());


        //Dealing with the Camera
        WorldShapes.setCamera(mainCamera);
        mainCamera.setZoom(mainCamera.getZoom() * Math.pow(1.001, Mouse.getDWheel()));
        mainCamera.followParticle(mainCamera.world.Entity_HashMap.get("player").getParticle());
        mainCamera.iterate();
        mainCamera.entityDrag();        //Deals with dragging points

        //Update the physics
        updateWorld(worldOne);

        //Rendering and stuff
        Render.testResize();

        FrameBufferObject.setFrameBuffer(extra_frame.getFrameBufferIdentifier()); //applies the extra framebuffer
        mainCamera.render();
        FrameBufferObject.setFrameBuffer(0); //applies the normal screen

        //Drawing the framebuffer
        HUDShapes.drawTexturedQuad(0, 0, Display.getWidth(), Display.getHeight(), extra_frame.getTexture(), Display.getWidth(), Display.getHeight());
        Display.update();
    }

    public static void updateWorld(World w){
        for (Entity x : w.Entity_HashMap.values()) {
            x.getParticle().update();
        }
    }

    public static void init() {


        WorldShapes.init();
        ImageLibrary.init();

        worldOne = new World();

        //Setting up arbitrary testing entities
        for (int i = -10; i < 10; i++) {
            for(int j = -10; j<10; j++) {
                Entity e = new Entity(i * 10, j * 10, 5, ImageLibrary.randomImage());
                e.getParticle().setVelocityVector(new Complex(Calculation.rand(-0.1f, 0.1f), Calculation.rand(-0.1f, 0.1f)));
                e.getParticle().setAngularVelocity(Calculation.rand(-1, 1));
                e.getParticle().setAngularVelocity(Calculation.rand(-100,100));
                worldOne.Entity_HashMap.put(Calculation.rand(0, 1000) + "", e);
            }
        }

        worldOne.Entity_HashMap.put("player", new Entity(12, 12, 12, ImageLibrary.get("smile")));
        mainCamera = new POV(worldOne.Entity_HashMap.get("player").getParticle().getPositionVector(),Display.getWidth(), Display.getHeight(), 0,  5, worldOne);

    }

    public static Entity getPlayerEntity() {
        return mainCamera.world.Entity_HashMap.get("player");
    }
}

package controlCenter;


import math.Calculation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import world.World;
import world.render.*;
import world.things.Entity;
import world.things.MovablePoint;

import world.things.Particle;

public class Controller {

    public static FrameBufferObject extra_frame = new FrameBufferObject();
    public static String names[] = {"texture", "zen", "glass", "texel", "smile"};
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

        ImageLibrary.addImage("texture", "bush");
        ImageLibrary.addImage("texel", "warning");
        ImageLibrary.addImage("smile", "defcon");
        ImageLibrary.addImage("glass", "computer");
        ImageLibrary.addImage("zen", "ice");
        ImageLibrary.addImage("stripes", "sky");

        worldOne = new World();

        for (int i = 0; i < 100; i++) {
            worldOne.Entity_HashMap.put(Calculation.rand(0, 1000) + "", generateRandomEntity());
        }

        worldOne.Entity_HashMap.put("player", new Entity(12, 12, 12, ImageLibrary.get("smile")));
        mainCamera = new POV(worldOne.Entity_HashMap.get("player").getParticle().getPositionVector(),Display.getWidth(), Display.getHeight(), 0,  5, worldOne);

    }

    public static Entity generateRandomEntity() {
        return new Entity(Calculation.rand(-1000, 1000), Calculation.rand(-1000, 1000), Calculation.rand(2, 200), ImageLibrary.get(names[(int)Calculation.rand(0, names.length - 1)]));
    }

    public static Entity getPlayerEntity() {
        return mainCamera.world.Entity_HashMap.get("player");
    }
}

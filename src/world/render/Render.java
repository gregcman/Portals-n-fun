package world.render;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.Color;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;

/**
 * Created by The Phantom on 10/24/2015.
 */
public class Render {
    public static Color stroke = new Color();
    public static Color fill = new Color();

    public static Color getFill() {
        return fill;
    }

    public static void setFill(Color fill) {
        WorldShapes.fill = fill;
        glColor4d(fill.getRed() / 255f, fill.getGreen() / 255f, fill.getBlue() / 255f, fill.getAlpha() / 255f);
    }

    public static Color getStroke() {
        return stroke;
    }

    public static void setStroke(Color stroke) {
        WorldShapes.stroke = stroke;
    }

    public static void testResize() {
        if (Display.wasResized()) {
            resizeGL();
        }
    }

    public static void init() {
        // OpenGL
        initGL();
        resizeGL();
    }

    public static void initGL() {

        glEnable(GL_TEXTURE_2D);
        glDisable(GL_DEPTH_TEST);

        glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void resizeGL() {

        // 2D Scene
        glViewport(0, 0, Display.getWidth(), Display.getHeight());
        glMatrixMode(GL_MODELVIEW);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluOrtho2D(0.0f, Display.getWidth(), 0.0f, Display.getHeight());
        glMatrixMode(GL_MODELVIEW);
    }

}

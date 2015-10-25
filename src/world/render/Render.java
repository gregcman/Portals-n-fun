package world.render;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.Color;

import static org.lwjgl.opengl.GL11.glColor4d;

/**
 * Created by The Phantom on 10/24/2015.
 */
public class Render {
    public static Color stroke = new Color();
    public static Color fill = new Color();
    public static int texture_dimensions_width;
    public static int texture_dimensions_height;

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
            OpenglInitializer.resizeGL();
        }
    }

    public static void init() {
        // OpenGL
        OpenglInitializer.initGL();
        OpenglInitializer.resizeGL();
    }

}

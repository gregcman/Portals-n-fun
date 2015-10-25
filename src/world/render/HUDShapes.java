package world.render;

import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by The Phantom on 10/24/2015.
 */
public class HUDShapes extends Render {
    public static void drawQuad(double x1, double y1, double x2, double y2) {
        //glDisable(GL_TEXTURE_2D);

        glBegin(GL_QUADS);
        glVertex2d(x1, y1);
        glVertex2d(x1, y2);
        glVertex2d(x2, y2);
        glVertex2d(x2, y1);
        glEnd();
        //glEnable(GL_TEXTURE_2D);
    }

    /**
     * Created by The Phantom on 10/24/2015.
     */

    public static void drawTexturedQuad(float ax1, float ay1, float ax2, float ay2, Texture texture, int texture_width, int texture_height) {
        texture.bind();

        float real_width = texture.getTextureWidth();
        float real_height = texture.getTextureHeight();

        float x1 = 0;
        float y1 = 0;

        float x2 = texture_width / real_width;
        float y2 = texture_height / real_height;

        glColor4d(1, 1, 1, 1f);
        glBegin(GL_QUADS);
        glTexCoord2d(x1, y1);
        glVertex2d(ax1, ay1);
        glTexCoord2d(x1, y2);
        glVertex2d(ax1, ay2);
        glTexCoord2d(x2, y2);
        glVertex2d(ax2, ay2);
        glTexCoord2d(x2, y1);
        glVertex2d(ax2, ay1);
        glEnd();

    }

}

package world.render;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.util.HashMap;

public class ImageLibrary {
    private static HashMap<String, Texture> imageLib = new HashMap<String, Texture>();

    public static Texture get(String s) {
        return imageLib.get(s);

    }

    public static HashMap<String, Texture> getImageLib() {
        return imageLib;
    }

    public static void setImageLib(HashMap<String, Texture> imageLib) {
        ImageLibrary.imageLib = imageLib;
    }

    public static void addImage(String name, String location) {
        imageLib.put(name, getTexture("res/Images/" + location));
    }

    public static void init() {

    }

    private static Texture getTexture(String s) {
        try {
            return TextureLoader.getTexture("PNG",
                    ResourceLoader.getResourceAsStream(s + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

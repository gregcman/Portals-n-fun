package world.render.Renderers;

import world.things.Particle;
import world.things.Entity;

public class Renderer_Sprites extends Renderer {
	public static void drawSprite(Entity sprite) {
		Particle p = sprite.getParticle();
		Renderer_TexturedShapes.drawQuad(p.getPositionVector().x, p.getPositionVector().y,p.getSize(), p.getSize(), p.getRotation(), sprite.getImage());
	}
}

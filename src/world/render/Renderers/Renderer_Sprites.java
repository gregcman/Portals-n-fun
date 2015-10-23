package world.render.Renderers;

import world.physic.Particle;
import world.things.Entity;

public class Renderer_Sprites extends Renderer {
	public static void drawSprite(Entity sprite) {
		Particle p = sprite.getParticle();
		Renderer_TexturedShapes.drawQuad(p.getCoord().getPosition().x, p.getCoord().getPosition().y,p.getSize(), p.getSize(), p.getRotation(), sprite.getImage());
	}
}

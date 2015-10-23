package controlCenter;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.ReadableColor;

import world.physic.Particle;
import world.portal.data.Portal_Bezier;
import world.portal.link.PortalLinker;
import world.portal.link.PortalLinker_Bezier;
import world.render.FrameBufferHandler;
import world.render.ImageLibrary;
import world.render.POV;
import world.render.Renderers.Renderer;
import world.render.Renderers.Renderer_Curves;
import world.render.Renderers.Renderer_Portals;
import world.render.Renderers.Renderer_Sprites;
import world.render.Renderers.Renderer_TexturedShapes;
import world.things.Entity;
import world.things.MovablePoint;
import world.things.Player;

public class Controller {

	private static HashMap<String, Entity> pointLib = new HashMap<String, Entity>();

	// list which stores the movablePoints in each curve
	private static Map<String, Entity[]> curveLib = new HashMap<String, Entity[]>();
	private static Map<String, Boolean> curveLibMoved = new HashMap<String, Boolean>();
	public static Map<String, Portal_Bezier> bezLib = new HashMap<String, Portal_Bezier>();
	public static Map<String, PortalLinker_Bezier> linkLib = new HashMap<String, PortalLinker_Bezier>();

	private static Point2D.Double whereMouse;

	// Creates a player
	private static Player player;

	public static void act() {
		
		
		whereMouse = Renderer.getCamera().getCoordinateIn(Mouse.getX(), Mouse.getY());


		for (int i = 0; i<curveLib.size();i++){
			curveLibMoved.put(i+"",false);
			for (Entity x : curveLib.get(i+"")) {
			MovablePoint.act(x.getParticle().getCoord().getPosition(), x.getParticle().getSize());
			curveLibMoved.put(i+"",curveLibMoved.get(i+"") || MovablePoint.isNotVacant());
			}
		}
		
		bezLib.get("curve").revaluate();
		bezLib.get("curve2").revaluate();
		// Displays the projectile and also any teleportation that might occur
		
		
		for (int i = 0; i<linkLib.size();i++)
		{
			linkLib.get(i+"").update();
			
		}
		for (int i = 0; i<linkLib.size();i++)
		{
			linkLib.get(i+"").projectileWork(player.getLocation());
		
		}		

		player.act();
		
		POV came = Renderer.getCamera();
		Particle cam = came.getParticle();

		cam.getCoord().getPosition().setLocation(player.getPlace().getCoord().getPosition());
		cam.setSize(25f / player.getSprite().getParticle().getSize());
		cam.setRotation(-(player.getPlace().getRotation() * 180 / Math.PI));
		came.setZoom((came.getZoom() * Math.pow(1.001, Mouse.getDWheel())));

		Renderer.getFill().setColor(ReadableColor.WHITE);
		player.getSprite().getParticle().setRotation(-Renderer.getCamera().getParticle().getRotation());
		KeyHandler.keyboardWork();
		
				
		glClear(GL_COLOR_BUFFER_BIT);
		// Clear the screen buffer	
		Renderer.testResize();	
		FrameBufferHandler.beginFrameBuffer();
		display();
		FrameBufferHandler.endFrameBuffer();
		FrameBufferHandler.drawFrameBuffer();
		PortalLinker[] plinklist = new PortalLinker[linkLib.size()];
		for(int i = 0; i<linkLib.size(); i++){
			plinklist[i] = linkLib.get(i+"");
		}
		Renderer_Portals.draw(plinklist);
		//Renderer_Portals.drawTrace(new PortalLinker[]{linkLib.get("0"), linkLib.get("1")});
		
		Renderer.getFill().set(255, 255, 255);
		player.getSprite().setImage(ImageLibrary.get("smile"));
		Renderer_Sprites.drawSprite(player.getSprite());
		Display.update();
	}

	public static void display() {
		
		for (int j = -5; j<5;j++)
		{
			for (int i = -5; i<5;i++)
			{
				Renderer_TexturedShapes.drawQuad(i*1000, j*1000, 1000, 1000, (i*j)*System.currentTimeMillis()/10, ImageLibrary.get("stripes"));
			}
		}

		for (Entity x : curveLib.get("0")) {
			Renderer_Sprites.drawSprite(x);
		}

		for (Entity x : curveLib.get("1")) {
			Renderer_Sprites.drawSprite(x);
		}

		Renderer.getFill().set(222, 255, 80);
		Renderer_Curves.display(bezLib.get("curve").getCurve());
		Renderer.getFill().set(0, 60, 80);
		Renderer_Curves.display(bezLib.get("curve2").getCurve());

		Renderer.getFill().set(255, 255, 255);
		player.getSprite().setImage(ImageLibrary.get("smile"));
		Renderer_Sprites.drawSprite(player.getSprite());
	}

	public static Player getPlayer() {
		return player;
	}

	public static Point2D.Double getWhereMouse() {
		return whereMouse;
	}

	public static void init() {

		Renderer.init();
		ImageLibrary.init();

		pointLib.put("00",new Entity(-160, -40, 20, ImageLibrary.get("texture")));
		pointLib.put("01", new Entity(0, -40, 30, ImageLibrary.get("zen")));
		pointLib.put("02", new Entity(160, -40, 40, ImageLibrary.get("texture")));
		pointLib.put("10", new Entity(-160, 50, 30, ImageLibrary.get("glass")));
		pointLib.put("11", new Entity(0, 50, 20, ImageLibrary.get("texel")));
		pointLib.put("12", new Entity(160, 50, 40, ImageLibrary.get("zen")));
		
		curveLib.put("0", new Entity[3]);
		curveLib.put("1", new Entity[3]);
		
		putPoints(curveLib.get("0"), "0",3);
		putPoints(curveLib.get("1"), "1",3);
					
		Point2D.Double[] Pointd = new Point2D.Double[3];

		for (int i = 0; i < 3; i++) {
			Pointd[i] = (pointLib.get("0" + i)).getParticle().getCoord().getPosition();
		}

		Point2D.Double[] Pointc = new Point2D.Double[3];

		for (int i = 0; i < 3; i++) {
			Pointc[i] = (pointLib.get("1" + i)).getParticle().getCoord().getPosition();
		}
		
		bezLib.put("curve", new Portal_Bezier(Pointc));
		bezLib.put("curve2", new Portal_Bezier(Pointd));
			
		player = new Player(new Point2D.Double(0, 0));

		bezLib.get("curve").revaluate();
		bezLib.get("curve2").revaluate();
		
		PortalLinker_Bezier link, link2;
		
		link = new PortalLinker_Bezier(bezLib.get("curve"), bezLib.get("curve2"), 0);
		link2 = new PortalLinker_Bezier(bezLib.get("curve2"), bezLib.get("curve"), 0);
		
		linkLib.put("0", link);
		linkLib.put("1", link2);
	}
	
	public static void putPoints(Entity[] list, String name, int numberOfPoints) {
		for (int i = 0; i < numberOfPoints; i++) {
			list[i] = (pointLib.get(name + i));
		}
	}

}

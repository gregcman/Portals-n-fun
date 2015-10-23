package controlCenter;


public class Main {

	public static boolean exit = false;

	private static FrameRateMeasurer frm = new FrameRateMeasurer();

	public static void main(String[] args) {
		frm.startFPS();
		DisplayHandler.createStuff();
		Controller.init();

		while (!exit) {
			Controller.act();
			DisplayHandler.act(DisplayHandler.getTitle() + " FPS: "+ frm.updateFPS());
		}

		DisplayHandler.destroyDisplay();
	}

}

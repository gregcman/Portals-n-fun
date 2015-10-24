package controlCenter;


public class Main {

	public static boolean exit = false;

	private static DisplayHandler.FrameRateMeasurer frm = new DisplayHandler.FrameRateMeasurer();

	public static void main(String[] args) {
		frm.startFPS();
		DisplayHandler.createStuff();
		Controller.init();

		while (!exit) {
			Controller.iterate();
			DisplayHandler.act(DisplayHandler.getTitle() + " FPS: "+ frm.updateFPS());
		}

		DisplayHandler.destroyDisplay();
	}

}

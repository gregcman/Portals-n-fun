package controlCenter;

import org.lwjgl.Sys;

public class FrameRateMeasurer {

	private long lastFrame; // time at last frame

	private int fps; // frames per second

	private long lastFPS; // last fps time
	
	private int savedfps;

	public int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;
		return delta;
	}

	// Get the time in milliseconds
	// @return The system time in milliseconds
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	public void startFPS() {
		getDelta();
		lastFPS = getTime();
	}

	// Calculate the FPS 
	public int updateFPS() {
		int i = -1;
		if (getTime() - lastFPS > 1000) {
			i = fps;
			savedfps = fps;
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
		if(!(i==-1)){
		return i;}
		else {return savedfps;}
	}

}

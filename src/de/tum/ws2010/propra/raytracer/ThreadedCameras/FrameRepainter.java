package de.tum.ws2010.propra.raytracer.ThreadedCameras;

import javax.swing.JFrame;

/**
 * Runnable object, that repaints a given JFrame any 0.1 seconds while the rendering is not complete.
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 *
 */
public class FrameRepainter implements Runnable {
	
	/**
	 * Frame that has to be repainted
	 */
	protected JFrame myFrame;
	
	/**
	 * Render thread that renders the image. 
	 */
	protected Thread renderThread;

	/**
	 * Constructor with the frame to be updated and the thread that renders the image.
	 * 
	 * @param frame Frame to be updated.
	 * @param renderThread Thread that renders the image.
	 */
	public FrameRepainter(JFrame frame, Thread renderThread) {
		myFrame = frame;
		this.renderThread = renderThread;
	}
	
	/**
	 * Updates the frame any 0.1 seconds until rendering is finished.
	 */
	public void run() {
		System.out.println(Thread.currentThread().getName() + " starts...");
		while ( renderThread.isAlive() && ! Thread.interrupted() ) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				return;
			}
			myFrame.repaint();
		}
		myFrame.repaint();
		System.out.println(Thread.currentThread().getName() + " ends...");
	}
}
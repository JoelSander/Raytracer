package de.tum.ws2010.propra.raytracer.ThreadedCameras;

import de.tum.ws2010.propra.raytracer.ThreadedRaytracer;


/**
 * Worker thread, that renders the image.
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 *
 */
public class RenderWorker implements Runnable {

	/**
	 * ThreadedRaytracer for which the image has to be rendered.
	 */
	protected ThreadedRaytracer frame;
	
	/**
	 * Constructor.
	 * 
	 * @param trs ThreadedRaytracer for which the image has to be rendered.
	 */
	public RenderWorker (ThreadedRaytracer trs) {
		frame = trs;
	}
	
	/**
	 * Renders the image of the ThreadedRaytracer.
	 */
	public void run() {
		System.out.println(Thread.currentThread().getName() + " starts...");
		frame.renderImage();
		System.out.println(Thread.currentThread().getName() + " ends...");
	}

}

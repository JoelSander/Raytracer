package de.tum.ws2010.propra.raytracer.ThreadedCameras;

/**
 * Worker thread that renders a fixed number of pixels in an image.
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 *
 */
public class RenderFixedNumWorker implements Runnable {

	/**
	 * Multithread camera for which the pixels are rendered
	 */
	protected MultithreadCamera c;
	
	/**
	 * Index of the pixel to begin rendering.
	 */
	protected int begPixel;
	
	/**
	 * Index of the first pixel that is not rendered.
	 */
	protected int endPixel;
	
	/**
	 * Constructor for the worker.
	 * 
	 * @param camera Multithread camera for which the pixels are rendered.
	 * @param firstPixel Index of the pixel to begin rendering.
	 * @param lastPixel  Index of the first pixel that is not rendered.
	 */
	public RenderFixedNumWorker(MultithreadCamera camera, int firstPixel, int lastPixel) {
		c = camera;
		begPixel = firstPixel;
		endPixel = lastPixel;
	}

	/**
	 * Renders all pixels with index i, where begPixel <= i < endPixel
	 */
	public void run() {
		System.out.println(Thread.currentThread().getName() + " starts...");

		for (int i = begPixel; i < endPixel && ! Thread.interrupted(); i++) {
			c.renderPixel(i);
		}

		System.out.println(Thread.currentThread().getName() + " ends...");
	}
	
}
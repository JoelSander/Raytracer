package de.tum.ws2010.propra.raytracer.ThreadedCameras;

/**
 * Worker thread that renders a single line of a LinethreadCamera.
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 *
 */
class RenderLineWorker implements Runnable {

	/**
	 * Camera for which the line is rendered.
	 */
	protected LinethreadCamera c;
	/**
	 * Vertical position of the line to be rendered.
	 */
	protected int y;
	
	/**
	 * Constructor for the RenderLineWorker
	 * 
	 * @param camera Camera for which the line is rendered.
	 * @param line   Vertical position of the line to be rendered.
	 */
	public RenderLineWorker(LinethreadCamera camera, int line) {
		c = camera;
		y = line;
	}
	
	/**
	 * Renders all pixels within the line to be rendered.
	 */
	public void run() {
		System.out.println(Thread.currentThread().getName() + " starts...");

		int width  = c.getImage().getWidth();
		
		for (int x = 0; x < width && ! Thread.interrupted() ; x++)
				c.renderPixel(x, y);

		System.out.println(Thread.currentThread().getName() + " ends...");
	}	
}

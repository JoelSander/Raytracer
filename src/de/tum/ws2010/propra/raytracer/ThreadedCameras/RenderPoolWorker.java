package de.tum.ws2010.propra.raytracer.ThreadedCameras;

/**
 * Worker thread that renders horizontal lines of a camera until there are no more lines to be rendered.  
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 *
 */
public class RenderPoolWorker implements Runnable{
	
	/**
	 * Camera for which the lines are rendered.
	 */
	protected ThreadpoolCamera c;
	
	/**
	 * Constructor
	 * 
	 * @param camera Camera for which the lines are rendered.
	 */
	public RenderPoolWorker(ThreadpoolCamera camera) {
		c = camera;
	}
	
	/**
	 * Renders horizontal lines of a camera until there are no more lines to be rendered.
	 */
	public void run() {
		System.out.println(Thread.currentThread().getName() + " starts...");
		
		// get Image an initial line to be rendered
		int width = c.getImage().getWidth();
		int line = c.getNextLine();
		
		// while there is a line to be rendered, render all pixels within this line.
		while (line != -1) {
			for (int x = 0; x < width && ! Thread.interrupted(); x++)
				c.renderPixel(x, line);	
			// get the next line to be rendered.
			line = c.getNextLine();
		}
		System.out.println(Thread.currentThread().getName() + " ends...");
	}	
}

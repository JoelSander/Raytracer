package de.tum.ws2010.propra.raytracer.ThreadedCameras;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

/**
 * Camera that renders the Image using a given number of threads, that render the same number of pixels.
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 *
 */
public class MultithreadCamera extends ThreadedCamera {

	/**
	 * Number of threads to be used.
	 */
	protected int numThreads; 
	
	/**
	 * Constructor with field of view only.
	 * 
	 * @param fieldOfViewY Vertical field of view.
	 */
	public MultithreadCamera(double fieldOfViewY, int numberThreads) {
		super(fieldOfViewY);
		numThreads = numberThreads;
	}
		
	@Override
	public List<Thread> startRenderThreads() {
		
		// compute number of pixels per thread.
		BufferedImage bImg = getImage();
		int numPixel = bImg.getHeight() * bImg.getWidth();
		float numPixelPerThread = numPixel / (float) numThreads;
		
		List<Thread> threads = new LinkedList<Thread>();
		
		// create threads
		for (int i = 0; i < numThreads; i++) {
			
			int startPixel = (int) (i * numPixelPerThread);
			int endPixel =  (int) ((i+1) * numPixelPerThread);
			
			RenderFixedNumWorker w = new RenderFixedNumWorker(this, startPixel, endPixel);
			
			Thread t = new Thread(w, "renderFixedNumWorker " + i);
			threads.add(t);
			t.start();
		}
		
		return threads;
	}
	
	/**
	 * Method to render a pixel by a given index instead of a 2D coordinate.
	 * 
	 * @param pixelIdx Index of the pixel to be rendered.
	 */
	public void renderPixel(int pixelIdx) {
		int y = pixelIdx / this.resX;
		int x = pixelIdx - y * this.resX;
		renderPixel(x, y);
	}
}


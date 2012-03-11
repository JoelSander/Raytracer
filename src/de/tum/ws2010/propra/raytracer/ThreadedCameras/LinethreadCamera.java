package de.tum.ws2010.propra.raytracer.ThreadedCameras;
import java.util.LinkedList;
import java.util.List;

/**
 * Camera that uses one thread for each horizontal line to be rendered. 
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 *
 */
public class LinethreadCamera extends ThreadedCamera {

	/**
	 * Constructor with field of view only.
	 * 
	 * @param fieldOfViewY Vertical field of view.
	 */
	public LinethreadCamera(double fieldOfViewY) {
		super(fieldOfViewY);
	}
		
	@Override
	public List<Thread> startRenderThreads() {
		
		List<Thread> threads = new LinkedList<Thread>();
		
		// for each line, create a RenderLineWorker
		for (int y = 0; y < getImage().getHeight(); y++) {
			RenderLineWorker w = new RenderLineWorker(this, y);
			Thread t = new Thread(w, "RenderLineWorker" + y);
			threads.add(t);
			t.start();
		}
		
		return threads;
	}
}


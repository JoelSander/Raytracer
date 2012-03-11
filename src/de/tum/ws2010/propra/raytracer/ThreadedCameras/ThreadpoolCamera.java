package de.tum.ws2010.propra.raytracer.ThreadedCameras;

import java.util.LinkedList;
import java.util.List;

/**
 * Camera that renders the Image using a given number of threads, that get and
 * process lines from a "task pool".
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 * 
 */
public class ThreadpoolCamera extends ThreadedCamera {

	/**
	 * Number of threads used for rendering.
	 */
	protected int numThreads;

	/**
	 * Next line to be rendered
	 */
	protected int nextLine;

	/**
	 * Total number of lines in the image to be rendered.
	 */
	protected int numLines;

	/**
	 * Constructor with field of view only.
	 * 
	 * @param fieldOfViewY
	 *            Vertical field of view.
	 */
	public ThreadpoolCamera(double fieldOfViewY, int numberThreads) {
		super(fieldOfViewY);
		numThreads = numberThreads;
	}

	/**
	 * Method for the worker threads to ask for the next line to be rendered.
	 * 
	 * @return Next line to be rendered. -1, if there are nor more lines left.
	 * 
	 */
	public synchronized int getNextLine() {
		if (nextLine >= numLines)
			return -1;
		else
			return nextLine++;
	}

	@Override
	public List<Thread> startRenderThreads() {

		nextLine = 0;
		numLines = getImage().getHeight();

		List<Thread> threads = new LinkedList<Thread>();

		// start all threads
		for (int i = 0; i < numThreads; i++) {
			RenderPoolWorker w = new RenderPoolWorker(this);
			Thread t = new Thread(w, "RenderPoolWorker " + i);
			threads.add(t);
			t.start();
		}

		return threads;
	}
}

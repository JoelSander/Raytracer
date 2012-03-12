package de.tum.ws2010.propra.raytracer.ThreadedCameras;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.List;

import de.tum.ws2010.propra.raytracer.primitives.Ray;
import de.tum.ws2010.propra.raytracer.render.Camera;
import de.tum.ws2010.propra.raytracer.render.Scenery;

/**
 * Abstract class for all cameras using multihreading.
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 * 
 */
abstract public class ThreadedCamera extends Camera {

    /**
     * Image to be filled by rendering
     */
    protected BufferedImage bImg;
    /**
     * Image that is synchronized for writing
     */
    protected SynchronizedImage sImg;
    /**
     * Scenery that has to be rendered
     */
    protected Scenery scenery;

    /**
     * Constructor with field of view only.
     * 
     * @param fieldOfViewY
     *            Vertical field of view.
     */
    public ThreadedCamera(double fieldOfViewY) {
        super(fieldOfViewY);
    }

    /**
     * Returns image that is the target for rendering.
     * 
     * @return BufferedImage to be filled by rendering.
     */
    public BufferedImage getImage() {
        if (sImg == null) {
            return null;
        } else {
            return bImg;
        }
    }

    /**
     * Renders a single pixel for a given scenery. This method can be called in
     * parallel by any number of threads.
     * 
     * @param pixX
     *            Horizontal pixel index.
     * @param pixY
     *            Vertical pixel index.
     */
    public void renderPixel(int pixX, int pixY) {
        // create ray
        Ray r = createRay(pixX, pixY);

        // determine Color along ray
        Color color = scenery.traceRay(r, scenery.maxRecursions);

        // set pixel
        sImg.writePixel(pixX, pixY, color);
    }

    /**
     * Renders an image of a scene via raytracing.
     * 
     * @param s
     *            Scene to be rendered.
     * @param img
     *            SynchronizedImage to store the resulting image.
     */
    @Override
    public void renderImage(Scenery s, BufferedImage img) {

        // set up the image settings for calling createRay(x,y)
        initImageSettings(img);

        // store reference to buffered image
        bImg = img;

        // store scenery for renderPixel(...)
        scenery = s;
        
        s.setCamera(this);
        
        // encapsulate the image for synchronized writes
        sImg = new SynchronizedImage(img);

        // start render threads and wait until all threads are finished
        List<Thread> threads = startRenderThreads();

        // show the number of threads that have been started
        System.out.println("Num Threads to wait for: " + threads.size());

        // wait until all threads are finished
        try {
            for (Thread t : threads) {
                t.join();
            }
        } catch (InterruptedException e) {
            // if the rendering is interrupted, interrupt all rendering threads.
            for (Thread t : threads) {
                t.interrupt();
            }
        }
    }

    /**
     * Starts the threads that are needed for rendering.
     * 
     * @return A list of all threads that have been started.
     */
    public abstract List<Thread> startRenderThreads();
}

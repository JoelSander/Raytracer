package de.tum.ws2010.propra.raytracer.ThreadedCameras;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 * Runnable object, that repaints a given JFrame any 0.1 seconds while the rendering is not complete.
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 *
 */
public class FrameRepainter extends Canvas implements Runnable {

    /**
     * Render thread that renders the image. 
     */
    protected Thread renderThread;
    protected BufferedImage img;

    /**
     * Constructor with the frame to be updated and the thread that renders the image.
     * 
     * @param frame Frame to be updated.
     * @param renderThread Thread that renders the image.
     */
    public FrameRepainter( Thread renderThread, BufferedImage toDraw) {
        super();
        setSize(toDraw.getWidth(), toDraw.getHeight());
        this.renderThread = renderThread;
        img = toDraw;
    }

    /**
     * Updates the frame any 0.2 seconds until rendering is finished.
     */
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " starts...");
        while (renderThread.isAlive() && !Thread.interrupted()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                return;
            }
            repaint();
        }
        repaint();
        System.out.println(Thread.currentThread().getName() + " ends...");
    }
    
    /**
     * Paint-method for the frame, that repaints the image to be shown.
     */
    @Override
    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, this);
    }
}

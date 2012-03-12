package de.tum.ws2010.propra.raytracer.ThreadedCameras;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.RenderingHints;
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
    
    int width;
    int height;
    public double zoomFactor=1.0f;
    
    
    /**
     * Constructor with the frame to be updated and the thread that renders the image.
     * 
     * @param frame Frame to be updated.
     * @param renderThread Thread that renders the image.
     */
    public FrameRepainter( Thread renderThread, BufferedImage toDraw) {
        super();
        img = toDraw;
        width=img.getWidth();
        height=img.getHeight();
        setPreferredSize(new Dimension((int)(width*zoomFactor),(int)(height*zoomFactor)));  
        //setSize(toDraw.getWidth(), toDraw.getHeight());
        this.renderThread = renderThread;
        
    }

    /**
     * Updates the frame any 0.3 seconds until rendering is finished.
     */
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " starts...");
        while (renderThread.isAlive() && !Thread.interrupted()) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                return;
            }
            repaint();
        }
        repaint();
        System.out.println(Thread.currentThread().getName() + " ends...");
    }
    
    
    @Override
    public void paint(Graphics g) {
        double zoomFactorX=(double)(getBounds().width)/width;
        double zoomFactorY=(double)(getBounds().height)/height;
        zoomFactor=Math.min(zoomFactorX, zoomFactorY);
        
        //g.setColor(Color.darkGray);
        //g.fillRect(0,0, getBounds().width, getBounds().height);
        
        //((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //((Graphics2D)g).setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawImage(img, 0,0,(int)(width*zoomFactor),(int)(height*zoomFactor),0,0,width,height, null);
        
    }
    
    
}

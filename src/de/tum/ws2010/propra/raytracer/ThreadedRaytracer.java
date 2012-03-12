package de.tum.ws2010.propra.raytracer;

import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.UIManager;

import de.tum.ws2010.propra.raytracer.ThreadedCameras.FrameRepainter;
import de.tum.ws2010.propra.raytracer.ThreadedCameras.RenderWorker;
import de.tum.ws2010.propra.raytracer.ThreadedCameras.ThreadpoolCamera;
import de.tum.ws2010.propra.raytracer.render.Camera;
import de.tum.ws2010.propra.raytracer.render.Scene;
import de.tum.ws2010.propra.raytracer.render.Scenery;
import de.tum.ws2010.propra.raytracer.scenes.CheckerboardScene;
import de.tum.ws2010.propra.raytracer.scenes.ChristmasScene;
import de.tum.ws2010.propra.raytracer.scenes.CornellBoxScene;
import de.tum.ws2010.propra.raytracer.scenes.CornellBoxScene2;
import de.tum.ws2010.propra.raytracer.scenes.TestLightsScene;

/**
 * Swing GUI for raytracing a scene and showing the resulting image.
 * 
 * 
 * @author (c) 2009 Roland Fraedrich (fraedrich@tum.de)
 */
public class ThreadedRaytracer extends JFrame {

    //! rendered image 
    protected BufferedImage buffImg;
    static int width = 1000;
    static int height = 1000;

    /**
     * Sets up and renders the scene.
     */
    public void renderImage() {
        // Declaration of Camera. Position is set later on by the scene.
        //Camera cam = new Camera(Math.PI / 3.0f);
        //Camera cam = new LinethreadCamera(Math.PI / 3.0f);
        //Camera cam = new MultithreadCamera(Math.PI / 3.0f, 4);
        Camera cam = new ThreadpoolCamera(Math.PI / 3.0f, 4);

        // Get or create a scene.
        // Remove comment of the scene you want to render.
        Scene s = null;
        //s = new TestLightsScene();
         s = new CheckerboardScene();
        // s = new CornellBoxScene2();
        // s = new MirrorBoxScene();
        // s = new TetrisScene();
        // s = new StairwayScene();
        // s = new ChristmasScene();

        // Get scenery of the selected scene.
        Scenery scenery = s.getScenery();
        // Change camera according to the selected scene.
        s.setCamera(cam);

        // memorize time when starting rendering
        long startTime = System.nanoTime();

        // Render the scene in one of the render modes
        //cam.renderDepth(scenery, buffImg);
        //cam.renderColorOf1stHit(scenery, buffImg);
        //cam.renderNormals(scenery, buffImg);
        cam.renderImage(scenery, buffImg);

        // Get time when rendering is finished
        long endTime = System.nanoTime();

        // Print out needed time
        System.out.println("Rendering took "
                + (endTime - startTime) / (1000.0 * 1000.0 * 1000.0)
                + " s");
    }

    /**
     * Constructor with the image to be shown.
     * 
     * @param img Image that is rendered and presented in the window.
     */
    public ThreadedRaytracer(BufferedImage img) {
        super("Raytracer");
        buffImg = img;
    }

    /**
     * main-method.
     * 
     * @param args All arguments are ignored.
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception evt) {
        }



        // create image to be rendered
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // create and set up frame
        ThreadedRaytracer raytraceFrame = new ThreadedRaytracer(img);
        raytraceFrame.setResizable(true);
        raytraceFrame.addWindowListener(new WindowAdapter() {
            @Override public void windowClosing(WindowEvent e) {
                //TODO ask for saving
                System.exit(0);
            }
        });
        
        Thread renderThread = new Thread(new RenderWorker(raytraceFrame), "RenderWorker");
        FrameRepainter frp=new FrameRepainter(renderThread,img);
        Thread repaintThread = new Thread(frp, "RepaintWorker");
        // fix frame size to size of image
        //raytraceFrame.setSize(width, height);
        raytraceFrame.add(frp);
        raytraceFrame.pack();
        raytraceFrame.setVisible(true);

        // render image
        

        // start render threads
        renderThread.start();
        repaintThread.start();
        try {
            renderThread.join();
            Thread.sleep(300);
        } catch (InterruptedException ex) {}
        raytraceFrame.repaint();
    }

    
    /**
     * ID, that is necessary for a JFrame.  
     */
    private static final long serialVersionUID = -4395108284480194303L;
}
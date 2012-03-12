package de.tum.ws2010.propra.raytracer.ThreadedCameras;

import de.tum.ws2010.propra.raytracer.primitives.Ray;
import java.awt.Color;

/**
 * Worker thread that renders horizontal lines of a camera until there are no more lines to be rendered.  
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 *
 */
public class RenderPoolWorker implements Runnable {

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
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " starts...");

        // get Image an initial line to be rendered
        int width = c.getImage().getWidth();
        int height = c.getImage().getHeight();
        int line = c.getNextLine();

        // while there is a line to be rendered, render all pixels within this line.
        while (line != -1) {
            for (int x = 0; x < width && !Thread.interrupted(); x++) {
                c.renderPixel(x, line);
            }
            // get the next line to be rendered.
            line = c.getNextLine();
        }

        //TODO now do AA
        //search for pixels
        for(int y=0;y<height&&!Thread.interrupted();y++) {
            for (int x = 0; x < width && !Thread.interrupted(); x++) {
                if(c.sImg.getPixel(x, y).getAlpha()==0)
                {
                    for(int iix=x-10;iix<=x+10;iix++) {
                        for(int iiy=y-10;iix<=y+10;iix++) {
                            c.sImg.writePixel(iix,iiy,Color.RED);//mark
                        }
                    }
                    //TODO
                    //do aa
                    int r=0,g=0,b=0,a=0;
                    for(int iy=0;iy<c.scenery.aaLevel;iy++) {
                        for(int ix=0;ix<c.scenery.aaLevel;ix++) {
                            Ray ray = c.createRay(x+??,y);
                            //TODO
                            Color co=c.scenery.traceRay(ray, c.scenery.maxRecursions);
                            r+=co.getRed(); g+=co.getGreen(); b+=co.getBlue(); a+=co.getAlpha();
                        }
                    }
                    double f=c.scenery.aaLevel*c.scenery.aaLevel;
                    r/=f; g/=f; b/=f; a/=f;
                    c.sImg.writePixel(x, y, new Color(r,g,b,a));
                }
            }
        }

        System.out.println(Thread.currentThread().getName() + " ends...");
    }
}

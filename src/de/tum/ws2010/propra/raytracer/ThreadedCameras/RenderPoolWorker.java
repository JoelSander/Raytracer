package de.tum.ws2010.propra.raytracer.ThreadedCameras;

import de.tum.ws2010.propra.raytracer.primitives.Ray;
import de.tum.ws2010.propra.raytracer.render.Scenery;
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
    
    
    int width;
    int height;
    int line;
    double aaLvl;
    double daaLvl;
    
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
        width = c.getImage().getWidth();
        height = c.getImage().getHeight();
        
        
        if(Scenery.adaptiveAA) {
            line = c.getNextLine();
            // while there is a line to be rendered, render all pixels within this line.
            while (line != -1) {
                for (int x = 0; x < width && !Thread.interrupted(); x++) {
                    c.renderPixel(x, line);
                }
                // get the next line to be rendered.
                line = c.getNextLine();
            }
        }

        c.scenery.maxAARecursions=0;
        aaLvl=c.scenery.aaLevel;
        daaLvl=aaLvl*2;
        if(Scenery.adaptiveAA) {
            for(int y=0;y<height&&!Thread.interrupted();y++) {
                for (int x = 0; x < width && !Thread.interrupted(); x++) {
                    if(c.sImg.getMark(x, y,true))
                    {
                        for(int ix=x-10;ix<=x+10;ix++) {
                            for(int iy=y-10;iy<=y+10;iy++) {
                                if(ix<0||iy<0||iy>=width||iy>=height)
                                    continue;
                                c.sImg.markSecPixel(ix,iy,true);//mark
                            }
                        }
                    }
                }
            }
        }
        
        if(Scenery.adaptiveAA) {
            for(int y=0;y<height&&!Thread.interrupted();y++) {
                doLine(y);
            }
        } else {
            for(int y=0; y != -1 &&!Thread.interrupted();y=c.getNextLine()) {
                doLine(y);
            }
        }
        
        

        System.out.println(Thread.currentThread().getName() + " ends...");
    }

    private void doLine(int y) {
        for (int x = 0; x < width && !Thread.interrupted(); x++) {
                if (x < 0 || y < 0 || x >= width || y >= height) {
                    continue;
                }
                if (Scenery.adaptiveAA && !c.sImg.getSecMark(x, y, true)) {
                    continue;
                }

                int r = 0, g = 0, b = 0, a = 0;
                for (int ay = 1; ay <= aaLvl; ay++) {
                    for (int ax = 1; ax <= aaLvl; ax++) {
                        Ray ray = c.createRay(x + ax / daaLvl, y + ay / daaLvl);
                        Color co = c.scenery.traceRay(ray, c.scenery.maxRecursions);
                        r += co.getRed();
                        g += co.getGreen();
                        b += co.getBlue();
                        a += co.getAlpha();
                    }
                }
                double f = aaLvl * aaLvl;
                r /= f;
                g /= f;
                b /= f;
                a /= f;
                c.sImg.writePixel(x, y, new Color(r, g, b, a));
            }
    }
}

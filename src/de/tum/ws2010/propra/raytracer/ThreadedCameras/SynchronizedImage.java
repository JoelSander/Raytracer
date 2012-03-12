package de.tum.ws2010.propra.raytracer.ThreadedCameras;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.BitSet;

/**
 * Wrapper class that allows synchronized writes to a BufferedImage.
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 *
 */
public class SynchronizedImage {

    /**
     * BufferedImage for which the writes are synchronized.
     */
    protected final BufferedImage img;
    protected final int height;
    protected final int width;
    
    /**
     * a set of bits to set marks on the pixels.
     * 
     */
    protected final BitSet marks;
    protected final BitSet marksSec;
    protected final Object imgSync = new Object();

    /**
     * Constructor.
     * 
     * @param image BufferedImage for which the writes are synchronized.
     */
    SynchronizedImage(BufferedImage image) {
        img = image;
        height=img.getHeight();
        width=img.getWidth();
        marks = new BitSet(width * height);
        marksSec = new BitSet(width * height);
    }

    /**
     * Synchronized write for a single pixel in the BufferedImage.
     * 
     * @param x Horizontal position of the pixel
     * @param y Vertical position of the pixel
     * @param c Color for the pixel. 
     */
    public void writePixel(int x, int y, Color c) {
        synchronized(imgSync) {
            img.setRGB(x, y, c.getRGB());
        }
    }

    /**
     * Marks a pixel for later AA
     * 
     * @param x Horizontal position of the pixel
     * @param y Vertical position of the pixel
     * @param mark true/false mark
     */
    public void markPixel(int x, int y, boolean mark) {
        synchronized(imgSync) {
            marks.set(y*width+x, mark);
        }
    }
    
    /**
     * Marks a pixel for later AA
     * 
     * @param x Horizontal position of the pixel
     * @param y Vertical position of the pixel
     * @param mark true/false mark
     */
    public void markSecPixel(int x, int y, boolean mark) {
        synchronized(imgSync) {
            marksSec.set(y*width+x, mark);
        }
    }

    /**
     * returns the Color set in a given pixel.
     * if the unmark option is true, it unsets the mark at the given pixel and
     * returns NULL, if the mark was set.
     * @param x the x coordinate
     * @param y the y coordinate
     * @param unmark whether to only get marked pixels
     * @return a Color or NULL if the pixel was marked
     */
    public Color getPixel(int x, int y, boolean unmark) {
        synchronized(imgSync) {
            if(marks.get(y*width+x)){
                marks.clear(y*width+x);
                return null;         
            }
            return new Color(img.getRGB(x, y));
        }
    }
    
    public boolean getMark(int x, int y, boolean unmark) {
        synchronized(imgSync) {
            if(marks.get(y*width+x)){
                if(unmark)
                    marks.clear(y*width+x);
                return true;
            }
            return false;
        }
    }
    
    public boolean getSecMark(int x, int y, boolean unmark) {
        synchronized(imgSync) {
            if(marksSec.get(y*width+x)){
                if(unmark)
                    marksSec.clear(y*width+x);
                return true;
            }
            return false;
        }
    }
}

package de.tum.ws2010.propra.raytracer.ThreadedCameras;

import java.awt.Color;
import java.awt.image.BufferedImage;

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
	protected BufferedImage img;
	
	/**
	 * Constructor.
	 * 
	 * @param image BufferedImage for which the writes are synchronized.
	 */
	SynchronizedImage(BufferedImage image) {
		img = image;
	}
		
	/**
	 * Synchronized write for a single pixel in the BufferedImage.
	 * 
	 * @param x Horizontal position of the pixel
	 * @param y Vertical position of the pixel
	 * @param c Color for the pixel. 
	 */
	public synchronized void writePixel(int x, int y, Color c) {
		img.setRGB(x, y, c.getRGB());
	}
        
        public synchronized Color getPixel(int x, int y) {
            //TODO synchronize with write
            return new Color(img.getRGB(x, y));
        }
}

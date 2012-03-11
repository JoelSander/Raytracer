package de.tum.ws2010.propra.raytracer;

import java.applet.Applet;
import java.awt.Graphics;

/**
 * Applet for raytracing a scene and showing the resulting image. Naturally, it
 * has to be started as applet in Eclipse.
 * 
 * Notice for running the applet in eclipse:
 * 
 * - For changing the initial applet window size go to Run Menu > Run
 * Configurations...
 * 
 * - In "Parameters" you can change the width and height to whatever you want.
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 */
public class RaytracerApplet extends Applet {

	/**
	 * Field that is needed for all serializable objects like an applet.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Renders the scene and stores the resulting image in buffImg.
	 */
	public void init() {
		Raytracer.render();
	}

	/**
	 * Paints the scene in the applet window.
	 */
	public void paint(Graphics g) {
		g.drawImage(Raytracer.getBuffImg(), 0, 0, this);
	}

}

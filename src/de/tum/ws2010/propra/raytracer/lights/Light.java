package de.tum.ws2010.propra.raytracer.lights;

import java.awt.Color;

import de.tum.ws2010.propra.raytracer.primitives.Intersection;
import de.tum.ws2010.propra.raytracer.render.Scenery;

/**
 * Abstract class for all types of light sources.
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 */
public abstract class Light {

	/**
	 * Constructor for a light source with a given intensity.
	 */
	public Light(double lightIntensity) {
		intensity = lightIntensity;
	}
	
	/**
	 * Illuminates an intersection point by the light source.
	 * 
	 * @param i
	 *            Intersection point to illuminate.
	 * @param s
	 *            Scenery to test if the light source is shadowed.
	 * @return Color as the result of illumination. Black, if light source is
	 *         shadowed.
	 */
	public abstract Color illuminate(Intersection i, Scenery s);

	/**
	 * Intensity of a light source in the range [0, 1]. For the sake of
	 * simplicity, we do not model intensity fall-off with increasing distance
	 * to the light source.
	 */
	protected double intensity;
}

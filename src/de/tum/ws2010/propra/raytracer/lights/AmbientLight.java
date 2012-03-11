package de.tum.ws2010.propra.raytracer.lights;

import java.awt.Color;

import de.tum.ws2010.propra.raytracer.primitives.Intersection;
import de.tum.ws2010.propra.raytracer.render.Scenery;

/**
 * Ambient light source.
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 * 
 */
public class AmbientLight extends Light {

	/**
	 * Constructor setting the intensity of the ambient light source.
	 * 
	 * @param intensity
	 *            Intensity of the ambient light source.
	 */
	public AmbientLight(double intensity) {
		super(intensity);
	}

	@Override
	public Color illuminate(Intersection i, Scenery s) {
		return i.material.shadeAmbient(intensity);
	}
}

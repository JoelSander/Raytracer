package de.tum.ws2010.propra.raytracer.lights;

import de.tum.ws2010.propra.raytracer.primitives.Intersection;
import de.tum.ws2010.propra.raytracer.primitives.Ray;
import de.tum.ws2010.propra.raytracer.primitives.Vector3d;
import de.tum.ws2010.propra.raytracer.render.Scenery;

/**
 * Directional light source.
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 */
public class DirectionalLight extends DirectLight {

	/**
	 * Constructor for a directional light source with a given intensity and
	 * direction
	 * 
	 * @param lightIntensity
	 *            Intensity of the directional light source.
	 * @param lightDir
	 *            Light direction of the light source.
	 */
	public DirectionalLight(double lightIntensity, Vector3d lightDir) {
		super(lightIntensity);
		lightDirection = lightDir.getNormalized();
	}

	@Override
	public Vector3d getLightDirection(Vector3d pos) {
		return lightDirection;
	}

	@Override
	public boolean isShadowed(Vector3d pos, Scenery s) {
		Ray r = getShadowRay(pos);
		Intersection shadowInters = s.getNearestIntersection(r);
		return (shadowInters != null);
	}

	/**
	 * Light direction of the light source
	 */
	protected Vector3d lightDirection;

}

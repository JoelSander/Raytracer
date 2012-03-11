package de.tum.ws2010.propra.raytracer.lights;

import de.tum.ws2010.propra.raytracer.primitives.Intersection;
import de.tum.ws2010.propra.raytracer.primitives.Ray;
import de.tum.ws2010.propra.raytracer.primitives.Vector3d;
import de.tum.ws2010.propra.raytracer.render.Scenery;

/**
 * Point light source.
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 */
public class PointLight extends DirectLight {

	/**
	 * Constructor for a point light source with a given intensity and
	 * position.
	 * 
	 * @param lightIntensity
	 *            Intensity of the point light source.
	 * @param lightPosition
	 *            Position of the point light source.
	 */
	public PointLight(double lightIntensity, Vector3d lightPosition) {
		super(lightIntensity);
		position = lightPosition;
	}
	
	@Override
	public boolean isShadowed(Vector3d pos, Scenery s) {

		Vector3d lightDir = getLightDirection(pos);
		
		if (lightDir == null)
			return true;

		Ray r = getShadowRay(pos);
		Intersection shadowInters = s.getNearestIntersection(r);
		
		double dist = position.minus(pos).getLength();

		// if intersection exists between position and light source
		return (shadowInters != null && shadowInters.getDistance() < dist);
	}
	
	@Override
	public Vector3d getLightDirection(Vector3d pos) {
		Vector3d dir = pos.minus(position);
		dir = dir.getNormalized();
		return dir;
	}
	
	/**
	 * Position of the point light source.
	 */
	protected Vector3d position;
}

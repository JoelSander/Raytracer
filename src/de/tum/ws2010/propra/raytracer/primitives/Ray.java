package de.tum.ws2010.propra.raytracer.primitives;


/**
 * A ray with an origin and a normalized direction.
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 */
public class Ray {

	/**
	 * Origin of the ray.
	 */
	public final Vector3d origin;

	/**
	 * Normalized ray direction.
	 */
	public final Vector3d direction;

	/**
	 * Constructor for a ray with given origin and direction.
	 * 
	 * @param rayOrigin
	 *            Origin of the ray.
	 * @param dir
	 *            Direction of the ray.
	 */
	public Ray(Vector3d rayOrigin, Vector3d dir) {
		origin = rayOrigin;
		direction = dir.getNormalized();
	}
}

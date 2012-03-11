package de.tum.ws2010.propra.raytracer.primitives;


/**
 * Interface for all intersectable objects.
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 */
public interface Intersectable {

	/**
	 * Returns the closest intersection of the ray and the object.
	 * 
	 * @param r
	 *            The ray for which to find an intersection.
	 * @return Intersection of the ray and the object. null if no intersection
	 *         exists.
	 */
	public Intersection intersect(Ray r);

	/**
	 * Gives the minimal and maximum extent of the object in x-, y- and
	 * z-direction. Results is written to the given parameters.
	 * 
	 * @param min
	 *            Vector to write the minimum extent to.
	 * @param max
	 *            Vector to write the maximal extent to.
	 */
	public void getMinMaxXYZ(Vector3d min, Vector3d max);

}

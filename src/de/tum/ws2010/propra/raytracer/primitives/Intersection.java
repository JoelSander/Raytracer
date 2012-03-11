package de.tum.ws2010.propra.raytracer.primitives;

import de.tum.ws2010.propra.raytracer.material.Material;

/**
 * Intersection of a ray with an object.
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 */
public class Intersection {

	/**
	 * Creates an Intersection of a ray and an object, with the corresponding
	 * position, normal and material.
	 * 
	 * @param r
	 *            Ray that was intersected.
	 * @param intersPosition
	 *            Position of the intersection.
	 * @param intersNormal
	 *            Normal at the intersection.
	 * @param m
	 *            Material of the intersected object.
	 */
	public Intersection(Ray r, Vector3d intersPosition, Vector3d intersNormal, Material m) {
		ray = r;
		position = intersPosition;
		normal = intersNormal.getNormalized();
		material = m;
	}

	/**
	 * Copy constructor.
	 * 
	 * @param i
	 *            Intersection to be copied.
	 */
	public Intersection(Intersection i) {
		ray = i.ray;
		position = i.position;
		normal = i.normal;
		material = i.material;
	}

	/**
	 * Distance of the ray's origin to the intersection position.
	 * 
	 * @return Distance of the ray's origin to the intersection position.
	 */
	public double getDistance() {
		if (ray != null && position != null)
			return (ray.origin.minus(position)).getLength();
		else
			return Float.MAX_VALUE;
	}



	/**
	 * Ray, for which an intersection was found.
	 */
	public final Ray ray;

	/**
	 * Intersected Object.
	 */
	public final Vector3d position;

	/**
	 * Normal of the object's surface at the intersection.
	 */
	public final Vector3d normal;

	/**
	 * Material of the intersected object.
	 */
	public final Material material;

}

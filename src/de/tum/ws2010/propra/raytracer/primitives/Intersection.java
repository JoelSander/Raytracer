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
     * The distance to the next edge is not initialized and will be ignored while rendering
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
        this(r,intersPosition,intersNormal,m,Float.NaN);
    }
    
    /**
     * Creates an Intersection of a ray and an object, with the corresponding
     * position, normal and material.
     * 
     * The distance to the next edge is not initialized and will be ignored while rendering
     * 
     * @param r
     *            Ray that was intersected.
     * @param intersPosition
     *            Position of the intersection.
     * @param intersNormal
     *            Normal at the intersection.
     * @param m
     *            Material of the intersected object.
     * @param distanceToEdge 
     *      the distance to the next edge as degrees off ray direction
     */
    public Intersection(Ray r, Vector3d intersPosition, Vector3d intersNormal, Material m,float distanceToEdge) {
        ray = r;
        position = intersPosition;
        if(intersNormal !=null)
            normal = intersNormal.getNormalized();
        else
            normal=intersNormal;
        material = m;        
        this.distanceToEdge = distanceToEdge;
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
        distanceToEdge = i.distanceToEdge;
    }

    /**
     * Distance of the ray's origin to the intersection position.
     * 
     * @return Distance of the ray's origin to the intersection position.
     */
    public double getDistance() {
        if (ray != null && position != null) {
            return (ray.origin.minus(position)).getLength();
        } else {
            return Float.MAX_VALUE;
        }
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
    public final float distanceToEdge;
}

package de.tum.ws2010.propra.raytracer.primitives;

import de.tum.ws2010.propra.raytracer.material.Material;
import de.tum.ws2010.propra.raytracer.render.Scenery;

/**
 * Sphere that can be placed in the scene.
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 */
public class Sphere implements Intersectable {

    /**
     * Constructor for a sphere with a given center, radius and material.
     * 
     * @param center
     *            Center of the sphere.
     * @param radius
     *            Radius of the sphere.
     * @param material
     *            Material of the sphere.
     */
    public Sphere(Vector3d center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        this.material = material;
    }

    @Override
    public Intersection intersect(Ray ray) {
        return intersect(ray, center, radius, material);
    }
    
    
    public static Intersection intersect(Ray ray, Vector3d center, double radius, Material material) {
        double boundingSquare = radius * radius;
        Vector3d center2origin = ray.origin.minus(center);
        double a, b, c;
        a = ray.direction.scalarproduct(ray.direction);
        b = 2 * (center2origin.scalarproduct(ray.direction));
        // * ^= normales Skalarprod
        c = center2origin.scalarproduct(center2origin) - boundingSquare;

        double solutions[] = MathHelper.solveQuadrEq(a, b, c);

        boolean hasIntersect = false;
        double solution = 0;

        for (double s : solutions) {
            if (s > 0) {
                solution = s;
                hasIntersect = true;
                break;
            }
        }

        if (hasIntersect) {
            Vector3d intPos = ray.origin.plus(ray.direction.times(solution));
            
            //get edge distance
            //double d = Math.sqrt( Math.pow(intPos.x-center.x,2)+Math.pow(intPos.y-center.y,2)+Math.pow(intPos.z-center.z,2) );              
            
            Vector3d normal=intPos.minus(center).getNormalized();
            float dist=0;
            if(Scenery.adaptiveAA)
                dist=(float)( Math.toDegrees(normal.angle(ray.direction))-90);
            //System.out.println(dist);
            return new Intersection(ray, intPos, normal, material,dist);
        } else {
            return null;
        }
    }

    @Override
    public void getMinMaxXYZ(Vector3d min, Vector3d max) {
        Vector3d hSize = new Vector3d(radius, radius, radius);
        Vector3d newMin = center.minus(hSize);
        Vector3d newMax = center.plus(hSize);

        min.x = newMin.x;
        min.y = newMin.y;
        min.z = newMin.z;
        max.x = newMax.x;
        max.y = newMax.y;
        max.z = newMax.z;
    }
    /**
     * Center of the sphere.
     */
    protected Vector3d center;
    /**
     * Radius of the sphere.
     */
    protected double radius;
    /**
     * Material of the sphere.
     */
    protected Material material;
}

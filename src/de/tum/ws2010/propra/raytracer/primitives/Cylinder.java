package de.tum.ws2010.propra.raytracer.primitives;

import de.tum.ws2010.propra.raytracer.material.Material;

/**
 * Intersectable Cylinder in y-direction.
 * 
 * @author (c) 2010-11 Roland Fraedrich (fraedrich@tum.de)
 */
public class Cylinder implements Intersectable {

    /**
     * Constructor of a sphere with a given center and radius.
     * 
     * @param top
     *            Top of the sphere.
     * @param height
     *            Height of the sphere.
     * @param bottomRadius
     *            Radius of the bottom cap of the cone.
     */
    public Cylinder(Vector3d top, double height, double bottomRadius, Material m) {
        this.top = top;
        this.height = Math.abs(height);
        this.bottomRadius = Math.abs(bottomRadius);
        this.material = m;
    }


    /* (non-Javadoc)
     * @see de.tum.ws2010.propra.raytracer.primitives.Intersectable#intersect(de.tum.ws2010.propra.raytracer.primitives.Ray)
     */
    @Override
    public Intersection intersect(Ray ray) {

        // calculate r^2/h^2
        double a = ray.direction.x * ray.direction.x
                + ray.direction.z * ray.direction.z;

        double b = 2 * ray.direction.x * (ray.origin.x - top.x)
                + 2 * ray.direction.z * (ray.origin.z - top.z);

        Vector3d ot = ray.origin.minus(top);

        double c = ot.x * ot.x + ot.z * ot.z - bottomRadius * bottomRadius;

        double solutions[] = MathHelper.solveQuadrEq(a, b, c);


        Vector3d normal = null;
        double distance = Double.MAX_VALUE;

        for (double s : solutions) {
            if (s > 0) {
                Vector3d intPos = ray.origin.plus(ray.direction.times(s));

                if (intPos.y > top.y || intPos.y < top.y - height) {
                    continue;
                }

                distance = s;

                normal = intPos.minus(top);
                normal.y = 0;

                // negate normal if ray hits the cone from inside to outside
                if (normal.scalarproduct(ray.direction) > 0) {
                    normal = normal.times(-1);
                }
                break;
            }
        }
        // a ray parallel to the xz-plane can not intersect top or bottom
        if (ray.direction.y != 0) {
            // intersect with ground plane
            double t = (top.y - ray.origin.y) / ray.direction.y;
            // if farer away than shortest distance
            if (t > 0 && t < distance) {
                Vector3d pos = ray.direction.times(t).plus(ray.origin);
                // test if intersection is within cylinder radius
                if (Math.pow(pos.x - top.x, 2) + Math.pow(pos.z - top.z, 2) < bottomRadius * bottomRadius) {
                    distance = t;
                    normal = new Vector3d(0, 1, 0);
                }
            }
            // intersect with top plane
            t = (top.y - height - ray.origin.y) / ray.direction.y;
            // if farer away than shortest distance
            if (t > 0 && t < distance) {
                Vector3d pos = ray.direction.times(t).plus(ray.origin);
                // test if intersection is within cylinder radius
                if (Math.pow(pos.x - top.x, 2) + Math.pow(pos.z - top.z, 2) < bottomRadius * bottomRadius) {
                    distance = t;
                    normal = new Vector3d(0, -1, 0);
                }
            }
        }
        // if no intersection found, return null
        if (normal == null) {
            return null;
        } else {
            Vector3d intPos = ray.origin.plus(ray.direction.times(distance));
            return new Intersection(ray, intPos, normal.getNormalized(), material);
        }
    }

    /* (non-Javadoc)
     * @see de.tum.ws2010.propra.raytracer.primitives.Intersectable#getMinMaxXYZ(de.tum.ws2010.propra.raytracer.primitives.Vector3d, de.tum.ws2010.propra.blatt08.raytracer.primitives.Vector3d)
     */
    public void getMinMaxXYZ(Vector3d min, Vector3d max) {
        min.x = top.x - bottomRadius;
        min.y = top.y - height;
        min.z = top.z - bottomRadius;
        max.x = top.x + bottomRadius;
        max.y = top.y;
        max.z = top.z + bottomRadius;
    }
    /**
     * Top of the cone.
     */
    protected Vector3d top;
    /**
     * Height of the cone.
     */
    protected double height;
    /**
     * Radius of the bottom cap of the cone.
     */
    protected double bottomRadius;
    /**
     * Material of the cone.
     */
    protected Material material;
}
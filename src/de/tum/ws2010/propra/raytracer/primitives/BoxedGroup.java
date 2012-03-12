package de.tum.ws2010.propra.raytracer.primitives;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Group of objects with a bounding box.
 * 
 * @author (c) 2010-11 Roland Fraedrich (fraedrich@tum.de)
 *
 */
public class BoxedGroup extends Box {

    /**
     * Default constructor for an empty group.
     */
    public BoxedGroup() {
        // init bounding box
        super(new Vector3d(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY),
                new Vector3d(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY));
        minBB = new Vector3d(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        maxBB = new Vector3d(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
    }

    /**
     * Adds the given intersectable object to the collection.
     * 
     * @param s
     *            Intersectable object to be added.
     */
    public void add(Intersectable s) {
        objects.add(s);
        Vector3d objMin = new Vector3d(-1, -1, -1);
        Vector3d objMax = new Vector3d(-1, -1, -1);
        s.getMinMaxXYZ(objMin, objMax);

        if (objMin.x == objMax.x) {
            System.out.println("No good...");
        }

        if (minBB.x > objMin.x) {
            minBB.x = objMin.x;
        }
        if (minBB.y > objMin.y) {
            minBB.y = objMin.y;
        }
        if (minBB.z > objMin.z) {
            minBB.z = objMin.z;
        }

        if (maxBB.x < objMax.x) {
            maxBB.x = objMax.x;
        }
        if (maxBB.y < objMax.y) {
            maxBB.y = objMax.y;
        }
        if (maxBB.z < objMax.z) {
            maxBB.z = objMax.z;
        }

        setMinMax(minBB, maxBB);
    }


    /* (non-Javadoc)
     * @see de.tum.ws2010.propra.raytracer.primitives.Box#getMinMaxXYZ(de.tum.ws2010.propra.blatt08.raytracer.primitives.Vector3d, de.tum.ws2010.propra.raytracer.primitives.Vector3d)
     */
    @Override
    public void getMinMaxXYZ(Vector3d min, Vector3d max) {
        min.x = minBB.x;
        min.y = minBB.y;
        min.z = minBB.z;
        max.x = maxBB.x;
        max.y = maxBB.y;
        max.z = maxBB.z;
    }

    /* (non-Javadoc)
     * @see de.tum.ws2010.propra.raytracer.primitives.Box#intersect(de.tum.ws2010.propra.raytracer.primitives.Ray)
     */
    @Override
    public Intersection intersect(Ray r) {
        if (super.intersect(r) == null) {
            return null;
        }

        // for each object in scene
        // -> find nearest intersection
        Intersection nearestInt = null;
        Iterator<Intersectable> objIt = objects.iterator();
        // find first hit
        while (objIt.hasNext() && nearestInt == null) {
            nearestInt = objIt.next().intersect(r);
        }
        // find next hits
        while (objIt.hasNext()) {
            Intersection i = objIt.next().intersect(r); // TODO optimize with early z rejection
            if (i != null && nearestInt.getDistance() > i.getDistance()) {
                nearestInt = i;
            }
        }
        return nearestInt;
    }
    /**
     * Minimum of the bounding box.
     */
    Vector3d minBB;
    /**
     * Maximum of the bounding box.
     */
    Vector3d maxBB;
    /**
     * List of the collected intersectable objects. 
     */
    protected ArrayList<Intersectable> objects = new ArrayList<Intersectable>();
}

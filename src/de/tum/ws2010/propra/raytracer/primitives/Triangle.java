/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tum.ws2010.propra.raytracer.primitives;

import de.tum.ws2010.propra.raytracer.material.Material;

/**
 *
 * @author claus
 */
public class Triangle implements Intersectable {
    
    protected Vector3d[] coords;
    
    /**
     * Material of the cone.
     */
    protected Material material;
    
    public Triangle(Material mat, Vector3d c1, Vector3d c2, Vector3d c3) {
        coords = new Vector3d[3];
        coords[0]=c1;
        coords[1]=c2;
        coords[3]=c3;
        material=mat;
    }
    
    @Override
    public Intersection intersect(Ray r) {
        //check if we intersect
        //get normal vector
        Vector3d a=coords[0];//parameterform
        Vector3d b=coords[1].minus(coords[0]); //richtungsvektoren
        Vector3d c=coords[2].minus(coords[0]);
        Vector3d normal=b.crossproduct(c);
        normal.normalize();
        
        //check if ray intersects with layer
        //TODO intersect layer with ray
        //return an intersection
        return new Intersection(r, null, null, material, 0);
    }

    @Override
    public void getMinMaxXYZ(Vector3d min, Vector3d max) {
        min.x=coords[0].x;min.y=coords[0].y;min.z=coords[0].z;
        max.x=coords[0].x;max.y=coords[0].y;max.z=coords[0].z;
        for(Vector3d v: coords) {
            min.x=Math.min(min.x, v.x);
            min.y=Math.min(min.y, v.y);
            min.z=Math.min(min.z, v.z);
            
            max.x=Math.max(max.x, v.x);
            max.y=Math.max(max.y, v.y);
            max.z=Math.max(max.z, v.z);
        }
    }
    
}

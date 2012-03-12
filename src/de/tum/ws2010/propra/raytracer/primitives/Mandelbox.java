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
public class Mandelbox extends Box {
    
    double scale;
    
    int iterations;
    
    double c;
    
    double invIterations;
    
    Material material;
    
    /**
     * Constructor with minimum and maximum corner of the box.
     * 
     * @param min
     *            Minimum corner of the box (with lowest x, y, z).
     * @param max
     *            Maximum corner of the box (with highest x, y, z).
     */
    public Mandelbox(Vector3d min, Vector3d max, double scale, int iterations, double c, Material m) {
        super(min,max);
        this.scale = scale;
        this.c=c;
        this.iterations=iterations;
        this.invIterations=1d/iterations;
        this.material=m;
    }
    
    
    @Override public Intersection intersect(Ray ray) {
        //if(super.intersect(ray, true)==null)
        //    return null;
         // this is WRONG
        // see https://sites.google.com/site/mandelbox/what-is-a-mandelbox
        Intersection is;
        Vector3d z=new Vector3d(0,0,0);
        for(int i=0;i<iterations;i++) {
            if(z.x>1) z.x = 2-z.x;
            else if (z.x<-1) z.x = -2 -z.x;
            if(z.y>1) z.y = 2-z.y;
            else if (z.y<-1) z.y = -2 -z.y;
            if(z.z>1) z.z = 2-z.z;
            else if (z.z<-1) z.z = -2 -z.z;
            
            double l=z.getLength();
            if(l < 0.5)
                z=z.times(4);
            else if (l < 1) {
                l= l*l;
                z.x=z.x-l;
                z.y=z.y-l;
                z.z=z.z-l;
            }
            z = z.times(scale);
            z.x += c;
            z.y += c;
            z.z += c;
            
            //now check
            is=Sphere.intersect(ray, z,invIterations*10 , material);
            if(is!=null)
                return is;
        }
        
        return null;
    }
    
}

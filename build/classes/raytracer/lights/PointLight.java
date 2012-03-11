/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracer.lights;

import java.awt.Color;
import raytracer.primitives.Intersection;
import raytracer.primitives.Ray;
import raytracer.primitives.Vector3d;
import raytracer.render.Scenery;

/**
 *
 * @author claus
 */
public class PointLight extends Light {
    
    Vector3d position;
    double _90d = Math.toRadians(90);
    
    static final Vector3d V3dNULL=new Vector3d(0, 0,0);

    
    public PointLight(double intensity, Vector3d position) {
        super(intensity);
        this.position=position;
    }
    
    @Override
    public Color illuminate(Intersection i, Scenery s) {
        /*
        // schattencheck
        Vector3d dirToLight = position.minus(i.position);
        Intersection is= s.getNearestIntersection(new Ray(i.position.plus(dirToLight.times(0.01)), dirToLight));
        if(is!=null && is.getDistance() <= dirToLight.getLength()) return Color.BLACK;
        //*/
        
        Vector3d directionToSurface=i.position.minus(position).getNormalized();
        return i.material.shade(directionToSurface,i.normal,i.ray.direction, intensity);

    }
    
}

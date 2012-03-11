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
public class DirectionalLight extends Light {
    
    Vector3d direction;
    double _90d = Math.toRadians(90);
    
    static final Vector3d V3dNULL=new Vector3d(0, 0,0);
    
    public DirectionalLight(double intensity, Vector3d direction){
        super(intensity);
        this.direction=direction.getNormalized();
    }
    
    @Override
    public Color illuminate(Intersection i, Scenery s) {
        //schattencheck
        Vector3d dirToLight = V3dNULL.minus(direction).getNormalized();
        Intersection is= s.getNearestIntersection(new Ray(i.position.plus(dirToLight.times(0.01)), dirToLight.times(1e100)));
        if(is!=null) return Color.BLACK;

        //System.out.println("IS from:"+is.ray.origin+" dir:"+ is.ray.direction+ ""+is.);
        //check if is is from same ray
        //if( is!=null &&is.ray.origin.equals(i.position) && is.ray.direction.getNormalized().equals(V3dNULL.minus(direction).getNormalized())) return Color.BLACK;
        
        
        //System.out.println(winkel+"(rag) = (deg)"+Math.toDegrees(winkel));
        //if( Math.acos(i.normal.scalarproduct(direction)) < _90d){
        //    return Color.BLACK;
        //} else {
            return i.material.shade(direction, i.normal, i.ray.direction, intensity);
            //return i.material.shadeAmbient(1.0);
        //}
        //return null;
    }
    
}

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
public class SpotLight extends Light {
    
    final static double _90d = Math.toRadians(90);
    private Vector3d pos;
    private Vector3d lookAt;
    private double halfAngle;
    
    public SpotLight ( double intensity , Vector3d pos, Vector3d lookAt , double halfAngle ) {
        super(intensity);
        this.pos=pos;
        this.lookAt=lookAt.getNormalized();
        this.halfAngle=halfAngle;
        System.out.println(pos + " -> "+ lookAt);
    }

    @Override
    public Color illuminate(Intersection i, Scenery s) {
        
        Vector3d dirToLight = pos.minus(i.position);
        Intersection is= s.getNearestIntersection(new Ray(i.position.plus(dirToLight.times(0.01)), dirToLight));
        if(is!=null && is.getDistance() <= dirToLight.getLength()) return Color.BLACK;
        
        
        //System.out.println(winkel+"(rag) = (deg)"+Math.toDegrees(winkel));
        Vector3d directionToSurface=i.position.minus(pos).getNormalized();
        
        if( Math.acos( directionToSurface.scalarproduct(lookAt) ) > halfAngle ){//||
          //     ( Math.acos( i.normal.scalarproduct(lookAt)) < _90d &&false ) ){
            return Color.BLACK;
        } else {
            return i.material.shade(lookAt,i.normal.times(-1),i.ray.direction, intensity);
            //return i.material.shadeAmbient(0.5);
        }
        
    }

}

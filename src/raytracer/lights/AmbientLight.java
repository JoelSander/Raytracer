/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package raytracer.lights;

import java.awt.Color;
import raytracer.primitives.Intersection;
import raytracer.render.Scenery;

/**
 *
 * @author claus
 */
public class AmbientLight extends Light {
    
    public AmbientLight (double intensity) {
        super(intensity);
    } 
    
    @Override
    public Color illuminate(Intersection i, Scenery scnr) {
        return i.material.shadeAmbient(intensity);
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.tum.ws2010.propra.raytracer.test;

import de.tum.ws2010.propra.raytracer.primitives.Vector3d;

/**
 *
 * @author claus
 */
public class MathTest {
    public static void main(String[] args) {
        
        testAngle();
        
    }
    
    public static boolean testAngle() {
        
        Vector3d a= new Vector3d(0, 0, 1);
        Vector3d b= new Vector3d(1,0,0);
        Vector3d c=new Vector3d(0,0,-1);
        System.out.println(a.angle(b)+" should be "+Math.toRadians(90));
        System.out.println(a.angle(c)+" should be "+Math.toRadians(180));
        System.out.println(b.angle(c)+" should be "+Math.toRadians(90));
        return false;
    }
    
}

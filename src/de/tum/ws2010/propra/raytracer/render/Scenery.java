package de.tum.ws2010.propra.raytracer.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;

import de.tum.ws2010.propra.raytracer.lights.Light;
import de.tum.ws2010.propra.raytracer.material.Material;
import de.tum.ws2010.propra.raytracer.primitives.BoxedGroup;
import de.tum.ws2010.propra.raytracer.primitives.Intersectable;
import de.tum.ws2010.propra.raytracer.primitives.Intersection;
import de.tum.ws2010.propra.raytracer.primitives.Ray;


/**
 * Scenery for the raytracer, which collects the objects and light sources
 * of the scene.
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 */
public class Scenery {

	/**
	 * Adds the given light source to the scene.
	 * 
	 * @param l
	 *            Light source to be added.
	 */
	public void add(Light l) {
		lights.add(l);
	}

	/**
	 * Adds the given intersectable shadable object to the scene.
	 * 
	 * @param o
	 *            Intersectable object to be added.
	 */
	public void add(Intersectable o) {
		objects.add(o);
	}

	/**
	 * Traces a ray through the scene to determine its color.
	 * 
	 * @param r
	 *            Ray to be traced.
	 * @param maxRecursions
	 *            Number of maximal recursions like reflections and refractions
	 * @return Shaded color determined for the traced ray.
	 */
	public Color traceRay(Ray r, int maxRecursions) {


		Intersection intersect = objects.intersect(r);

		if (intersect == null)
			return background;

		Color c = illuminate(intersect);
		double reflFactor = intersect.material.getReflectivity(intersect);

		if (reflFactor == 0 || maxRecursions == 0)
			return c;
		else {
			Ray rr = intersect.material.getReflectanceVector(intersect);
			Color rc = traceRay(rr, maxRecursions - 1);

			Color c2 = Material.scaleColor(c, 1 - reflFactor);
			Color rc2 = Material.scaleColor(rc, reflFactor);

			return Material.addColors(c2, rc2);
		}
	}		

	/**
	 * Returns the nearest intersection for a ray in the scene.
	 * 
	 * @param r
	 *            Ray for which to find the nearest intersection.
	 * @return Nearest intersection with an object.
	 */
	public Intersection getNearestIntersection(Ray r) {
		return objects.intersect(r);
	}

	/**
	 * Computes the illumination at a given intersection with all light sources.
	 * 
	 * @param i
	 *            Intersection to be illuminated.
	 * @return Color value of the illumination.
	 */
	protected Color illuminate(Intersection i) {
		// for all light sources in scene
		// -> shade for all lights and sum color
		Color c = new Color(0, 0, 0);
		Iterator<Light> lightIt = lights.iterator();

		while (lightIt.hasNext()) {
			Light l = lightIt.next();

			Color col1 = l.illuminate(i, this);
			c = Material.addColors(c, col1);
		}
		return c;
	}

	/**
	 * Sets the background color for the scene
	 * 
	 * @param c New background color of the scene
	 */
	public void setBackgroundColor(Color c) {
		background = c;
	}
	
	/**
	 * Objects in the scene.
	 */
	protected BoxedGroup objects = new BoxedGroup();
	
	/**
	 * List of all light sources of the scene.
	 */
	protected ArrayList<Light> lights = new ArrayList<Light>();

	/**
	 * Background color which is set for a ray having no intersection with the
	 * scene. Default is black.
	 */
	protected Color background = new Color(0, 0, 0);

}

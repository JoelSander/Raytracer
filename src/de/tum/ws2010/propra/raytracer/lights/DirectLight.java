package de.tum.ws2010.propra.raytracer.lights;

import java.awt.Color;

import de.tum.ws2010.propra.raytracer.primitives.Intersection;
import de.tum.ws2010.propra.raytracer.primitives.Ray;
import de.tum.ws2010.propra.raytracer.primitives.Vector3d;
import de.tum.ws2010.propra.raytracer.render.Scenery;

/**
 * Represents all kinds direct light sources, i.e. light sources that have a
 * light direction to each position in space. Accordingly, these light sources
 * can be shadowed by objects in the scenery.
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 * 
 */
public abstract class DirectLight extends Light {

	/**
	 * Constructor for a direct light source with a certain intensity. 
	 */
	public DirectLight(double lightIntensity) {
		super(lightIntensity);
	}
	
	/**
	 * Returns the direction from the light source to a certain position.
	 * 
	 * Has to be implemented for all derived light sources.
	 * 
	 * @param pos
	 *            Postion for which the light direction has to be computed.
	 * @return Light direction, from the light source to the given position.
	 */
	public abstract Vector3d getLightDirection(Vector3d pos);

	/**
	 * Returns the shadow ray from a certain position to the light source.
	 * 
	 * @param pos
	 *            Position for which the shadow ray has to be created.
	 * @return Shadow Ray from the given position to the light source.
	 */
	public Ray getShadowRay(Vector3d pos) {
		Vector3d lightDir = getLightDirection(pos);
		Vector3d invLightDir = lightDir.times(-1);
		Vector3d offset = invLightDir.times(0.00001);
		Vector3d newPos = pos.plus(offset);
		Ray r = new Ray(newPos, invLightDir);
		return r;
	}

	/**
	 * Tests if a point is shadowed by an object of the scenery
	 * 
	 * Has to be implemented for all derived light sources.
	 * 
	 * @param position
	 * 		Position for which the shadow test is performed.
	 * @param scenery
	 * 		Scenery which might occlude the light source.
	 * @return
	 * 		True, if the light source is shadowed by the scenery, false if not.
	 */
	public abstract boolean isShadowed(Vector3d position, Scenery scenery);

	@Override
	public Color illuminate(Intersection i, Scenery s) {
		if (isShadowed(i.position, s))
			return Color.black;

		Vector3d lightDir = getLightDirection(i.position);

		return i.material.shade(lightDir, i.normal, i.ray.direction, intensity);
	}
}

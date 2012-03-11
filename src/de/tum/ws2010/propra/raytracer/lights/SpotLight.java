package de.tum.ws2010.propra.raytracer.lights;

import de.tum.ws2010.propra.raytracer.primitives.Vector3d;
import de.tum.ws2010.propra.raytracer.render.Scenery;

/**
 * Spot light source.
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 */
public class SpotLight extends PointLight {

	/**
	 * Constructor for a spot light source with a given intensity and position,
	 * lookAt and halfAngle.
	 * 
	 * @param intensity
	 *            Intensity of the spot light source.
	 * @param position
	 *            Position of the spot light source.
	 * @param lookAt
	 *            Look at point of the spot light source.
	 * @param halfAngle
	 *            Half angle of the spot light source.
	 */
	public SpotLight(double intensity, Vector3d position, Vector3d lookAt,
			double halfAngle) {
		super(intensity, position);
		dir = lookAt.minus(position).getNormalized();
		cuttOffAngle = halfAngle;
	}

	@Override
	public boolean isShadowed(Vector3d pos, Scenery s) {

		// test cut-off angle
		Vector3d dir2pos = super.getLightDirection(pos);
		double angle2pos = Math.acos(dir.scalarproduct(dir2pos));

		return (Math.abs(angle2pos) > cuttOffAngle || super.isShadowed(pos, s));
	}

	/**
	 * Direction of the spotlight.
	 */
	protected Vector3d dir;

	/**
	 * Cut-off angle of the spotlight.
	 */
	protected double cuttOffAngle;

}

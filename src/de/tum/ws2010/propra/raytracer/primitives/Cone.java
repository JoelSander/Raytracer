package de.tum.ws2010.propra.raytracer.primitives;

import de.tum.ws2010.propra.raytracer.material.Material;

/**
 * Intersectable Cone in y-direction.
 * 
 * @author (c) 2010-11 Roland Fraedrich (fraedrich@tum.de)
 */
public class Cone implements Intersectable {

	/**
	 * Constructor of a sphere with a given center and radius.
	 * 
	 * @param top
	 *            Top of the cone.
	 * @param height
	 *            Height of the cone.
	 * @param bottomRadius
	 *            Radius of the bottom cap of the cone.
	 */
	public Cone(Vector3d top, double height, double bottomRadius, Material m) {
		this.top = top;
		this.height = Math.abs(height);
		this.bottomRadius = Math.abs(bottomRadius);
		this.material = m;
	}

	// 
	/**
	 * Solves a quadratic equation a * x^2 + b * x + c
	 * 
	 * @param a
	 *            Scalar factor of the quadratic term.
	 * @param b
	 *            Scalar factor of the linear term.
	 * @param c
	 *            Constant of the equation.
	 * @return Double vector of size 0, 1 or 2 according to the number of
	 *         solutions. Element 0 of the return vector is always the smallest
	 *         possible solution!
	 */
	double[] SolveQuadrEq(double a, double b, double c) {
		double d = b * b - 4 * a * c;

		if (d < 0) {
			return new double[0]; // lustig, dass das geht :)
		} else if (d == 0) {
			double ret[] = new double[1];
			ret[0] = -b / (2 * a);
			return ret;
		} else {
			double ret[] = new double[2];
			double sqrtD = Math.sqrt(d);
			// first index should be smaller value
			if (a < 0) {
				ret[0] = (-b + sqrtD) / (2 * a);
				ret[1] = (-b - sqrtD) / (2 * a);
			} else {
				ret[1] = (-b + sqrtD) / (2 * a);
				ret[0] = (-b - sqrtD) / (2 * a);
			}
			return ret;
		}
	}

	/* (non-Javadoc)
	 * @see de.tum.ws2010.propra.raytracer.primitives.Intersectable#intersect(de.tum.ws2010.propra.raytracer.primitives.Ray)
	 */
	public Intersection intersect(Ray ray) {
		
		// calculate r^2/h^2
		double rh = bottomRadius/height;
		rh *= rh;
		
		double a = ray.direction.x * ray.direction.x
				 + ray.direction.z * ray.direction.z
				 - ray.direction.y * ray.direction.y * rh;
		
		double b = 2 * ray.direction.x * (ray.origin.x - top.x)
				 + 2 * ray.direction.z * (ray.origin.z - top.z)
				 - 2 * ray.direction.y * (ray.origin.y - top.y) * rh;
		
		Vector3d ot = ray.origin.minus(top);
		double cyh = (height + top.y);
		cyh *= cyh;
		
		double c = ot.x*ot.x + ot.z*ot.z - (ot.y*ot.y) * rh;
		
		double solutions[] = SolveQuadrEq(a, b, c);

		for (double s : solutions) {
			if (s > 0) {
				Vector3d intPos = ray.origin.plus(ray.direction.times(s));
				
				if (intPos.y > top.y || intPos.y < top.y - height)
					continue;

				float normalY = (float) (bottomRadius / Math.sqrt(bottomRadius*bottomRadius + height*height));

				Vector3d normal = intPos.minus(top);
				normal.y = 0;
				normal.normalize();
				normal = normal.times(Math.sqrt(1-normalY*normalY));
				normal.y = normalY;

				// negate normal if ray hits the cone from inside to outside
				if (normal.scalarproduct(ray.direction) > 0)
					normal = normal.times(-1);
				return new Intersection(ray, intPos, normal.getNormalized(), material);
			}
		}
		
		return null;
	}


	/* (non-Javadoc)
	 * @see de.tum.ws2010.propra.raytracer.primitives.Intersectable#getMinMaxXYZ(de.tum.ws2010.propra.blatt08.raytracer.primitives.Vector3d, de.tum.ws2010.propra.raytracer.primitives.Vector3d)
	 */
	public void getMinMaxXYZ(Vector3d min, Vector3d max) {		
		min.x = top.x-bottomRadius;
		min.y = top.y-height;
		min.z = top.z-bottomRadius;
		max.x = top.x+bottomRadius;
		max.y = top.y;
		max.z = top.z+bottomRadius;
	}


	/**
	 * Top of the cone.
	 */
	protected Vector3d top;

	/**
	 * Height of the cone.
	 */
	protected double height;

	/**
	 * Radius of the bottom cap of the cone.
	 */
	protected double bottomRadius;

	/**
	 * Material of the cone.
	 */
	protected Material material;
}
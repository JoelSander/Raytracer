package de.tum.ws2010.propra.raytracer.primitives;

import de.tum.ws2010.propra.raytracer.material.Material;

/**
 * Box object that can be placed in the scene.
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 *
 */
public class Box implements Intersectable {


	/**
	 * Constructor with minimum and maximum corner of the box.
	 * 
	 * @param min
	 *            Minimum corner of the box (with lowest x, y, z).
	 * @param max
	 *            Maximum corner of the box (with highest x, y, z).
	 */
	public Box(Vector3d min, Vector3d max) {
		halfSize = new double[3];
		normals = new Vector3d[3];
		normals[0] = new Vector3d(1, 0, 0);
		normals[1] = new Vector3d(0, 1, 0);
		normals[2] = new Vector3d(0, 0, 1);
		setMinMax(min, max);
	}

	/**
	 * Changes with minimum and maximum corner of the box.
	 * 
	 * @param min
	 *            Minimum corner of the box (with lowest x, y, z).
	 * @param max
	 *            Maximum corner of the box (with highest x, y, z).
	 */
	public void setMinMax(Vector3d min, Vector3d max) {
		center = max.plus(min).times(0.5);
		Vector3d hVector = max.minus(min).times(0.5);
		halfSize[0] = hVector.getX();
		halfSize[1] = hVector.getY();
		halfSize[2] = hVector.getZ();
	}
	
	
	/* (non-Javadoc)
	 * @see de.tum.ws2010.propra.raytracer.primitives.Intersectable#intersect(de.tum.ws2010.propra.raytracer.primitives.Ray)
	 */
	public Intersection intersect(Ray ray) {
		double tmin = -Double.MAX_VALUE;
		double tmax = Double.MAX_VALUE;

		int idmin = 0;
		int idmax = 0;

		Vector3d p = center.minus(ray.origin);

		for (int i = 0; i < 3; i++) {
			double e = normals[i].scalarproduct(p);
			double f = normals[i].scalarproduct(ray.direction);

			int id1 = i * 2;
			int id2 = i * 2 + 1;

			if (Math.abs(f) > 0.0000000001) {
				double t1 = (e + halfSize[i]) / f;
				double t2 = (e - halfSize[i]) / f;

				if (t1 > t2) {
					double temp = t1;
					t1 = t2;
					t2 = temp;
					int tmp = id1;
					id1 = id2;
					id2 = tmp;
				}

				if (tmin < t1) {
					tmin = t1;
					idmin = id1;
				}
				if (tmax > t2) {
					tmax = t2;
					idmax = id2;
					if (tmax < 0)
						return null;
				}
				if (tmin > tmax)
					return null;
			} else if (-e - halfSize[i] > 0 || -e + halfSize[i] < 0)
				return null;
		}
		double t;
		Vector3d normal;
		if (tmin > 0) {
			t = tmin;// box in front of camera
			if (idmin % 2 == 0)
				normal = normals[idmin / 2];
			else
				normal = normals[idmin / 2].times(-1);
		} else {
			t = tmax;// camera inside of box
			if (idmax % 2 == 0)
				normal = normals[idmax / 2];
			else
				normal = normals[idmax / 2].times(-1);
		}
		Vector3d intPos = ray.origin.plus(ray.direction.times(t));
		
		// determine material
		Material mat;		
		if (normal.getX() == -1)
			mat = materialLeft;
		else if  (normal.getX() == 1)
			mat = materialRight;
		else if (normal.getY() == -1)
			mat = materialBottom;
		else if  (normal.getY() == 1)
			mat = materialTop;
		else if (normal.getZ() == -1)
			mat = materialBack;
		else //if  (normal.y() == 1)
			mat = materialFront;
		
		return new Intersection(ray, intPos, normal, mat);

	}

	/**
	 * Center of the box.
	 */
	protected Vector3d center;

	/**
	 * Precomputed normals of the three directions (x, y and z).
	 */
	protected Vector3d normals[];

	/**
	 * Half size of the box of the three directions (x, y and z).
	 */
	protected double halfSize[];

	/* (non-Javadoc)
	 * @see de.tum.ws2010.propra.raytracer.primitives.Intersectable#getMinMaxXYZ(de.tum.ws2010.propra.raytracer.primitives.Vector3d, de.tum.ws2010.propra.blatt08.raytracer.primitives.Vector3d)
	 */
	public void getMinMaxXYZ(Vector3d min, Vector3d max) {
		Vector3d hSize = new Vector3d(halfSize);
		Vector3d newMin = center.minus(hSize);
		Vector3d newMax = center.plus(hSize);
		
		min.x = newMin.x;
		min.y = newMin.y;
		min.z = newMin.z;
		max.x = newMax.x;
		max.y = newMax.y;
		max.z = newMax.z;
	}
	
	
	/**
	 * Different sides of the box.
	 */
	public enum Side {
	    TOP, BOTTOM, LEFT, RIGHT, FRONT, BACK, ALL; 
	}

	/**
	 * Constructor with minimum, maximum and material.
	 * @param min Vector of lower x, y and z corner of the box.
	 * @param max Vector of higher x, y and z corner of the box.
	 * @param material Material for all sides of the box.
	 */
	public Box(Vector3d min, Vector3d max, Material material) {
		halfSize = new double[3];
		normals = new Vector3d[3];
		normals[0] = new Vector3d(1, 0, 0);
		normals[1] = new Vector3d(0, 1, 0);
		normals[2] = new Vector3d(0, 0, 1);
		setMinMax(min, max);
		setMaterial(Side.ALL, material);
	}
	
	/**
	 * Sets the material for a single side of the box.
	 * @param bSide Side of which the material is changed. 
	 * @param material New material of the side.
	 */
	public void setMaterial(Side bSide, Material material) {
		switch(bSide) {
			case TOP:
				materialTop = material;
				return;
			case BOTTOM:
				materialBottom = material;
				return;
			case LEFT:
				materialLeft = material;
				return;
			case RIGHT:
				materialRight = material;
				return;
			case FRONT:
				materialFront = material;
				return;
			case BACK:
				materialBack = material;
				return;
			case ALL:
				materialTop = material;
				materialBottom = material;
				materialLeft = material;
				materialRight = material;
				materialFront = material;
				materialBack = material;
				return;
		}
	}
	
	/**
	 * Material of the top (y) of the box.
	 */
	protected Material materialTop;
	/**
	 * Material of the bottom (-y) of the box.
	 */
	protected Material materialBottom;
	/**
	 * Material of the left side (-x) of the box.
	 */
	protected Material materialLeft;
	/**
	 * Material of the right side (+x) of the box.
	 */
	protected Material materialRight;
	/**
	 * Material of the front (+z) of the box.
	 */
	protected Material materialFront;
	/**
	 * Material of the back (-z) of the box.
	 */
	protected Material materialBack;
	
}

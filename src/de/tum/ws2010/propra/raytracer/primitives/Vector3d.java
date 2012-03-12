package de.tum.ws2010.propra.raytracer.primitives;

/**
 * Represents a 3-dimensional vector of doubles.
 * 
 * @author (c) 2009-11 Hans-Georg Menz (menz@tum.de) and Roland Fraedrich (fraedrich@tum.de)
 * 
 */
public class Vector3d {

	/**
	 * Single coordinates of the 3D vector
	 */
	public double x, y, z;

	/**
	 * Default Constructor initializing the vector as (0,0,0).
	 */
	public Vector3d() {
		super();
		setZero();
	}

	/**
	 * Contstructor that initializes the vector according to the given 3d-Array.
	 * If doubleArray3d is not of length 3, the vector is set to (0, 0, 0).
	 * 
	 * @param doubleArray3d
	 *            Double-Array of length 3 expected, which is used to set x, y
	 *            and z.
	 */
	public Vector3d(double[] doubleArray3d) {
		super();
		if (doubleArray3d.length != 3)
			setZero();
		else
			set(doubleArray3d[0], doubleArray3d[1], doubleArray3d[2]);
	}

	/**
	 * Constructor that initializes the vector with the given components.
	 * 
	 * @param x
	 *            X component of the vector.
	 * @param y
	 *            Y component of the vector.
	 * @param z
	 *            Z component of the vector.
	 */
	public Vector3d(double x, double y, double z) {
		super();
		set(x, y, z);
	}

	/**
	 * Sets the vector to (0,0,0).
	 */
	public void setZero() {
		set(0.0, 0.0, 0.0);
	}

	/**
	 * Sets the vector according to the given components.
	 * 
	 * @param x
	 *            X component of the vector.
	 * @param y
	 *            Y component of the vector.
	 * @param z
	 *            Z component of the vector.
	 */
	public void set(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Returns the x component of the vector.
	 * 
	 * @return X component of the vector.
	 */
	public double getX() {
		return x;
	}

	/**
	 * Sets the x component of the vector.
	 * 
	 * @param x
	 *            X component of the vector.
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Returns the y component of the vector.
	 * 
	 * @return Y component of the vector.
	 */
	public double getY() {
		return y;
	}

	/**
	 * Sets the y component of the vector.
	 * 
	 * @param y
	 *            Y component of the vector.
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Returns the z component of the vector.
	 * 
	 * @return Z component of the vector.
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Sets the z component of the vector.
	 * 
	 * @param z
	 *            Z component of the vector.
	 */
	public void setZ(double z) {
		this.z = z;
	}

	public double scalarproduct(Vector3d v) {
		return x * v.x + y * v.y + z * v.z;
	}

	/**
	 * Calculates the cross product of this vector and v and returns the result
	 * as a new vector. This and v remain unchanged.
	 * 
	 * @param v
	 *            Second operand of the cross product.
	 * @return Result of the cross product as a new vector.
	 */
	public Vector3d crossproduct(Vector3d v) {
		return new Vector3d((y * v.z) - (z * v.y), (z * v.x) - (x * v.z),
				(x * v.y) - (y * v.x));
	}

	/**
	 * Returns the length of the vector.
	 * 
	 * @return Length of the vector.
	 */
	public double getLength() {
		return Math.sqrt(scalarproduct(this));
	}

	/**
	 * Componentwise addition of this and v. This and v remain unchanged.
	 * 
	 * @param v
	 *            Second operand of the addition.
	 * @return Result of the addition as a new vector.
	 */
	public Vector3d plus(Vector3d v) {
		return new Vector3d(x + v.x, y + v.y, z + v.z);
	}

	/**
	 * Componentwise subtraction of v from this. This and v remain unchanged.
	 * 
	 * @param v
	 *            Second operand of the subtraction.
	 * @return Result of the subtraction as a new vector.
	 */
	public Vector3d minus(Vector3d v) {
		return new Vector3d(x - v.x, y - v.y, z - v.z);
	}

	/**
	 * Returns a new scaled vector. The vector itself remains unchanged.
	 * 
	 * @param d
	 *            Scale factor.
	 * @return New vector with the direction of this, but scaled with d.
	 */
	public Vector3d times(double d) {
		return new Vector3d(x * d, y * d, z * d);
	}

	/**
	 * Returns a new normalized version of the vector. The vector itself remains
	 * unchanged.
	 * 
	 * @return New vector with the same direction as this, but with length 1.
	 */
	public Vector3d getNormalized() {
		double l = getLength();
		return new Vector3d(x / l, y / l, z / l);
	}

	/**
	 * Normalizes this vector to the length of 1.
	 */
	public void normalize() {
		double l = getLength();
		set(x / l, y / l, z / l);
	}
        
        /**
         * computes the angle between this and another vector
         */
        public double angle(Vector3d other) {
            
            return Math.acos( scalarproduct(other)/(getLength()*other.getLength()) );
        }
        
	/**
	 * Returns the values of a vector like "(1,0,0)"
	 */
	public String toString() {
		return "(" + Double.toString(x) + "," + Double.toString(y) + ","
				+ Double.toString(z) + ")";
	}

	/**
	 * Checks whether this and v have the same x, y and z.
	 * 
	 * @param v
	 *            Vector to compare this with.
	 * @return True if the vectors are equal, false if not.
	 */
	public boolean equals(Vector3d v) {
		double eps = 0.0000001;
		Vector3d diff = this.minus(v);

		return (Math.abs(diff.x) < eps && Math.abs(diff.y) < eps && Math
				.abs(diff.z) < eps);
	}

}

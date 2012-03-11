package de.tum.ws2010.propra.raytracer.material;

import java.awt.Color;

import de.tum.ws2010.propra.raytracer.primitives.Intersection;
import de.tum.ws2010.propra.raytracer.primitives.Ray;
import de.tum.ws2010.propra.raytracer.primitives.Vector3d;

/**
 * Simple Material class with diffuse (Lambertian) and specular (Blinn-Phong)
 * reflection.
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 * 
 */
public class Material {

	/**
	 * Computes the sum of two colors.
	 * 
	 * @param color1
	 *            First color.
	 * @param color2
	 *            Second color.
	 * @return Summed color.
	 */
	public static Color addColors(Color color1, Color color2) {
		int red = color1.getRed() + color2.getRed();
		int green = color1.getGreen() + color2.getGreen();
		int blue = color1.getBlue() + color2.getBlue();

		if (red > 255)
			red = 255;
		if (green > 255)
			green = 255;
		if (blue > 255)
			blue = 255;

		return new Color(red, green, blue);
	}

	/**
	 * Scales a color by a given factor.
	 * 
	 * @param color
	 *            Color to be scaled.
	 * @param factor
	 *            Scaling factor for the color that must >= 0.
	 * @return Scaled color.
	 */
	public static Color scaleColor(Color color, double factor) {
		int red = (int) Math.round(color.getRed() * factor);
		int green = (int) Math.round(color.getGreen() * factor);
		int blue = (int) Math.round(color.getBlue() * factor);

		if (red > 255)
			red = 255;
		if (green > 255)
			green = 255;
		if (blue > 255)
			blue = 255;

		return new Color(red, green, blue);
	}

	/**
	 * Constructor for a diffuse material.
	 * 
	 * @param color
	 *            Diffuse color of the material.
	 */
	public Material(Color color) {
		this.color = color;
		this.shininess = 0;
		this.reflectivity = 0;
	}

	/**
	 * Constructor for a specular material.
	 * 
	 * @param c
	 *            Diffuse color of the material.
	 * @param specularShininess
	 *            Shininess factor for the specular highlight, according to the
	 *            Blinn�Phong shading model.
	 */
	public Material(Color c, double specularShininess) {
		color = c;
		shininess = specularShininess;
		reflectivity = 0;
	}

	/**
	 * Constructor for a specular and reflecting material.
	 * 
	 * @param c
	 *            Diffuse color of the material.
	 * @param specularShininess
	 *            Shininess factor for the specular highlight, according to the
	 *            Blinn�Phong shading model.
	 * @param degreeOfReflectivity
	 *            degree of reflectivity in the range of [0, 1], where 1 is a
	 *            perfect mirror.
	 */
	public Material(Color c, double specularShininess,
			double degreeOfReflectivity) {
		color = c;
		shininess = specularShininess;

		if (degreeOfReflectivity < 0 || degreeOfReflectivity > 1) {
			System.out.println("Warning: reflectivity of Material ("
					+ degreeOfReflectivity
					+ ") will be clamped to range [0, 1];");
			reflectivity = Math.min(Math.max(degreeOfReflectivity, 0), 1);
		} else {
			reflectivity = degreeOfReflectivity;
		}
	}

	/**
	 * Applies ambient shading with the given light source intensity.
	 * 
	 * @param intensity
	 *            Intensity of the ambient light source.
	 * @return Color that is the result of the illumination.
	 */
	public Color shadeAmbient(double intensity) {
		return Material.scaleColor(color, intensity);
	}

	/**
	 * Shades the material according to the given light direction, surface
	 * normal, view direction and light source intensity.
	 * 
	 * @param lightDir
	 *            Light direction to the position to be shaded.
	 * @param normal
	 *            Surface normal at the position to be shaded.
	 * @param viewDir
	 *            View direction to the position to be shaded.
	 * @param intensity
	 *            Intensity of the light for shading the material.
	 * @return Shaded color.
	 */
	public Color shade(Vector3d lightDir, Vector3d normal, Vector3d viewDir,
			double intensity) {

		Vector3d n = normal;
		if (n.scalarproduct(viewDir) > 0)
			n = n.times(-1);

		// diffuse illumination
		double factorDiff = n.scalarproduct(lightDir) * -1;
		if (factorDiff < 0)
			return new Color(0, 0, 0);
		Color c = Material.scaleColor(color, factorDiff * intensity);

		if (shininess <= 0) {
			return c;
		} else {
			// specular illumination
			Vector3d halfway = lightDir.plus(viewDir).getNormalized().times(-1);
			double factorSpec = Math.pow(halfway.scalarproduct(n), shininess);

			return Material.addColors(c, Material.scaleColor(Color.white,
					intensity * factorSpec));
		}
	}

	/**
	 * Computes the reflectance vector of the intersecting ray at the point of
	 * intersection.
	 * 
	 * @return Reflected vector of the ray at the point of intersection.
	 */
	public Ray getReflectanceVector(Intersection i) {
		Vector3d invRayDir = i.ray.direction.times(-1);
		// TODO Auto-generated method stub
		Vector3d reflectDir = i.normal.times(i.normal.scalarproduct(invRayDir))
				.times(2).minus(invRayDir);
		reflectDir = reflectDir.getNormalized();

		Vector3d offsetPos = i.position.plus(reflectDir.times(0.000001));

		return new Ray(offsetPos, reflectDir);
	}

	/**
	 * Computes the reflectivity at the given intersection.
	 * 
	 * In this simple Material, the reflectivity is independent of the viewing
	 * direction.
	 * 
	 * @return Reflectivity at the given intersection.
	 */
	public double getReflectivity(Intersection i) {
		return reflectivity;
	}

	/**
	 * Diffuse material color.
	 */
	private final Color color;

	/**
	 * Shininess factor for the specular highlight, according to the Blinn�Phong
	 * shading model.
	 */
	private final double shininess;

	/**
	 * Degree of reflectivity in the range of [0, 1], where 1 is a perfect
	 * mirror.
	 */
	private final double reflectivity;
}
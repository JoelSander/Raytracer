package raytracer.scenes;

import java.awt.Color;

import raytracer.lights.AmbientLight;
import raytracer.lights.PointLight;

import raytracer.material.Material;
import raytracer.primitives.Box;
import raytracer.primitives.BoxedGroup;
import raytracer.primitives.Vector3d;
import raytracer.render.Camera;
import raytracer.render.Scene;
import raytracer.render.Scenery;

/**
 * Scene of a stairway. Only boxes are used as geometric objects.
 *
 * @author (c) 2009-11 Roland Fraedrich
 *
 */
public class StairwayScene extends Scene {

	private double depth;
	private double height;

	public StairwayScene() {
		scenery = new Scenery();

		// create Light sources
		AmbientLight al = new AmbientLight(0.1);
		// DirectionalLight dl = new DirectionalLight(0.6, new
		// Vector3d(-1,-1,-1));

		scenery.add(al);
		// s.add(dl);

		Material white = new Material(new Color(255, 255, 255));

		double tiefe = 2.9;
		double breite = 7;
		double hoehe = 1.7;
		double overlap = 0.8; // 0.35;

		Vector3d stepSize = new Vector3d(breite, hoehe, tiefe + overlap);
		Vector3d stepOffset = new Vector3d(0, hoehe, tiefe);
		Vector3d baseSize = new Vector3d(2 * breite, hoehe, 2 * breite);
		Vector3d baseOffset = new Vector3d(2 * breite - overlap, hoehe,
				2 * breite);

		double x = 0;
		double y = 0;
		double z = 0;

		// ! basement
		scenery.add(new Box(new Vector3d(0, 0, 0), new Vector3d(50.4, hoehe, 50.4),
				white));

		z += 2 * breite - overlap;
		y += hoehe;

		for (int t = 0; t < 12; t++) {

			BoxedGroup stairs = new BoxedGroup();

			for (int i = 0; i < 8; i++) {
				Vector3d min = new Vector3d(x, y, z);
				Vector3d max = min.plus(stepSize);

				scenery.add(new Box(min, max, white));
				z += stepOffset.getZ();
				y += stepOffset.getY();
				x += stepOffset.getX();
			}

			Vector3d baseMin = new Vector3d(x, y, z);
			Vector3d baseCtr = baseSize.times(0.5).plus(
					new Vector3d(x, y + 10.5, z));
			Vector3d baseMax = baseSize.plus(new Vector3d(x, y, z));

			scenery.add(new Box(baseMin, baseMax, white));
			scenery.add(stairs);
			scenery.add(new PointLight(0.1, baseCtr));

			x += baseOffset.getX();
			y += baseOffset.getY();
			z += baseOffset.getZ();

			Vector3d newStepSize = new Vector3d(stepSize.getZ(), stepSize
					.getY(), -stepSize.getX());
			Vector3d newStepOffset = new Vector3d(stepOffset.getZ(), stepOffset
					.getY(), -stepOffset.getX());
			Vector3d newBaseSize = new Vector3d(baseSize.getZ(), baseSize
					.getY(), -baseSize.getX());
			Vector3d newBaseOffset = new Vector3d(baseOffset.getZ(), baseOffset
					.getY(), -baseOffset.getX());

			stepSize = newStepSize;
			stepOffset = newStepOffset;
			baseSize = newBaseSize;
			baseOffset = newBaseOffset;

		}

		scenery.add(new Box(new Vector3d(-1, 0, 0), new Vector3d(0, y + 20, 50.4),
				white));
		scenery.add(new Box(new Vector3d(0, 0, -1), new Vector3d(50.4, y + 20, 0),
				white));
		scenery.add(new Box(new Vector3d(50.4, 0, 0),
				new Vector3d(51.4, y + 20, 50.4), white));
		scenery.add(new Box(new Vector3d(0, 0, 50.4),
				new Vector3d(50.4, y + 20, 51.4), white));

		depth  = tiefe;
		height = y;
}

	@Override
	public void setCamera(Camera cam) {
		Vector3d camPos = new Vector3d(8 * depth, 40 + height, 5 * depth);
		Vector3d lookAt = new Vector3d(8 * depth, 0, 10 * depth);
		Vector3d upDir = new Vector3d(-1, 0, 1);

		cam.setPositionAndLookAt(camPos, lookAt, upDir);
	}
}
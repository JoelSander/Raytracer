package raytracer.scenes;

import java.awt.Color;

import raytracer.lights.AmbientLight;
import raytracer.lights.PointLight;

import raytracer.material.Material;
import raytracer.primitives.Box;
import raytracer.primitives.Vector3d;
import raytracer.render.Camera;
import raytracer.render.Scene;
import raytracer.render.Scenery;

/**
 * Scene of a 3D tetris state of play. Only boxes are used as geometric objects.
 *
 * @author (c) 2009-11 Roland Fraedrich
 *
 */
public class TetrisScene extends Scene {

	/**
	 * Constructor generating the scenery and defining a camera position.
	 */
	public TetrisScene() {
		scenery = new Scenery();

		// create Light sources
		scenery.add(new AmbientLight(0.05));
		scenery.add(new PointLight(0.8, new Vector3d(3, 44, 3)));

		scenery.add(new PointLight(0.1, new Vector3d(0.1, 3.1, 3)));
		scenery.add(new PointLight(0.1, new Vector3d(5.9, 3.1, 3)));
		scenery.add(new PointLight(0.1, new Vector3d(3, 3.1, 5.9)));
		scenery.add(new PointLight(0.1, new Vector3d(3, 3.1, 0.1)));

		double refl = 0.0;

		Material darkGrey = new Material(new Color(255, 255, 255), 100, 0.2);
		Material grey = new Material(new Color(200, 200, 200), refl*50, refl);
		Material blue = new Material(new Color(0, 0, 255), refl*50, refl);
		Material red = new Material(new Color(255, 0, 0), refl*50, refl);
		Material green = new Material(new Color(0, 255, 0), refl*50, refl);
		Material yellow = new Material(new Color(255, 255, 0), refl*50, refl);
		Material magenta = new Material(new Color(255, 0, 255), refl*50, refl);
		Material cyan = new Material(new Color(0, 255, 255), refl*50, refl);

		scenery.add(new Box(new Vector3d(0, 0, 0), new Vector3d(6, 0, 6), grey));
		scenery
				.add(new Box(new Vector3d(0, 0, -1), new Vector3d(6, 14, 0),
						darkGrey));
		scenery
				.add(new Box(new Vector3d(-1, 0, 0), new Vector3d(0, 14, 6),
						darkGrey));
		scenery.add(new Box(new Vector3d(0, 0, 6), new Vector3d(6, 14, 7), darkGrey));
		scenery.add(new Box(new Vector3d(6, 0, 0), new Vector3d(7, 14, 6), darkGrey));

		// L
		scenery.add(new Box(new Vector3d(0, 0, 0), new Vector3d(1, 1, 2), blue));
		scenery.add(new Box(new Vector3d(1, 0, 0), new Vector3d(3, 1, 1), blue));

		// Z
		scenery.add(new Box(new Vector3d(3, 0, 0), new Vector3d(4, 1, 2), red));
		scenery.add(new Box(new Vector3d(2, 0, 1), new Vector3d(3, 1, 3), red));

		// S
		scenery.add(new Box(new Vector3d(4, 0, 0), new Vector3d(5, 1, 2), green));
		scenery.add(new Box(new Vector3d(5, 0, 1), new Vector3d(6, 1, 3), green));

		// T
		scenery.add(new Box(new Vector3d(5, 0, 0), new Vector3d(6, 3, 1), magenta));
		scenery.add(new Box(new Vector3d(4, 1, 0), new Vector3d(5, 2, 1), magenta));

		// T
		scenery.add(new Box(new Vector3d(4, 0, 2), new Vector3d(5, 1, 3), magenta));
		scenery.add(new Box(new Vector3d(3, 0, 3), new Vector3d(6, 1, 4), magenta));

		// 0
		scenery.add(new Box(new Vector3d(4, 0, 4), new Vector3d(6, 2, 6), yellow));

		// L
		scenery.add(new Box(new Vector3d(3, 0, 5), new Vector3d(4, 2, 6), blue));
		scenery.add(new Box(new Vector3d(3, 2, 5), new Vector3d(5, 3, 6), blue));

		// I
		scenery.add(new Box(new Vector3d(0, 0, 5), new Vector3d(1, 4, 6), cyan));

		// S
		scenery.add(new Box(new Vector3d(0, 0, 3), new Vector3d(1, 1, 5), green));
		scenery.add(new Box(new Vector3d(1, 0, 4), new Vector3d(2, 1, 6), green));

		// Z
		scenery.add(new Box(new Vector3d(1, 8, 3), new Vector3d(3, 9, 4), red));
		scenery.add(new Box(new Vector3d(2, 8, 4), new Vector3d(4, 9, 5), red));
	}

	@Override
	public void setCamera(Camera cam) {
		Vector3d camPos = new Vector3d(5, 13, 1);
		Vector3d lookAt = new Vector3d(1.5, 0, 4.5);
		Vector3d upDir = new Vector3d(-1, 0, 1);

		cam.setPositionAndLookAt(camPos, lookAt, upDir);
	}
}

package de.tum.ws2010.propra.raytracer.scenes;

import java.awt.Color;

import de.tum.ws2010.propra.raytracer.lights.AmbientLight;
import de.tum.ws2010.propra.raytracer.lights.DirectionalLight;
import de.tum.ws2010.propra.raytracer.lights.PointLight;
import de.tum.ws2010.propra.raytracer.material.Material;
import de.tum.ws2010.propra.raytracer.primitives.Box;
import de.tum.ws2010.propra.raytracer.primitives.BoxedGroup;
import de.tum.ws2010.propra.raytracer.primitives.Mandelbox;
import de.tum.ws2010.propra.raytracer.primitives.Sphere;
import de.tum.ws2010.propra.raytracer.primitives.Vector3d;
import de.tum.ws2010.propra.raytracer.render.Camera;
import de.tum.ws2010.propra.raytracer.render.Scene;
import de.tum.ws2010.propra.raytracer.render.Scenery;

/**
 * Reflecting spheres on a checkerboard.
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 */
public class CheckerboardScene extends Scene {

	/**
	 * Constructor generating the scenery and defining a camera position.
	 */
	public CheckerboardScene() {
		scenery = new Scenery(5,2,2);

		// create Light sources
		AmbientLight al = new AmbientLight(0.1);
		DirectionalLight dl = new DirectionalLight(0.6, new Vector3d(-1, -1, 0));
		PointLight pl = new PointLight(0.8, new Vector3d(1, 4, -1));

		scenery.add(al);
		scenery.add(dl);
		scenery.add(pl);

		Material white = new Material(new Color(255, 255, 255), 150, 0.3);
                Material mbmat = new Material(new Color(255, 255, 255), 150);
		Material dark = new Material(new Color(25, 25, 25), 150, 0.3);
		Material yellowRefl = new Material(new Color(255, 255, 0), 300, 0.6);
		Material blueRefl = new Material(new Color(0, 0, 255), 300, 0.4);

		for (int x = 0; x < 10; x++) {
			BoxedGroup line = new BoxedGroup();
			for (int z = 0; z < 10; z++) {
				Vector3d min = new Vector3d(x, -0.2, z);
				Vector3d max = new Vector3d(x + 1, 0, z + 1);

				Material mat;
				if ((x + z) % 2 == 0) {
					mat = white;
				} else {
					mat = dark;
				}
				line.add(new Box(min, max, mat));

			}
			scenery.add(line);
		}

		scenery
				.add(new Sphere(new Vector3d(2.33, 0.75, 4.75), 0.75,
						yellowRefl));
		scenery.add(new Sphere(new Vector3d(3.23, 0.75, 2.88), 0.75, blueRefl));
                
                //scenery.add(new Mandelbox(new Vector3d(3, 0, 2),  new Vector3d(20, 20, 40), 3, 500, 0.5, mbmat));
                
		// Sphere sph2 = new Sphere(new Vector3d(-0.75,-1.25,-3.5), 0.75f,
		// greenRefl);

	}

	@Override
	public void setCamera(Camera cam) {
		Vector3d camPos = new Vector3d(-1, 6, -10);
		Vector3d lookAt = new Vector3d(3.5, 0, 3);
		Vector3d upDir = new Vector3d(0, 1, 0);

		cam.setPositionAndLookAt(camPos, lookAt, upDir);
	}
}

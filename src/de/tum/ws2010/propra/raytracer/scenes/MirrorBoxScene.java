package de.tum.ws2010.propra.raytracer.scenes;

import java.awt.Color;

import de.tum.ws2010.propra.raytracer.lights.PointLight;
import de.tum.ws2010.propra.raytracer.lights.SpotLight;
import de.tum.ws2010.propra.raytracer.material.Material;
import de.tum.ws2010.propra.raytracer.primitives.Box;
import de.tum.ws2010.propra.raytracer.primitives.Sphere;
import de.tum.ws2010.propra.raytracer.primitives.Vector3d;
import de.tum.ws2010.propra.raytracer.render.Camera;
import de.tum.ws2010.propra.raytracer.render.Scene;
import de.tum.ws2010.propra.raytracer.render.Scenery;

/**
 * Cornell like box scene with a mirroring box. 
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 *
 */
public class MirrorBoxScene extends Scene {

	/**
	 * Constructor generating the scenery and defining a camera position. 
	 */
	public MirrorBoxScene() {
		scenery = new Scenery(5,2,2);

		// create Light sources
		PointLight pl = new PointLight(0.4, new Vector3d(0, 1.5, -3));
		SpotLight sl = new SpotLight(0.4, new Vector3d(-1.8, 1.8, 0),
				new Vector3d(0.5, -2, -5), Math.PI / 10);

		Material whiteRefl = new Material(new Color(255, 255, 255), 400, 0.8);
		Material redRefl = new Material(new Color(255, 0, 0), 200, 0.3);
		Material greenRefl = new Material(new Color(0, 255, 0), 300, 0.5);
		Material blueRefl = new Material(new Color(0, 0, 255), 100, 0.15);

		// add light sources to scene
		// s.add(dl);
		scenery.add(pl);
		scenery.add(sl);

		// create objects in scene
		Sphere sph1 = new Sphere(new Vector3d(0.75, -1, -4.75), 1.0f, redRefl);
		Sphere sph2 = new Sphere(new Vector3d(-0.75, -1.25, -3.5), 0.75f,
				greenRefl);
		Sphere sph3 = new Sphere(new Vector3d(0.5, -1.6, -3.5), 0.4, blueRefl);
		// Box box1 = new Box(new Vector3d(1.25,-2,-4), new
		// Vector3d(1.75,-1.5,-3.5), blue);
		Box box2 = new Box(new Vector3d(-2, -2, -6), new Vector3d(2, 2, 0.1),
				whiteRefl);

		// add objects to scene
		scenery.add(sph1);
		scenery.add(sph2);
		scenery.add(sph3);
		scenery.add(box2);
	}

	@Override
	public void setCamera(Camera cam) {
		Vector3d camPos = new Vector3d(0, 0, 0);
		Vector3d lookAt = new Vector3d(0, 0, -1);
		Vector3d upDir = new Vector3d(0, 1, 0);

		cam.setPositionAndLookAt(camPos, lookAt, upDir);
	}
	
}

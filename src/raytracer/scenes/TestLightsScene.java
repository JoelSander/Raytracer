package raytracer.scenes;

import java.awt.Color;

import raytracer.lights.AmbientLight;
import raytracer.lights.DirectionalLight;
import raytracer.lights.PointLight;
import raytracer.lights.SpotLight;

import raytracer.lights.Light;
import raytracer.material.Material;
import raytracer.primitives.Sphere;
import raytracer.primitives.Vector3d;
import raytracer.render.Camera;
import raytracer.render.Scene;
import raytracer.render.Scenery;

/**
 * Scene to test the light sources.
 *
 * @author (c) 2009-11 Roland Fraedrich
 */

public class TestLightsScene extends Scene {

	public TestLightsScene() {
		scenery = new Scenery();

		// create Light sources
		Light light;
		//light = new AmbientLight(0.5);
		//light = new DirectionalLight(0.7, new Vector3d(-1, -1, -1));
		//light = new PointLight(1.0, new Vector3d(1.2, 1.2, 1.2));
		light = new SpotLight(1.0, new Vector3d(1.2, 1.2, 1.2), new Vector3d(-1, -1, -1), Math.PI / 7);

		// add light source to scenery
		scenery.add(light);
                
                //light = new AmbientLight(0.2);
                //scenery.add(light);
                
		// create sphere to be illuminated and add it to the scenery
		Material yellow = new Material(new Color(255, 255, 0));
		Sphere sph1 = new Sphere(new Vector3d(0, 0, 0), 1.0f, yellow);
		scenery.add(sph1);

		// add small sphere to indicate light position
		if (light instanceof PointLight || light instanceof SpotLight) {
			Material black = new Material(new Color(0, 0, 0));
			Sphere sph2 = new Sphere(new Vector3d(1.25, 1.25, 1.25), 0.02f, black);
			scenery.add(sph2);
		}

		// set background color to white
		scenery.setBackgroundColor(new Color(255,255,255));

	}

	@Override
	public void setCamera(Camera cam) {
		Vector3d camPos = new Vector3d(0, 0, 4);
		Vector3d lookAt = new Vector3d(0, 0, 0);
		Vector3d upDir = new Vector3d(0, 1, 0);

		cam.setPositionAndLookAt(camPos, lookAt, upDir);
	}

}

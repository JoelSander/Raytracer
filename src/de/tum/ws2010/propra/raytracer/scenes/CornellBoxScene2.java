package de.tum.ws2010.propra.raytracer.scenes;

import de.tum.ws2010.propra.raytracer.lights.*;
import de.tum.ws2010.propra.raytracer.primitives.*;
import de.tum.ws2010.propra.raytracer.render.*;
import de.tum.ws2010.propra.raytracer.material.*;
import java.awt.Color;


/**
 * Cornell like box scene.
 *
 * @author (c) 2009-11 Roland Fraedrich
 */
public class CornellBoxScene2 extends Scene {

	/**
	 * Constructor generating the scenery and defining a camera position.
	 */
	public CornellBoxScene2() {

		scenery = new Scenery(5,3,4);

		// create Light sources
		AmbientLight al = new AmbientLight(0.1);
		PointLight pl = new PointLight(0.7, new Vector3d(0, 1.5, -3));
		SpotLight sl = new SpotLight(0.3, new Vector3d(-1.8, 1.8, 0),
				new Vector3d(-0.3, -0.7, -5.5), Math.PI / 10);
                DirectionalLight dl = new DirectionalLight(0.3, new Vector3d(1, -2, -2.5));
                
                Material white = new Material(new Color(255, 255, 255));
		Material red = new Material(new Color(255, 0, 0));
		Material blue = new Material(new Color(0, 0, 255));

                Material glass = new Material(new Color(255, 255, 255), 0, 0.1, 0.8);
		Material greenRefl = new Material(new Color(0, 255, 0), 300, 0.3);
		Material yellowRefl = new Material(new Color(255, 255, 0), 600, 0.6);
		Material cyanRefl = new Material(new Color(0, 255, 255), 100, 0.1);

		// add light sources to scene
		//scenery.add(dl);
		scenery.add(al);
		scenery.add(pl);
		scenery.add(sl);

		// create objects in scene
		Sphere sph1 = new Sphere(new Vector3d(0.75, -1, -4.75), 1.0f, cyanRefl);
		Sphere sph2 = new Sphere(new Vector3d(-0.75, -1.25, -3.5), 0.75f,
				greenRefl);
		Sphere sph3 = new Sphere(new Vector3d(0.5, -1.6, -3.5), 0.4, glass);
		// Box box1 = new Box(new Vector3d(1.25,-2,-4), new
		// Vector3d(1.75,-1.5,-3.5), blue);
		Box box2 = new Box(new Vector3d(-2, -2, -6), new Vector3d(2, 2, 0.1),
				white);
		box2.setMaterial(Box.Side.LEFT, red);
		box2.setMaterial(Box.Side.RIGHT, blue);

                
                
		// add objects to scene
		scenery.add(sph1);
		scenery.add(sph2);
		scenery.add(sph3);
		scenery.add(box2);
	}

	@Override
	public void setCamera(Camera cam) {
		Vector3d camPos = new Vector3d(0, -0.7, -0.3);
		Vector3d lookAt = new Vector3d(0, -0.7, -6);
		Vector3d upDir = new Vector3d(0, 1, 0);

		cam.setPositionAndLookAt(camPos, lookAt, upDir);
	}

}

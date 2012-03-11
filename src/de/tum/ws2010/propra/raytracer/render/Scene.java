package de.tum.ws2010.propra.raytracer.render;


/**
 * Scene that is represents by the scenery to be rendered and
 * the camera to render it.
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 * 
 */
public abstract class Scene {

	/**
	 * Scenery to be rendered.
	 */
	protected Scenery scenery = null;
		
	/**
	 * Returns the scenery to be rendered.
	 * 
	 * @return Scenery to be rendered.
	 */
	public Scenery getScenery() {
		return scenery;
	}
	
	/**
	 * Sets the camera position and viewing direction to render the scenery.
	 * 
	 * @param cam Camera of which the position and viewing directions is changed.
	 */
	public abstract void setCamera(Camera cam);

}

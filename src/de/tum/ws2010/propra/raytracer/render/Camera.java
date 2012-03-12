package de.tum.ws2010.propra.raytracer.render;

import java.awt.Color;
import java.awt.image.BufferedImage;

import de.tum.ws2010.propra.raytracer.primitives.Intersection;
import de.tum.ws2010.propra.raytracer.primitives.Ray;
import de.tum.ws2010.propra.raytracer.primitives.Vector3d;

/**
 * Camera for the raytracer.
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 */
public class Camera {

	/**
	 * Constructor with position, look-at, up-vector and field of view.
	 * 
	 * @param position
	 *            Origin of the camera.
	 * @param lookAt
	 *            Look at direction.
	 * @param up
	 *            Up direction.
	 * @param fieldOfViewY
	 *            Vertical field of view.
	 */
	public Camera(Vector3d position, Vector3d lookAt, Vector3d up,
			double fieldOfViewY) {
		setPositionAndLookAt(position, lookAt, up);
		fovy = fieldOfViewY;
	}

	/**
	 * Constructor with field of view only. Initial position is (0,0,0),
	 * up-vector is (0,1,0) and look-at is (0,0,-1).
	 * 
	 * @param fieldOfViewY
	 *            Vertical field of view.
	 */
	public Camera(double fieldOfViewY) {
		setPositionAndViewingDirection(new Vector3d(0, 0, 0), new Vector3d(0,
				0, -1), new Vector3d(0, 1, 0));
		fovy = fieldOfViewY;
	}

	/**
	 * Copy constructor.
	 * 
	 * @param cam
	 *            Camera to be copied
	 */
	public Camera(Camera cam) {
		pos = cam.pos;
		viewDir = cam.viewDir;
		rightDir = cam.rightDir;
		upDir = cam.upDir;
		fovy = cam.fovy;
		resX = cam.resX;
		resY = cam.resY;
		viewPlaneHeight = cam.viewPlaneHeight;
		viewPlaneWidth = cam.viewPlaneWidth;
	}

	/**
	 * Sets the position, viewing direction and up vector of the camera.
	 * 
	 * @param position
	 *            Position of the camera.
	 * @param viewingDir
	 *            Viewing direction of the camera.
	 * @param up
	 *            Up vector of the camera. Has not to be orthogonal to the
	 *            viewing direction, but must not be parallel to it.
	 */
	public void setPositionAndViewingDirection(Vector3d position,
			Vector3d viewingDir, Vector3d up) {
		pos = position;
		viewDir = viewingDir;
		rightDir = viewDir.crossproduct(up).getNormalized();
		upDir = rightDir.crossproduct(viewDir).getNormalized();
	}

	/**
	 * Sets the parameters of the camera based on the position, a look-at point
	 * and the up-vector.
	 * 
	 * @param position
	 *            Position of the camera.
	 * @param lookAt
	 *            Look at point of the camera.
	 * @param up
	 *            Up vector of the camera. Has not to be orthogonal to the
	 *            viewing direction, but must not be parallel to it.
	 */
	public void setPositionAndLookAt(Vector3d position, Vector3d lookAt,
			Vector3d up) {
		setPositionAndViewingDirection(position, lookAt.minus(position)
				.getNormalized(), up);
	}

	/**
	 * Initializes the image settings based on the properties of the given
	 * BufferedImage.
	 * 
	 * @param img
	 *            BufferedImage that implicitly defines the resolution of the
	 *            camera.
	 */
	protected void initImageSettings(BufferedImage img) {
		resX = img.getWidth(null);
		resY = img.getHeight(null);

		// compute height and width for a viewPlane at distance 1.0f
		viewPlaneHeight = 2.0f * Math.tan(fovy / 2.0);
		viewPlaneWidth = (viewPlaneHeight * resX) / resY;
	}

	/**
	 * Creates a single ray for a given pixel.
	 * 
	 * @param pixX
	 * @param pixY
	 * @return Ray from the camera's origin through the given pixel.
	 */
	public Ray createRay(int pixX, int pixY) {

		// compute pixel offset along right and up direction
		double offsetX = viewPlaneWidth
				* (pixX / (double) resX - 0.5f + 0.5f / resX);
		double offsetY = -viewPlaneHeight
				* (pixY / (double) resY - 0.5f + 0.5f / resY);

		Vector3d viewPlaneOffX = rightDir.times(offsetX);
		Vector3d viewPlaneOffY = upDir.times(offsetY);

		Vector3d dir = viewDir.plus(viewPlaneOffX).plus(viewPlaneOffY);

		return new Ray(pos, dir);
	}
        
        public Ray createRay(double pixX, double pixY) {
            //TODO
        }

	/**
	 * Renders an image of a scene via raytracing.
	 * 
	 * @param s
	 *            Scene to be rendered.
	 * @param img
	 *            BufferedImage to store the resulting image.
         * 
	 */
	public void renderImage(Scenery s, BufferedImage img) {
                System.out.println("Warning: using singlethread-renderImage");
		initImageSettings(img);

		String progress = " Raytracing of image in Progress: ";
		int progressNum = 100;
		int firstMinus = (progressNum - progress.length()) / 2;
		int lastMinus = progressNum - progress.length() - firstMinus;

		for (int i = 0; i < firstMinus; i++)
			progress = "-" + progress;
		for (int i = 0; i < lastMinus; i++)
			progress = progress + "-";

		System.out.println(progress);

		int totalPixel = resX * resY;
		int numPixel = 0;

		// for all pixels
		for (int x = 0; x < resX; x++)
			for (int y = 0; y < resY; y++) {
				// create ray
				Ray r = createRay(x, y);

				// determine Color along ray
				Color c = s.traceRay(r, 3);

				// set pixel
				img.setRGB(x, y, c.getRGB());

				numPixel++;

				if (numPixel % (totalPixel / progressNum) == 0)
					System.out.print("#");
			}

		System.out.println();
	}

	/**
	 * Renders an image by setting each pixel value to the color of the first
	 * intersected object.
	 * 
	 * @param s
	 *            Scene to be rendered.
	 * @param img
	 *            BufferedImage to store the resulting image.
	 */
	public void renderColorOf1stHit(Scenery s, BufferedImage img) {

		System.out.print("Rendering of color of first hit in progress... ");

		initImageSettings(img);

		// for all pixels
		for (int x = 0; x < resX; x++)
			for (int y = 0; y < resY; y++) {
				// create ray
				Ray r = createRay(x, y);

				// determine color at nearest Intersection
				Intersection i = s.getNearestIntersection(r);

				Color c = new Color(0, 0, 0);
				if (i != null)
					c = i.material.shadeAmbient(1.0);

				// set pixel
				img.setRGB(x, y, c.getRGB());
			}
		System.out.println("done!");
	}

	/**
	 * Renders an image by setting each to a grey value representing the
	 * distance of the first intersection to the camera. The grey values are
	 * scaled such that the nearest intersection point of the scene is white and
	 * the most distance one is black.
	 * 
	 * @param s
	 *            Scene to be rendered.
	 * @param img
	 *            BufferedImage to store the resulting image.
	 */
	public void renderDepth(Scenery s, BufferedImage img) {

		System.out.print("Rendering of depth image in progress... ");

		initImageSettings(img);

		double distImg[][] = new double[resY][resX];

		double minDist = Double.MAX_VALUE;
		double maxDist = 0;

		// for all pixels
		for (int x = 0; x < resX; x++)
			for (int y = 0; y < resY; y++) {
				// create ray
				Ray r = createRay(x, y);

				// determine Color along ray
				Intersection i = s.getNearestIntersection(r);
				if (i == null)
					distImg[y][x] = Double.MAX_VALUE;
				else {
					distImg[y][x] = i.getDistance();
					if (minDist > distImg[y][x])
						minDist = distImg[y][x];
					if (maxDist < distImg[y][x])
						maxDist = distImg[y][x];
				}
			}

		double distRange = 1.2 * (maxDist - minDist);

		// for all pixels
		for (int x = 0; x < resX; x++)
			for (int y = 0; y < resY; y++) {
				Color depthCol = new Color(0, 0, 0);

				if (distImg[y][x] < Double.MAX_VALUE) {
					int depthVal = (int) (255 * (1 - (distImg[y][x] - minDist)
							/ distRange));
					depthCol = new Color(depthVal, depthVal, depthVal);
				}
				img.setRGB(x, y, depthCol.getRGB());
			}

		System.out.println("done!");
	}

	/**
	 * Renders an image by setting the color value of each pixel according to
	 * the normal at the first intersection. Here, red, green and blue represent
	 * the normal's absolute value in x, y and z direction respectively.
	 * 
	 * @param s
	 *            Scene to be rendered.
	 * @param img
	 *            BufferedImage to store the resulting image.
	 */
	public void renderNormals(Scenery s, BufferedImage img) {

		System.out.print("Rendering of normals in progress... ");

		initImageSettings(img);

		// for all pixels
		for (int x = 0; x < resX; x++)
			for (int y = 0; y < resY; y++) {
				// create ray
				Ray r = createRay(x, y);

				Intersection i = s.getNearestIntersection(r);

				Color normalColor = new Color(0, 0, 0);

				if (i != null) {
					Vector3d norm = i.normal;
					int colR = (int) (Math.abs(norm.getX()) * 255);
					int colG = (int) (Math.abs(norm.getY()) * 255);
					int colB = (int) (Math.abs(norm.getZ()) * 255);
					normalColor = new Color(colR, colG, colB);
				}
				img.setRGB(x, y, normalColor.getRGB());
			}
		System.out.println("done!");
	}

	/**
	 * Position of the camera.
	 */
	protected Vector3d pos;
	/**
	 * Viewing direction of the camera.
	 */
	protected Vector3d viewDir;
	/**
	 * Up-vector of the camera (orthogonal to the viewing direction).
	 */
	protected Vector3d upDir;
	/**
	 * Right-vector of the camera (orthogonal to upDir and viewDir).
	 */
	protected Vector3d rightDir;
	/**
	 * Vertical field of view.
	 */
	protected double fovy;
	/**
	 * Horizontal resolution of the image to be rendered.
	 */
	protected int resX;
	/**
	 * Vertical resolution of the image to be rendered.
	 */
	protected int resY;
	/**
	 * Width of the virtual view plane.
	 */
	protected double viewPlaneWidth;
	/**
	 * Height of the virtual view plane.
	 */
	protected double viewPlaneHeight;
}

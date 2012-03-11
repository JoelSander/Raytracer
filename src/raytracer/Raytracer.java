package raytracer;


import java.awt.Color;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import raytracer.scenes.*;
import raytracer.render.Camera;
import raytracer.render.Scene;
import raytracer.render.Scenery;


/**
 * Raytracer, in which camera and scenery are set up and where the actual
 * rendering is called.
 *
 * @author (c) 2009-11 Roland Fraedrich
 */
public class Raytracer {
        
        final static int resX=800;
        final static int resY=600;
        
        // WILL produce bugs if not square, e.g. x^2
        final static int antiAliasing=1;
        static int sqaa=(int)Math.pow(antiAliasing,2);
        
	/**
	 * Object to store the rendered image.
	 */
	private static BufferedImage buffImg = new BufferedImage(resX,resY,
			BufferedImage.TYPE_INT_RGB);
        
	/**
	 * Returns the image that will be rendered.
	 *
	 * @return Image that will be rendered.
	 */
	public static BufferedImage getBuffImg() {
		return buffImg;
	}

	/**
	 * Renders the scene and stores the resulting image in buffImg.
	 */
	public static void render() {

		// memorize time when starting rendering
		long startTime = System.currentTimeMillis();

		// Declaration of Camera. Position is set later on by the scene.
		Camera c = new Camera(Math.PI / 2.5);

		// Get or create a scene.
		// Remove comment of the scene you want to render.
		Scene s = null;
		//s = new TestLightsScene();
		// s = new CheckerboardScene();
		// s = new CornellBoxScene();
		// s = new MirrorBoxScene();
		 s = new TetrisScene();
		// s = new StairwayScene();
		// s = new ChristmasScene();
                 System.out.println(s.getClass().toString());
		// Get scenery of the selected scene.
		Scenery scenery = s.getScenery();
		// Change camera according to the selected scene.
		s.setCamera(c);
                
                BufferedImage beforeAAImg=new BufferedImage(resX*antiAliasing, resY*antiAliasing, BufferedImage.TYPE_INT_RGB);
                
		// Render the scene in one of the render modes
		// c.renderColorOf1stHit(scenery, buffImg);
		// c.renderDepth(scenery, buffImg);
		// c.renderNormals(scenery, buffImg);
		c.renderImage(scenery, beforeAAImg);
                
                // Antialiasing // TODO
		buffImg=doAA(beforeAAImg);
                
                
                // Get time when rendering is finished
		long endTime = System.currentTimeMillis();

		// Print out needed time
		System.out.println("Rendering took " + (endTime - startTime) / 1000.0f
				+ " s");
	}
        
    private static BufferedImage doAA(BufferedImage beforeAAImg) {
        Color tmp=null;
        int r,g,b;
        
        
        for(int iy=0;iy<resY;iy++) {
            for(int ix=0;ix<resX;ix++){
                r=0;g=0;b=0;
                for(int iax=0;iax<antiAliasing;iax++){
                    for(int iay=0;iay<antiAliasing;iay++){
                        tmp=new Color(beforeAAImg.getRGB(ix*antiAliasing+iax, iy*antiAliasing+iay));
                        r+=tmp.getRed(); g+=tmp.getGreen(); b+=tmp.getBlue();
                    }
                }
                r/=sqaa; g/=sqaa; b/=sqaa;
                buffImg.setRGB(ix, iy, new Color(r,g,b).getRGB());
                
            }
        }
        
        return buffImg;
    }
}

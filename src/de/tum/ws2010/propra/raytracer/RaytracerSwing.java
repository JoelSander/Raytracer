package de.tum.ws2010.propra.raytracer;

import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * Swing GUI for raytracing a scene and showing the resulting image.
 * 
 * @author (c) 2009-11 Roland Fraedrich (fraedrich@tum.de)
 */
class RaytracerSwing extends JFrame {

	/**
	 * Constructor.
	 */
	public RaytracerSwing() {
		super("Raytracer");
		Raytracer.render();
	}

	/**
	 * Renders the image and shows it afterwards in a window.
	 * 
	 * @param args No arguments expected.
	 */
	public static void main(String args[]) {
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception evt) {
		}

		// create frame and add listener
		RaytracerSwing frame = new RaytracerSwing();
		frame.setResizable(false);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		// resize frame to the correct size and make the window visible 
		frame.setSize(Raytracer.getBuffImg().getWidth(), Raytracer.getBuffImg()
				.getHeight());
		frame.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see java.awt.Container#paint(java.awt.Graphics)
	 */
	public void paint(Graphics g) {
		g.drawImage(Raytracer.getBuffImg(), 0, 0, this);
	}

	/**
	 * Field that is needed to make the class serializable.
	 */
	private static final long serialVersionUID = -4395108284480194303L;

}
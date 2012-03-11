package raytracer.scenes;

import java.awt.Color;

import raytracer.lights.AmbientLight;
import raytracer.lights.DirectionalLight;
import raytracer.lights.PointLight;
import raytracer.lights.SpotLight;

import raytracer.material.Material;
import raytracer.primitives.Box;
import raytracer.primitives.BoxedGroup;
import raytracer.primitives.Cone;
import raytracer.primitives.Cylinder;
import raytracer.primitives.Sphere;
import raytracer.primitives.Vector3d;
import raytracer.render.Camera;
import raytracer.render.Scene;
import raytracer.render.Scenery;

/**
 * A nice Christmas scene. Funny what you can do by only using spheres, boxes, cylinders and cones.
 *
 * @author (c) 2010-11 Roland Fraedrich
 *
 */
public class ChristmasScene extends Scene {

	Material snowMat = new Material(Color.WHITE, 20);
	Material redRefl = new Material(new Color(255, 0, 0), 200, 0.2);

	/**
	 * Constructor generating the scenery and defining a camera position.
	 */
	public ChristmasScene() {
		scenery = new Scenery();
		scenery.setBackgroundColor(new Color(0, 0, 100));

		// create Light sources
		AmbientLight al = new AmbientLight(0.01);
		DirectionalLight dl = new DirectionalLight(0.5, new Vector3d(-1, -0.8, -1));
		SpotLight  sl  = new SpotLight(0.6, new Vector3d(150, 245, 150), new Vector3d(150, 100, 150), Math.PI/2.8);
		PointLight pl  = new PointLight(0.4, new Vector3d(250, 180, 205));

		scenery.add(al);
		scenery.add(dl);
		scenery.add(sl);
		scenery.add(pl);

		Material ceilingMat = new Material(Color.WHITE);
		Material wallMat    = new Material(new Color(200,200,200));

		Material floorMat = new Material(new Color(20,20,20), 100, 0.05);
		Material greyMat  = new Material(Color.darkGray);

		// floor:
		BoxedGroup floor = new BoxedGroup();
		Vector3d min = new Vector3d( -0.6, -0.2,   -0.6);
		Vector3d max = new Vector3d(450.6, -0.02, 450.6);
		scenery.add(new Box(min, max, greyMat));

		for (int x = 0; x < 15; x++) {
			BoxedGroup line = new BoxedGroup();
			for (int z = 0; z < 15; z++) {
				min = new Vector3d(    x*30+0.3, -0.2,     z*30+0.3);
				max = new Vector3d((x+1)*30-0.3,  0,   (z+1)*30-0.3);

				line.add(new Box(min, max, floorMat));
			}
			floor.add(line);
		}
		scenery.add(floor);

		// ceiling
		min = new Vector3d( -0.6,  250,  -0.6);
		max = new Vector3d(450.6,  252, 450.6);
		scenery.add(new Box(min, max, ceilingMat));

		// walls
		min = new Vector3d( -10.0, -0.6,  -0.6);
		max = new Vector3d(  -0.6,  250, 450.6);
		scenery.add(new Box(min, max, wallMat));
		// walls
		min = new Vector3d( 450.6, -0.6,  -0.6);
		max = new Vector3d( 460.0,  250, 450.6);
		scenery.add(new Box(min, max, wallMat));
		// walls
		min = new Vector3d( -0.6,  -0.6,  450.6);
		max = new Vector3d( 450.6,  250,  460.0);
		scenery.add(new Box(min, max, wallMat));
		// walls

		BoxedGroup wall = new BoxedGroup();
		min = new Vector3d(  -0.6, -0.6,  -10.0);
		max = new Vector3d( 150.6,  250,  -0.6);
		wall.add(new Box(min, max, wallMat));
		min = new Vector3d( 150.6, -0.6,  -10.0);
		max = new Vector3d( 250.6,   80,  -0.6);
		wall.add(new Box(min, max, wallMat));
		min = new Vector3d( 150.6,  210,  -10.0);
		max = new Vector3d( 250.6,  250,  -0.6);
		wall.add(new Box(min, max, wallMat));
		min = new Vector3d( 250.6, -0.6,  -10.0);
		max = new Vector3d( 460.6,  250,  -0.6);
		wall.add(new Box(min, max, wallMat));
		scenery.add(wall);
		// window
		Material windowMat = new Material(new Color(130, 50, 10));
		Material marble    = new Material(new Color(230, 228, 216), 80, 0.2);
		BoxedGroup window = new BoxedGroup();
		min = new Vector3d( 146.6,  76  ,  -10.0);
		max = new Vector3d( 254.6,  80.5,   10);
		window.add(new Box(min, max, marble));
		min = new Vector3d( 146.6,  209  ,  -10.0);
		max = new Vector3d( 254.6,  214,      3);
		window.add(new Box(min, max, windowMat));
		min = new Vector3d( 146.6,  76,  -10.0);
		max = new Vector3d( 151.6,  214,   3);
		window.add(new Box(min, max, windowMat));
		min = new Vector3d( 249.6,  76,  -10.0);
		max = new Vector3d( 254.6,  214,   3);
		window.add(new Box(min, max, windowMat));
		// window continued...
		min = new Vector3d( 146.6,  80.5  ,  -6);
		max = new Vector3d( 254.6,  86.5,    -4);
		window.add(new Box(min, max, windowMat));
		min = new Vector3d( 146.6,  203  ,  -6);
		max = new Vector3d( 254.6,  209,    -4);
		window.add(new Box(min, max, windowMat));
		min = new Vector3d( 151.6,  76,  -6);
		max = new Vector3d( 157.6,  214, -4);
		window.add(new Box(min, max, windowMat));
		min = new Vector3d( 243.6,  76,  -6);
		max = new Vector3d( 249.6,  214, -4);
		window.add(new Box(min, max, windowMat));
		min = new Vector3d( 198,  76,  -6);
		max = new Vector3d( 202,  214, -4);
		window.add(new Box(min, max, windowMat));
		min = new Vector3d( 146.6, 143  ,  -6);
		max = new Vector3d( 254.6, 147,    -4);
		window.add(new Box(min, max, windowMat));

		wall.add(window);

		//ground
		scenery.add(new Box(new Vector3d(-80000, -2,   -80000),
							new Vector3d( 80000, -0.5,  80000),
							snowMat));

		scenery.add(createTree(new Vector3d(100, 0, 100), 210, 60, true));

		scenery.add(createSnowman(new Vector3d( 110, -5, -280), 2.1));

		BoxedGroup forrest = new BoxedGroup();
		for(int i = 0; i < 50; i++) {
			double xPos = Math.random()*1000-700;
			double zPos = -Math.random()*1000-700;
			double height = Math.random()*300+150;
			double radius = height/5;
			forrest.add(createTree(new Vector3d(xPos, 0, zPos), height, radius, false));
		}
		scenery.add(forrest);


		// presents
		Material darkBlue = new Material(new Color(0,0,150), 6);
		Material darkRed  = new Material(new Color(150,0,0), 5);
		Material red      = new Material(new Color(255,0,0), 7);
		Material white    = new Material(new Color(200,200,200), 7);
		Material cyan     = new Material(new Color(0,120,120), 8);
		Material silver   = new Material(new Color(240,240,240), 40);
		Material green    = new Material(new Color(0,180,0), 8);
		Material yellowRefl = new Material(new Color(220,220,0), 9);
		Material pink     = new Material(new Color(200,0, 200), 15);
		Material pinkRefl = new Material(new Color(160,0,160), 20);

		scenery.add(createPresent(new Vector3d(150,0,15), new Vector3d(230, 20, 70), 7, green, yellowRefl));

		scenery.add(createPresent(new Vector3d(160,20,20), new Vector3d(190, 39, 50), 5, darkBlue, pink));
		scenery.add(createPresent(new Vector3d(159.7,36,19.7), new Vector3d(190.3, 40, 50.3), 5, darkBlue, pink));

		scenery.add(createPresent(new Vector3d(196,20,27), new Vector3d(213, 31, 63), 4, cyan, silver));
		scenery.add(createPresent(new Vector3d(195.8,29,26.8), new Vector3d(213.2, 32, 63.2), 4, cyan, silver));

		for (double d = 41.6; d < 48; d+= 1.6) {
			scenery.add(new Cylinder(new Vector3d(173, d, 32), 0.8, 11, red));
			scenery.add(new Cylinder(new Vector3d(173, d+0.8, 32), 0.8, 11, white));
		}
		scenery.add(new Cylinder(new Vector3d(173, 48.5, 32), 2, 11.3, darkRed));

		scenery.add(createLocomotive(new Vector3d(155, 22, 57)));

		scenery.add(new Sphere(new Vector3d(2.33, 0.75, 4.75), 0.75, yellowRefl));
		scenery.add(new Sphere(new Vector3d(3.23, 0.75, 2.88), 0.75, pinkRefl));
		// Sphere sph2 = new Sphere(new Vector3d(-0.75,-1.25,-3.5), 0.75f,
		// greenRefl);

	}

	private BoxedGroup createLocomotive(Vector3d pos) { //
		BoxedGroup locomotive = new BoxedGroup();

		Material black   = new Material(new Color(10,10,10), 20);
		Material red     = new Material(new Color(255,0,0), 20);
		Material blue    = new Material(new Color(0,0,255), 20);
		Material yellow  = new Material(new Color(255,255,0), 20);

		locomotive.add(new Box(pos.plus(new Vector3d(0, 0, 0)), pos.plus(new Vector3d(20, 0.5, 7)), yellow));

		locomotive.add(new Box(pos.plus(new Vector3d(0, 0, 0.5)), pos.plus(new Vector3d(6.5, 4, 1.5)), blue));
		locomotive.add(new Box(pos.plus(new Vector3d(0, 0, 5.5)), pos.plus(new Vector3d(6.5, 4, 6.5)), blue));
		locomotive.add(new Box(pos.plus(new Vector3d(5.5, 0, 0.5)), pos.plus(new Vector3d(5.5, 5.5, 6.5)), blue));
		locomotive.add(new Box(pos.plus(new Vector3d(-0.5, 8, -0.5)), pos.plus(new Vector3d(7, 9, 7.5)), blue));
		locomotive.add(new Cylinder(pos.plus(new Vector3d(6,   9, 6)), 7, 0.5, blue));
		locomotive.add(new Cylinder(pos.plus(new Vector3d(0.5, 9, 6)), 7, 0.5, blue));
		locomotive.add(new Cylinder(pos.plus(new Vector3d(6,   9, 1)), 7, 0.5, blue));
		locomotive.add(new Cylinder(pos.plus(new Vector3d(0.5, 9, 1)), 7, 0.5, blue));

		locomotive.add(new Box(pos.plus(new Vector3d(5.5, 0, 1)), pos.plus(new Vector3d(19, 5, 6)), red));
		locomotive.add(new Sphere(pos.plus(new Vector3d(17.5, 2.5, 3.5)), 2.5, red));

		locomotive.add(new Cylinder(pos.plus(new Vector3d(16, 10, 3.5)), 1.8, 1.8, black));
		locomotive.add(new Cylinder(pos.plus(new Vector3d(16, 10, 3.5)), 7, 1.2, black));
		locomotive.add(new Cylinder(pos.plus(new Vector3d(11,  7, 3.5)), 3, 1.2, black));

		locomotive.add(new Sphere(pos.plus(new Vector3d(16.5, -0.5, 7)), 1.5, black));
		locomotive.add(new Sphere(pos.plus(new Vector3d(12,   -0.5, 7)), 1.5, black));
		locomotive.add(new Sphere(pos.plus(new Vector3d( 7.5, -0.5, 7)), 1.5, black));
		locomotive.add(new Sphere(pos.plus(new Vector3d( 3,   -0.5, 7)), 1.5, black));
		locomotive.add(new Sphere(pos.plus(new Vector3d(16.5, -0.5, 0)), 1.5, black));
		locomotive.add(new Sphere(pos.plus(new Vector3d(12,   -0.5, 0)), 1.5, black));
		locomotive.add(new Sphere(pos.plus(new Vector3d( 7.5, -0.5, 0)), 1.5, black));
		locomotive.add(new Sphere(pos.plus(new Vector3d( 3,   -0.5, 0)), 1.5, black));

		return locomotive;
	}

	public BoxedGroup createTree(Vector3d pos, double height, double width, boolean withBallsAndCandles) {
		Material woodMat = new Material(new Color(139, 69, 19));
		Material treeMat = new Material(new Color(0, 169, 0));

		BoxedGroup tree = new BoxedGroup();

		if (withBallsAndCandles) {
			tree.add(new Cone(pos.plus(new Vector3d(0, 15*height/14, 0)), height/7, width/24, redRefl));
			tree.add(new Sphere(pos.plus(new Vector3d(0, height, 0)), width/24, redRefl));
		}

		tree.add(new Cone(pos.plus(new Vector3d(0, height, 0)), height, width/6, woodMat));

		double top = height;
		double size = height/3;
		double radius = width/2;
		tree.add(new Cone(pos.plus(new Vector3d(0, top, 0)), size, radius, treeMat));
		if(withBallsAndCandles)
			tree.add(createBalls(pos.plus(new Vector3d(0, top-size, 0)), radius, 3.75, (int) radius/4));

		top = 5*height/7;
		size = 2*height/7;
		radius = 5*width/6;
		tree.add(new Cone(pos.plus(new Vector3d(0, top, 0)), size, radius, treeMat));
		if (withBallsAndCandles)
			tree.add(createBalls(pos.plus(new Vector3d(0, top-size, 0)), radius, 3.75, (int) radius/4));

		top = 10*height/21;
		size = 2*height/7;
		radius = width;
		tree.add(new Cone(pos.plus(new Vector3d(0, top, 0)), size, radius, treeMat));
		if (withBallsAndCandles)
			tree.add(createBalls(pos.plus(new Vector3d(0, top-size, 0)), radius, 3.75, (int) radius/4));

		return tree;
	}

	public BoxedGroup createSnowman(Vector3d groundPos, double size) {
		// materials
		Material coleMat = new Material(new Color(0, 0, 0), 30);
		Material hatMat  = new Material(new Color(0, 0, 0), 100);
		Material redMat  = new Material(new Color(255, 0, 0), 20);

		BoxedGroup snowman = new BoxedGroup();

		// bottom sphere
		snowman.add(new Sphere(groundPos.plus(new Vector3d(0,size*23,0)), //new Vector3d( 100, 45, -350)
							size*23,
							snowMat));

		// tail sphere
		BoxedGroup tail = new BoxedGroup();
		tail.add(new Sphere(groundPos.plus(new Vector3d(0,size*53,0)),
				size*18,
				snowMat));
		tail.add(new Sphere(groundPos.plus(new Vector3d(0,size*62,size*16)),
				size*1.2,
				coleMat));
		tail.add(new Sphere(groundPos.plus(new Vector3d(0,size*55,size*18)),
				size*1.2,
				coleMat));
		tail.add(new Sphere(groundPos.plus(new Vector3d(0,size*48,size*17)),
				size*1.2,
				coleMat));
		snowman.add(tail);

		BoxedGroup head = new BoxedGroup();

		final Vector3d headCenter = groundPos.plus(new Vector3d(0,size*80,0));
		head.add(new Sphere(headCenter,
				size*12.5, // Vector3d( 100, 175, -350),
				snowMat));
		head.add(new Sphere(headCenter.plus(new Vector3d(-3.5, 2.5, 11.75).times(size)),
				size*1.2,
				coleMat));
		head.add(new Sphere(headCenter.plus(new Vector3d( 3.5, 2.5, 11.75).times(size)),
				size*1.2,
				coleMat));
		head.add(new Sphere(headCenter.plus(new Vector3d( -6, -1.5, 10.5).times(size)),
				size*0.8, //88.5, 168, -328.5),
				coleMat));
//		head.add(new Sphere(headCenter.add(new Vector3d( -5.75, -3.5, 10.5).scale(size)),
//				size*0.8, //88.5, 168, -328.5),
//				cole));
		head.add(new Sphere(headCenter.plus(new Vector3d( -4.75, -4, 10.75).times(size)),
				size*0.8, // 92, 165, -327.5),
				coleMat));
		head.add(new Sphere(headCenter.plus(new Vector3d( -3, -5.5, 11).times(size)),
				size*0.8, // 96, 163, -327),
				coleMat));
		head.add(new Sphere(headCenter.plus(new Vector3d( 0, -6, 11.5).times(size)),
				size*0.8, // 100, 162, -326.5),
				coleMat));
		head.add(new Sphere(headCenter.plus(new Vector3d(  3, -5.5, 11).times(size)),
				size*0.8, // 104, 163, -327),
				coleMat));
		head.add(new Sphere(headCenter.plus(new Vector3d(  4.75, -4, 10.75).times(size)),
				size*0.8, // 108, 165, -327.5),
				coleMat));
//		head.add(new Sphere(headCenter.add(new Vector3d(  5.75, -3.5, 10.5).scale(size)),
	//			size*0.8, // 111.5, 168, -328.5),
		//		cole));
		head.add(new Sphere(headCenter.plus(new Vector3d(  6, -1.5, 10.5).times(size)),
				size*0.8, //88.5, 168, -328.5),
				coleMat));

		snowman.add(head);

		BoxedGroup hat = new BoxedGroup();
		hat.add(new Cylinder(groundPos.plus(new Vector3d(0,size*107,0)), size*20, size*10, hatMat));
		hat.add(new Cylinder(groundPos.plus(new Vector3d(0,size*90,0)), size*3, size*10.5, redMat));
		hat.add(new Cylinder(groundPos.plus(new Vector3d(0,size*87.5,0)), size*0.5, size*15, hatMat));


		Material carrotMat = new Material(new Color(255, 125, 0), 15);
		Vector3d carrPos = headCenter.plus(new Vector3d(0, 0, size*12.5));
		double carrotSize = size*1.3;

		for (int i = 0; i < 10; i++) {
			hat.add(new Sphere(carrPos, carrotSize, carrotMat));
			carrPos = carrPos.plus(new Vector3d(0, 0, 1.25*carrotSize));
			carrotSize *= 0.90;
			double randX = (Math.random() - 0.5) * 0.35 * carrotSize;
			double randY = (Math.random() - 0.5) * 0.35 * carrotSize;
			carrPos = carrPos.plus(new Vector3d(randX, randY, 0));
		}


		snowman.add(hat);

		return snowman;
	}

	public BoxedGroup createBalls(Vector3d coneBottomCenter, double radius, double size, int numBalls) {
		Vector3d center = coneBottomCenter.minus(new Vector3d(0,7,0));
		BoxedGroup g = new BoxedGroup();
		Material wax = new Material(new Color(255,255,190));
		Material black = new Material(Color.BLACK);
		Material silver = new Material(new Color(50,50,50), 20);

		for (int i = 0; i < numBalls; i++) {
			double a = i * 2.0 * Math.PI / numBalls;
			Vector3d sCenter = center.plus(new Vector3d(Math.sin(a), 0, Math.cos(a)).times(radius));
			Sphere s = new Sphere(sCenter, size, redRefl);
			Cylinder c = new Cylinder(sCenter.plus(new Vector3d(0,7,0)), 7, 0.1, silver);
			g.add(c);

			sCenter = center.plus(new Vector3d(Math.sin(a), 0, Math.cos(a)).times(radius*0.9));
			sCenter.y += 10;
			c = new Cylinder(sCenter.plus(new Vector3d(0,+3*size,0)), 10, 1, wax);
			g.add(c);
			c = new Cylinder(sCenter.plus(new Vector3d(0,+3.5*size,0)), 10, 0.15, black);
			g.add(c);

		g.add(s);
		}

		return g;
	}

	public BoxedGroup createPresent(Vector3d min, Vector3d max, double ribbon, Material mBox, Material mRibbon) {

		BoxedGroup p = new BoxedGroup();

		p.add(new Box(min, max, mBox));

		Vector3d distToR = max.minus(min).minus(new Vector3d(ribbon, ribbon, ribbon)).times(0.5);

		p.add(new Box(new Vector3d(min.x - 0.1, min.y - 0.1, min.z + distToR.z),
					  new Vector3d(max.x + 0.1, max.y + 0.1, min.z + distToR.z + ribbon), mRibbon));

		p.add(new Box(new Vector3d(min.x + distToR.x, min.y - 0.1, min.z - 0.1),
				  	  new Vector3d(min.x + distToR.x + ribbon, max.y + 0.1, max.z + 0.1), mRibbon));

		return p;
	}

	@Override
	public void setCamera(Camera cam) {
		Vector3d camPos = new Vector3d(310, 150, 205);
		Vector3d lookAt = new Vector3d(100, 110, 0);
		Vector3d upDir = new Vector3d(0, 1, 0);

		//camPos = new Vector3d( 220, 160, -150);
		//lookAt = new Vector3d( 120, 160, -300);
		//Vector3d upDir = new Vector3d(0, 1, 0);

		cam.setPositionAndLookAt(camPos, lookAt, upDir);
	}
}

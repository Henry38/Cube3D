package geometry;

import graphics.WorldRenderer;

import java.awt.image.BufferedImage;

import world3d.Camera;
import world3d.Camera.TYPE;
import world3d.World3D;
import math.Coord;
import math.Point3D;
import math.Vecteur3D;

public class Miroir extends Shape3D {
	
	public static int depth = 1;
	
	private WorldRenderer w;
	private BufferedImage img;
	
	/** Consturcteur */
	public Miroir(Shape3D parent, double x, double y, double z, World3D world) {
		super(parent, x, y, z);
		
		Point3D p1 = new Point3D(0, 0, 0);
		Point3D p2 = new Point3D(1, 0, 0);
		Point3D p3 = new Point3D(0, 0, 2);
		Point3D p4 = new Point3D(1, 0, 2);
		
		Triangle triangle1 = new Triangle(p1, p2, p3);
		triangle1.setNormal(new Vecteur3D(0, -1, 0));
//		triangle1.setColor(Color.red);
		triangle1.setCoord(new Coord(0, 1), new Coord(1, 1), new Coord(0, 0));
		Triangle triangle2 = new Triangle(p2, p3, p4);
		triangle2.setNormal(new Vecteur3D(0, -1, 0));
//		triangle2.setColor(Color.blue);
		triangle2.setCoord(new Coord(1, 1), new Coord(0, 0), new Coord(1, 0));
		
		addTriangle(triangle1);
		addTriangle(triangle2);
		
		Camera camera = new Camera(new Point3D(-1, -1, 1.3), new Point3D(.5, .5, .5), TYPE.PERSPECTIVE);
		w = new WorldRenderer(world, camera, 256, 256);
		w.drawScene();
		img = w.getImage();
		setTexture("image/cube2.jpg");
	}
	
	@Override
	public BufferedImage getTexture() {
		return img;
//		if (depth == 0) {
//			return null;
//		}
//		depth--;
//		return w.getImage();
	}
}

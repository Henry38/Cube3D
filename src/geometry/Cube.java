package geometry;

import java.awt.Color;

import math.Coord;
import math.Point3D;
import math.Vecteur3D;

public class Cube extends Shape3D {
	
	/** Constructeur */
	public Cube(Shape3D parent, double x, double y, double z) {
		super(parent, x, y, z);
		
		Point3D p1 = new Point3D(0, 0, 0);
		Point3D p2 = new Point3D(1, 0, 0);
		Point3D p3 = new Point3D(1, 1, 0);
		Point3D p4 = new Point3D(0, 1, 0);
		Point3D p5 = new Point3D(0, 0, 1);
		Point3D p6 = new Point3D(1, 0, 1);
		Point3D p7 = new Point3D(1, 1, 1);
		Point3D p8 = new Point3D(0, 1, 1);
		
		// z = 0
		Triangle triangle1 = new Triangle(p1, p2, p3);
		triangle1.setNormale(new Vecteur3D(0, 0, -1));
		triangle1.setColor(Color.red);
		triangle1.setCoord(new Coord(0.0, 0.0), new Coord(1.0, 0.0), new Coord(1.0, 1.0));
		Triangle triangle2 = new Triangle(p1, p3, p4);
		triangle2.setNormale(new Vecteur3D(0, 0, -1));
		triangle2.setColor(Color.red);
		triangle2.setCoord(new Coord(0.0, 0.0), new Coord(1.0, 1.0), new Coord(0.0, 1.0));
		
		// z = 1
		Triangle triangle3 = new Triangle(p5, p6, p7);
		triangle3.setNormale(new Vecteur3D(0, 0, 1));
		triangle3.setColor(Color.blue);
		triangle3.setCoord(new Coord(0.0, 0.0), new Coord(1.0, 0.0), new Coord(1.0, 1.0));
		Triangle triangle4 = new Triangle(p5, p7, p8);
		triangle4.setNormale(new Vecteur3D(0, 0, 1));
		triangle4.setColor(Color.blue);
		triangle4.setCoord(new Coord(0.0, 0.0), new Coord(1.0, 1.0), new Coord(0.0, 1.0));
		
		// x = 0
		Triangle triangle5 = new Triangle(p1, p2, p6);
		triangle5.setNormale(new Vecteur3D(0, -1, 0));
		triangle5.setColor(Color.green);
		triangle5.setCoord(new Coord(0.0, 0.0), new Coord(1.0, 0.0), new Coord(1.0, 1.0));
		Triangle triangle6 = new Triangle(p1, p6, p5);
		triangle6.setNormale(new Vecteur3D(0, -1, 0));
		triangle6.setColor(Color.green);
		triangle6.setCoord(new Coord(0.0, 0.0), new Coord(1.0, 1.0), new Coord(0.0, 1.0));
		
		// x = 1
		Triangle triangle7 = new Triangle(p3, p4, p8);
		triangle7.setNormale(new Vecteur3D(0, 1, 0));
		triangle7.setColor(Color.pink);
		triangle7.setCoord(new Coord(0.0, 0.0), new Coord(1.0, 0.0), new Coord(1.0, 1.0));
		Triangle triangle8 = new Triangle(p3, p8, p7);
		triangle8.setNormale(new Vecteur3D(0, 1, 0));
		triangle8.setColor(Color.pink);
		triangle8.setCoord(new Coord(0.0, 0.0), new Coord(1.0, 1.0), new Coord(0.0, 1.0));
		
		// y = 0
		Triangle triangle9 = new Triangle(p1, p4, p8);
		triangle9.setNormale(new Vecteur3D(-1, 0, 0));
		triangle9.setColor(Color.orange);
		triangle9.setCoord(new Coord(0.0, 0.0), new Coord(1.0, 0.0), new Coord(1.0, 1.0));
		Triangle triangle10 = new Triangle(p1, p8, p5);
		triangle10.setNormale(new Vecteur3D(-1, 0, 0));
		triangle10.setColor(Color.orange);
		triangle10.setCoord(new Coord(0.0, 0.0), new Coord(1.0, 1.0), new Coord(0.0, 1.0));
		
		// y = 1
		Triangle triangle11 = new Triangle(p2, p3, p7);
		triangle11.setNormale(new Vecteur3D(1, 0, 0));
		triangle11.setColor(Color.cyan);
		triangle11.setCoord(new Coord(0.0, 0.0), new Coord(1.0, 0.0), new Coord(1.0, 1.0));
		Triangle triangle12 = new Triangle(p2, p7, p6);
		triangle12.setNormale(new Vecteur3D(1, 0, 0));
		triangle12.setColor(Color.cyan);
		triangle12.setCoord(new Coord(0.0, 0.0), new Coord(1.0, 1.0), new Coord(0.0, 1.0));
		
		addTriangle(triangle1);
		addTriangle(triangle2);
		addTriangle(triangle3);
		addTriangle(triangle4);
		addTriangle(triangle5);
		addTriangle(triangle6);
		addTriangle(triangle7);
		addTriangle(triangle8);
		addTriangle(triangle9);
		addTriangle(triangle10);
		addTriangle(triangle11);
		addTriangle(triangle12);
	}
}

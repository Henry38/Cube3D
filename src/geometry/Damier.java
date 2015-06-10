package geometry;

import java.awt.Color;

import math.Coord;
import math.Point3D;

public class Damier extends Shape3D {
	
	public Damier(Shape3D parent) {
		super(parent, 0, 0, 0);
		
		Point3D p1 = new Point3D(-2, -2, 0);
		Point3D p2 = new Point3D(-2, 2, 0);
		Point3D p3 = new Point3D(2, 2, 0);
		Point3D p4 = new Point3D(2, -2, 0);
		
		Triangle triangle1 = new Triangle(p1, p2, p4);
		//triangle1.setNormale(new Vecteur3D(0, 0, 1));
		triangle1.setColor(Color.blue);
		triangle1.setCoord(new Coord(0.0, 0.0), new Coord(1.0, 0.0), new Coord(1.0, 1.0));
		Triangle triangle2 = new Triangle(p2, p3, p4);
		//triangle2.setNormale(new Vecteur3D(0, 0, 1));
		triangle2.setColor(Color.pink);
		triangle2.setCoord(new Coord(0.0, 0.0), new Coord(1.0, 0.0), new Coord(1.0, 1.0));
		
		addTriangle(triangle1);
		addTriangle(triangle2);
	}
}

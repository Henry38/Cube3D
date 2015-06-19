package geometry;

import java.awt.Color;
import java.util.ArrayList;

import math.Coord;
import math.Point3D;
import math.Vecteur3D;

public class Damier extends Shape3D {
	
	public Damier(Shape3D parent) {
		super(parent, 0, 0, 0);
		setTexture("./image/cube1.jpg");
		
//		Point3D p1 = new Point3D(-2, -2, 0);
//		Point3D p2 = new Point3D(-2, 2, 0);
//		Point3D p3 = new Point3D(2, 2, 0);
//		Point3D p4 = new Point3D(2, -2, 0);
		
		ArrayList<Point3D> listPoint = new ArrayList<Point3D>();
		Triangle triangle1, triangle2;
		
		for (int x = -2; x <= 2; x++) {
			for (int y = -2; y <= 2; y++) {
				listPoint.add(new Point3D(x, y, 0));
			}
		}
		
		int index;
		Point3D point1, point2, point3;
		Color color;
		
		for (int i = 0; i < 16; i++) {
			index = (i / 4) * 5 + (i % 4);
			if (index % 2 == 0) {
				color = Color.red;
			} else {
				color = Color.blue;
			}
			
			point1 = listPoint.get(index);
			point2 = listPoint.get(index+1);
			point3 = listPoint.get(index+1+5);
			triangle1 = new Triangle(point1, point2, point3, color);
			triangle1.setNormal(new Vecteur3D(0, 0, 1));
			triangle1.setCoord(new Coord((index%5)/4.0, (index/5)/4.0), new Coord(((index+1)%5)/4.0, ((index+1)/5)/4.0), new Coord(((index+1+5)%5)/4.0, ((index+1+5)/5)/4.0));
			
			point1 = listPoint.get(index);
			point2 = listPoint.get(index+5);
			point3 = listPoint.get(index+1+5);
			triangle2 = new Triangle(point1, point2, point3, color);
			triangle2.setNormal(new Vecteur3D(0, 0, 1));
			triangle2.setCoord(new Coord((index%5)/4.0, (index/5)/4.0), new Coord(((index+5)%5)/4.0, ((index+5)/5)/4.0), new Coord(((index+1+5)%5)/4.0, ((index+1+5)/5)/4.0));
			
			addTriangle(triangle1);
			addTriangle(triangle2);
		}
		
//		Triangle triangle1 = new Triangle(p1, p2, p4);
//		//triangle1.setNormale(new Vecteur3D(0, 0, 1));
//		triangle1.setColor(Color.blue);
//		triangle1.setCoord(new Coord(0.0, 0.0), new Coord(1.0, 0.0), new Coord(1.0, 1.0));
//		Triangle triangle2 = new Triangle(p2, p3, p4);
//		//triangle2.setNormale(new Vecteur3D(0, 0, 1));
//		triangle2.setColor(Color.pink);
//		triangle2.setCoord(new Coord(0.0, 0.0), new Coord(1.0, 0.0), new Coord(1.0, 1.0));
//		
//		addTriangle(triangle1);
//		addTriangle(triangle2);
	}
}

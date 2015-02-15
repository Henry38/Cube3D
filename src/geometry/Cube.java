package geometry;

import java.awt.Color;

import math.Point3D;
import math.Vecteur3D;

public class Cube extends Shape3D {
	
	/** Constructeur */
	public Cube(double x, double y, double z) {
		super(x, y, z);
		
		// z = 0
		Polygon3D poly1 = new Polygon3D();
		poly1.addPoint(new Point3D(0, 0, 0));
		poly1.addPoint(new Point3D(0, 1, 0));
		poly1.addPoint(new Point3D(1, 1, 0));
		poly1.addPoint(new Point3D(1, 0, 0));
		poly1.setNormale(new Vecteur3D(0, 0, -1));
		poly1.setColor(Color.red);
		// z = 1
		Polygon3D poly2 = new Polygon3D();
		poly2.addPoint(new Point3D(0, 0, 1));
		poly2.addPoint(new Point3D(1, 0, 1));
		poly2.addPoint(new Point3D(1, 1, 1));
		poly2.addPoint(new Point3D(0, 1, 1));
		poly2.setNormale(new Vecteur3D(0, 0, 1));
		poly2.setColor(Color.blue);
		
		// x = 0
		Polygon3D poly3 = new Polygon3D();
		poly3.addPoint(new Point3D(0, 0, 0));
		poly3.addPoint(new Point3D(0, 0, 1));
		poly3.addPoint(new Point3D(0, 1, 1));
		poly3.addPoint(new Point3D(0, 1, 0));
		poly3.setNormale(new Vecteur3D(-1, 0, 0));
		poly3.setColor(Color.green);
		// x = 1
		Polygon3D poly4 = new Polygon3D();
		poly4.addPoint(new Point3D(1, 0, 0));
		poly4.addPoint(new Point3D(1, 1, 0));
		poly4.addPoint(new Point3D(1, 1, 1));
		poly4.addPoint(new Point3D(1, 0, 1));
		poly4.setNormale(new Vecteur3D(1, 0, 0));
		poly4.setColor(Color.pink);
		
		// y = 0
		Polygon3D poly5 = new Polygon3D();
		poly5.addPoint(new Point3D(0, 0, 0));
		poly5.addPoint(new Point3D(1, 0, 0));
		poly5.addPoint(new Point3D(1, 0, 1));
		poly5.addPoint(new Point3D(0, 0, 1));
		poly5.setNormale(new Vecteur3D(0, -1, 0));
		poly5.setColor(Color.orange);
		// y = 1
		Polygon3D poly6 = new Polygon3D();
		poly6.addPoint(new Point3D(0, 1, 0));
		poly6.addPoint(new Point3D(0, 1, 1));
		poly6.addPoint(new Point3D(1, 1, 1));
		poly6.addPoint(new Point3D(1, 1, 0));
		poly6.setNormale(new Vecteur3D(0, 1, 0));
		poly6.setColor(Color.cyan);
		
		addPolygon(poly1);
		addPolygon(poly2);
		addPolygon(poly3);
		addPolygon(poly4);
		addPolygon(poly5);
		addPolygon(poly6);
		
		setBarycentre(0.5, 0.5, 0.5);
	}
	
}

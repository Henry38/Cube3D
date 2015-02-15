package geometry;

import java.awt.Color;

import math.Point3D;

public class Damier extends Shape3D {
	
	public Damier() {
		super(-2, -2, -2);
		
		Polygon3D poly;
		for (int i=0; i<5; i++) {
			for (int j=0; j<5; j++) {
				poly = new Polygon3D();
				if ((i+j) % 2 == 0) {
					poly.setColor(Color.red);
				} else {
					poly.setColor(Color.blue);
				}
				
				poly.addPoint(new Point3D(i, j, 0));
				poly.addPoint(new Point3D(i+1, j, 0));
				poly.addPoint(new Point3D(i+1, j+1, 0));
				poly.addPoint(new Point3D(i, j+1, 0));
				addPolygon(poly.clone());
			}
		}
		
	}
	
	
}

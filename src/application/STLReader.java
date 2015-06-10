package application;

import geometry.Shape3D;
import geometry.Triangle;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import math.Coord;
import math.Point3D;
import math.Vecteur3D;

public class STLReader {
	
	private Shape3D shape;
	
	/** Constructeur */
	public STLReader() {
		
		Scanner sc = null;
		String line = "";
		String[] tab;
		Vecteur3D vect;
		Point3D point1, point2, point3;
		double dx, dy, dz, x, y, z;
		
		shape = new Shape3D(null, 0, 0, 0);
		Triangle triangle;
		
		try {
			
			sc = new Scanner(new FileInputStream("model/sphere.stl"));
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				tab = line.split(" ");
				if (tab[0].equals("facet")) {
					
					dx = Double.parseDouble(tab[2]);
					dy = Double.parseDouble(tab[3]);
					dz = Double.parseDouble(tab[4]);
					vect = new Vecteur3D(dx, dy, dz);
					
					line = sc.nextLine();
					line = sc.nextLine(); // point1
					tab = line.split(" ");
					x = Double.parseDouble(tab[7]);
					y = Double.parseDouble(tab[8]);
					z = Double.parseDouble(tab[9]);
					point1 = new Point3D(x, y, z);
					
					line = sc.nextLine(); // point2
					tab = line.split(" ");
					x = Double.parseDouble(tab[7]);
					y = Double.parseDouble(tab[8]);
					z = Double.parseDouble(tab[9]);
					point2 = new Point3D(x, y, z);
					
					line = sc.nextLine(); // point3
					tab = line.split(" ");
					x = Double.parseDouble(tab[7]);
					y = Double.parseDouble(tab[8]);
					z = Double.parseDouble(tab[9]);
					point3 = new Point3D(x, y, z);
					
					triangle = new Triangle(point1, point2, point3);
					triangle.setNormale(vect);
					triangle.setCoord(new Coord(0, 0), new Coord(1, 0), new Coord(0, 1));
					triangle.setColor(Color.red);
					shape.addTriangle(triangle);
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Shape3D getShape() {
		return shape;
	}
}
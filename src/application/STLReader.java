package application;

import geometry.Shape3D;
import geometry.Triangle;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import math.Coord;
import math.Point3D;
import math.Vecteur3D;

public class StlReader {
	
	private Scanner sc;
	
	/** Constructeur */
	public StlReader() {
		sc = null;
	}
	
	public void corrective(String path) {
		try {
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(new FileInputStream("model/" + path + ".stl"));
			@SuppressWarnings("resource")
			FileWriter fileWriter = new FileWriter("model/" + path + "2.stl");
			double dx, dy, dz;
			
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				String[] tab = line.split(" ");
				if (tab[0].equals("facet")) {
					fileWriter.write("facet normal ");
					dx = Double.parseDouble(tab[2]);
					dy = Double.parseDouble(tab[3]);
					dz = Double.parseDouble(tab[4]);
					fileWriter.write((-dx) + " " + (-dy) + " " + (-dz) + "\n");
				} else {
					fileWriter.write(line + "\n");
				}
				fileWriter.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/** Retourne la shape3D du fichier stl */
	public Shape3D getShape(String filename) {
		String line = "";
		String[] tab;
		Vecteur3D vect;
		Point3D point1, point2, point3;
		double dx, dy, dz, x, y, z;
		
		Shape3D shape = new Shape3D(null, 0, 0, 0);
		Triangle triangle;
		
		String[] vecteur, point;
		
		try {
			
			sc = new Scanner(new FileInputStream("model/" + filename));
			Pattern pattern1 = Pattern.compile("\\s*facet normal .*");
			Matcher matcher1;
			
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				matcher1 = pattern1.matcher(line);
				
				if (matcher1.matches()) {
					tab = line.split("facet normal ");
					vecteur = tab[1].split(" ");
					dx = Double.parseDouble(vecteur[0]);
					dy = Double.parseDouble(vecteur[1]);
					dz = Double.parseDouble(vecteur[2]);
					vect = new Vecteur3D(dx, dy, dz);
					
					line = sc.nextLine();
					line = sc.nextLine(); // point1
					tab = line.split("vertex ");
					point = tab[1].split(" ");
					x = Double.parseDouble(point[0]);
					y = Double.parseDouble(point[1]);
					z = Double.parseDouble(point[2]);
					point1 = new Point3D(x, y, z);
					
					line = sc.nextLine(); // point2
					tab = line.split("vertex ");
					point = tab[1].split(" ");
					x = Double.parseDouble(point[0]);
					y = Double.parseDouble(point[1]);
					z = Double.parseDouble(point[2]);
					point2 = new Point3D(x, y, z);
					
					line = sc.nextLine(); // point3
					tab = line.split("vertex ");
					point = tab[1].split(" ");
					x = Double.parseDouble(point[0]);
					y = Double.parseDouble(point[1]);
					z = Double.parseDouble(point[2]);
					point3 = new Point3D(x, y, z);
					
					triangle = new Triangle(point1, point2, point3, new Color(0, 0, 255));
					triangle.setNormale(vect);
					triangle.setCoord(new Coord(0, 0), new Coord(1, 0), new Coord(0, 1));
					shape.addTriangle(triangle);
				}
				
//				tab = line.split("\\s");
//				if (tab[0].equals("facet")) {
//					
//					dx = Double.parseDouble(tab[2]);
//					dy = Double.parseDouble(tab[3]);
//					dz = Double.parseDouble(tab[4]);
//					vect = new Vecteur3D(dx, dy, dz);
//					
//					line = sc.nextLine();
//					line = sc.nextLine(); // point1
//					tab = line.split(" ");
//					x = Double.parseDouble(tab[7]);
//					y = Double.parseDouble(tab[8]);
//					z = Double.parseDouble(tab[9]);
//					point1 = new Point3D(x, y, z);
//					
//					line = sc.nextLine(); // point2
//					tab = line.split(" ");
//					x = Double.parseDouble(tab[7]);
//					y = Double.parseDouble(tab[8]);
//					z = Double.parseDouble(tab[9]);
//					System.out.println(x + ", " + y + ", " + z);
//					point2 = new Point3D(x, y, z);
//					
//					line = sc.nextLine(); // point3
//					tab = line.split(" ");
//					x = Double.parseDouble(tab[7]);
//					y = Double.parseDouble(tab[8]);
//					z = Double.parseDouble(tab[9]);
//					point3 = new Point3D(x, y, z);
//					
//					triangle = new Triangle(point1, point2, point3, new Color(0, 0, 255));
//					triangle.setNormale(vect);
//					triangle.setCoord(new Coord(0, 0), new Coord(1, 0), new Coord(0, 1));
//					shape.addTriangle(triangle);
//				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return shape;
	}
}

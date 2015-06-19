package application;

import geometry.Shape3D;
import geometry.Triangle;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
		this.sc = null;
	}
	
	/** Inverse le sens des normales */
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
	public Shape3D readFromSTL(String filename) {
		String line = "";
		String[] array;
		double nx, ny, nz, x, y, z;
		
		Vecteur3D normal;
		Point3D[] points = new Point3D[3];
		
		Shape3D shape = new Shape3D(null, 0, 0, 0);
		Triangle triangle;
		
		try {
			
			sc = new Scanner(new FileInputStream("model/" + filename));
			Pattern pattern1 = Pattern.compile("[\\s]*facet normal[\\s]+.*");
			Matcher matcher1;
			
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				matcher1 = pattern1.matcher(line);
				
				if (matcher1.matches()) {
					array = line.split("[\\s]*facet normal[\\s]+")[1].split(" ");
					nx = Double.parseDouble(array[0]);
					ny = Double.parseDouble(array[1]);
					nz = Double.parseDouble(array[2]);
					normal = new Vecteur3D(nx, ny, nz);
					
					line = sc.nextLine();
					// Iteration sur les 3 points
					for (int i = 0; i < 3; i++) {
						line = sc.nextLine();
						array = line.split("[\\s]*vertex[\\s]+")[1].split(" ");
						x = Double.parseDouble(array[0]);
						y = Double.parseDouble(array[1]);
						z = Double.parseDouble(array[2]);
						points[i] = new Point3D(x, y, z);
					}
					
					triangle = new Triangle(points[0], points[1], points[2], new Color(0, 0, 255));
					triangle.setNormal(normal);
					triangle.setCoord(new Coord(0, 0), new Coord(0, 0), new Coord(0, 0));
					shape.addTriangle(triangle);
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return shape;
	}
	
}

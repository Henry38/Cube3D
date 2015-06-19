package application;

import geometry.Shape3D;
import geometry.Triangle;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import math.Coord;
import math.Point3D;
import math.Vecteur3D;

public class ObjReader {
	
	private Scanner sc;
	private ArrayList<Point3D> listPoint;
	private ArrayList<Coord> listCoord;
	private ArrayList<Vecteur3D> listNormal;
	
	/** Constructeur */
	public ObjReader() {
		this.sc = null;
		this.listPoint = new ArrayList<Point3D>();
		this.listCoord = new ArrayList<Coord>();
		this.listNormal = new ArrayList<Vecteur3D>();
	}
	
	/** Retourne la shape3D du fichier stl */
	public Shape3D readFromOBJ(String filename) {
		String line = "";
		String[] array;
		String[] param1, param2, param3;
		double x, y, z, u, v, nx, ny, nz;
		
		Point3D point1, point2, point3;
		Coord coord1, coord2, coord3;
		Vecteur3D normale1, normale2, normale3;
		
		listPoint.clear();
		listNormal.clear();
		listCoord.clear();
		Shape3D shape = new Shape3D(null, 0, 0, 0);
		Triangle triangle;
		
		try {
			
			sc = new Scanner(new FileInputStream("model/" + filename));
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				if (!line.equals("")) {
					array = line.split(" ");
					if (array[0].equals("o")) {
						// Titre
						
					} else if (array[0].equals("v")) {
						// Vertex
						x = Double.parseDouble(array[1]);
						y = Double.parseDouble(array[2]);
						z = Double.parseDouble(array[3]);
						listPoint.add(new Point3D(x, y, z));
						
					} else if (array[0].equals("vn")) {
						// Normal
						nx = Double.parseDouble(array[1]);
						ny = Double.parseDouble(array[2]);
						nz = Double.parseDouble(array[3]);
						listNormal.add(new Vecteur3D(nx, ny, nz));
						
					} else if (array[0].equals("vt")) {
						// Texture Coord
						u = Double.parseDouble(array[1]);
						v = Double.parseDouble(array[2]);
						listCoord.add(new Coord(u, v));
						
					} else if (array[0].equals("f")) {
						// Face
						param1 = array[1].split("/");
						param2 = array[2].split("/");
						param3 = array[3].split("/");
						
						point1 = listPoint.get(Integer.parseInt(param1[0])-1);
						point2 = listPoint.get(Integer.parseInt(param2[0])-1);
						point3 = listPoint.get(Integer.parseInt(param3[0])-1);
						
						if (!param1[1].equals("")) {
							coord1 = listCoord.get(Integer.parseInt(param1[1])-1);
							coord2 = listCoord.get(Integer.parseInt(param2[1])-1);
							coord3 = listCoord.get(Integer.parseInt(param3[1])-1);
						} else {
							coord1 = new Coord(0, 0);
							coord2 = new Coord(0, 0);
							coord3 = new Coord(0, 0);
						}
						
						normale1 = listNormal.get(Integer.parseInt(param1[2])-1);
						normale2 = listNormal.get(Integer.parseInt(param2[2])-1);
						normale3 = listNormal.get(Integer.parseInt(param3[2])-1);
						
						nx = (normale1.getDx() + normale2.getDx() + normale3.getDx()) / 3.0;
						ny = (normale1.getDy() + normale2.getDy() + normale3.getDy()) / 3.0;
						nz = (normale1.getDz() + normale2.getDz() + normale3.getDz()) / 3.0;
						
						triangle = new Triangle(point1, point2, point3, new Color(0, 0, 255));
						triangle.setCoord(coord1, coord2, coord3);
						triangle.setNormal(new Vecteur3D(nx, ny, nz));
						
						shape.addTriangle(triangle);
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return shape;
	}
}

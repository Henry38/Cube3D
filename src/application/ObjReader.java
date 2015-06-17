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
	private ArrayList<Vecteur3D> listNormale;
	
	/** Constructeur */
	public ObjReader() {
		this.sc = null;
		this.listPoint = new ArrayList<Point3D>();
		this.listCoord = new ArrayList<Coord>();
		this.listNormale = new ArrayList<Vecteur3D>();
	}
	
	/** Retourne la shape3D du fichier stl */
	public Shape3D getShape(String filename) {
		String line = "";
		String[] tab;
		String[] param1, param2, param3;
		double x, y, z, u, v, nx, ny, nz, dx, dy, dz;
		
		Point3D point1, point2, point3;
//		Coord coord1, coord2, coord3;
		Vecteur3D normale1, normale2, normale3;
		
		listPoint.clear();
		listNormale.clear();
		listCoord.clear();
		Shape3D shape = new Shape3D(null, 0, 0, 0);
		Triangle triangle;
		
		try {
			
			sc = new Scanner(new FileInputStream("model/" + filename));
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				if (!line.equals("")) {
					tab = line.split(" ");
					if (tab[0].equals("o")) {
						// Titre
						
					} else if (tab[0].equals("v")) {
						// Vertex
						x = Double.parseDouble(tab[1]);
						y = Double.parseDouble(tab[2]);
						z = Double.parseDouble(tab[3]);
						listPoint.add(new Point3D(x, y, z));
						
					} else if (tab[0].equals("vn")) {
						// Normale
						nx = Double.parseDouble(tab[1]);
						ny = Double.parseDouble(tab[2]);
						nz = Double.parseDouble(tab[3]);
						listNormale.add(new Vecteur3D(nx, ny, nz));
						
					} else if (tab[0].equals("vt")) {
						// Texture Coord
						u = Double.parseDouble(tab[1]);
						v = Double.parseDouble(tab[2]);
						listCoord.add(new Coord(u, v));
						
					} else if (tab[0].equals("f")) {
						// Face
						param1 = tab[1].split("/");
						param2 = tab[2].split("/");
						param3 = tab[3].split("/");
						
						point1 = listPoint.get(Integer.parseInt(param1[0])-1);
						point2 = listPoint.get(Integer.parseInt(param2[0])-1);
						point3 = listPoint.get(Integer.parseInt(param3[0])-1);
						
//						coord1 = listCoord.get(Integer.parseInt(param1[1])-1);
//						coord2 = listCoord.get(Integer.parseInt(param2[1])-1);
//						coord3 = listCoord.get(Integer.parseInt(param3[1])-1);
						
						normale1 = listNormale.get(Integer.parseInt(param1[2])-1);
						normale2 = listNormale.get(Integer.parseInt(param2[2])-1);
						normale3 = listNormale.get(Integer.parseInt(param3[2])-1);
						
						dx = (normale1.getDx() + normale2.getDx() + normale3.getDx()) / 3.0;
						dy = (normale1.getDy() + normale2.getDy() + normale3.getDy()) / 3.0;
						dz = (normale1.getDz() + normale2.getDz() + normale3.getDz()) / 3.0;
						
						triangle = new Triangle(point1, point2, point3, new Color(0, 0, 255));
//						triangle.setCoord(coord1, coord2, coord3);
						triangle.setNormale(new Vecteur3D(dx, dy, dz));
						
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

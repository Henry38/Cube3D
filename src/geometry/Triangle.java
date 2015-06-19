package geometry;

import java.awt.Color;

import math.Coord;
import math.Mat4;
import math.Point3D;
import math.Vec4;
import math.Vecteur3D;

public class Triangle {
	
	private Point3D p1, p2, p3;
	private Color color;
	private Coord coord1, coord2, coord3;
	private Vecteur3D normal;
	
	/** Constructeur */
	public Triangle(Point3D p1, Point3D p2, Point3D p3) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		setColor(Color.white);
		setCoord(new Coord(), new Coord(), new Coord());
		setNormal(null);
	}
	
	/** Constructeur */
	public Triangle(Point3D p1, Point3D p2, Point3D p3, Color color) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		setColor(color);
		setCoord(new Coord(), new Coord(), new Coord());
		setNormal(null);
	}
	
	public Color getColor() {
		return color;
	}
	
	public Point3D getP1() {
		return p1;
	}
	
	public Point3D getP2() {
		return p2;
	}
	
	public Point3D getP3() {
		return p3;
	}
	
	public Vecteur3D getNormal() {
		return normal;
	}
	
	public final Coord[] getListCoord() {
		return new Coord[] {coord1, coord2, coord3};
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public void setCoord(Coord coord1, Coord coord2, Coord coord3) {
		this.coord1 = coord1;
		this.coord2 = coord2;
		this.coord3 = coord3;
	}
	
	public void setNormal(Vecteur3D normal) {
		this.normal = normal;
	}
	
//	public double area() {
//		Vecteur3D p1p2 = new Vecteur3D(p1, p2);
//		Vecteur3D p1p3 = new Vecteur3D(p1, p3);
//		double dot = Vecteur3D.produit_scalaire(p1p2, p1p3);
//		double norme = p1p2.getNorme() * p1p3.getNorme();
//		double teta = Math.acos(dot / norme);
//		return 0.5 * norme * Math.sin(teta);
//	}
}

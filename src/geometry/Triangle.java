package geometry;

import java.awt.Color;
import math.Coord;
import math.Point3D;
import math.Vecteur3D;

public class Triangle {
	
	private Point3D p1, p2, p3;
	private Color color1, color2, color3;
	private Coord coord1, coord2, coord3;
	private Vecteur3D normale1, normale2, normale3;
	
	public Triangle(Point3D p1, Point3D p2, Point3D p3) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		setColor(Color.white);
		setCoord(new Coord(), new Coord(), new Coord());
		setNormale(null);
	}
	
	public Color getColor() {
		return color1;
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
	
	public Vecteur3D getNormale1() {
		return normale1;
	}
	
	public Vecteur3D getNormale2() {
		return normale2;
	}
	
	public Vecteur3D getNormale3() {
		return normale3;
	}
	
	public final Color[] getListColor() {
		return new Color[] {color1, color2, color3};
	}
	
	public final Coord[] getListCoord() {
		return new Coord[] {coord1, coord2, coord3};
	}
	
	public final Vecteur3D[] getNormale() {
		return new Vecteur3D[] {normale1, normale2, normale3};
	}
	
	public void setColor(Color color) {
		this.color1 = color;
		this.color2 = color;
		this.color3 = color;
	}
	
	public void setColor(Color color1, Color color2, Color color3) {
		this.color1 = color1;
		this.color2 = color2;
		this.color3 = color3;
	}
	
	public void setCoord(Coord coord1, Coord coord2, Coord coord3) {
		this.coord1 = coord1;
		this.coord2 = coord2;
		this.coord3 = coord3;
	}
	
	public void setNormale(Vecteur3D normale) {
		this.normale1 = normale;
		this.normale2 = normale;
		this.normale3 = normale;
	}
	
	public void setNormale(Vecteur3D normale1, Vecteur3D normale2, Vecteur3D normale3) {
		this.normale1 = normale1;
		this.normale2 = normale2;
		this.normale3 = normale3;
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

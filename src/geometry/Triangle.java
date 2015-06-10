package geometry;

import java.awt.Color;
import math.Coord;
import math.Mat4;
import math.Point3D;
import math.Vec4;
import math.Vecteur3D;

public class Triangle {
	
	private Point3D p1, p2, p3;
	private Color color1, color2, color3;
	private Coord coord1, coord2, coord3;
	private Vecteur3D normale;
	
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
	
	public final Color[] getListColor() {
		return new Color[] {color1, color2, color3};
	}
	
	public final Coord[] getListCoord() {
		return new Coord[] {coord1, coord2, coord3};
	}
	
	public final Vecteur3D getNormale() {
		return normale;
	}
	
	public boolean isVisible(Mat4 modelMat, Point3D eye) {
		if (getNormale() == null) {
			return true;
		}
		
		Point3D p = modelMat.mult(new Vec4(p1)).normalized();
		double dx = p.getX() - eye.getX();
		double dy = p.getY() - eye.getY();
		double dz = p.getZ() - eye.getZ();
		Vecteur3D vision = new Vecteur3D(dx, dy, dz);
		Vecteur3D normale = modelMat.mult(new Vec4(getNormale())).toVecteur3D();
		
		return (Vecteur3D.produit_scalaire(vision, normale) > 0);
	}
	
//	public Vecteur3D getVision(Mat4 modelMat, Point3D eye) {
//		Point3D p = modelMat.mult(new Vec4(p1)).toPoint3D();
//		double dx = p.getX() - eye.getX();
//		double dy = p.getX() - eye.getX();
//		double dz = p.getX() - eye.getX();
//		return new Vecteur3D(dx, dy, dz);
//	}
	
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
	
	public void setNormale(Vecteur3D vect) {
		this.normale = vect;
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

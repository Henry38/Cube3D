package geometry;

import java.util.ArrayList;

import math.Base3D;
import math.Point3D;
import math.Vecteur3D;

public class Shape3D {
	
	private Base3D base;
	private ArrayList<Polygon3D> listPolygon;
	private Point3D barycentre;
	
	public Shape3D(double x, double y, double z) {
		this.base = new Base3D(new Point3D(x, y, z));
		this.listPolygon = new ArrayList<Polygon3D>();
		this.barycentre = new Point3D(0, 0, 0);
	}
	
	public final Base3D getBase() {
		return base;
	}
	
	public final ArrayList<Polygon3D> getListPolygon3D() {
		return listPolygon;
	}
	
	public final Point3D getBarycentre() {
		return barycentre;
	}
	
	public void rotation(Vecteur3D axe, int degre) {
		base.getOrigine().rotationAxe(axe, degre);
	}
	
	/** Augmente le degré de rotation */
	public void rotationOx(double radian) {
		base.rotationOx(radian);
	}
	
	/** Augmente le degré de rotation */
	public void rotationOy(double radian) {
		base.rotationOy(radian);
	}
	
	/** Augmente le degré de rotation */
	public void rotationOz(double radian) {
		base.rotationOz(radian);
	}
	
	public void addPolygon(Polygon3D polygon) {
		listPolygon.add(polygon);
	}
	
	public void removePolygon(Polygon3D polygon) {
		listPolygon.remove(polygon);
	}
	
	public void setBarycentre(double x, double y, double z) {
		barycentre.setX(x);
		barycentre.setY(y);
		barycentre.setZ(z);
	}
}

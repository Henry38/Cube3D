package geometry;

import java.awt.Color;
import java.util.ArrayList;

import math.Point3D;
import math.Vecteur3D;

public class Polygon3D {
	
	private ArrayList<Point3D> listPoint;
	private Vecteur3D normale;
	private Color color;
	
	public Polygon3D() {
		this.listPoint = new ArrayList<Point3D>();
		this.normale = null;
		this.color = Color.orange;
	}
	
	public Polygon3D clone() {
		Polygon3D poly = new Polygon3D();
		for (Point3D p : getListPoint()) {
			poly.addPoint(p.clone());
		}
		poly.setNormale(getNormale());
		poly.setColor(getColor());
		return poly;
	}
	
	public final Vecteur3D getNormale() {
		return normale;
	}
	
	public final Point3D get(int index) {
		return listPoint.get(index);
	}
	
	public final Color getColor() {
		return color;
	}
	
	public final ArrayList<Point3D> getListPoint() {
		return listPoint;
	}
	
	public int nbPoints() {
		return listPoint.size();
	}
	
	public void addPoint(int index, Point3D point) {
		listPoint.add(index, point);
	}
	
	public void addPoint(Point3D point) {
		addPoint(listPoint.size(), point);
	}
	
	public void removePoint(Point3D point) {
		listPoint.remove(point);
	}
	
	public void setNormale(Vecteur3D vect) {
		this.normale = vect;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
}

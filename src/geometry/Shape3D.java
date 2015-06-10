package geometry;

import java.util.ArrayList;

import math.Base3D;
import math.Mat4;
import math.Point3D;

public class Shape3D {
	
	private Shape3D parent;
	private Base3D base;
	private ArrayList<Triangle> listTriangle;
	private String path;
	
	/** Construteur */
	public Shape3D(Shape3D parent) {
		this.parent = parent;
		this.path = "./image/cube2.jpg";
	}
	
	public Shape3D(Shape3D parent, double x, double y, double z) {
		this.parent = parent;
		this.base = new Base3D(new Point3D(x, y, z));
		this.listTriangle = new ArrayList<Triangle>();
		this.path = "./image/cube2.jpg";
	}
	
	public final Base3D getBase() {
		return base;
	}
	
	public Mat4 getModelMat() {
		if (parent == null) {
			return new Mat4(getBase());
		}
		return parent.getModelMat().mult(new Mat4(getBase()));
	}
	
	public final ArrayList<Triangle> getListTriangle() {
		return listTriangle;
	}
	
	public String getPath() {
		return path;
	}
	
	public void translation (double dx, double dy, double dz) {
		base.translation(dx, dy, dz);
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
	
	public void addTriangle(Triangle triangle) {
		listTriangle.add(triangle);
	}
	
	public void removeTriangle(Triangle triangle) {
		listTriangle.remove(triangle);
	}
}

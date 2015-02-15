package world3d;

import geometry.Shape3D;

import java.util.ArrayList;

import math.Base3D;
import math.Point3D;
import math.Vecteur3D;

public class World3D {
	
	private Base3D repereWorld;
	private ArrayList<Shape3D> listShape;
	
	public World3D() {
		this.repereWorld = new Base3D(new Point3D(0, 0, 0), new Vecteur3D(1, 0, 0), new Vecteur3D(0, 1, 0), new Vecteur3D(0, 0, 1));
		this.listShape = new ArrayList<Shape3D>();
	}
	
	public final Point3D getOrigine() {
		return repereWorld.getOrigine();
	}
	
	public final Base3D getBase() {
		return repereWorld;
	}
	
	public final ArrayList<Shape3D> getlistShape() {
		return listShape;
	}
	
	public void addShape(Shape3D shape) {
		listShape.add(shape);
	}
	
	public void removeShape(Shape3D shape) {
		listShape.remove(shape);
	}
	
}

package world3d;

import geometry.Light;
import geometry.Shape3D;

import java.util.ArrayList;

import math.Base3D;
import math.Point3D;
import math.Vecteur3D;

public class World3D {
	
	private Base3D repereWorld;
	private ArrayList<Shape3D> listShape;
	private Light light;
	
	/** Constructeur */
	public World3D() {
		this.repereWorld = new Base3D(new Point3D(0, 0, 0), new Vecteur3D(1, 0, 0), new Vecteur3D(0, 1, 0), new Vecteur3D(0, 0, 1));
		this.listShape = new ArrayList<Shape3D>();
		this.light = null;
	}
	
	public final Point3D getOrigine() {
		return repereWorld.getOrigine();
	}
	
	public final Base3D getBase() {
		return repereWorld;
	}
	
	public final ArrayList<Shape3D> getListShape() {
		return listShape;
	}
	
	public final Light getLight() {
		return light;
	}
	
	public void addShape(Shape3D shape) {
		listShape.add(shape);
	}
	
	public void removeShape(Shape3D shape) {
		listShape.remove(shape);
	}
	
	public void setLight(Light light) {
		this.light = light;
	}

	public Shape3D getShape(int i) {
		return listShape.get(i);
	}
}

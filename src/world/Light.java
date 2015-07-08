package world;

import java.awt.Color;

import math.Point3D;
import math.Vecteur3D;

public class Light {
	
	private Point3D origin;
	private Vecteur3D direction;
	private Color color;
	
	/** Constructeur */
	public Light(Point3D origin, Vecteur3D direction) {
		this.origin = origin;
		this.direction = direction;
		this.color = Color.white;
	}
	
	/** Constructeur */
	public Light(Point3D origin, Vecteur3D direction, Color color) {
		this.origin = origin;
		this.direction = direction;
		this.color = color;
	}
	
	public final Point3D getOrigin() {
		return origin;
	}
	
	public final Vecteur3D getDirection() {
		return direction;
	}
	
	public final Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
}

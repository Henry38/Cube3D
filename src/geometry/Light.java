package geometry;

import math.Point3D;
import math.Vecteur3D;

public class Light {
	
	private Point3D origin;
	private Vecteur3D direction;
	
	public Light(Point3D origin, Vecteur3D direction) {
		this.origin = origin;
		this.direction = direction;
	}
	
	public Point3D getOrigin() {
		return origin;
	}
	
	public Vecteur3D getDirection() {
		return direction;
	}
}

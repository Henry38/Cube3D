package geometry;

import math.Point3D;
import math.Vecteur3D;

public class Plan3D {
	
	private double a, b, c, d;
	
	/** Constructeur */
	public Plan3D(double a, double b, double c, double d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	public final Vecteur3D getNormale() {
		return new Vecteur3D(a, b, c).getVecteurUnitaire();
	}
	
	public double getA() {
		return a;
	}
	
	public double getB() {
		return b;
	}
	
	public double getC() {
		return c;
	}
	
	public double getD() {
		return d;
	}
	
	public String toString() {
		return a + ".x + " + b + ".y + " + c + ".z = " + d;
	}
	
	public boolean Point3DInPlan(Point3D p) {
		return (Math.abs(a*p.getX() + b*p.getY() + c*p.getZ() - d) < 0.001);
	}
	
	public boolean isPoint3DOnRightSide(Point3D point) {
		return ((a*point.getX() + b*point.getY() + c*point.getZ() - d) < 0);
	}
	
	/** Retourne le point qui intersecte le plan par le segment formé par les 2 points */
	public Point3D getIntersectionWithSegment3D(Point3D point1, Point3D point2) {
		if (!isPoint3DSideToSide(point1, point2)) {
			return null;
		}
		
		if (isPoint3DOnRightSide(point1)) {
			Point3D tmp = point1;
			point1 = point2;
			point2 = tmp;
		}
		
		Vecteur3D vect1 = new Vecteur3D(0, 0, 0);
		vect1.setDx(point2.getX() - point1.getX());
		vect1.setDy(point2.getY() - point1.getY());
		vect1.setDz(point2.getZ() - point1.getZ());
		
		double dist = distance(point1);
		double radian = Math.PI - Math.acos(Vecteur3D.cosinus(vect1, getNormale()));
		double h = dist / Math.cos(radian);
		
		Point3D proj = point1.clone();
		Vecteur3D t = vect1.getVecteurUnitaire();
		t.mult(h);
		proj.translation(t);
		
		return proj;
	}
	
	public double distance(Point3D point) {
		return Math.abs(a*point.getX() + b*point.getY() + c*point.getZ() + d) / Math.sqrt(a*a + b*b + c*c);
	}
	
	/** Retourne vrai si les 2 points sont situés de part et d'autre du plan */
	public boolean isPoint3DSideToSide(Point3D point1, Point3D point2) {
		return ((a*point1.getX() + b*point1.getY() + c*point1.getZ() - d) *
				(a*point2.getX() + b*point2.getY() + c*point2.getZ() - d) < 0);
	}
	
	/** Retourne le plan défini par 3 points */
	public static Plan3D getPlan3DFromPoint3D(Point3D p1, Point3D p2, Point3D p3) {
		// Plan non défini par les 3 points
		if (Point3D.aligner(p1, p2, p3)) {
			return null;
		}
		
		Vecteur3D vect1 = new Vecteur3D(p2.getX()-p1.getX(), p2.getY()-p1.getY(), p2.getZ()-p1.getZ());
		Vecteur3D vect2 = new Vecteur3D(p3.getX()-p1.getX(), p3.getY()-p1.getY(), p3.getZ()-p1.getZ());
		Vecteur3D normal = Vecteur3D.produit_vectoriel(vect1, vect2).getVecteurUnitaire();
		int d = (int) (normal.getDx()*p1.getX() + normal.getDy()*p1.getY() + normal.getDz()*p1.getZ());
		
		return new Plan3D(normal.getDx(), normal.getDy(), normal.getDz(), d);
	}
	
}

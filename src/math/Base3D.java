package math;

public class Base3D {
	
	private Point3D origine;
	public Vecteur3D oi, oj, ok;
	
	/** Constructeur */
	public Base3D(Point3D origine) {
		this.origine = origine;
		this.oi = new Vecteur3D(1, 0, 0);
		this.oj = new Vecteur3D(0, 1, 0);
		this.ok = new Vecteur3D(0, 0, 1);
	}
	
	public Base3D(Point3D origine, Vecteur3D oi, Vecteur3D oj, Vecteur3D ok) {
		this.origine = origine;
		this.oi = oi;
		this.oj = oj;
		this.ok = ok;
	}
	
	public final Point3D getOrigine() {
		return origine;
	}
	
	public final Vecteur3D[] getVecteurs() {
		return new Vecteur3D[] {oi, oj, ok};
	}
	
	public void translation(double dx, double dy, double dz) {
		origine.translation(dx, dy, dz);
	}
	
	public void translation(Vecteur3D t) {
		translation(t.getDx(), t.getDy(), t.getDz());
	}
	
	/** Augmente le degré de rotation */
	public void rotationOx(double radian) {
		oj.rotationAxe(oi, radian);
		ok.rotationAxe(oi, radian);
	}
	
	/** Augmente le degré de rotation */
	public void rotationOy(double radian) {
		oi.rotationAxe(oj, radian);
		ok.rotationAxe(oj, radian);
	}
	
	/** Augmente le degré de rotation */
	public void rotationOz(double radian) {
		oi.rotationAxe(ok, radian);
		oj.rotationAxe(ok, radian);
	}
	
	/** Change l'echelle en ox */
	public void scaleX(double rx) {
		oi.mult(rx);
	}
	
	/** Change l'echelle en oy */
	public void scaleY(double ry) {
		oj.mult(ry);
	}
	
	/** Change l'echelle en oz */
	public void scaleZ(double rz) {
		ok.mult(rz);
	}
	
	/** Change l'echelle sur les trois axes */
	public void scale(double r) {
		scaleX(r);
		scaleY(r);
		scaleZ(r);
	}
}

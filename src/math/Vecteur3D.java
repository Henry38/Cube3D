package math;

public class Vecteur3D {
	
	private Matrix matrix;
	
	/** Constructeur */
	public Vecteur3D(double dist_x, double dist_y, double dist_z) {
		matrix = new Matrix(3, 1);
		setDx(dist_x);
		setDy(dist_y);
		setDz(dist_z);
	}
	
	public Vecteur3D clone() {
		return new Vecteur3D(getDx(), getDy(), getDz());
	}
	
	public double getDx() {
		return matrix.get(0, 0);
	}
	
	public double getDy() {
		return matrix.get(1, 0);
	}
	
	public double getDz() {
		return matrix.get(2, 0);
	}
	
	public void setDx(double dist_x) {
		matrix.set(0, 0, dist_x);
	}
	
	public void setDy(double dist_y) {
		matrix.set(1, 0, dist_y);
	}
	
	public void setDz(double dist_z) {
		matrix.set(2, 0, dist_z);
	}
	
	public double getNorme() {
		return Math.sqrt(Math.pow(getDx(), 2) + Math.pow(getDy(), 2) + Math.pow(getDz(), 2));
	}
	
	public Vecteur3D getVecteurUnitaire() {
		double norme = getNorme();
		double dx, dy, dz;
		dx = getDx() / norme;
		dy = getDy() / norme;
		dz = getDz() / norme;
		return new Vecteur3D(dx, dy, dz);
	}
	
	public final Matrix getMatrix() {
		return matrix;
	}
	
	public String toString() {
		return "(" + ((int)(getDx()*100))/100.0 + " , " + ((int)(getDy()*100))/100.0 + " , " + ((int)(getDz()*100))/100.0 + ")";
	}
	
	public void plus(Vecteur3D vect) {
		setDx(getDx() + vect.getDx());
		setDy(getDy() + vect.getDy());
		setDz(getDz() + vect.getDz());
	}
	
	public void minus(Vecteur3D vect) {
		setDx(getDx() - vect.getDx());
		setDy(getDy() - vect.getDy());
		setDz(getDz() - vect.getDz());
	}
	
	public void mult(double k) {
		setDx(k * getDx());
		setDy(k * getDy());
		setDz(k * getDz());
	}
	
	/** Retourne la projection du vecteur courant sur le vecteur passé en paramètre */
	public Vecteur3D projection(Vecteur3D vect) {
		double norme = (Vecteur3D.produit_scalaire(this, vect)) / Math.pow(vect.getNorme(), 2);
		double dx, dy, dz;
		dx = getDx() * norme;
		dy = getDy() * norme;
		dz = getDz() * norme;
		return new Vecteur3D(dx, dy, dz);
	}
	
	/** Rotation du vecteur autour d'un axe et avec un angle donnée */
	public void rotationAxe(Vecteur3D axe, double radian) {
		//double radian = (degre / 180.0) * Math.PI;
		double x = getDx();
		double y = getDy();
		double z = getDz();
		double cos = Math.cos(radian);
		double sin = Math.sin(radian);
		Vecteur3D vect = axe.getVecteurUnitaire();
		setDx(  x * (Math.pow(vect.getDx(), 2) + (1 - Math.pow(vect.getDx(), 2)) * cos) +
				y * (vect.getDx()*vect.getDy() * (1 - cos) - vect.getDz()*sin) +
				z * (vect.getDx()*vect.getDz() * (1 - cos) + vect.getDy()*sin) );
		setDy(  x * (vect.getDx()*vect.getDy() * (1 - cos) + vect.getDz()*sin) +
				y * (Math.pow(vect.getDy(), 2) + (1 - Math.pow(vect.getDy(), 2)) * cos) +
				z * (vect.getDy()*vect.getDz() * (1 - cos) - vect.getDx()*sin) );
		setDz(  x * (vect.getDx()*vect.getDz() * (1 - cos) - vect.getDy()*sin) +
				y * (vect.getDy()*vect.getDz() * (1 - cos) + vect.getDx()*sin) +
				z * (Math.pow(vect.getDz(), 2) + (1 - Math.pow(vect.getDz(), 2)) * cos) );
	}
	
	/** Rotation du vecteur autour d'un axe et avec un angle donnée */
	/*public void rotationAxe(Vecteur3D axe, int degre) {
		double radian = ((double)degre / 180.0) * Math.PI;
		double x = getDx();
		double y = getDy();
		double z = getDz();
		double c = Math.cos(radian);
		double s = Math.sin(radian);
		Vecteur3D vect = new Vecteur3D(0, 0, 0);
		vect.setDx(axe.getDx() / axe.getNorme());
		vect.setDy(axe.getDy() / axe.getNorme());
		vect.setDz(axe.getDz() / axe.getNorme());
		setDx(x * (Math.pow(vect.getDx(), 2) + (1 - Math.pow(vect.getDx(), 2)) * c) +
				y * (vect.getDx()*vect.getDy() * (1 - c) - vect.getDz()*s) +
				z * (vect.getDx()*vect.getDz() * (1 - c) + vect.getDy()*s));
		setDy(x * (vect.getDx()*vect.getDy() * (1 - c) + vect.getDz()*s) +
				y * (Math.pow(vect.getDy(), 2) + (1 - Math.pow(vect.getDy(), 2)) * c) +
				z * (vect.getDy()*vect.getDz() * (1 - c) - vect.getDx()*s));
		setDz(x * (vect.getDx()*vect.getDz() * (1 - c) - vect.getDy()*s) +
				y * (vect.getDy()*vect.getDz() * (1 - c) + vect.getDx()*s) +
				z * (Math.pow(vect.getDz(), 2) + (1 - Math.pow(vect.getDz(), 2)) * c));
	}*/
	
	/** Retourne le produit scalaire de deux vecteurs en 3 dimensions */
	public static double produit_scalaire(Vecteur3D vect1, Vecteur3D vect2) {
		return (vect1.getDx() * vect2.getDx() + vect1.getDy() * vect2.getDy() + vect1.getDz() * vect2.getDz());
	}
	
	/** Retourne le produit vectoriel de deux vecteurs en 3 dimensions */
	public static Vecteur3D produit_vectoriel(Vecteur3D vect1, Vecteur3D vect2) {
		double dx, dy, dz;
		dx = (vect1.getDy() * vect2.getDz()) - (vect1.getDz() * vect2.getDy());
		dy = (vect1.getDz() * vect2.getDx()) - (vect1.getDx() * vect2.getDz());
		dz = (vect1.getDx() * vect2.getDy()) - (vect1.getDy() * vect2.getDx());
		return new Vecteur3D(dx, dy, dz);
	}
	
	public static double cosinus(Vecteur3D vect1, Vecteur3D vect2) {
		return produit_scalaire(vect1, vect2) / (vect1.getNorme() * vect2.getNorme());
	}
	
	public static double sinus(Vecteur3D vect1, Vecteur3D vect2) {
		return produit_vectoriel(vect1, vect2).getNorme() / (vect1.getNorme() * vect2.getNorme());
	}
	
	public static boolean colineaire(Vecteur3D vect1, Vecteur3D vect2) {
		boolean a = Math.abs(vect1.getDx()/vect2.getDx() - vect1.getDy()/vect2.getDy()) < 0.01;
		boolean b = Math.abs(vect1.getDy()/vect2.getDy() - vect1.getDz()/vect2.getDz()) < 0.01;
		return a && b;
	}
	
	/** Retourne le cosinus de l'angle formé par deux vecteurs */
	/*public static double cosinus_angle(Vecteur3D vect1, Vecteur3D vect2) {
		return (Math.abs(Vecteur3D.produit_scalaire(vect1, vect2)) / (vect1.getNorme() * vect2.getNorme()));
	}*/
	
	/** Retourne le sinus de l'angle formé par deux vecteurs */
	/*public static double sinus_angle(Vecteur3D vect1, Vecteur3D vect2) {
		return (Math.abs(vect1.getDx() * vect2.getDy() - vect1.getDy() * vect2.getDx()) / (vect1.getNorme() * vect2.getNorme()));
	}*/
}

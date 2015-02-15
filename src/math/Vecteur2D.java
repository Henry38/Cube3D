package math;

public class Vecteur2D {// extends Matrix {
	
	private Matrix matrix;
	
	/** Constructeur */
	public Vecteur2D(double dist_x, double dist_y) {
		matrix = new Matrix(2, 1);
		//matrix(2, 1);
		setDx(dist_x);
		setDy(dist_y);
	}
	
	public double getDx() {
		return matrix.get(0, 0);
	}
	
	public double getDy() {
		return matrix.get(1, 0);
	}
	
	public void setDx(double dist_x) {
		matrix.set(0, 0, dist_x);
	}
	
	public void setDy(double dist_y) {
		matrix.set(1, 0, dist_y);
	}
	
	public double getNorme() {
		return Math.sqrt(Math.pow(getDx(), 2) + Math.pow(getDy(), 2));
	}
	
	public Vecteur2D getVecteurUnitaire() {
		double norme = getNorme();
		double dx, dy;
		dx = getDx() / norme;
		dy = getDy() / norme;
		return new Vecteur2D(dx, dy);
	}
	
	public final Matrix getMatrix() {
		return matrix;
	}
	
	public String toString() {
		return "(" + ((int)(getDx()*100))/100.0 + " , " + ((int)(getDy()*100))/100.0 + ")";
	}
	
	public void plus(Vecteur2D vect) {
		setDx(getDx() + vect.getDx());
		setDy(getDy() + vect.getDy());
	}
	
	public void minus(Vecteur2D vect) {
		setDx(getDx() - vect.getDx());
		setDy(getDy() - vect.getDy());
	}
	
	public void mult(double k) {
		setDx(k * getDx());
		setDy(k * getDy());
	}
	
	/** Retourne la projection du vecteur courant sur le vecteur passé en paramètre */
	public Vecteur2D projection(Vecteur2D vect) {
		double norme = (Vecteur2D.produit_scalaire(this, vect)) / Math.pow(vect.getNorme(), 2);
		double dx, dy;
		dx = getDx() * norme;
		dy = getDy() * norme;
		return new Vecteur2D(dx, dy);
	}
	
	/** Rotation du vecteur autour d'un point et avec un angle donnée */
	public void rotationAxe(Point2D point, int degre) {
		double radian = ((double)degre / 180.0) * Math.PI;
		double x = getDx();
		double y = getDy();
		double cos = Math.cos(radian);
		double sin = Math.sin(radian);
		setDx((x * cos) + (y * sin));
		setDy((x * -sin) + (y * cos));
	}
	
	/** Retourne le produit scalaire de deux vecteurs en 3 dimensions */
	public static double produit_scalaire(Vecteur2D vect1, Vecteur2D vect2) {
		return (vect1.getDx() * vect2.getDx() + vect1.getDy() * vect2.getDy());
	}
	
	/** Retourne le produit vectoriel de deux vecteurs en 3 dimensions */
	public static double produit_vectoriel(Vecteur2D vect1, Vecteur2D vect2) {
		return (vect1.getDx() * vect2.getDy() - vect1.getDy() * vect2.getDx());
	}
	
	/** Retourne le cosinus de l'angle formé par deux vecteurs */
	/*public static double cosinus_angle(Vecteur2D vect1, Vecteur2D vect2) {
		return (Math.abs(Vecteur2D.produit_scalaire(vect1, vect2)) / (vect1.getNorme() * vect2.getNorme()));
	}*/
	
	/** Retourne le sinus de l'angle formé par deux vecteurs */
	/*public static double sinus_angle(Vecteur2D vect1, Vecteur2D vect2) {
		return (Math.abs(Vecteur2D.produit_vectoriel(vect1, vect2)) / (vect1.getNorme() * vect2.getNorme()));
	}*/
}

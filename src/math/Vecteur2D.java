package math;

public class Vecteur2D {
	
	private double dist_x, dist_y;
	
	/** Constructeur */
	public Vecteur2D(double dist_x, double dist_y) {
		this.dist_x = dist_x;
		this.dist_y = dist_y;
	}
	
	/** Constructeur */
	public Vecteur2D(Vecteur2D vect) {
		this.dist_x = vect.getDx();
		this.dist_y = vect.getDy();
	}
	
	/** Constructeur */
	public Vecteur2D(Point2D a, Point2D b) {
		this.dist_x = b.getX() - a.getX();
		this.dist_y = b.getY() - a.getY();
	}
	
	/** Constructeur */
	public Vecteur2D(Point2D point) {
		this.dist_x = point.getX();
		this.dist_y = point.getY();
	}
	
	/** Constructeur */
	public Vecteur2D() {
		this.dist_x = 0;
		this.dist_y = 0;
	}
	
	/** Retourne la composante x */
	public double getDx() {
		return dist_x;
	}
	
	/** Retourne la composante y */
	public double getDy() {
		return dist_y;
	}
	
	/** Set la composante x */
	public void setDx(double dist_x) {
		this.dist_x = dist_x;
	}
	
	/** Set la composante y */
	public void setDy(double dist_y) {
		this.dist_y = dist_y;
	}
	
	/** Retourne la norme du vecteur */
	public double getNorme() {
		return Math.sqrt(Math.pow(getDx(), 2) + Math.pow(getDy(), 2));
	}
	
	/** Retourne le vecteur unitaire */
	public Vecteur2D getVecteurUnitaire() {
		double norme = getNorme();
		double dx, dy;
		dx = getDx() / norme;
		dy = getDy() / norme;
		return new Vecteur2D(dx, dy);
	}
	
	/** Normalise le vecteur */
	public void normalized() {
		double norme = getNorme();
		setDx(getDx() / norme);
		setDy(getDy() / norme);
	}
	
	/** Ajoute le vecteur passe en parametre */
	public void plus(Vecteur2D vect) {
		setDx(getDx() + vect.getDx());
		setDy(getDy() + vect.getDy());
	}
	
	/** Soustrait le vecteur passe en parametre */
	public void minus(Vecteur2D vect) {
		setDx(getDx() - vect.getDx());
		setDy(getDy() - vect.getDy());
	}
	
	/** Multiplie par le coefficent passe en parametre */
	public void mult(double k) {
		setDx(k * getDx());
		setDy(k * getDy());
	}
	
	/** Retourne la projection du vecteur courant sur le vecteur passe en parametre */
	public Vecteur2D projection(Vecteur2D vect) {
		double norme = (Vecteur2D.produit_scalaire(this, vect)) / Math.pow(vect.getNorme(), 2);
		double dx, dy;
		dx = getDx() * norme;
		dy = getDy() * norme;
		return new Vecteur2D(dx, dy);
	}
	
	/** Rotation du vecteur autour d'un point et avec un angle donne */
	public void rotationAxe(Point2D point, int degre) {
		double radian = ((double)degre / 180.0) * Math.PI;
		double x = getDx();
		double y = getDy();
		double cos = Math.cos(radian);
		double sin = Math.sin(radian);
		setDx((x * cos) + (y * sin));
		setDy((x * -sin) + (y * cos));
	}
	
	/** Representation textuelle d'un Vecteur2D */
	public String toString() {
		return "(" + ((int)(getDx()*100))/100.0 + " , " + ((int)(getDy()*100))/100.0 + ")";
	}
	
	/** Retourne le produit scalaire de deux vecteurs en 3 dimensions */
	public static double produit_scalaire(Vecteur2D vect1, Vecteur2D vect2) {
		return (vect1.getDx() * vect2.getDx() + vect1.getDy() * vect2.getDy());
	}
	
	/** Retourne le produit vectoriel de deux vecteurs en 3 dimensions */
	public static double produit_vectoriel(Vecteur2D vect1, Vecteur2D vect2) {
		return (vect1.getDx() * vect2.getDy() - vect1.getDy() * vect2.getDx());
	}
	
	/** Retourne le cosinus de l'angle forme par deux vecteurs */
	public static double cosinus_angle(Vecteur2D vect1, Vecteur2D vect2) {
		return (Math.abs(Vecteur2D.produit_scalaire(vect1, vect2)) / (vect1.getNorme() * vect2.getNorme()));
	}
	
	/** Retourne le sinus de l'angle forme par deux vecteurs */
	public static double sinus_angle(Vecteur2D vect1, Vecteur2D vect2) {
		return (Math.abs(Vecteur2D.produit_vectoriel(vect1, vect2)) / (vect1.getNorme() * vect2.getNorme()));
	}
	
	/** Retourne vrai si les deux vecteurs sont colinaires */
	public static boolean colineaire(Vecteur2D vect1, Vecteur2D vect2) {
		return (Math.abs(vect1.getDx()/vect2.getDx() - vect1.getDy()/vect2.getDy()) < 0.01);
	}
}

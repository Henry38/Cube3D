package math;

public class Vecteur3D {
	
	private double dist_x, dist_y, dist_z;
	
	/** Constructeur */
	public Vecteur3D(double dist_x, double dist_y, double dist_z) {
		this.dist_x = dist_x;
		this.dist_y = dist_y;
		this.dist_z = dist_z;
	}
	
	/** Constructeur */
	public Vecteur3D(Vecteur3D vect) {
		this.dist_x = vect.getDx();
		this.dist_y = vect.getDy();
		this.dist_z = vect.getDz();
	}
	
	/** Constructeur */
	public Vecteur3D(Point3D a, Point3D b) {
		this.dist_x = b.getX() - a.getX();
		this.dist_y = b.getY() - a.getY();
		this.dist_z = b.getZ() - a.getZ();
	}
	
	/** Constructeur */
	public Vecteur3D(Point3D point) {
		this.dist_x = point.getX();
		this.dist_y = point.getY();
		this.dist_z = point.getZ();
	}
	
	/** Constructeur */
	public Vecteur3D() {
		this.dist_x = 0;
		this.dist_y = 0;
		this.dist_z = 0;
	}
	
	/** Retourne la composante x */
	public double getDx() {
		return dist_x;
	}
	
	/** Retourne la composante y */
	public double getDy() {
		return dist_y;
	}
	
	/** Retourne la composante z */
	public double getDz() {
		return dist_z;
	}
	
	/** Set la composante x */
	public void setDx(double dist_x) {
		this.dist_x = dist_x;
	}
	
	/** Set la composante y */
	public void setDy(double dist_y) {
		this.dist_y = dist_y;
	}
	
	/** Set la composante z */
	public void setDz(double dist_z) {
		this.dist_z = dist_z;
	}
	
	/** Retourne la norme du vecteur */
	public double getNorme() {
		return Math.sqrt(Math.pow(getDx(), 2) + Math.pow(getDy(), 2) + Math.pow(getDz(), 2));
	}
	
	/** Retourne le vecteur unitaire */
	public Vecteur3D getVecteurUnitaire() {
		double norme = getNorme();
		double dx, dy, dz;
		dx = getDx() / norme;
		dy = getDy() / norme;
		dz = getDz() / norme;
		return new Vecteur3D(dx, dy, dz);
	}
	
	/** Normalise le vecteur */
	public void normalized() {
		double norme = getNorme();
		setDx(getDx() / norme);
		setDy(getDy() / norme);
		setDz(getDz() / norme);
	}
	
	/** Ajoute le vecteur passe en parametre */
	public void plus(Vecteur3D vect) {
		setDx(getDx() + vect.getDx());
		setDy(getDy() + vect.getDy());
		setDz(getDz() + vect.getDz());
	}
	
	/** Soustrait le vecteur passe en parametre */
	public void minus(Vecteur3D vect) {
		setDx(getDx() - vect.getDx());
		setDy(getDy() - vect.getDy());
		setDz(getDz() - vect.getDz());
	}
	
	/** Multiplie par le coefficent passe en parametre */
	public void mult(double k) {
		setDx(k * getDx());
		setDy(k * getDy());
		setDz(k * getDz());
	}
	
	/** Retourne la projection du vecteur courant sur le vecteur passe en parametre */
	public Vecteur3D projection(Vecteur3D vect) {
		double norme = (Vecteur3D.produit_scalaire(this, vect)) / Math.pow(vect.getNorme(), 2);
		double dx, dy, dz;
		dx = getDx() * norme;
		dy = getDy() * norme;
		dz = getDz() * norme;
		return new Vecteur3D(dx, dy, dz);
	}
	
	/** Rotation du vecteur autour d'un axe et avec un angle donne */
	public void rotationAxe(Vecteur3D axe, double radian) {
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
	
	/** Representation textuelle d'un Vecteur2D */
	public String toString() {
		return "(" + ((int)(getDx()*100))/100.0 + " , " + ((int)(getDy()*100))/100.0 + " , " + ((int)(getDz()*100))/100.0 + ")";
	}
	
	/** Retourne le Vecteur3D reflechi par la normal
	 * e & normal sont normalises
	 * */
	public static Vecteur3D reflect(Vecteur3D e, Vecteur3D normal) {
		double dot = Vecteur3D.produit_scalaire(e, normal);
		double dx = e.getDx() - 2 * dot * normal.getDx();
		double dy = e.getDy() - 2 * dot * normal.getDy();
		double dz = e.getDz() - 2 * dot * normal.getDz();
		return new Vecteur3D(dx, dy, dz);
	}
	
	/** Retourne le produit scalaire de deux vecteurs */
	public static double produit_scalaire(Vecteur3D vect1, Vecteur3D vect2) {
		return (vect1.getDx() * vect2.getDx() + vect1.getDy() * vect2.getDy() + vect1.getDz() * vect2.getDz());
	}
	
	/** Retourne le produit vectoriel de deux vecteurs */
	public static Vecteur3D produit_vectoriel(Vecteur3D vect1, Vecteur3D vect2) {
		double dx, dy, dz;
		dx = (vect1.getDy() * vect2.getDz()) - (vect1.getDz() * vect2.getDy());
		dy = (vect1.getDz() * vect2.getDx()) - (vect1.getDx() * vect2.getDz());
		dz = (vect1.getDx() * vect2.getDy()) - (vect1.getDy() * vect2.getDx());
		return new Vecteur3D(dx, dy, dz);
	}
	
	/** Retourne le cosinus de l'angle forme par les deux vecteurs */
	public static double cosinus(Vecteur3D vect1, Vecteur3D vect2) {
		return produit_scalaire(vect1, vect2) / (vect1.getNorme() * vect2.getNorme());
	}
	
	/** Retourne le sinus de l'angle forme par les deux vecteurs */
	public static double sinus(Vecteur3D vect1, Vecteur3D vect2) {
		return produit_vectoriel(vect1, vect2).getNorme() / (vect1.getNorme() * vect2.getNorme());
	}
	
	/** Retourne vrai si les deux vecteurs sont colinaires */
	public static boolean colineaire(Vecteur3D vect1, Vecteur3D vect2) {
		boolean a = Math.abs(vect1.getDx()/vect2.getDx() - vect1.getDy()/vect2.getDy()) < 0.01;
		boolean b = Math.abs(vect1.getDy()/vect2.getDy() - vect1.getDz()/vect2.getDz()) < 0.01;
		return (a && b);
	}
}

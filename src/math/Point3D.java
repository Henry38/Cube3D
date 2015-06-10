package math;

public class Point3D {
	
	private Matrix matrix;
	
	public Point3D() {
		matrix = new Matrix(3, 1);
		setX(0);
		setY(0);
		setZ(0);
	}
	
	public Point3D(double x, double y, double z) {
		matrix = new Matrix(3, 1);
		setX(x);
		setY(y);
		setZ(z);
	}
	
	public Point3D clone() {
		return new Point3D(getX(), getY(), getZ());
	}
	
	/** Retourne la composante x du point */
	public double getX() {
		return matrix.get(0, 0);
	}
	
	/** Retourne la composante y du point */
	public double getY() {
		return matrix.get(1, 0);
	}
	
	/** Retourne la composante z du point */
	public double getZ() {
		return matrix.get(2, 0);
	}
	
	public void setX(double x) {
		matrix.set(0, 0, x);
	}
	
	public void setY(double y) {
		matrix.set(1, 0, y);
	}
	
	public void setZ(double z) {
		matrix.set(2, 0, z);
	}
	
	public double getModule() {
		return Math.sqrt(getX()*getX() + getY()*getY() + getZ()*getZ());
	}
	
	public final Matrix getMatrix() {
		return matrix;
		/*return new Matrix(new double[][] {
				{getX()},
				{getY()},
				{getZ()}
		});*/
	}
	
	public String toString() {
		return "(" + getX() + ", " + getY() + ", " + getZ() + ")";
	}
	
	public void translation(double dx, double dy, double dz) {
		setX(getX() + dx);
		setY(getY() + dy);
		setZ(getZ() + dz);
	}
	
	public void translation(Vecteur3D t) {
		translation(t.getDx(), t.getDy(), t.getDz());
	}
	
	/** Rotation d'un point autour d'un axe et avec un angle donnee */
	public void rotationAxe(Vecteur3D axe, int degre) {
		double radian = ((double) degre / 180.0) * Math.PI;
		double x = getX();
		double y = getY();
		double z = getZ();
		double cos = Math.cos(radian);
		double sin = Math.sin(radian);
		Vecteur3D vect = axe.getVecteurUnitaire();
		setX(   x * (Math.pow(vect.getDx(), 2) + (1 - Math.pow(vect.getDx(), 2)) * cos) +
				y * (vect.getDx()*vect.getDy() * (1 - cos) - vect.getDz()*sin) +
				z * (vect.getDx()*vect.getDz() * (1 - cos) + vect.getDy()*sin) );
		setY(   x * (vect.getDx()*vect.getDy() * (1 - cos) + vect.getDz()*sin) +
				y * (Math.pow(vect.getDy(), 2) + (1 - Math.pow(vect.getDy(), 2)) * cos) +
				z * (vect.getDy()*vect.getDz() * (1 - cos) - vect.getDx()*sin) );
		setZ(   x * (vect.getDx()*vect.getDz() * (1 - cos) - vect.getDy()*sin) +
				y * (vect.getDy()*vect.getDz() * (1 - cos) + vect.getDx()*sin) +
				z * (Math.pow(vect.getDz(), 2) + (1 - Math.pow(vect.getDz(), 2)) * cos) );
	}
	
	public static double distance(Point3D p1, Point3D p2) {
		Vecteur3D vect = new Vecteur3D(p1, p2);
		return vect.getNorme();
	}
	
	public static boolean distinct(Point3D p1, Point3D p2) {
		return (Point3D.distance(p1, p2) > 0.001);
	}
	
	public static boolean aligner(Point3D p1, Point3D p2, Point3D p3) {
		Vecteur3D vect1 = new Vecteur3D(p1, p2);
		Vecteur3D vect2 = new Vecteur3D(p1, p3);
		return Vecteur3D.colineaire(vect1, vect2);
	}
}

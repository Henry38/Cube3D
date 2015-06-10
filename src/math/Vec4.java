package math;

public class Vec4 {
	
	private double[] matrix;
	
	/** Constructeur */
	public Vec4() {
		this.matrix = new double[] {0, 0, 0, 1};
	}
	
	public Vec4(Point3D point) {
		this.matrix = new double[] {point.getX(), point.getY(), point.getZ(), 1};
	}

	public Vec4(Vecteur3D vect) {
		this.matrix = new double[] {vect.getDx(), vect.getDy(), vect.getDz(), 0};
	}
	
//	public Vec4(Point3D point1, Point3D point2) {
//		double x = point2.getX() - point1.getX();
//		double y = point2.getY() - point1.getY();
//		double z = point2.getZ() - point1.getZ();
//		this.matrix = new double[] {x, y, z, 0};
//	}
	
	public double getX() {
		return matrix[0];
	}
	
	public double getY() {
		return matrix[1];
	}
	
	public double getZ() {
		return matrix[2];
	}
	
	public double getW() {
		return matrix[3];
	}
	
	public double get(int i) {
		return matrix[i];
	}
	
	public void set(int i, double d) {
		matrix[i] = d;
	}
	
	public void set(Point3D point) {
		set(0, point.getX());
		set(1, point.getY());
		set(2, point.getZ());
		set(3, 1);
	}
	
	public Point3D normalized() {
		if (Math.abs(getW()) < 0.001) {
			throw new ArithmeticException("Coordonnees homogenes : division par 0");
		}
		double x = getX() / getW();
		double y = getY() / getW();
		double z = getZ() / getW();
		return new Point3D(x, y, z);
	}
	
	public Vecteur3D toVecteur3D() {
		return new Vecteur3D(getX(), getY(), getZ());
	}
	
	public String toString() {
		return "(" + getX() + ", " + getY() + ", " + getZ() + ", " + getW() + ")";
	}
}

package math;

public class Vec4 {
	
	double x, y, z, w;
	
	/** Constructeur */
	public Vec4(double x, double y, double z, double w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	/** Constructeur */
	public Vec4(Point3D point) {
		this(point.getX(), point.getY(), point.getZ(), 1);
	}
	
	/** Constructeur */
	public Vec4(Vecteur3D vect) {
		this(vect.getDx(), vect.getDy(), vect.getDz(), 0);
	}
	
	/** Constructeur */
	public Vec4() {
		this(0, 0, 0, 1);
	}
	
	/** Retourne la composante x */
	public double getX() {
		return x;
	}
	
	/** Retourne la composante y */
	public double getY() {
		return y;
	}
	
	/** Retourne la composante z */
	public double getZ() {
		return z;
	}
	
	/** Retourne la composante w */
	public double getW() {
		return w;
	}
	
	/** Retourne la composante i */
	public double get(int i) {
		double res = 0;
		switch (i) {
		case 0:
			res = getX();
			break;
		case 1:
			res = getY();
			break;
		case 2:
			res = getZ();
			break;
		case 3:
			res = getW();
			break;
		default:
			break;
		}
		return res;
	}
	
	/** Set la composante i */
	public void set(int i, double d) {
		switch (i) {
		case 0:
			this.x = d;
			break;
		case 1:
			this.y = d;
			break;
		case 2:
			this.z = d;
			break;
		case 3:
			this.w = d;
			break;
		default:
			break;
		}
	}
	
	/** Normalise le Vec4 */
	public Vec4 normalized() {
		if (Math.abs(getW()) < 0.0001) {
			throw new ArithmeticException("Coordonnees homogenes : division par 0");
		}
		double x = getX() / getW();
		double y = getY() / getW();
		double z = getZ() / getW();
		return new Vec4(x, y, z, 1);
	}
	
	/** Converti en Point3D */
	public Point3D toPoint3D() {
		double w  = getW();
		return new Point3D(getX()/w, getY()/w, getZ()/w);
	}
	
	/** Converti en Vecteur3D */
	public Vecteur3D toVecteur3D() {
		return new Vecteur3D(getX(), getY(), getZ());
	}
	
	public String toString() {
		return "(" + getX() + ", " + getY() + ", " + getZ() + ", " + getW() + ")";
	}
}

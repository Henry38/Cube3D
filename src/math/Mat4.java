package math;

public class Mat4 {
	
	private double[][] matrix;
	
	/** Constructeur */
	public Mat4() {
		this.matrix = new double[][] {
				{1, 0, 0, 0},
				{0, 1, 0, 0},
				{0, 0, 1, 0},
				{0, 0, 0, 1}
		};
	}
	
	/** Constructeur */
	public Mat4(double[][] matrix) {
		if (matrix.length != 4) {
			throw new ArithmeticException("Dimension Mat4 non conforme : " + matrix.length + " lignes");
		}
		for (int i = 0; i < 4; i++) {
			if (matrix[i].length != 4) {
				throw new ArithmeticException("Dimension Mat4 non conforme : " + matrix[i].length + " colonnes");
			}
		}
		this.matrix = matrix;
	}
	
	/** Constructeur */
	public Mat4(Base3D base) {
		Point3D origin = base.getOrigine();
		Vecteur3D oi = base.oi;
		Vecteur3D oj = base.oj;
		Vecteur3D ok = base.ok;
		this.matrix = new double[][] {
				{oi.getDx(), oj.getDx(), ok.getDx(), origin.getX()},
				{oi.getDy(), oj.getDy(), ok.getDy(), origin.getY()},
				{oi.getDz(), oj.getDz(), ok.getDz(), origin.getZ()},
				{         0,          0,          0,             1}
		};
	}
	
	public double get(int i, int j) {
		return matrix[i][j];
	}
	
	public void set(int i, int j, double d) {
		matrix[i][j] = d;
	}
	
	public Mat4 mult(Mat4 matrix) {
		Mat4 res = new Mat4();
		double d;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				d = 0;
				for (int k = 0; k < 4; k++) {
					d += get(i, k) * matrix.get(k, j);
				}
				res.set(i, j, d);
			}
		}
		return res;
	}
	
	public Vec4 mult(Vec4 vect) {
		Vec4 res = new Vec4();
		double d;
		for (int i = 0; i < 4; i++) {
			d = 0;
			for (int k = 0; k < 4; k++) {
				d += get(i, k) * vect.get(k);
			}
			res.set(i, d);
		}
		return res;
	}
	
	public String toString() {
		String s = "";
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (get(i, j) >= 0) {
					s += " ";
				}
				s += Math.round(1000*get(i, j))/1000.0 + " ";
			}
			s += "\n";
		}
		return s;
	}
}

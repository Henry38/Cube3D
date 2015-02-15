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
	
	public final Matrix getMatrix() {
		Matrix res = new Matrix(3, 0);
		res.concatByColumn(oi.getMatrix());
		res.concatByColumn(oj.getMatrix());
		res.concatByColumn(ok.getMatrix());
		return res;
	}
	
	public void translation(double dx, double dy, double dz) {
		origine.translation(dx, dy, dz);
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
	
	/** Renvoie la matrice de translation de la base courante vers la base donnée exprimé dans la base courante */
	public Matrix getMatrixTranslation(Base3D base) {
		//Matrix mp = getMatrixPassage(base);
		double dx, dy, dz;
		dx = base.getOrigine().getX() - getOrigine().getX();
		dy = base.getOrigine().getY() - getOrigine().getY();
		dz = base.getOrigine().getZ() - getOrigine().getZ();
		Matrix m = new Matrix(new double[][] {
				{dx},
				{dy},
				{dz}
		});
		Matrix res = getMatrix().getMatrixTranspose(); 
		res.mult(m);
		return res;
	}
	
	/** Renvoie la matrice de passage de la base courante vers la base passée en paramètre */
	public Matrix getMatrixPassage(Base3D base) {
		Matrix A = getMatrix();
		Matrix res = new Matrix(3, 0);
		for (Vecteur3D vect : base.getVecteurs()) {
			res.concatByColumn(A.solve(vect.getMatrix()));
		}
		return res;
	}
	
	/** Retourne le point 3D exprimé dans la base courante défini depuis la base passé en paramètre */
	public Point3D getPoint3DFromBase(Point3D point, Base3D base) {
		Matrix R = getMatrixPassage(base);
		Matrix T = getMatrixTranslation(base);
		
		//Matrix p = (R.mult(point.getMatrix()));
		//p = p.plus(T);
		R.mult(point.getMatrix());
		R.plus(T);
		
		return new Point3D(R.get(0, 0), R.get(1, 0), R.get(2, 0));
	}
	
}

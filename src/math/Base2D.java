package math;

public class Base2D {
	
	private Point2D origine;
	public Vecteur2D oi, oj;
	
	/** Constructeur */
	public Base2D(Point2D origine) {
		this.origine = origine;
		this.oi = new Vecteur2D(1, 0);
		this.oj = new Vecteur2D(0, 1);
	}
	
	public Base2D(Point2D origine, Vecteur2D oi, Vecteur2D oj) {
		this.origine = origine;
		this.oi = oi;
		this.oj = oj;
	}
	
	public final Point2D getOrigine() {
		return origine;
	}
	
	public final Vecteur2D[] getVecteurs() {
		return new Vecteur2D[] {oi, oj};
	}
	
	public final Matrix getMatrix() {
		Matrix res = new Matrix(2, 0);
		res.concatByColumn(oi.getMatrix());
		res.concatByColumn(oj.getMatrix());
		return res;
	}
	
	public void translation(double dx, double dy) {
		origine.translation((int) dx, (int) dy);
	}
	
	public void rotation(int degre) {
		oi.rotationAxe(origine, degre);
		oj.rotationAxe(origine, degre);
	}
	
	public Matrix getMatrixTranslation(Base2D base) {
		double dx, dy;
		dx = base.getOrigine().getX() - getOrigine().getX();
		dy = base.getOrigine().getY() - getOrigine().getY();
		Matrix m = new Matrix(new double[][] {
				{dx},
				{dy}
		});
		Matrix res = getMatrix().getMatrixTranspose(); 
		res.mult(m);
		return res;
		//return getMatrixPassage(base).mult(m);
	}
	
	/* La matrice de passage de base vers base1 est une matrice dont les
	 * colonnes représentent les vecteurs de base1 exprimé dans base
	 * X dans base et X' ce même point dans base1
	 * matricePassage : base1 vers base :
	 * matricePassage * X' = X
	 */
	// Les colonnes de la matrice de passage sont les coordonnées des vecteurs de la nouvelle base
	// exprimés dans l'ancienne base.
	/** Renvoie la matrice de passage pour passer de la base courrante a celle passee en parametre */
	public Matrix getMatrixPassage(Base2D base) {
		Matrix A = getMatrix();
		Matrix res = new Matrix(2, 0);
		for (Vecteur2D vect : base.getVecteurs()) {
			res.concatByColumn(A.solve(vect.getMatrix()));
		}
		return res;
	}
	
	public Point2D getPointFromBase(Point2D point, Base2D base) {
		Matrix R = getMatrixPassage(base);
		//System.out.println(R);
		Matrix T = getMatrixTranslation(base);
		//System.out.println(T);
		
		//Matrix p = (R.mult(point.getMatrix()));
		//p = p.plus(T);
		R.mult(point.getMatrix());
		R.plus(T);
		
		return new Point2D(R.get(0, 0), R.get(1, 0));
	}
	
	/*public Matrix getMatrixTranslation(Base2D base) {
		double dx, dy;
		dx = getOrigine().getX() - base.getOrigine().getX();
		dy = getOrigine().getY() - base.getOrigine().getY();
		return new Matrix(new double[][] {
				{dx},
				{dy}
		});
	}
	
	public Matrix getMatrixPassage(Base2D base) {
		Matrix A = base.getMatrix();
		Matrix res = new Matrix(2, 0);
		for (Vecteur2D vect : getVecteurs()) {
			res = res.concatByColumn(A.solve(vect));
		}
		return res;
	}
	
	public Point2D getPointFromBase(Point2D point, Base2D base) {
		Matrix R = getMatrixPassage(base);
		Matrix T = getMatrixTranslation(base);
		
		Matrix p = (R.mult(point.getMatrix()));
		p = p.plus(R.mult(T));
		
		return new Point2D(p.get(0, 0), p.get(1, 0));
	}*/
	
}

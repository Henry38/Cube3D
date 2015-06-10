package math;

public class Matrix {
	
	private double[][] matrix;
	
	/** Constructeur */
	public Matrix(int nbLine, int nbColumn) {
		if (nbLine < 0 || nbColumn < 0) {
			throw new ArithmeticException("Dimension matrice non conforme");
		}
		this.matrix = new double[nbLine][];
		for (int i=0; i<nbLine; i++) {
			matrix[i] = new double[nbColumn];
		}
	}
	
	/** Constructeur */
	public Matrix(double[][] tab) {
		this.matrix = new double[tab.length][];
		for (int i=0; i<tab.length; i++) {
			matrix[i] = new double[tab[0].length];
			for (int j=0; j<tab[i].length; j++) {
				set(i, j, tab[i][j]);
			}
		}
	}
	
	/** Retourne la matrice clonée */
	public Matrix clone() {
		Matrix m = new Matrix(getLineDimension(), getColumnDimension());
		for (int i=0; i<getLineDimension(); i++) {
			for (int j=0; j<getColumnDimension(); j++) {
				m.set(i, j, get(i, j));
			}
		}
		return m;
	}
	
	/** Retourne le nombre ligne */
	public int getLineDimension() {
		return matrix.length;
	}
	
	/** Retourne le nombre de colonne */
	public int getColumnDimension() {
		if (matrix.length > 0) {
			return matrix[0].length;
		} else {
			return 0;
		}
	}
	
	/** Retourne l'élément ligne i colonne j */
	public double get(int i, int j) {
		return matrix[i][j];
	}
	
	public Matrix getLine(int i) {
		Matrix m = new Matrix(1, getColumnDimension());
		for (int j=0; j<getColumnDimension(); j++) {
			m.set(0, j, get(i, j));
		}
		return m;
	}
	
	public Matrix getColumn(int j) {
		Matrix m = new Matrix(getLineDimension(), 1);
		for (int i=0; i<getLineDimension(); i++) {
			m.set(i, 0, get(i, j));
		}
		return m;
	}
	
	public void set(int i, int j, double d) {
		matrix[i][j] = d;
	}
	
	/** Redimensionne la matrice */
	public void resize(int nbLine, int nbColumn) {
		if (nbLine < 0 || nbColumn < 0) {
			throw new ArithmeticException("Dimension matrice non conforme");
		}
		
		double[][] res = new double[nbLine][];
		for (int i=0; i<nbLine; i++) {
			res[i] = new double[nbColumn];
		}
		for (int i=0; i<Math.min(nbLine, getLineDimension()); i++) {
			for (int j=0; j<Math.min(nbColumn, getColumnDimension()); j++) {
				res[i][j] = get(i, j);
			}
		}
		matrix = res;
	}
	
	public void inverseLine(int line1, int line2) {
		if (line1 < 0 || line1 >= getLineDimension() || line2 < 0 || line2 >= getLineDimension()) {
			throw new ArithmeticException("Indice en dehors de la matrice");
		}
		
		Matrix tmp = getLine(line1);
		for (int j=0; j<getColumnDimension(); j++) {
			set(line1, j, get(line2, j));
			set(line2, j, tmp.get(0, j));
		}
	}
	
	public void inverseColumn(int column1, int column2) {
		if (column1 < 0 || column1 >= getColumnDimension() || column2 < 0 || column2 >= getColumnDimension()) {
			throw new ArithmeticException("Indice en dehors de la matrice");
		}
		
		Matrix tmp = getColumn(column1);
		for (int i=0; i<getLineDimension(); i++) {
			set(i, column1, get(i, column2));
			set(i, column2, tmp.get(i, 0));
		}
	}
	
	public void concatByLine(Matrix m) {
		if (getColumnDimension() != m.getColumnDimension()) {
			throw new ArithmeticException("Dimension matrice non conforme");
		}
		
		int nbLine = getLineDimension() + m.getLineDimension();
		int nbColumn = getColumnDimension();
		resize(nbLine, nbColumn);
		for (int i=0; i<m.getLineDimension(); i++) {
			for (int j=0; j<m.getColumnDimension(); j++) {
				set(nbLine-m.getLineDimension()+i, j, m.get(i, j));
			}
		}
	}
	
	public void concatByColumn(Matrix m) {
		if (getLineDimension() != m.getLineDimension()) {
			throw new ArithmeticException("Dimension matrice non conforme");
		}
		
		int nbLine = getLineDimension();
		int nbColumn = getColumnDimension() + m.getColumnDimension();
		resize(nbLine, nbColumn);
		for (int i=0; i<m.getLineDimension(); i++) {
			for (int j=0; j<m.getColumnDimension(); j++) {
				set(i, nbColumn-m.getColumnDimension()+j, m.get(i, j));
			}
		}
	}
	
	/** Retourne l'addition de la matrice par m */
	public void plus(Matrix m) {
		if (getLineDimension() != m.getLineDimension() && getColumnDimension() != m.getColumnDimension()) {
			throw new ArithmeticException("Dimension matrice non conforme");
		}
		
		for (int i=0; i<getLineDimension(); i++) {
			for (int j=0; j<getColumnDimension(); j++) {
				set(i, j, get(i,j)+m.get(i, j));
			}
		}
	}
	
	/** Retourne la soustraction de la matrice par m */
	public void minus(Matrix m) {
		if (getLineDimension() != m.getLineDimension() && getColumnDimension() != m.getColumnDimension()) {
			throw new ArithmeticException("Dimension matrice non conforme");
		}
		
		for (int i=0; i<getLineDimension(); i++) {
			for (int j=0; j<getColumnDimension(); j++) {
				set(i, j, get(i,j)-m.get(i, j));
			}
		}
	}
	
	public void mult(double k) {
		for (int i=0; i<getLineDimension(); i++) {
			for (int j=0; j<getColumnDimension(); j++) {
				set(i, j, get(i, j)*k);
			}
		}
	}
	
	/** Retourne la mutliplication de la matrice par m */
	public Matrix mult(Matrix m) {
		if (getColumnDimension() != m.getLineDimension()) {
			throw new ArithmeticException("Dimension matrice non conforme");
		}
		
		int nbLine = getLineDimension();
		int nbColumn = m.getColumnDimension();
		double[][] res = new double[nbLine][];
		for (int i=0; i<nbLine; i++) {
			res[i] = new double[nbColumn];
			for (int j=0; j<nbColumn; j++) {
				for (int k=0; k<getColumnDimension(); k++) {
					res[i][j] += get(i, k)*m.get(k, j);
				}
			}
		}
		return new Matrix(res);
	}
	
	/** Retourne la matrice transposée */
	public Matrix getMatrixTranspose() {
		Matrix m = new Matrix(getColumnDimension(), getLineDimension());
		for (int i=0; i<getLineDimension(); i++)  {
			for (int j=0; j<getColumnDimension(); j++)  {
				m.set(j, i, get(i, j));
			}
		}
		return m;
	}
	
	/** Retourne la matrice X solution de AX = B, B étant passée en paramètre */
	public Matrix solve(Matrix m) {
		if (getLineDimension() != getColumnDimension()) {
			throw new ArithmeticException("Matrice non carrée");
		} else if (m.getColumnDimension() != 1) {
			throw new ArithmeticException("Dimension matrice non conforme");
		}
		
		int a;
		double p, coef;
		Matrix A = clone();
		Matrix b = m.clone();
		// Triangularisation de la matrice
		for (int k=0; k<getColumnDimension(); k++) {
			p = A.get(k, k);
			// Recherche a cause du pivot nul
			if (p == 0) {
				a = k+1;
				while (A.get(a, k) == 0) {
					a++;
					if (a >= getLineDimension()) {
						throw new ArithmeticException("Pivot nul = atrice non inversible");
					}
				}
				A.inverseLine(k, a);
				b.inverseLine(k, a);
			}
			// Mise à 0 des coefficients de chaque ligne
			for (int i=k+1; i<getLineDimension(); i++) {
				if (A.get(i, k) != 0) {
					coef = p / A.get(i, k);
					for (int j=k; j<getColumnDimension(); j++) {
						A.set(i, j, (A.get(i, j)*coef) - A.get(k, j));
					}
					b.set(i, 0, b.get(i, 0)*coef - b.get(k, 0));
				}
			}
		}
		
		Matrix res = new Matrix(getLineDimension(), 1);
		// Calcul des solutions du systeme
		for (int i=getLineDimension()-1; i>=0; i--) {
			for (int j=getColumnDimension()-1; j>=i+1; j--) {
				b.set(i, 0, b.get(i, 0) - (A.get(i, j)*res.get(j, 0)));
			}
			res.set(i, 0, b.get(i, 0)/A.get(i, i));
		}
		
		return res;
	}
	
	public String toString() {
		String s = "";
		for (int i=0; i<getLineDimension(); i++) {
			for (int j=0; j<getColumnDimension(); j++) {
				if (get(i, j) >= 0) {
					s += " ";
				}
				s += Math.round(1000*get(i, j))/1000.0 + " ";
			}
			s += "\n";
		}
		return s;
	}
	
	public double[][] toArray() {
		double[][] res = new double[getLineDimension()][];
		for (int i=0; i<getLineDimension(); i++) {
			res[i] = new double[getColumnDimension()];
			for (int j=0; j<getColumnDimension(); j++) {
				res[i][j] = get(i, j);
			}
		}
		return res;
	}
	
}

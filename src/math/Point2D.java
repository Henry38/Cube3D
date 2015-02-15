package math;

public class Point2D {
	
	private Matrix matrix;
	
	public Point2D(double x, double y) {
		matrix = new Matrix(2, 1);
		setX(x);
		setY(y);
	}
	
	public double getX() {
		return matrix.get(0, 0);
	}
	
	public double getY() {
		return matrix.get(1, 0);
	}
	
	public void setX(double x) {
		matrix.set(0, 0, x);
	}
	
	public void setY(double y) {
		matrix.set(1, 0, y);
	}
	
	public Matrix getMatrix() {
		return new Matrix(new double[][] {
				{getX()},
				{getY()}
		});
	}
	
	public String toString() {
		return "(" + getX() + ", " + getY() + ")";
	}
	
	public void translation(double dx, double dy) {
		setX(getX() + dx);
		setY(getY() + dy);
	}
	
	/** Rotation d'un point autour d'un axe et avec un angle donnée */
	public void rotationAxe(Point2D point, int degre) {
		double radian = ((double) degre / 180.0) * Math.PI;
		double x = getX();
		double y = getY();
		double cos = Math.cos(radian);
		double sin = Math.sin(radian);
		setX((x * cos) + (y * sin));
		setY((x * -sin) + (y * cos));
	}
	
	public static double distance(Point2D p1, Point2D p2) {
		return Math.sqrt((p1.getX()-p2.getX())*(p1.getX()-p2.getX()) + (p1.getY()-p2.getY())*(p1.getY()-p2.getY()));
	}
	
}

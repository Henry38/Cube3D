package math;

public class Point2D {
	
	private int x, y;
	
	/** Constructeur */
	public Point2D() {
		this.x = 0;
		this.y = 0;
	}
	
	/** Constructeur */
	public Point2D(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/** Retourne la composante x du point */
	public int getX() {
		return x;
	}
	
	/** Retourne la composante y du point */
	public int getY() {
		return y;
	}
	
	/** Set la composante x du point */
	public void setX(int x) {
		this.x = x;
	}
	
	/** Set la composante y du point */
	public void setY(int y) {
		this.y = y;
	}
	
	/** Retourne la distance a l'origine */
	public double getModule() {
		return Math.sqrt(getX()*getX() + getY()*getY());
	}
	
	public String toString() {
		return "(" + getX() + ", " + getY() + ")";
	}
	
	/** Translate le point */
	public void translation(int dx, int dy) {
		setX(getX() + dx);
		setY(getY() + dy);
	}
	
	/** Rotation d'un point autour d'un axe et avec un angle donnée */
	public void rotationAxe(Point2D point, int degre) {
		double radian = ((double) degre / 180.0) * Math.PI;
		int x1 = getX();
		int y1 = getY();
		double cos = Math.cos(radian);
		double sin = Math.sin(radian);
		setX((int) ((x1 * cos) + (y1 * sin)));
		setY((int) ((x1 * -sin) + (y1 * cos)));
	}
	
	/** Retourne la distance entre deux points */
	public static double distance(Point2D p1, Point2D p2) {
		return Math.sqrt((p1.getX()-p2.getX())*(p1.getX()-p2.getX()) + (p1.getY()-p2.getY())*(p1.getY()-p2.getY()));
	}
	
	/** Retourne vrai si le point C est a droite de AB */
	public static boolean oriented2d(Point2D a, Point2D b, Point2D c) {
	    return ( ((b.getX()-a.getX())*(c.getY()-a.getY()) - (b.getY()-a.getY())*(c.getX()-a.getX())) >= 0 );
	}
	
	/** Retourne vrai si les deux points ont les memes coordonnees */
	public boolean equals(Point2D point) {
		return (getX() == point.getX() && getY() == point.getY());
	}
}

package world3d;

import math.Base3D;
import math.Mat4;
import math.Point3D;
import math.Vecteur3D;

public class Camera {
	
	public enum PROJECTION {PERSPECTIVE, ORTHOGRAPHIC};
	
	private Point3D pointCamera;
	private Point3D pointObserver;
	private PROJECTION projection;
	private double zNear, zFar;
	private double uMin, uMax, vMin, vMax;
	
	/** Constructeur */
	public Camera(Point3D pointCamera, Point3D pointObserver, PROJECTION projection) {
		this.pointCamera = pointCamera;
		this.pointObserver = pointObserver;
		this.projection = projection;
		this.zNear = 1;
		this.zFar = 60;
		this.uMin = -1;
		this.uMax = 1;
		this.vMin = -1;
		this.vMax = 1;
	}
	
	public final Point3D getOrigine() {
		return pointCamera;
	}
	
	public final Point3D getObserver() {
		return pointObserver;
	}
	
	public double getZNear() {
		return zNear;
	}
	
	public double getZFar() {
		return zFar;
	}
	
	/** Positionne la camera dans le repere World */
	public void placeCamera(int x, int y, int z) {
		pointCamera.setX(x);
		pointCamera.setY(y);
		pointCamera.setZ(z);
	}
	
	/** Defini le point du monde a regarder */
	public void lookAt(int x, int y, int z) {
		pointObserver.setX(x);
		pointObserver.setY(y);
		pointObserver.setZ(z);
	}
	
	public void translation(double dx, double dy, double dz) {
		pointCamera.translation(dx, dy, dz);
		pointObserver.translation(dx, dy, dz);
	}
	
	public final Base3D getBase() {
		Vecteur3D k = new Vecteur3D(pointCamera, pointObserver);
		Vecteur3D up = new Vecteur3D(0, 0, 1);
		if (Vecteur3D.colineaire(k, up)) {
			up.setDy(1);
			up.setDz(0);
		}
		if (Math.abs(Vecteur3D.produit_scalaire(k, up)) > 0.75) {
			
		}
		Vecteur3D u = Vecteur3D.produit_vectoriel(k, up);
		Vecteur3D v = Vecteur3D.produit_vectoriel(k, u);
		
		u.normalized();
		v.normalized();
		k.normalized();
		return new Base3D(getOrigine(), u, v, k);
	}
	
	public Mat4 getModel() {
		Base3D base = getBase();
		return new Mat4(base);
//		Point3D origin = base.getOrigine();
//		Vecteur3D oi = base.oi;
//		Vecteur3D oj = base.oj;
//		Vecteur3D ok = base.ok;
//		Mat4 m = new Mat4(new double[][] {
//				{oi.getDx(), oj.getDx(), ok.getDx(), origin.getX()},
//				{oi.getDy(), oj.getDy(), ok.getDy(), origin.getY()},
//				{oi.getDz(), oj.getDz(), ok.getDz(), origin.getZ()},
//				{         0,          0,          0,             1}
//		});
//		
//		return m;
	}
	
	/** Recupere la matrice camera */
	public Mat4 viewMat() {
		Vecteur3D k = new Vecteur3D(pointCamera, pointObserver);
		Vecteur3D up = new Vecteur3D(0, 0, 1);
		if (Vecteur3D.colineaire(k, up)) {
			up.setDy(1);
			up.setDz(0);
		}
		Vecteur3D u = Vecteur3D.produit_vectoriel(k, up);
		Vecteur3D v = Vecteur3D.produit_vectoriel(k, u);
		
		u.normalized();
		v.normalized();
		k.normalized();
		
		Vecteur3D t = new Vecteur3D(pointCamera, new Point3D(0, 0, 0));
		double dx = u.getDx()*t.getDx() + u.getDy()*t.getDy() + u.getDz()*t.getDz();
		double dy = v.getDx()*t.getDx() + v.getDy()*t.getDy() + v.getDz()*t.getDz();
		double dz = k.getDx()*t.getDx() + k.getDy()*t.getDy() + k.getDz()*t.getDz();
		Mat4 m = new Mat4(new double[][] {
				{u.getDx(), u.getDy(), u.getDz(), dx},
				{v.getDx(), v.getDy(), v.getDz(), dy},
				{k.getDx(), k.getDy(), k.getDz(), dz},
				{        0,         0,         0,  1}
		});
		
		return m;
	}
	
	public Mat4 projMat() {
		if (projection == PROJECTION.PERSPECTIVE) {
			Mat4 m = new Mat4(new double[][] {
					{2*zNear/(uMax-uMin),                   0,  -(uMax+uMin)/(uMax-uMin),                          0},
					{                  0, 2*zNear/(vMax-vMin),  -(vMax+vMin)/(vMax-vMin),                          0},
					{                  0,                   0, (zFar+zNear)/(zFar-zNear), -2*zFar*zNear/(zFar-zNear)},
					{                  0,                   0,                         1,                          0}
			});
			return m;
		} else {
			Mat4 m = new Mat4(new double[][] {
					{2/(uMax-uMin),             0,              0,   -(uMax+uMin)/(uMax-uMin)},
					{            0, 2/(vMax-vMin),              0,   -(vMax+vMin)/(vMax-vMin)},
					{            0,             0, 2/(zFar-zNear), -(zFar+zNear)/(zFar-zNear)},
					{            0,             0,              0,                          1}
			});
			return m;
		}
	}
	
	public Mat4 normalMat(Mat4 modelMat, Mat4 viewMat) {
		return viewMat.mult(modelMat);
		// normalMat = transpose(inverse(modelView));
		//			   (modelView-1)T
	}
	
	public String toString() {
		Mat4 m = viewMat();
		String res = "";
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				res += m.get(i, j) + " ";
			}
			res += "\n";
		}
		return res;
	}
}

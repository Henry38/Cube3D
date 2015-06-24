package geometry;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import math.Base3D;
import math.Mat4;
import math.Point3D;
import math.Viewport;

public class Shape3D {
	
	public final static int WIREFRAME = 0;
	public final static int OPAQUE = 1;
	
	private Shape3D parent;
	private Base3D base;
	private ArrayList<Triangle> listTriangle;
	private String pathTexture;
	public Viewport viewport;
	private int render;
	private String name;
	
	/** Constructeur */
	public Shape3D(Shape3D parent, double x, double y, double z) {
		this.parent = parent;
		this.base = new Base3D(new Point3D(x, y, z));
		this.listTriangle = new ArrayList<Triangle>();
		this.pathTexture = "";
		this.render = OPAQUE;
		this.setName("Objet");
	}
	
	/** Construteur */
	public Shape3D(Shape3D parent) {
		this(parent, 0, 0, 0);
	}
	
	/** Retourne la base de la shape3D */
	public final Base3D getBase() {
		return base;
	}
	
	/** Retourne la liste des triangles*/
	public final ArrayList<Triangle> getListTriangle() {
		return listTriangle;
	}
	
	/** Retourne la matrice modele */
	public Mat4 modelMat() {
		if (parent == null) {
			return new Mat4(getBase());
		}
		return parent.modelMat().mult(new Mat4(getBase()));
	}
	
	/** Retourne l'image utilisee pour la texture */
	public BufferedImage getTexture() {
		if (pathTexture.equals("")) {
			return null;
		}
		BufferedImage texture = null;
		try {
			texture = ImageIO.read(new File(pathTexture));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return texture;
	}
	
	/** Ajoute le triangle a la shape3D */
	public void addTriangle(Triangle triangle) {
		listTriangle.add(triangle);
	}
	
	/** Retire le triangle a la shape3D */
	public void removeTriangle(Triangle triangle) {
		listTriangle.remove(triangle);
	}
	
	public void setTexture(String pathTexture) {
		this.pathTexture = pathTexture;
	}
	
	/** Translation de la base */
	public void translation (double dx, double dy, double dz) {
		base.translation(dx, dy, dz);
	}
	
	/** Augmente le degre de rotation */
	public void rotationOx(double radian) {
		base.rotationOx(radian);
	}
	
	/** Augmente le degre de rotation */
	public void rotationOy(double radian) {
		base.rotationOy(radian);
	}
	
	/** Augmente le degre de rotation */
	public void rotationOz(double radian) {
		base.rotationOz(radian);
	}
	
	/** Change l'echelle en ox */
	public void scaleX(double rx) {
		base.scaleX(rx);
	}
	
	/** Change l'echelle en oy */
	public void scaleY(double ry) {
		base.scaleY(ry);
	}
	
	/** Change l'echelle en oz */
	public void scaleZ(double rz) {
		base.scaleZ(rz);
	}
	
	/** Change l'echelle sur les trois axes */
	public void scale(double r) {
		scaleX(r);
		scaleY(r);
		scaleZ(r);
	}
	
	public boolean isWireframeMode() {
		return (render == WIREFRAME);
	}
	
	public void setRenderingMode(int render) {
		this.render = render;
	}
	
	public void toggleRenderingMode() {
		if (isWireframeMode()) {
			render = OPAQUE;
		} else {
			render = WIREFRAME;
		}
	}
	
	public int getRenderingMode() {
		return render;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

package graphics;

import geometry.Light;
import geometry.Miroir;
import geometry.Shape3D;
import geometry.Triangle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.LinkedList;

import math.Coord;
import math.Mat4;
import math.Point2D;
import math.Point3D;
import math.Vec4;
import math.Vecteur3D;
import world3d.Camera;
import world3d.World3D;

public class WorldRenderer {
	
	private World3D world;
	private Camera camera;
	
	Mat4 modelMat, viewMat, projMat, projViewModelMat, screenMat;
	
	private BufferedImage image;
	private WritableRaster raster;
	
	private BufferedImage texture;
	private int textureWidth, textureHeight;
	
	private int[] colorBuffer;
	private double[] zBuffer;
	private int width, height;
	
	/** Constructeur */
	public WorldRenderer(World3D world, Camera camera, int width, int height) {
		this.world = world;
		this.camera = camera;
		
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		this.raster = image.getRaster();
		
		this.colorBuffer = new int[width * height * 3];
		this.zBuffer = new double[width * height];
		this.width = width;
		this.height = height;
	}
	
	/** Retourne l'image du rendu */
	public BufferedImage getImage() {
		return image;
	}
	
	public Mat4 screenMat() {
		int w = width;
		int h = height;
		int n = 0;
		int f = 1;
		Mat4 m = new Mat4(new double[][] {
				{w/2,   0,       0,     w/2},
				{  0, h/2,       0,     h/2},
				{  0,   0, (f-n)/2, (f+n)/2},
				{  0,   0,       0,       1}
		});
		return m;
	}
	
	/** Dessine le rendu */
	public void drawScene() {
		for (int i = 0; i < colorBuffer.length; i++) {
			colorBuffer[i] = 0;
		}
		for (int i = 0; i < zBuffer.length; i++) {
			zBuffer[i] = 1.0;
		}
		
		viewMat = camera.viewMat();
		projMat = camera.projMat();
		screenMat = screenMat();
		
		for (Shape3D shape : world.getlistShape()) {
			modelMat = shape.modelMat();
			projViewModelMat = projMat.mult(viewMat).mult(modelMat);
			drawShape3D(shape);
		}
		modelMat = new Mat4();
		projViewModelMat = projMat.mult(viewMat).mult(modelMat);
		
		raster.setPixels(0, 0, width, height, colorBuffer);
	}
	
	/** Retourne l'aire du triangle */
	private double areaTriangle(int x1, int y1, int x2, int y2, int x3, int y3) {
	    return Math.abs((x2-x1)*(y3-y1) - (x3-x1)*(y2-y1));
	}
	
	/** Trace la ligne definie entre deux points du repere monde */
	public LinkedList<Point2D> Bresenham2D(int x1, int y1, int x2, int y2) {
		LinkedList<Point2D> res = new LinkedList<Point2D>();
		int[] point = new int[] {x1, y1};
		
	    int dx = x2 - x1;
	    int dy = y2 - y1;
	    int x_inc = (dx < 0 ? -1 : 1);
	    int y_inc = (dy < 0 ? -1 : 1);
	    int abs_dx = Math.abs(dx);
	    int abs_dy = Math.abs(dy);
	    
	    // Multiplication par 2
	    int dx2 = abs_dx << 1;
	    int dy2 = abs_dy << 1;
	    
	    if (abs_dx >= abs_dy) {
	        int err = -abs_dx;
	        // Iteration sur x
	        for (int i = 0; i <= abs_dx; ++i) {
	        	res.add(new Point2D(point[0], point[1]));
	            err += dy2;
	            if (err > 0) {
	                point[1] += y_inc;
	                err -= dx2;
	            }
	            point[0] += x_inc;
	        }
	    } else {
	        int err = -abs_dy;
	        // Iteration sur y
	        for (int i = 0; i <= abs_dy; ++i) {
	        	res.add(new Point2D(point[0], point[1]));
	            err += dx2;
	            if (err > 0) {
	                point[0] += x_inc;
	                err -= dy2;
	            }
	            point[1] += y_inc;
	        }
	    }
	    
	    return res;
	}
	
	/** Calcul le bord gauche strict de la ligne */
	public LinkedList<Point2D> leftEdgeScan(int x1, int y1, int x2, int y2, boolean b) {
		LinkedList<Point2D> res = new LinkedList<Point2D>();
		
	    int[] point = new int[] {x1, y1};
	    
	    int dx = x2 - x1;
	    int dy = y2 - y1;
	    int x_inc = (dx < 0 ? -1 : 1);
	    int y_inc = (dy < 0 ? -1 : 1);
	    int abs_dx = Math.abs(dx);
	    int abs_dy = Math.abs(dy);
	    
	    if (dx == 0 && dy == 0) {
	        // Remplissage d'un point
	        res.add(new Point2D(point[0], point[1]));
	        return res;
	    }
	    if (dx == 0) {
	        // Remplissage d'une ligne verticale
	        for (int i = 0; i <= abs_dy; ++i) {
	            res.add(new Point2D(point[0], point[1]));
	            point[1] += y_inc;
	        }
	        return res;
	    } else if (dy == 0)  {
	        // Remplissage d'une ligne horizontale
	        for (int i = 0; i <= abs_dx; ++i) {
	            res.add(new Point2D(point[0], point[1]));
	            point[0] += x_inc;
	        }
	        return res;
	    }
	    
	    if (abs_dx >= abs_dy) {
	        int numerator = abs_dy;
	        int denominator = abs_dx;
	        int increment = denominator;
            // Iteration sur x
            for (int i = 0; i <= abs_dx; ++i) {
                increment += numerator;
                if (increment > denominator) {
                    res.add(new Point2D(point[0], point[1]));
                    point[1] += y_inc;
                    increment -= denominator;
                }
                point[0] += x_inc;
            }
	        
	    } else if (abs_dx < abs_dy) {
	        int numerator = abs_dx;
	        int denominator = abs_dy;
	        int increment = denominator;
	        // Iteration sur y
            for (int i = 0; i <= abs_dy; ++i) {
            	res.add(new Point2D(point[0], point[1]));
                increment += numerator;
                if (increment > denominator) {
                    point[0] += x_inc;
                    increment -= denominator;
                }
                point[1] += y_inc;
            }
	    }
	    
	    return res;
	}
	
	
	private ArrayList<Cell> getHash(Point2D p1, Point2D p2, Point2D p3) {
		
		if (p1.equals(p2) && p2.equals(p3)) {
			//hash = new Hash(minY, 0);
			return new ArrayList<Cell>(0);
		}
		if (p1.getX() == p2.getX() && p2.getX() == p3.getX()) {
			//hash = new Hash(minY, 0);
			return new ArrayList<Cell>(0);
		}
		if (p1.getY() == p2.getY() && p2.getY() == p3.getY()) {
			//hash = new Hash(minY, 0);
			return new ArrayList<Cell>(0);
		}
		
		int nx1 = -(p2.getY() - p1.getY());
		int ny1 = p2.getX() - p1.getX();
		int nx2 = -(p3.getY() - p2.getY());
		int ny2 = p3.getX() - p2.getX();
		int nx3 = -(p1.getY() - p3.getY());
		int ny3 = p1.getX() - p3.getX();
		boolean b1 = (nx1 < 0 || (nx1 == 0 && ny1 < 0));
		boolean b2 = (nx2 < 0 || (nx2 == 0 && ny2 < 0));
		boolean b3 = (nx3 < 0 || (nx3 == 0 && ny3 < 0));
		
		int minY = Math.min(p1.getY(), Math.min(p2.getY(), p3.getY()));
		int maxY = Math.max(p1.getY(), Math.max(p2.getY(), p3.getY()));
		
		ArrayList<Cell> listCell = new ArrayList<Cell>(maxY-minY+1);
		for (int i = 0; i < maxY-minY+1; i++) {
			listCell.add(new Cell());
		}
		
		LinkedList<Point2D> lineP1P2;
		LinkedList<Point2D> lineP2P3;
		LinkedList<Point2D> lineP3P1;
		
		if (p1.getY() == p2.getY()) {
			// Top || Bottom flat triangle P1P2
			lineP1P2 = new LinkedList<Point2D>();
			lineP2P3 = leftEdgeScan(p2.getX(), p2.getY(), p3.getX(), p3.getY(), b2);
			lineP3P1 = leftEdgeScan(p3.getX(), p3.getY(), p1.getX(), p1.getY(), b3);
			
		} else if (p2.getY() == p3.getY()) {
			// Top || Bottom flat triangle P2P3
			lineP1P2 = leftEdgeScan(p1.getX(), p1.getY(), p2.getX(), p2.getY(), b1);
			lineP2P3 = new LinkedList<Point2D>();
			lineP3P1 = leftEdgeScan(p3.getX(), p3.getY(), p1.getX(), p1.getY(), b3);
			
		} else if (p3.getY() == p1.getY()) {
			// Top || Bottom flat triangle P3P1
			lineP1P2 = leftEdgeScan(p1.getX(), p1.getY(), p2.getX(), p2.getY(), b1);
			lineP2P3 = leftEdgeScan(p2.getX(), p2.getY(), p3.getX(), p3.getY(), b2);
			lineP3P1 = new LinkedList<Point2D>();
			
		} else {
			// Default triangle
			lineP1P2 = leftEdgeScan(p1.getX(), p1.getY(), p2.getX(), p2.getY(), b1);
			lineP2P3 = leftEdgeScan(p2.getX(), p2.getY(), p3.getX(), p3.getY(), b2);
			lineP3P1 = leftEdgeScan(p3.getX(), p3.getY(), p1.getX(), p1.getY(), b3);
		}
		
		for (Point2D p : lineP1P2) {
			listCell.get(p.getY()-minY).set(p.getX(), b1);
		}
		for (Point2D p : lineP2P3) {
			listCell.get(p.getY()-minY).set(p.getX(), b2);
		}
		for (Point2D p : lineP3P1) {
			listCell.get(p.getY()-minY).set(p.getX(), b3);
		}
		
		return listCell;
	}
	
	/** Retourne les coordonnees homogenes du projete du point defini dans le modele */
	private Vec4 getProjectivePoint3D(Point3D point) {
		Vec4 p = new Vec4(point);
		return projViewModelMat.mult(p);
	}
	
	/** Retourne vrai si le point en coordonnees homogene est dans le volume regarde */
	private boolean onViewVolume(Vec4 point) {
		double w = point.getW();
		return (point.getX() > -w && point.getX() < w && point.getY() > -w && point.getY() < w && point.getZ() > -w && point.getZ() < w);
	}
	
	/** Retourne la projection fenetre du point normalize dans le repere camera */
	private Point3D getWindowScreenPoint3D(Vec4 point) {
		Vec4 p = screenMat.mult(point);
		return new Point3D(p.getX(), p.getY(), p.getZ());
	}
	
	
	
	/** Dessine une droite entre deux points places dans le monde avec la modelMat */
	private void drawLine3D(Point3D point1, Point3D point2, Color color) {
		Vec4 proj_p1 = getProjectivePoint3D(point1);
		Vec4 proj_p2 = getProjectivePoint3D(point2);
		
		// Si la ligne en entierement dans le volume regarde
		if (onViewVolume(proj_p1) && onViewVolume(proj_p2)) {
			
			Vec4 ndcP1 = proj_p1.normalized();
			Vec4 ndcP2 = proj_p2.normalized();
			
			Point3D screenP1 = getWindowScreenPoint3D(ndcP1);
			Point3D screenP2 = getWindowScreenPoint3D(ndcP2);
			
			Point2D p1 = new Point2D((int)screenP1.getX(), (int)screenP1.getY());
			Point2D p2 = new Point2D((int)screenP2.getX(), (int)screenP2.getY());
			
			double z;
			double d1, d2, d, h;
			double coef1, coef2;
			int x, y, index;
			for (Point2D p : Bresenham2D(p1.getX(), p1.getY(), p2.getX(), p2.getY())) {
				x = p.getX();
				y = p.getY();
				if (x >= 0 && x < width && y >= 0 && y < height) {
					index = ((y * width) + x) * 3;
					
					d1 = Point2D.distance(p1, p);
					d2 = Point2D.distance(p, p2);
					d = d1 + d2;
					
					coef1 = d1 / (d * proj_p1.getW());
					coef2 = d2 / (d * proj_p2.getW());
					
					h = coef1 + coef2;
					z = ((coef1 * ndcP1.getZ()) + (coef2 * ndcP2.getZ())) / h;
					
	    			if (z < zBuffer[(y * width) + x]) {
	    				zBuffer[(y * width) + x] = z;
	    				
	    				colorBuffer[index + 0] = color.getRed();
						colorBuffer[index + 1] = color.getGreen();
						colorBuffer[index + 2] = color.getBlue();
	    			}
				}
			}
		}
	}
	
	/** Dessine le triangle en wireframe */
	private void drawEdgeTriangle(Triangle triangle) {
		Color color = triangle.getColor();
		drawLine3D(triangle.getP1(), triangle.getP2(), color);
		drawLine3D(triangle.getP2(), triangle.getP3(), color);
		drawLine3D(triangle.getP3(), triangle.getP1(), color);
	}
	
	/** Dessine le triangle defini par trois points du repere World */
	private void drawTriangle(Triangle triangle) {
		
		Vec4 proj_p1 = getProjectivePoint3D(triangle.getP1());	// Coordonnees clippees
		Vec4 proj_p2 = getProjectivePoint3D(triangle.getP2());
		Vec4 proj_p3 = getProjectivePoint3D(triangle.getP3());
		
		// Si le triangle est entierement dans le volume regarde
		if (onViewVolume(proj_p1) && onViewVolume(proj_p2) && onViewVolume(proj_p3)) {
			
			Vec4 ndcP1 = proj_p1.normalized();	// Coordonnees normalisees
			Vec4 ndcP2 = proj_p2.normalized();
			Vec4 ndcP3 = proj_p3.normalized();
			
			Point3D screenP1 = getWindowScreenPoint3D(ndcP1);	// Coordonnees ecran (pixelisees)
			Point3D screenP2 = getWindowScreenPoint3D(ndcP2);
			Point3D screenP3 = getWindowScreenPoint3D(ndcP3);
			
			Point2D p1 = new Point2D((int)screenP1.getX(), (int)screenP1.getY());
			Point2D p2 = new Point2D((int)screenP2.getX(), (int)screenP2.getY());
			Point2D p3 = new Point2D((int)screenP3.getX(), (int)screenP3.getY());
			
			ArrayList<Cell> listCell;
			if (Point2D.oriented2d(p1, p2, p3)) {
				listCell = getHash(p1, p3, p2);
			} else {
				listCell = getHash(p1, p2, p3);
			}
			
			Coord[] listCoord = triangle.getListCoord();
			// Flat Shading
//			Coord[] listCoord = new Coord[] {
//					new Coord((pp1.getY()-minY)/(maxY-minY), (pp1.getZ()-minZ)/(maxZ-minZ)),
//					new Coord((pp2.getY()-minY)/(maxY-minY), (pp2.getZ()-minZ)/(maxZ-minZ)),
//					new Coord((pp3.getY()-minY)/(maxY-minY), (pp3.getZ()-minZ)/(maxZ-minZ))
//			};
			double u, v, z;
			double area1, area2, area3, d, h;
			double coef1, coef2, coef3;
			
			Light light = world.getLight();
			Vecteur3D n = modelMat.mult(new Vec4(triangle.getNormal())).toVecteur3D();
			double cos = (light != null ? -Vecteur3D.cosinus(light.getDirection(), n) : 0);
			Color lightColor = (light != null ? light.getColor() : new Color(0, 0, 0));
			Color triangleColor = triangle.getColor();
			
			int r = (int) Math.max(0, Math.min(triangleColor.getRed() + lightColor.getRed() * cos, 255));
			int g = (int) Math.max(0, Math.min(triangleColor.getGreen() + lightColor.getGreen() * cos, 255));
			int b = (int) Math.max(0, Math.min(triangleColor.getBlue() + lightColor.getBlue() * cos, 255));
			
			Cell c;
			int minY = Math.min(p1.getY(), Math.min(p2.getY(), p3.getY()));
			int y, index;
			for (int k = 0; k < listCell.size(); k++) {
				c = listCell.get(k);
				y = minY + k;
				for (int x = c.x1; x <= c.x2; x++) {
					if (x >= 0 && x < width && y >= 0 && y < height) {
		    			index = ((y * width) + x) * 3;
		    			
		    			area1 = areaTriangle(p2.getX(), p2.getY(), p3.getX(), p3.getY(), x, y);
		    			area2 = areaTriangle(p1.getX(), p1.getY(), p3.getX(), p3.getY(), x, y);
		    			area3 = areaTriangle(p1.getX(), p1.getY(), p2.getX(), p2.getY(), x, y);
		    			d = area1 + area2 + area3;
		    			
		    			coef1 = area1 / (d * proj_p1.getW());
		    			coef2 = area2 / (d * proj_p2.getW());
		    			coef3 = area3 / (d * proj_p3.getW());
		    			
		    			h = coef1 + coef2 + coef3;
		    			
		    			u = ((coef1 * listCoord[0].getU()) + (coef2 * listCoord[1].getU()) + (coef3 * listCoord[2].getU())) / h;
		    			v = ((coef1 * listCoord[0].getV()) + (coef2 * listCoord[1].getV()) + (coef3 * listCoord[2].getV())) / h;
		    			z = ((coef1 * ndcP1.getZ()) + (coef2 * ndcP2.getZ()) + (coef3 * ndcP3.getZ())) / h;
		    			
		    			if (texture != null) {
		    				Color localColor = new Color(texture.getRGB((int)(u*textureWidth), (int)(v*textureHeight)));
		    				r = localColor.getRed();
		    				g = localColor.getGreen();
		    				b = localColor.getBlue();
		    			}
		    			
		    			if (z < zBuffer[(y * width) + x]) {
		    				zBuffer[(y * width) + x] = z;
		    				
		    				colorBuffer[index + 0] = r;
		    				colorBuffer[index + 1] = g;
		    				colorBuffer[index + 2] = b;
		    			}
					}
				}
			}
		}
	}
	
	/** Dessine les arêtes de la Shape3D definie dans le repere World */
	private void drawShape3D(Shape3D shape) {
		texture = shape.getTexture();
		if (texture != null) {
			textureWidth = texture.getWidth()-1;
			textureHeight = texture.getHeight()-1;
		} else {
			textureWidth = 0;
			textureHeight = 0;
		}
		
		Point3D p;
		Point3D eye = camera.getOrigine();
		Vecteur3D vision = new Vecteur3D(0, 0, 0);
		Vecteur3D normal = null;
		
		for (Triangle triangle : shape.getListTriangle()) {
			p = modelMat.mult(new Vec4(triangle.getP1())).toPoint3D();
			vision.setDx(p.getX() - eye.getX());
			vision.setDy(p.getY() - eye.getY());
			vision.setDz(p.getZ() - eye.getZ());
			normal = modelMat.mult(new Vec4(triangle.getNormal())).toVecteur3D();
			
			// Back face culling
			if (Vecteur3D.produit_scalaire(vision, normal) < 0) {
				if (shape.getRenderingMode() == Shape3D.WIREFRAME) {
					drawEdgeTriangle(triangle);
				} else {
					drawTriangle(triangle);
				}
			}
		}
	}
	
	
	/** Private class */
	private class Cell {
		
		public int x1, x2;
		
		public Cell() {
			this.x1 = 0;
			this.x2 = 0;
		}
		
		public void set(int value, boolean left) {
			if (left) {
				x1 = value;
			} else {
				x2 = value;
			}
		}
	}
}

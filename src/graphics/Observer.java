package graphics;

import geometry.Light;
import geometry.Shape3D;
import geometry.Triangle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JComponent;

import math.*;
import world3d.Camera;
import world3d.World3D;

public class Observer extends JComponent implements MouseMotionListener, MouseListener, MouseWheelListener {
	
	private static final long serialVersionUID = 1L;
	
	private World3D world;
	private Camera camera;
	private Viewport viewport;
	
	Mat4 modelMat, viewMat, projMat, screenMat, normalMat;
	
	private BufferedImage image, texture;
	private WritableRaster raster;
	
	private int[] colorBuffer;
	private double[] zBuffer;
	
	private int viewportWidth, viewportHeight;
	private int textureWidth, textureHeight;
	private int posX, posY, decalX, decalY;
	private int eventButton;
	
	/** Constructeur */
	public Observer(World3D world, Camera camera, int width, int height) {
		super();
		this.world = world;
		this.camera = camera;
		this.viewport = new Viewport(0, 0, width, height);
		this.viewportWidth = width;
		this.viewportHeight = height;
		
		this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		this.texture = null;
		this.raster = image.getRaster();
		
		this.colorBuffer = new int[width * height * 3];
		this.zBuffer = new double[width * height];
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		
		setPreferredSize(new Dimension(width, height));
	}
	
	public int getColor(int r, int g, int b) {
		return (r << 16) | (g << 8) | b;
	}
	
	/** Calcul le rendu du monde depuis la camera */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (int i = 0; i < colorBuffer.length; i++) {
			colorBuffer[i] = 0;
		}
		for (int i = 0; i < zBuffer.length; i++) {
			zBuffer[i] = 1.0;
		}
		
		viewMat = camera.viewMat();
		projMat = camera.projMat();
		screenMat = viewport.screenMat();
		
		for (Shape3D shape : world.getlistShape()) {
			drawShape3D(shape);
		}
		
		//drawRepere(world.getBase());
//		g.setColor(Color.red);
//		drawLine3D(world.getOrigine(), new Point3D(8, 0, 0));
		
		//Draw center
		Color color = Color.cyan;
		Point3D center = camera.getObserver();
//		drawLine3D(new Point3D(0, 0, 0), new Point3D(center.getX(), 0, 0), color);
//		drawLine3D(new Point3D(center.getX(), 0, 0), new Point3D(center.getX(), center.getY(), 0), color);
		drawLine3D(new Point3D(center.getX(), center.getY(), 0), new Point3D(center.getX(), center.getY(), center.getZ()), color);
		
		raster.setPixels(0, 0, viewportWidth, viewportHeight, colorBuffer);
		g.drawImage(image, 0, 0, null);
		
		// Draw cursor
		g.setColor(Color.magenta);
		g.drawLine(viewportWidth/2-4, viewportHeight/2, viewportWidth/2+4, viewportHeight/2);
		g.drawLine(viewportWidth/2, viewportHeight/2-4, viewportWidth/2, viewportHeight/2+4);
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
	
	/** Retourne les coordonnees homogenes dans le repere camera du point defini dans le repere World */
	private Vec4 getProjectivePoint3D(Point3D point) {
		Vec4 p = new Vec4(point);
		return projMat.mult(viewMat.mult(modelMat.mult(p)));
	}
	
	/** Retourne vrai si le point en coordonnees homogene est dans le volume regarde */
	private boolean onViewVolume(Vec4 point) {
		double w = point.getW();
		return (point.getX() > -w && point.getX() < w && point.getY() > -w && point.getY() < w && point.getZ() > -w && point.getZ() < w);
	}
	
	/** Retourne la projection fenetre du point normalize dans le repere camera */
	private Point3D getWindowScreenPoint3D(Vec4 point) {
		Vec4 p = screenMat.mult(point);
//		int x = (int) ((viewportWidth/2.0) * (point.getX()) + viewport.getX() + (viewportWidth/2.0));
//		int y = (int) ((viewportHeight/2.0) * (point.getY()) + viewport.getY() + (viewportHeight/2.0));
//		int z = (int) (((camera.getZFar()-camera.getZNear())/2.0)*point.getZ() + ((camera.getZFar()+camera.getZNear())/2.0));
		return new Point3D(p.getX(), p.getY(), p.getZ());
	}
	
	
	
	/** Dessine un point defini dans le repere World */
	private void drawPoint3D(Graphics g, Point3D point) {
		Vec4 proj_p = getProjectivePoint3D(point).normalized();
		Point3D p = getWindowScreenPoint3D(proj_p);
		int x = (int) p.getX();
		int y = (int) p.getY();
		
		int index;
		if (x >= 0 && x < viewportWidth && y >= 0 && y < viewportHeight) {
			index = ((y * viewportWidth) + x) * 3;
//			colorBuffer[index + 0] = color.getRed();
//			colorBuffer[index + 1] = color.getGreen();
//			colorBuffer[index + 2] = color.getBlue();
		}
	}
	
	/** Dessine une droite entre deux points du repere World */
	private void drawLine3D(Point3D point1, Point3D point2, Color color) {
		modelMat = new Mat4();
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
			
			int x, y, index;
			for (Point2D p : Bresenham2D(p1.getX(), p1.getY(), p2.getX(), p2.getY())) {
				x = p.getX();
				y = p.getY();
				if (x >= 0 && x < viewportWidth && y >= 0 && y < viewportHeight) {
					index = ((y * viewportWidth) + x) * 3;
					
					colorBuffer[index + 0] = color.getRed();
					colorBuffer[index + 1] = color.getGreen();
					colorBuffer[index + 2] = color.getBlue();
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
		Point3D pp1 = triangle.getP1();
		Point3D pp2 = triangle.getP2();
		Point3D pp3 = triangle.getP3();
		double minY = Math.min(pp1.getY(), Math.min(pp2.getY(), pp3.getY()));
		double maxY = Math.max(pp1.getY(), Math.max(pp2.getY(), pp3.getY()));
		double minZ = Math.min(pp1.getZ(), Math.min(pp2.getZ(), pp3.getZ()));
		double maxZ = Math.max(pp1.getZ(), Math.max(pp2.getZ(), pp3.getZ()));
		
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
			
			//Coord[] listCoord = triangle.getListCoord();
			Coord[] listCoord = new Coord[] {
					new Coord((pp1.getY()-minY)/(maxY-minY), (pp1.getZ()-minZ)/(maxZ-minZ)),
					new Coord((pp2.getY()-minY)/(maxY-minY), (pp2.getZ()-minZ)/(maxZ-minZ)),
					new Coord((pp3.getY()-minY)/(maxY-minY), (pp3.getZ()-minZ)/(maxZ-minZ))
			};
			int minY1 = Math.min(p1.getY(), Math.min(p2.getY(), p3.getY()));
			int y, index;
			double u1, v1, z1, u2, v2, z2, u3, v3, z3;
			double u, v, z;
			double area1, area2, area3, d, h;
			
			Light light = world.getLight();
			Vecteur3D n = modelMat.mult(new Vec4(triangle.getNormale())).toVecteur3D();
			double cos = (light != null ? -Vecteur3D.cosinus(light.getDirection(), n) : 0);
			Color lightColor = (light != null ? light.getColor() : new Color(0, 0, 0));
			Color triangleColor = triangle.getColor();
			
			int r = (int) Math.max(0, Math.min(triangleColor.getRed() + lightColor.getRed() * cos, 255));
			int g = (int) Math.max(0, Math.min(triangleColor.getGreen() + lightColor.getGreen() * cos, 255));
			int b = (int) Math.max(0, Math.min(triangleColor.getBlue() + lightColor.getBlue() * cos, 255));
//			int r = color.getRed();
//			int g = color.getGreen();
//			int b = color.getBlue();
			
			Cell c;
			for (int k = 0; k < listCell.size(); k++) {
				c = listCell.get(k);
				y = minY1 + k;
				for (int x = c.x1; x <= c.x2; x++) {
					if (x >= 0 && x < viewportWidth && y >= 0 && y < viewportHeight) {
		    			index = ((y * viewportWidth) + x) * 3;
		    			
		    			area1 = areaTriangle(p2.getX(), p2.getY(), p3.getX(), p3.getY(), x, y);
		    			area2 = areaTriangle(p1.getX(), p1.getY(), p3.getX(), p3.getY(), x, y);
		    			area3 = areaTriangle(p1.getX(), p1.getY(), p2.getX(), p2.getY(), x, y);
		    			d = area1 + area2 + area3;
		    			
		    			h = 0;
		    			h += (area1 / d) * (1.0 / proj_p1.getW());	// 1 / w
		    			h += (area2 / d) * (1.0 / proj_p2.getW());
		    			h += (area3 / d) * (1.0 / proj_p3.getW());
		    			
		    			z1 = (area1 / d) * (ndcP1.getZ() / proj_p1.getW());
		    			z2 = (area2 / d) * (ndcP2.getZ() / proj_p2.getW());
		    			z3 = (area3 / d) * (ndcP3.getZ() / proj_p3.getW());
		    			
		    			u1 = (area1 / d) * (listCoord[0].getU() / proj_p1.getW());	// u / w
		    			v1 = (area1 / d) * (listCoord[0].getV() / proj_p1.getW());	// v / w
		    			z1 = (area1 / d) * (ndcP1.getZ() / proj_p1.getW());		// z
		    			
		    			u2 = (area2 / d) * (listCoord[1].getU() / proj_p2.getW());
		    			v2 = (area2 / d) * (listCoord[1].getV() / proj_p2.getW());
		    			z2 = (area2 / d) * (ndcP2.getZ() / proj_p2.getW());
		    			
		    			u3 = (area3 / d) * (listCoord[2].getU() / proj_p3.getW());
		    			v3 = (area3 / d) * (listCoord[2].getV() / proj_p3.getW());
		    			z3 = (area3 / d) * (ndcP3.getZ()) / proj_p3.getW();
		    			
		    			u = (u1 + u2 + u3) / h;
		    			v = (v1 + v2 + v3) / h;
		    			z = (z1 + z2 + z3) / h;
		    			
		    			if (texture != null) {
		    				Color localColor = new Color(texture.getRGB((int)(u*textureWidth), (int)(v*textureHeight)));
		    				r = localColor.getRed();
		    				g = localColor.getGreen();
		    				b = localColor.getBlue();
		    			}
		    			
		    			if (z < zBuffer[(y * viewportWidth) + x]) {
		    				zBuffer[(y * viewportWidth) + x] = z;
		    				
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
		modelMat = shape.modelMat();
		normalMat = camera.normalMat(modelMat, viewMat);
		texture = shape.getTexture();
		if (texture != null) {
			textureWidth = texture.getWidth()-1;
			textureHeight = texture.getHeight()-1;
		} else {
			textureWidth = 0;
			textureHeight = 0;
		}
		
		Vec4 p;
		Point3D eye = camera.getOrigine();
		Vecteur3D vision = new Vecteur3D(0, 0, 0);
		Vecteur3D normale;
		
		for (Triangle triangle : shape.getListTriangle()) {
			p = modelMat.mult(new Vec4(triangle.getP1())).normalized();
			vision.setDx(p.getX() - eye.getX());
			vision.setDy(p.getY() - eye.getY());
			vision.setDz(p.getZ() - eye.getZ());
			normale = modelMat.mult(new Vec4(triangle.getNormale())).toVecteur3D();
			
			// Back face culling
			if (Vecteur3D.produit_scalaire(vision, normale) < 0) {
				//drawEdgeTriangle(triangle);
				drawTriangle(triangle);
			}
		}
	}
	
	/** Dessine la base definie dans le repere World */
	private void drawRepere(Base3D base) {
		Point3D origin = base.getOrigine();
		Point3D p = new Point3D();
		Color color;
		
		for (Vecteur3D vect : base.getVecteurs()) {
			if (vect == base.oi) {
				color = new Color(255, 0, 0);
				//p1 = new Point3D(Integer.MAX_VALUE, 0, 0);
			} else if (vect == base.oj) {
				color = new Color(0, 255, 0);
				//p1 = new Point3D(0, Integer.MAX_VALUE, 0);
			} else {// if (vect == base.ok) {
				color = new Color(0, 0, 255);
				//p1 = new Point3D(0, 0, Integer.MAX_VALUE);
			}
			
			p.setX(origin.getX());
			p.setY(origin.getY());
			p.setZ(origin.getZ());
			p.translation(vect);
			drawLine3D(origin, p, color);
		}
	}
	
	
	
	// Mouvement de la camera
	/** Translate la camera sur le plan d'affichage */
	public void moveTranslation(double dx, double dy) {
		Base3D base = camera.getBase();
		Vecteur3D t = new Vecteur3D(dx, dy, 0);
		Vecteur3D u = base.oi;
		Vecteur3D v = base.oj;
		Vecteur3D k = base.ok;
		double dx2 = u.getDx()*t.getDx() + v.getDx()*t.getDy() + k.getDx()*t.getDz();
		double dy2 = u.getDy()*t.getDx() + v.getDy()*t.getDy() + k.getDy()*t.getDz();
		double dz2 = u.getDz()*t.getDx() + v.getDz()*t.getDy() + k.getDz()*t.getDz();
		camera.translation(dx2, dy2, dz2);
	}
	
	/** Fait avancer la camera dans la direction de la vue */
	public void moveForward(int step) {
		Point3D pointCamera = camera.getOrigine();
		Point3D pointObserver = camera.getObserver();
		
		// Direction
		Vecteur3D vect = new Vecteur3D(0, 0, 0);
		vect.setDx(pointObserver.getX() - pointCamera.getX());
		vect.setDy(pointObserver.getY() - pointCamera.getY());
		vect.setDz(pointObserver.getZ() - pointCamera.getZ());
		
		double ratio = 0.25;
		double dx = step * ratio * vect.getDx();
		double dy = step * ratio * vect.getDy();
		double dz = step * ratio * vect.getDz();
		
		camera.translation(dx, dy, dz);
	}
	
	/** Fait tourner la camera autour du point regarde */
	public void moveRotation(double dx, double dy) {
		Point3D pointCamera = camera.getOrigine();
		Point3D pointObserver = camera.getObserver();
		
		// Direction
		Vecteur3D vect = new Vecteur3D(0, 0, 0);
		vect.setDx(pointCamera.getX() - pointObserver.getX());
		vect.setDy(pointCamera.getY() - pointObserver.getY());
		vect.setDz(pointCamera.getZ() - pointObserver.getZ());
		
		double r = vect.getNorme();
		double pPhi = Math.acos(vect.getDz() / r);
		double pTeta = Math.acos(vect.getDx() / (r*Math.sin(pPhi)));
		if (vect.getDy() < 0) {
			pTeta *= -1.0;
		}
		pTeta += ((float) dx) / ((float) viewportWidth) * Math.PI;
		pPhi += ((float) -dy) / ((float) viewportHeight) * Math.PI;
		if (pPhi < Math.PI/6) {
			pPhi = Math.PI/6;
		} else if (pPhi > 5*Math.PI/6) {
			pPhi = 5*Math.PI/6;
		}
		
		pointCamera.setX(pointObserver.getX() + r * Math.sin(pPhi) * Math.cos(pTeta));
		pointCamera.setY(pointObserver.getY() + r * Math.sin(pPhi) * Math.sin(pTeta));
		pointCamera.setZ(pointObserver.getZ() + r * Math.cos(pPhi));
	}
	
	/** Change le point regarde sur la sphere englobant la camera */
	public void rotationHead(int dx, int dy) {
		Point3D pointCamera = camera.getOrigine();
		Point3D pointObserver = camera.getObserver();
		
		// Direction
		Vecteur3D vect = new Vecteur3D(0, 0, 0);
		vect.setDx(pointObserver.getX() - pointCamera.getX());
		vect.setDy(pointObserver.getY() - pointCamera.getY());
		vect.setDz(pointObserver.getZ() - pointCamera.getZ());
		
		double r = vect.getNorme();
		double pPhi = Math.acos(vect.getDz() / r);
		double pTeta = Math.acos(vect.getDx() / (r*Math.sin(pPhi)));
		if (vect.getDy() < 0) {
			pTeta *= -1.0;
		}
		pTeta += ((float) dx) / ((float) viewportWidth) * Math.PI;
		pPhi += ((float) -dy) / ((float) viewportHeight) * Math.PI;
		if (pPhi < Math.PI/6) {
			pPhi = Math.PI/6;
		} else if (pPhi > 5*Math.PI/6) {
			pPhi = 5*Math.PI/6;
		}
		
		pointObserver.setX(pointCamera.getX() + r * Math.sin(pPhi) * Math.cos(pTeta));
		pointObserver.setY(pointCamera.getY() + r * Math.sin(pPhi) * Math.sin(pTeta));
		pointObserver.setZ(pointCamera.getZ() + r * Math.cos(pPhi));
	}
	
	
	// Detection de la souris
	public void mouseDragged(MouseEvent e) {
		decalX = e.getX() - posX;
		decalY = e.getY() - posY;
		
		// Click gauche
		if (eventButton == 1) {
			moveTranslation(-decalX/320.0, -decalY/240.0);
		}
		
		// Click molette
		if (eventButton == 2) {
			moveRotation(-decalX, decalY);
			//rotationHead(decalX, decalY);
		}
		
		// Click droit
		if (eventButton == 3) {
			//moveLook(decalX, decalY);
		}
		
		posX = e.getX();
		posY = e.getY();
		repaint();
	}
	
	public void mouseMoved(MouseEvent e) {
		
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}
	
	public void mouseEntered(MouseEvent e) {
		
	}
	
	public void mouseExited(MouseEvent e) {
		
	}
	
	public void mousePressed(MouseEvent e) {
		posX = e.getX();
		posY = e.getY();
		eventButton = e.getButton();
	}
	
	public void mouseReleased(MouseEvent e) {
		eventButton = 0;
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) {
		moveForward(-e.getWheelRotation());
		repaint();
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

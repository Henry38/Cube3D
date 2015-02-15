package graphics;

import geometry.Plan3D;
import geometry.Polygon3D;
import geometry.Shape3D;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import math.*;
import world3d.World3D;

public class Observer extends JComponent implements MouseMotionListener, MouseListener, MouseWheelListener {
	
	private static final long serialVersionUID = 1L;
	private Base2D baseGraphic;
	private Vecteur2D oi, oj;
	
	private World3D world;
	private Point3D pointObserver, center;
	private Base3D repereObserver;
	private Vecteur3D direction;
	private double teta, phi;
	
	private double scale, d;
	private int posX, posY, decalX, decalY;
	private int eventButton;
	
	private Plan3D planObserver;
	
	/** Constructeur */
	public Observer(World3D world, Point3D pointObserver, Point3D center, int width, int height) {
		this.world = world;
		this.repereObserver = new Base3D(pointObserver);
		this.pointObserver = pointObserver;
		this.center = center;
		this.direction = new Vecteur3D(0, 0, 0);
		
		getRepereObserver();
		
		this.scale = 40.0;
		this.oi = new Vecteur2D(1*scale, 0);
		this.oj = new Vecteur2D(0, 1*scale);
		this.baseGraphic = new Base2D(new Point2D(width/2, height/2), oi, oj);
		
		int teta2 = 45;
		double rad = (teta2 / 180.0) * Math.PI;
		d = 4 / Math.tan(rad);
		this.planObserver = new Plan3D(0, 0, 1, 0);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		
		setPreferredSize(new Dimension(width, height));
	}
	
	/** Positionne la caméra dans le repère World */
	public void placeCamera(int x, int y, int z) {
		pointObserver.setX(x);
		pointObserver.setY(y);
		pointObserver.setZ(z);
	}
	
	/** Défini le point du monde  à regarder */
	public void lookAt(int x, int y, int z) {
		center.setX(x);
		center.setY(y);
		center.setZ(z);
	}
	
	/** Calcul le repere Observer associé au pointObserver et au center */
	private void getRepereObserver() {
		
		direction.setDx(center.getX()-pointObserver.getX());
		direction.setDy(center.getY()-pointObserver.getY());
		direction.setDz(center.getZ()-pointObserver.getZ());
		
		phi = Math.acos(direction.getDz() / direction.getNorme());
		teta = Math.acos(direction.getDx() / (direction.getNorme()*Math.sin(phi)));
		if (direction.getDy() < 0) {
			teta *= -1;
		}
		
		repereObserver.oi.setDx(1);
		repereObserver.oi.setDy(0);
		repereObserver.oi.setDz(0);
		
		repereObserver.oj.setDx(0);
		repereObserver.oj.setDy(1);
		repereObserver.oj.setDz(0);
		
		repereObserver.ok.setDx(0);
		repereObserver.ok.setDy(0);
		repereObserver.ok.setDz(1);
		
		repereObserver.rotationOz(teta - Math.PI/2);
		repereObserver.rotationOx(-phi);
	}
	
	/** Retourne le point 3D exprimé dans la base Observer défini depuis le repère World */
	private Point3D getPoint3DFromWorldToObserver(Point3D point) {
		return  repereObserver.getPoint3DFromBase(point, world.getBase());
	}
	
	/** Retourne le point 3D exprimé dans la base world défini depuis le repère Observer */
	private Point3D getPoint3DFromObserverToWorld(Point3D point) {
		return world.getBase().getPoint3DFromBase(point, repereObserver);
	}
	
	/** Retourne le point 2D exprimé dans la base Graphic défini depuis le repère Observer */
	private Point2D getProjectivePoint2DFromObserver(Point3D point) {
		// Projection parallèle
		//return new Point2D(point.getX(), point.getY());
		// Projection en perspective
		//System.out.println(point.getX()*d/point.getZ());
		return new Point2D(point.getX()*d/point.getZ(), point.getY()*d/point.getZ());
	}
	
	/** Retourne le point 3D exprimé dans la base Observer défini depuis le repère Graphic */
	private Point3D getProjectivePoint3DFromGraphic(Point2D point, double z) {
		return new Point3D(point.getX()*z/d, point.getY()*z/d, z);
	}
	
	private boolean isPoint3DFromWorldVisible(Point3D point) {
		Point3D p = getPoint3DFromWorldToObserver(point);
		if (p.getZ() <= 0) {
			return false;
		}
		Point2D proj_p = getProjectivePoint2DFromObserver(p);
		int x = (int) (baseGraphic.getOrigine().getX() + proj_p.getX()*oi.getDx());
		int y = (int) (baseGraphic.getOrigine().getY() + proj_p.getY()*oj.getDy());
		return (x >= 0 && x <= getWidth() && y >= 0 && y <= getHeight());
	}
	
	
	
	// Dessin
	//Color[] colors = {Color.CYAN, Color.MAGENTA, Color.PINK, Color.YELLOW, Color.ORANGE};
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		// Ligne d'horizon
		/*
		Point3D p = getPoint3DFromWorldToObserver(new Point3D(Integer.MIN_VALUE,  0,  0));
		Point2D proj_p = getProjectivePoint2DFromObserver(p);
		int py = (int) baseGraphic.getOrigine().getY() + (int) (proj_p.getX()*oi.getDy() + proj_p.getY()*oj.getDy());
		g.setColor(Color.lightGray);
		g.drawLine(0, py, getWidth(), py);
		
		Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Paint gradientPaint = new GradientPaint(0, 0, new Color(15, 157, 232), 0, py, Color.white);
        
        // Ciel
        g2d.setPaint(gradientPaint);
        g2d.fillPolygon(new int[] {0, getWidth(), getWidth(), 0}, new int[] {0, 0, py, py}, 4);
        
        // Sol
        g2d.setColor(new Color(31, 160, 85));
        g2d.fillPolygon(new int[] {0, getWidth(), getWidth(), 0}, new int[] {py, py, getHeight(), getHeight()}, 4);
        */
		
		// Dessine toutes les objets du monde
		Collections.sort(world.getlistShape(), new Comparator<Shape3D>() {
			public int compare(Shape3D shp1, Shape3D shp2) {
				Point3D p1, p2, tmp;
				tmp = world.getBase().getPoint3DFromBase(shp1.getBarycentre(), shp1.getBase());
				p1 = getPoint3DFromWorldToObserver(tmp);
				tmp = world.getBase().getPoint3DFromBase(shp2.getBarycentre(), shp2.getBase());
				p2 = getPoint3DFromWorldToObserver(tmp);
				
				return (int) (p2.getZ() - p1.getZ());
			}
		});
		
		for (Shape3D shape : world.getlistShape()) {
			fillShape3D(g, shape);
		}
		
		drawRepere(g, world.getBase());
		//drawCenter(g);
		drawCursor(g);
	}
	
	/** Dessine un point défini dans le repère World */
	private void drawPoint3D(Graphics g, Point3D point) {
		Point3D p = getPoint3DFromWorldToObserver(point);
		
		if (isPoint3DFromWorldVisible(point)) {
			Point2D proj_p = getProjectivePoint2DFromObserver(p);
			int x = (int) (baseGraphic.getOrigine().getX() + proj_p.getX()*oi.getDx());
			int y = (int) (baseGraphic.getOrigine().getY() + proj_p.getY()*oj.getDy());
			
			try {
				Image img = ImageIO.read(new File("image/point.png"));
				g.drawImage(img, x - 4, y - 4, this);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/** Dessine une droite entre deux points définis dans le repère World */
	private void drawLine3D(Graphics g, Point3D point1, Point3D point2) {
		Point3D p1 = getPoint3DFromWorldToObserver(point1);
		Point3D p2 = getPoint3DFromWorldToObserver(point2);
		Point3D tmp;
		Point2D proj_p1 = null;
		Point2D proj_p2 = null;
		
		if (planObserver.isPoint3DSideToSide(p1, p2)) {
			tmp = planObserver.getIntersectionWithSegment3D(p1, p2);
			tmp.setZ(0.001);
			
			if (p1.getZ() > 0) {
				proj_p1 = getProjectivePoint2DFromObserver(p1);
				proj_p2 = getProjectivePoint2DFromObserver(tmp);
			} else if (p2.getZ() > 0) {
				proj_p1 = getProjectivePoint2DFromObserver(tmp);
				proj_p2 = getProjectivePoint2DFromObserver(p2);
			}
			
		} else if (p1.getZ() > 0) {
			proj_p1 = getProjectivePoint2DFromObserver(p1);
			proj_p2 = getProjectivePoint2DFromObserver(p2);
		}
		
		if (proj_p1 != null && proj_p2 != null) {
			int x1 = (int) (baseGraphic.getOrigine().getX() + proj_p1.getX()*oi.getDx());
			int y1 = (int) (baseGraphic.getOrigine().getY() + proj_p1.getY()*oj.getDy());
			int x2 = (int) (baseGraphic.getOrigine().getX() + proj_p2.getX()*oi.getDx());
			int y2 = (int) (baseGraphic.getOrigine().getY() + proj_p2.getY()*oj.getDy());
			
			g.drawLine(x1, y1, x2, y2);
		}
	}
	
	/** Dessine le polygone défini dans le repère passé en paramètre */
	private void drawPolygon3D(Graphics g, Polygon3D polygon, Base3D base) {
		Point3D p1, p2;
		for (int i=0; i<polygon.nbPoints(); i++) {
			p1 = world.getBase().getPoint3DFromBase(polygon.get(i), base);
			if (i == polygon.nbPoints()-1) {
				p2 = world.getBase().getPoint3DFromBase(polygon.get(0), base);
			} else {
				p2 = world.getBase().getPoint3DFromBase(polygon.get(i+1), base);
			}
			g.setColor(polygon.getColor());
			drawLine3D(g, p1, p2);
		}
	}
	
	/** Rempli le polygon défini dans le repère passé en paramètre */ 
	private void fillPolygon3D(Graphics g, Polygon3D polygon, Base3D base) {
		Polygon polygon2D = new Polygon();
		Vecteur3D vision = new Vecteur3D(0, 0, 0);
		Point3D po = base.getPoint3DFromBase(pointObserver, world.getBase());
		Point3D p;
		Point2D proj_p;
		int x, y;
		
		vision.setDx(polygon.get(0).getX()-po.getX());
		vision.setDy(polygon.get(0).getY()-po.getY());
		vision.setDz(polygon.get(0).getZ()-po.getZ());
		
		if (polygon.getNormale() == null || Vecteur3D.produit_scalaire(vision, polygon.getNormale()) < 0) {
			polygon2D.reset();
			for (Point3D point : polygon.getListPoint()) {
				point = world.getBase().getPoint3DFromBase(point, base);
				p = getPoint3DFromWorldToObserver(point);
				proj_p = getProjectivePoint2DFromObserver(p);
				x = (int) (baseGraphic.getOrigine().getX() + proj_p.getX()*oi.getDx());
				y = (int) (baseGraphic.getOrigine().getY() + proj_p.getY()*oj.getDy());
				polygon2D.addPoint(x, y);
			}
			g.setColor(polygon.getColor());
			g.fillPolygon(polygon2D);
		}
	}
	
	/** Dessine les arêtes de la Shape3D définie dans le repère World */
	private void drawShape3D(Graphics g, Shape3D shape) {
		for (Polygon3D polygon : shape.getListPolygon3D()) {
			drawPolygon3D(g, polygon, shape.getBase());
		}
	}
	
	/** Dessine les faces de la Shape3D définie dans le repère World */
	private void fillShape3D(Graphics g, Shape3D shape) {
		for (Polygon3D polygon : shape.getListPolygon3D()) {
			fillPolygon3D(g, polygon, shape.getBase());
		}
	}
	
	/** Dessine la base définie dans le repère World */
	private void drawRepere(Graphics g, Base3D base) {
		Point3D o, p;//, p1;
		
		o = base.getOrigine();
		for (Vecteur3D vect : base.getVecteurs()) {
			if (vect == base.oi) {
				g.setColor(new Color(255, 0, 0));
				//p1 = new Point3D(Integer.MAX_VALUE, 0, 0);
			} else if (vect == base.oj) {
				g.setColor(new Color(0, 255, 0));
				//p1 = new Point3D(0, Integer.MAX_VALUE, 0);
			} else {// if (vect == base.ok) {
				g.setColor(new Color(0, 0, 255));
				//p1 = new Point3D(0, 0, Integer.MAX_VALUE);
			}
			
			p = o.clone();
			p.translation(vect);
			drawLine3D(g, o, p);
		}
	}
	
	/** Dessine le chemin de l'origine jusqu'au point regardé */
	private void drawCenter(Graphics g) {
		g.setColor(Color.cyan);
		drawLine3D(g, new Point3D(0, 0, 0), new Point3D(center.getX(), 0, 0));
		drawLine3D(g, new Point3D(center.getX(), 0, 0), new Point3D(center.getX(), center.getY(), 0));
		drawLine3D(g, new Point3D(center.getX(), center.getY(), 0), new Point3D(center.getX(), center.getY(), center.getZ()));
	}
	
	/** Dessine le curseur du centre de la caméra */
	private void drawCursor(Graphics g) {
		int x = (int) baseGraphic.getOrigine().getX();
		int y = (int) baseGraphic.getOrigine().getY();
		g.setColor(Color.magenta);
		g.drawLine(x-4, y, x+4, y);
		g.drawLine(x, y-4, x, y+4);
	}
	
	
	
	
	// Mouvement de la caméra
	/** Translate la caméra sur le plan d'affichage */
	public void moveTranslation(int dx, int dy) {
		Point3D p3d = getPoint3DFromObserverToWorld(new Point3D(dx/(8*scale), dy/(8*scale), 0));
		Matrix m = world.getBase().getMatrixTranslation(repereObserver);
		p3d.translation(-m.get(0, 0), -m.get(1, 0), -m.get(2, 0));
		
		pointObserver.translation(p3d.getX(), p3d.getY(), p3d.getZ());
		center.translation(p3d.getX(), p3d.getY(), p3d.getZ());
	}
	
	/** Tourne autour du point regardé */
	public void moveRotation(int dx, int dy) {
		// Point3D de rotation
		Point3D p = pointObserver.clone();
		p.translation(repereObserver.ok);
		p.translation(repereObserver.ok);
		
		// Direction
		Vecteur3D vect = new Vecteur3D(0, 0, 0);
		vect.setDx(pointObserver.getX() - p.getX());
		vect.setDy(pointObserver.getY() - p.getY());
		vect.setDz(pointObserver.getZ() - p.getZ());
		
		double r = vect.getNorme();
		double pPhi = Math.acos(vect.getDz() / r);
		double pTeta = Math.acos(vect.getDx() / (r*Math.sin(pPhi)));
		if (vect.getDy() < 0) {
			pTeta *= -1.0;
		}
		pTeta += ((float) -dx) / ((float) getWidth()) * Math.PI;
		pPhi += ((float) -dy) / ((float) getHeight()) * Math.PI;
		if (pPhi > 7*Math.PI/8) {
			pPhi = 7*Math.PI/8;
		} else if (pPhi < Math.PI/8) {
			pPhi = Math.PI/8;
		}
		
		pointObserver.setX(p.getX() + r * Math.sin(pPhi) * Math.cos(pTeta));
		pointObserver.setY(p.getY() + r * Math.sin(pPhi) * Math.sin(pTeta));
		pointObserver.setZ(p.getZ() + r * Math.cos(pPhi));
		
		getRepereObserver();
	}
	
	/*public void moveLook(int dx, int dy) {
		teta += ((float) -dx) / ((float) getWidth()) * Math.PI;
		phi += ((float) dy) / ((float) getHeight()) * Math.PI;
		if (phi > 7*Math.PI/8) {
			phi = 7*Math.PI/8;
		} else if (phi < Math.PI/8) {
			phi = Math.PI/8;
		}
		getRepereObserver();
	}*/
	
	/** Fait avancer la caméra dans la direction du regard */
	public void moveForward(int step) {
		Point3D p3d = getPoint3DFromObserverToWorld(new Point3D(0, 0, step));
		Matrix m = world.getBase().getMatrixTranslation(repereObserver);
		p3d.translation(-m.get(0, 0), -m.get(1, 0), -m.get(2, 0));
		
		pointObserver.translation(p3d.getX(), p3d.getY(), p3d.getZ());
		center.translation(p3d.getX(), p3d.getY(), p3d.getZ());
	}
	
	/**Regarde dans la direction du point visé */
	public void moveToPoint2D(int x, int y) {
		Point2D p2d = new Point2D((x - (int) baseGraphic.getOrigine().getX()) / (scale), (y - (int) baseGraphic.getOrigine().getY()) / (scale));
		Point3D p3d = getProjectivePoint3DFromGraphic(p2d, 2);
		
		center = getPoint3DFromObserverToWorld(p3d);
		getRepereObserver();
	}
	
	
	
	
	// Detection de la souris
	public void mouseDragged(MouseEvent e) {
		decalX = e.getX() - posX;
		decalY = e.getY() - posY;
		
		// Click gauche
		if (eventButton == 1) {
			moveTranslation(-decalX, -decalY);
		}
		
		// Click molette
		if (eventButton == 2) {
			moveRotation(decalX, decalY);
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
		moveToPoint2D(e.getX(), e.getY());
		repaint();
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
	
}

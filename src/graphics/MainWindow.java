package graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import world.Camera;
import world.Scene3D;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	/** Constructeur */
	public MainWindow(Scene3D world, Camera camera, int width, int height) {
		super("3D Application");
		
		this.setFocusable(true);
//		this.setJMenuBar(new Menu());
		
		WorldPanel worldPanel = new WorldPanel(world);
		CameraPanel cameraPanel = new CameraPanel();
		
		camera.addObserver(cameraPanel);
		
		JTabbedPane controlPanel = new JTabbedPane();
		controlPanel.setPreferredSize(new Dimension(192, 480));
		controlPanel.add(worldPanel);
		controlPanel.add(cameraPanel);
		
		Viewer observer = new Viewer(world, camera, width, height);
		
		JPanel panneau = new JPanel();
		panneau.setLayout(new BorderLayout());
		panneau.add(controlPanel, BorderLayout.WEST);
		panneau.add(observer, BorderLayout.CENTER);
		setContentPane(panneau);
		
		setResizable(true);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}
	
//	private class Menu extends JMenuBar {
//		
//		JMenu fichier;
//		JMenuItem quit;
//		
//		public Menu() {
//			super();
//			
//			quit = new JMenuItem("Quitter");
//			fichier = new JMenu("Fichier");
//			fichier.add(quit);
//			
//			add(fichier);
//		}
//	}
}



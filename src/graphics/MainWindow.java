package graphics;

import java.awt.BorderLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import world3d.Camera;
import world3d.World3D;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	/** Constructeur */
	public MainWindow(World3D world, Camera camera, int width, int height) {
		super("3D Application");
		
		this.setFocusable(true);
//		this.setJMenuBar(new Menu());
		
		ControlPanel control = new ControlPanel(world, camera);
		Viewer observer = new Viewer(world, camera, width, height);
		
		JPanel panneau = new JPanel();
		panneau.setLayout(new BorderLayout());
		panneau.add(control, BorderLayout.WEST);
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



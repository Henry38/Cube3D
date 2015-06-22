package graphics;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import math.Point3D;
import world3d.Camera;
import world3d.Camera.TYPE;
import world3d.World3D;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel panneau;
	
	public MainWindow(World3D world) {
		super("3D Application");
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		int width = (int) tk.getScreenSize().getWidth();
		int height = (int) tk.getScreenSize().getHeight();
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		
		ControlPanel control = new ControlPanel();
		
		Camera camera = new Camera(new Point3D(2, 2, 1), new Point3D(0, 0, 0), TYPE.PERSPECTIVE);
		Observer observer = new Observer(world, camera, 640, 480);
		
		panneau = new JPanel();
		panneau.setLayout(new BorderLayout());
		//panneau.add(control, BorderLayout.WEST);
		panneau.add(observer, BorderLayout.CENTER);
		setContentPane(panneau);
		
		pack();
		setLocationRelativeTo(null);
	}
}



package graphics;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CameraPanel extends JPanel implements Observer {
	
	private static final long serialVersionUID = 1L;
	
	/** Constructeur */
	public CameraPanel() {
		super();
		
		setName("Camera");		
		setLayout(null);
		
		JLabel position = new JLabel("Position :\n\t 3.5");
		position.setBounds(16, 16, 160, 32);
		add(position);
	}
	
	@Override
	public void update(Observable obs, Object obj) {
//		Camera camera = (Camera) obs;
//		Point3D pointCamera = camera.getOrigine();
//		Point3D pointObserver = camera.getObserver();
//		Vecteur3D direction = new Vecteur3D(pointCamera, pointObserver);
//		
//		System.out.println(direction);
	}
	
//	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		
//	}
}

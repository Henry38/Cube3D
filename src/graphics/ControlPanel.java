package graphics;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	/** Constructeur */
	public ControlPanel() {
		super();
		
		setPreferredSize(new Dimension(128, 480));
		
		JButton ok = new JButton("Ok");
		add(ok);
	}
}

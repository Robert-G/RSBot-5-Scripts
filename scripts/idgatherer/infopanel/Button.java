package idgatherer.infopanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Button extends JButton {

	private static final long serialVersionUID = 1L;
	
	private String text = "Npcs";
	private byte value = 0;
	
	public byte getValue() {
		return value;
	}
	
	private void switchText() {
		if (text.equals("Npcs")) {
			text = "GameObjects";
			value = 1;
		} else {
			text = "Npcs";
			value = 0;
		}
		setText(text);
	}
	
	public Button() {
		setText(text);
		addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				switchText();
			}
			
		});
	}

}

package idgatherer.infopanel;

import idgatherer.classgenerator.ClassGenerator;
import idgatherer.datamanager.DataManager;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class InfoPanel extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;
	private InfoArea infoArea;
	private Button button;
	private DataManager dm;
	private File parentFile;
	private boolean closing;

	public byte getSetting() {
		return button.getValue();
	}
	
	public void updatePanel() {
		infoArea.updateText(dm.getMap(), button.getText());
	}
	
	public DataManager getDataManager() {
		return dm;
	}
	
	public boolean isClosing() {
		return closing;
	}
	
	private void generateClass() {
		ClassGenerator g = new ClassGenerator(dm.getMap(), parentFile, button.getText());
		g.generateClass();
	}
	
	public InfoPanel(File file) {
		dm = new DataManager(this);
		parentFile = file;
		scrollPane = new JScrollPane();
		infoArea = new InfoArea();
		addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {}

			@Override
			public void windowClosed(WindowEvent arg0) {
				generateClass();
				closing = true;
			}

			@Override
			public void windowClosing(WindowEvent arg0) {}

			@Override
			public void windowDeactivated(WindowEvent arg0) {}

			@Override
			public void windowDeiconified(WindowEvent arg0) {}

			@Override
			public void windowIconified(WindowEvent arg0) {}

			@Override
			public void windowOpened(WindowEvent arg0) {}
			
		});
		setTitle("ID Gatherer");
		
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		button = new Button();
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dm.clearMap();
				updatePanel();
			}
			
		});
		contentPane.add(button, BorderLayout.PAGE_START);

		scrollPane.setViewportView(infoArea);
		
		contentPane.add(scrollPane, BorderLayout.CENTER);
		pack();
		setSize(800, 500);
		setLocationRelativeTo(getOwner());
		setVisible(true);
	}
	
}

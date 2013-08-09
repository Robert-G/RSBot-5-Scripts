package idgatherer.infopanel;

import java.util.Map;

import javax.swing.JTextArea;

public class InfoArea extends JTextArea {

	private static final long serialVersionUID = 1L;
	
	public InfoArea() {
		setWrapStyleWord(true);
		setEditable(true);
	}
	
	public void updateText(Map<String, int[]> map, String objectType) {
		setText("");
		append("public class " + objectType + " {\n");
		append("\n");
			for (final Map.Entry<String, int[]> entry : map.entrySet()) {
				String fieldName = entry.getKey().replaceAll(" ", "_").replaceAll("[^A-Za-z0-9_]", "");
				if (entry.getValue().length == 1) {
					append("    public static final int " + fieldName + " = " + entry.getValue()[0] + ";\n");
					append("\n");
				} else {
					StringBuilder f = new StringBuilder("    public static final int[] " + fieldName + " = { ");
					int index = 0;
					for (int id : entry.getValue()) {
						if (index != entry.getValue().length-1) {
							f.append(id + ", ");
						} else {
							f.append(id + " };");
						}
						index++;
					}
					append(f.toString() + "\n");
					append("\n");
				}
		}
		append("}");
	}
	
	public void clearText() {
		setText("");
	}

}

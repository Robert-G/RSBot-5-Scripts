package idgatherer.classgenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class ClassGenerator {
	
	private String objectType;
	private Map<String, int[]> map;
	private File parentFile;
	
	public ClassGenerator(Map<String, int[]> m, File file, String objectT) {
		map = m;
		parentFile = file;
		objectType = objectT;
	}
	
	public void generateClass() {
		try {
			String fileName = objectType + ".java";
			File file = new File(parentFile, fileName);
			if (!file.exists())file.createNewFile();
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			out.write("public class  " + objectType + "  {");
			for (int i = 0; i < 2; i++)
				out.newLine();
   			for (final Map.Entry<String, int[]> entry : map.entrySet()) {
   				String fieldName = entry.getKey().replaceAll(" ", "_").replaceAll("[^A-Za-z0-9_]", "");
   				if (entry.getValue().length == 1) {
   					out.write("    public static final int " + fieldName + " = " + entry.getValue()[0] + ";");
   					out.newLine();
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
   					out.write(f.toString());
   					out.newLine();
   				}
				out.newLine();
			}
   			out.write("}");
			out.flush();
			out.close();
			System.out.println("Saved to: " + file.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

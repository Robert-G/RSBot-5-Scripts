package idgatherer.datamanager;

import idgatherer.infopanel.InfoPanel;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.powerbot.script.lang.BasicNamedQuery;
import org.powerbot.script.wrappers.GameObject;
import org.powerbot.script.wrappers.Identifiable;
import org.powerbot.script.wrappers.Npc;

public class DataManager {
	
	private Map<String, int[]> map;
	private InfoPanel infoPanel;
	
	public DataManager(InfoPanel infoP) {
		infoPanel = infoP;
		map = Collections.synchronizedMap(new HashMap<String, int[]>());
	}
	
	private int[] updateArray(int[] array, int idToAdd) {
		if (array == null) {
			return new int[]{idToAdd};
		} else {
			int[] updatedArray = new int[array.length + 1];
			for (int i = 0; i < array.length; i++) {
				updatedArray[i] = array[i];
			}
			updatedArray[updatedArray.length-1] = idToAdd;
			return updatedArray;
		}
	}
	
	private boolean containsId(int[] array, int id) {
		if (array == null)return false;
		for (int i : array) {
			if (i == id) {
				return true;
			}
		}
		return false;
	}
	
	private String getInfo(Object ob) {
		String def = null;
		if (ob instanceof GameObject) {
			def = ((GameObject)ob).getName().toUpperCase();
		} else {
			def = ((Npc)ob).getName().toUpperCase() + "_LEVEL_" + ((Npc)ob).getLevel();
		}
		return def;
	}
	
	private boolean mapContains(String key) {
		return map.containsKey(key);
	}
	
	private void putMap(String key, int[] array) {
		map.put(key, array);
	}
	
	public Map<String, int[]> getMap() {
		return map;
	}
	
	public void clearMap() {
		map.clear();
	}
	
	public void analyseObjects(BasicNamedQuery<?> objects) {
		if (objects != null) {
			for (Object ob : objects) {
				String obInfo = getInfo(ob);
				if (obInfo != null) {
					if (obInfo.toLowerCase().contains("null") || obInfo.length() < 1)continue;
					boolean update = false;
					if (!mapContains(obInfo)) {
						putMap(obInfo, new int[]{((Identifiable)ob).getId()});
						update = true;
					} else {
						int[] temp = map.get(obInfo);
						if (containsId(temp, ((Identifiable)ob).getId())) {
							continue;
						} else {
							temp = updateArray(temp, ((Identifiable)ob).getId());
							putMap(obInfo, temp);
							update = true;
						}
					}
					if (update) {
						infoPanel.updatePanel();
					}
				}
			}
		}
	}

}

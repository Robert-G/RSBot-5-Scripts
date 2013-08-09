package idgatherer;

import idgatherer.infopanel.InfoPanel;

import javax.swing.SwingUtilities;

import org.powerbot.script.Manifest;
import org.powerbot.script.PollingScript;
import org.powerbot.script.lang.BasicNamedQuery;

@Manifest(
		authors = { "Robert G" },
		name = "ID Gatherer", 
		description = "Gathers loaded NPC/SceneEntity names into ready to use fields along with the IDs, npcs of same name but different id are grouped into an array." +
				"All data generated is saved when the gui window is closed.")
public class IDGatherer extends PollingScript {
	
	private InfoPanel infoPanel;
	private BasicNamedQuery<?> cur, last;
	
	private BasicNamedQuery<?> loadSelectedObjects() {
		return infoPanel.getSetting() == 0 ? ctx.npcs.select() : ctx.objects.select();
	}
	
	@Override
	public int poll() {
		if (infoPanel == null) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					infoPanel = new InfoPanel(getStorageDirectory());
				}
			});
		} else {
			if (infoPanel.isClosing() || !infoPanel.isVisible()) {
				stop();
			} else {
				if ((cur = loadSelectedObjects()) != null && !cur.equals(last)) {
					infoPanel.getDataManager().analyseObjects(cur);
					last = cur;
				} else {
					return 100;
				}
			}
		}
		return 100;
	}
	
	@Override
	public void stop() {
		if (infoPanel != null) {
			infoPanel.dispose();
		}
	}

}

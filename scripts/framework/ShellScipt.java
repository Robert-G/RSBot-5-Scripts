package framework;

import java.util.ArrayList;

import org.powerbot.script.PollingScript;
import org.powerbot.script.methods.Game;

import framework.taskShell.TaskShell;

public abstract class ShellScipt extends PollingScript {

	private final ArrayList<TaskShell<?>> taskList = new ArrayList<TaskShell<?>>();
	private String scriptState = "Loading...";
	
	public void addTasks(TaskShell<?>... tasks) {
		for (TaskShell<?> task : tasks) {
			if (!taskList.contains(task)) {
				taskList.add(task);
			}
		}
	}
	
	@Override
	public int poll() {
		if (!ctx.game.isLoggedIn() || ctx.game.getClientState() != Game.INDEX_MAP_LOADED) {
			return 600;
		}
		for (TaskShell<?> task : taskList) {
			if (task.activate()) {
				return task.loop();
			}
		}
		return 100;
	}

	public String getScriptState() {
		return scriptState;
	}

	public void setScriptState(String scriptState) {
		this.scriptState = scriptState;
	}

}

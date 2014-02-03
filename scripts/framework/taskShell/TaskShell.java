package framework.taskShell;

import org.powerbot.script.methods.Game.Crosshair;
import org.powerbot.script.methods.MethodProvider;

import framework.ShellScipt;

public abstract class TaskShell<T extends ShellScipt> extends MethodProvider {
	
	protected final T script;

	public TaskShell(T script) {
		super(script.getController().getContext());
		this.script = script;
	}
	
	public abstract boolean activate();
	
	public int loop() {
		return 0;
	}
	
	protected boolean didAction() {
		return ctx.game.getCrosshair() == Crosshair.ACTION;
	}

}

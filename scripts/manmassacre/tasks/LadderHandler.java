package manmassacre.tasks;

import java.util.concurrent.Callable;

import manmassacre.ManMassacre;

import org.powerbot.script.util.Condition;

import framework.taskShell.TaskShell;

public class LadderHandler extends TaskShell<ManMassacre> {
	
	private final int ladder = 26983;

	public LadderHandler(ManMassacre script) {
		super(script);
	}

	@Override
	public boolean activate() {
		return !ctx.objects.select().id(ladder).isEmpty();
	}
	
	@Override
	public int loop() {
		script.setScriptState("Climbing down ladder.");
		if (ctx.objects.nearest().poll().interact("Climb-down", "Ladder") && didAction()) {
			Condition.wait(new Callable<Boolean>() {
	
				@Override
				public Boolean call() throws Exception {
					return ctx.objects.select().id(ladder).isEmpty();
				}
					
			});
		}
		return 0;
	}

}

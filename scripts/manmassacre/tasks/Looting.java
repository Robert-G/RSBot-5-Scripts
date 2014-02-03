package manmassacre.tasks;

import java.util.concurrent.Callable;

import org.powerbot.script.util.Condition;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.GroundItem;
import org.powerbot.script.wrappers.Player;

import framework.taskShell.TaskShell;
import manmassacre.ManMassacre;

public class Looting extends TaskShell<ManMassacre> {
	
	private final Player player;

	public Looting(ManMassacre script) {
		super(script);
		this.player = ctx.players.local();
	}

	@Override
	public boolean activate() {
		return ctx.backpack.select().count() < 28 && !player.getInteracting().isValid() && !ctx.groundItems.select().within(10).id(199).isEmpty();
	}
	
	@Override
	public int loop() {
		final GroundItem item = ctx.groundItems.poll();
		script.setScriptState("Looting " + item.getName() + ".");
		if (item.isOnScreen() && item.interact("take", item.getName()) && didAction()) {
			Condition.wait(new Callable<Boolean>() {

				@Override
				public Boolean call() throws Exception {
					return !item.isValid();
				}
				
			});
		} else {
			ctx.camera.turnTo(item);
		}
		return Random.nextInt(100, 600);
	}

}

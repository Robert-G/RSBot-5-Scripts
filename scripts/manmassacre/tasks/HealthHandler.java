package manmassacre.tasks;

import java.util.concurrent.Callable;

import manmassacre.ManMassacre;

import org.powerbot.script.util.Condition;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.Player;
import org.powerbot.script.wrappers.Tile;

import framework.taskShell.TaskShell;

public class HealthHandler extends TaskShell<ManMassacre> {
	
	private final Tile bankTile = new Tile(3094, 3496, 0);
	private final Player player;
	private long start = 0;
	
	public HealthHandler(ManMassacre script) {
		super(script);
		this.player = ctx.players.local();
	}
	
	private boolean isLessThan30() {
		return System.currentTimeMillis() - start < 30000;
	}
	
	@Override
	public boolean activate() {
		if (player.getHealthPercent() < 10) {
			start = System.currentTimeMillis();
			return true;
		}
		return player.getHealthPercent() < 100 && isLessThan30();
	}
	
	@Override
	public int loop() {
		while (isLessThan30()) {
			if (bankTile.distanceTo(player) <= 3) {
				script.setScriptState("Waiting for health to rise.");
				if (player.getHealthPercent() < 100) {
					start = 0;
					sleep(600);
				} else {
					start = System.currentTimeMillis() + 30000;
					return 0;
				}
			} else {
				script.setScriptState("Walking to bank.");
				if (ctx.movement.stepTowards(bankTile)) {
					Condition.wait(new Callable<Boolean>() {

						@Override
						public Boolean call() throws Exception {
							return ctx.movement.getDestination().distanceTo(player) < Random.nextInt(3, 9);
						}
						
					});
					return 0;
				}
			}
		}
		return 0;
	}

}

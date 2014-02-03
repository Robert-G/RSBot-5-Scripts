package manmassacre.tasks;

import java.util.concurrent.Callable;

import manmassacre.ManMassacre;

import org.powerbot.script.lang.Filter;
import org.powerbot.script.util.Condition;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.Npc;
import org.powerbot.script.wrappers.Player;
import org.powerbot.script.wrappers.Tile;

import util.Util;
import framework.taskShell.TaskShell;

public class Combat extends TaskShell<ManMassacre> {
	
	private final int[] ids = { 1, 2, 3 };
	private final Tile npcLoc = new Tile(3096, 3510, 0);
	private final Player player;

	public Combat(ManMassacre script) {
		super(script);
		this.player = ctx.players.local();
	}
	
	private Filter<Npc> interactingFilter() {
		return new Filter<Npc>() {

			@Override
			public boolean accept(Npc arg0) {
				if (arg0.getLevel() < 1 || arg0.getHealthPercent() < 1  || !arg0.getInteracting().isValid()) {
					return false;
				}
				return arg0.getInteracting().equals(player);
			}
			
		};
	}
	
	private Filter<Npc> npcFilter() {
		return new Filter<Npc>() {

			@Override
			public boolean accept(Npc arg0) {
				if (!Util.arrayContains(ids, arg0.getId()) || arg0.getInteracting().isValid()) {
					return false;
				}
				if (arg0.getHealthPercent() > 0 && arg0.getAnimation() == -1) {
					return !arg0.getInteracting().isValid();
				}
				return false;
			}
			
		};
	}
	
	@Override
	public boolean activate() {
		return player.getHealthPercent() >= 10 && !player.getInteracting().isValid();
	}
	
	@Override
	public int loop() {
		if (!ctx.npcs.select().select(interactingFilter()).isEmpty() || !ctx.npcs.select().select(npcFilter()).isEmpty()) {
			final Npc npc = ctx.npcs.nearest().poll();
			script.setScriptState("Attacking " + npc.getName() + ".");
			if (npc.interact("Attack", npc.getName()) && didAction()) {
				Condition.wait(new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						return player.getInteracting().isValid();
					}
					
				});
				return 0;
			}
		} else {
			if (!npcLoc.getMatrix(ctx).isOnScreen()) {
				if (ctx.movement.stepTowards(npcLoc)) {
					Condition.wait(new Callable<Boolean>() {

						@Override
						public Boolean call() throws Exception {
							return ctx.movement.getDestination().distanceTo(player) < Random.nextInt(3, 9);
						}
						
					});
					return 0;
				}
			} else {
				script.setScriptState("Waiting for man to spawn.");
				return Random.nextInt(100, 1200);
			}
		}
		return 0;
	}

}

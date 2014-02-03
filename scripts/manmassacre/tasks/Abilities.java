package manmassacre.tasks;

import manmassacre.ManMassacre;

import org.powerbot.script.lang.Filter;
import org.powerbot.script.lang.IdQuery;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.Action;
import org.powerbot.script.wrappers.Action.Type;
import org.powerbot.script.wrappers.Actor;

import framework.taskShell.TaskShell;

public class Abilities extends TaskShell<ManMassacre> {

	private IdQuery<Action> actions;
	private Actor in = null;

	public Abilities(ManMassacre script) {
		super(script);
	}
	
	private Filter<Action> actionFilter() {
		return new Filter<Action>() {

			@Override
			public boolean accept(Action arg0) {
				return arg0.getType() == Type.ABILITY && arg0.isReady();
			}
			
		};
	}

	@Override
	public boolean activate() {
		return (in = ctx.players.local().getInteracting()).isValid() && in.getLevel() > 0 && in.getHealthPercent() > 1;
	}
	
	@Override
	public int loop() {
		script.setScriptState("Using abilities.");
		if (ctx.combatBar.isExpanded()) {
			actions = ctx.combatBar.select().select(actionFilter());
			actions.shuffle();
			for (final Action a : actions) {
				ctx.keyboard.send(a.getBind());
				return Random.nextInt(800, 1200);
			}
		}
		return 50;
	}

}

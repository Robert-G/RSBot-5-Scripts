package manmassacre.tasks;

import java.util.concurrent.Callable;

import manmassacre.ManMassacre;

import org.powerbot.script.util.Condition;
import org.powerbot.script.util.Random;
import org.powerbot.script.wrappers.Tile;

import framework.taskShell.TaskShell;

public class Banking extends TaskShell<ManMassacre> {
	
	private final Tile bankTile = new Tile(3094, 3494);

	public Banking(ManMassacre script) {
		super(script);
	}

	@Override
	public boolean activate() {
		return ctx.backpack.select().count() == 28 || ctx.bank.isOpen();
	}
	
	@Override
	public int loop() {
		if (!bankTile.getMatrix(ctx).isOnScreen()) {
			script.setScriptState("Walking to bank.");
			if (ctx.movement.stepTowards(bankTile)) {
				Condition.wait(new Callable<Boolean>() {

					@Override
					public Boolean call() throws Exception {
						return ctx.movement.getDestination().distanceTo(ctx.players.local()) < Random.nextInt(3, 9);
					}
					
				});
				return 0;
			}
		} else {
			if (ctx.bank.open()) {
				script.setScriptState("Depositing items.");
				if (ctx.backpack.select().count() > 0) {
					if (ctx.bank.depositInventory()) {
						Condition.wait(new Callable<Boolean>() {

							@Override
							public Boolean call() throws Exception {
								return ctx.backpack.select().count() == 0;
							}

						});
						return Random.nextInt(10, 350);
					}
				} else {
					if (ctx.bank.close()) {
						Condition.wait(new Callable<Boolean>() {

							@Override
							public Boolean call() throws Exception {
								return ctx.bank.close();
							}

						});
						return Random.nextInt(10, 350);
					}
				}
			}
			if (!ctx.bank.isOpen()) {
				script.setScriptState("Opening bank.");
				if (ctx.bank.open()) {
					Condition.wait(new Callable<Boolean>() {

						@Override
						public Boolean call() throws Exception {
							return ctx.bank.isOpen();
						}

					});
				}
			} else {
				
			}
		}
		return 0;
	}

}

package manmassacre;

import java.awt.Graphics;

import org.powerbot.event.PaintListener;
import org.powerbot.script.Manifest;

import manmassacre.graphics.Paint;
import manmassacre.tasks.Abilities;
import manmassacre.tasks.Banking;
import manmassacre.tasks.Combat;
import manmassacre.tasks.HealthHandler;
import manmassacre.tasks.LadderHandler;
import manmassacre.tasks.Looting;
import framework.ShellScipt;

@Manifest(description = "Kills level 6 Men in Edgeville, uses abilities, heals in bank if health low, loots some items.", name = "ManMassacre")
public class ManMassacre extends ShellScipt implements PaintListener {
	
	private final Paint paint;

	public ManMassacre() {
		this.paint = new Paint(this);
	}
	
	@Override
	public void start() {
		this.addTasks(
			new LadderHandler(this),
			new Banking(this),
			new HealthHandler(this),
			new Looting(this),
			new Combat(this),
			new Abilities(this)
		);
	}

	@Override
	public void repaint(Graphics arg0) {
		paint.doPaint(arg0);
	}

}

package manmassacre.graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;

import manmassacre.ManMassacre;
import util.Util;
import wrappers.SkillData;

public class Paint {
	
	private final ManMassacre man;
	private final SkillData<ManMassacre> sd;
	private final int[] skills = new int[25];

	public Paint(ManMassacre man) {
		this.man = man;
		this.sd = new SkillData<ManMassacre>(man);
		for (int i = 0; i < skills.length; i++) {
			skills[i] = i;
		}
	}
	
	private int gainedExp(boolean ph) {
		int xp = 0;
		for (int skill : skills) {
			xp += sd.getGainedExp(skill, ph);
		}
		return xp;
	}
	
	private int gainedLevels() {
		int xp = 0;
		for (int skill : skills) {
			xp += sd.getGainedLevels(skill);
		}
		return xp;
	}

	public void doPaint(Graphics arg0) {
		Graphics g = (Graphics2D)arg0;
		g.drawString("ManMassacre v1.0", 5, 15);
		g.drawString("Run time: " + sd.getRunTime(), 5, 30);
		g.drawString("State: " + man.getScriptState(), 5, 45);
		g.drawString("Gained Levels: " + Util.formatNumber(gainedLevels()), 5, 60);
		g.drawString("Gained Exp: " + Util.formatNumber(gainedExp(false)) + " (" + Util.formatNumber(gainedExp(true)) + " ph)", 5, 75);
	}

}

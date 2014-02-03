package wrappers;

import org.powerbot.script.methods.MethodProvider;
import org.powerbot.script.methods.Skills;

import util.Util;
import framework.ShellScipt;

public class SkillData<T extends ShellScipt> extends MethodProvider {
	
	private final T t;
	private final int[] startLevels;
	private final int[] startExp;
	
	public SkillData(T script) {
		super(script.getController().getContext());
		this.t = script;
		this.startLevels = ctx.skills.getRealLevels();
		this.startExp = ctx.skills.getExperiences();
	}
	
	public int getGainedExp(int skill, boolean ph) {
		final int exp = (ctx.skills.getExperience(skill) - startExp[skill]);
		return exp > 0 ? ph ? perHour(exp) : exp : 0;
	}
	
	public String getGainedExpString(int skill, boolean ph) {
		final int exp = getGainedExp(skill, ph);
		return exp > 0 ? Util.formatNumber(exp) : "0";
	}
	
	public int getGainedLevels(int skill) {
		return ctx.skills.getRealLevel(skill) - startLevels[skill];
	}
	
	public String getRunTime() {
		return Util.formatTime(t.getRuntime());
	}
	
	public long getRunTimeLong() {
		return t.getRuntime();
	}
	
	public int getExpToLevel(int skill, int endLvl) {
		if (ctx.skills.getRealLevel(skill) < 99) {
			return Skills.XP_TABLE[endLvl] - ctx.skills.getExperience(skill);
		}
		return 0;
	}
	
	public int getExpToLevel(int skill) {
		final int level = ctx.skills.getRealLevel(skill);
		if (level < 99) {
			return Skills.XP_TABLE[level + 1] - ctx.skills.getExperience(skill);
		}
		return 0;
	}
	
	public long getTimeTNL(int skill) {
		final int exp = getGainedExp(skill, true);
		return exp > 0 ? (long)((double)getExpToLevel(skill) / (double)exp * 3600000) : 0;
	}
	
	public String getTimeTNLString(int skill) {
		final long time = getTimeTNL(skill);
		return time > 0 ? Util.formatTime(time) : "0";
	}

	public int perHour(int arg0) {
		return arg0 > 0 ? (int)(3600000.0 / t.getRuntime() * arg0) : 0;
	}
	
	public String perHourString(int arg0) {
		return arg0 > 0 ? Util.formatNumber(perHour(arg0)) : "0";
	}

}

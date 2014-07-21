package org.kenneh.scripts.fighter;

import org.kenneh.core.ActionScript;
import org.kenneh.core.context.Context;
import org.kenneh.util.Calculations;
import org.kenneh.util.SkillData;
import org.powerbot.script.PaintListener;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.Item;
import org.powerbot.script.rt6.Skills;

import java.awt.*;

/**
 * Created by Kenneth on 7/20/2014.
 */
@Script.Manifest(
        name = "Auto Fighter",
        description = "Fights things"
)
public class AutoFighter extends ActionScript<Context> implements PaintListener {

    private final Font font = new Font("Calibri", Font.PLAIN, 13);
    private final Color trans = new Color(0, 0, 0, 200);

    private FighterGUI gui;

    private int[] monsterIds;
    private String[] lootNames;

    @Override
    public void start() {
        gui = new FighterGUI(ctx);
        gui.setVisible(true);
        lastXP = ctx.skills.experience(Skills.CONSTITUTION);
    }

    private int lastXP = 0;
    private int drawY = 30;
    private int kills = -1;

    @Override
    public void repaint(Graphics graphics) {
        graphics.setFont(font);

        graphics.setColor(trans);
        graphics.fillRect(1, 88, 250, drawY - 92);
        graphics.setColor(Color.WHITE);

        graphics.drawString("Runtime: "+ Calculations.formatTime(getRuntime()), 5, 100);
        graphics.drawString("Status: " + ctx.status.get(), 5, 112);

        final Item food = ctx.utilities.getFood().poll();
        graphics.drawString("Food: "+ (food.valid() ? food.name() : "none"), 5, 124);
        graphics.drawString("Health: "+ ctx.utilities.getHealthPercent(), 5, 136);
        graphics.drawString("Kills: " + Calculations.perHour(kills) + "/hr (+" + Calculations.formatNumber(kills) + ")", 5, 148);

        int y = 175;
        for(int i = 0; i < SkillData.NUM_SKILL; i++) {
            final int experience = ctx.skillTracker.experience(i);
            if(experience > 0) {
                graphics.drawString(Calculations.capitalize(SkillData.SKILL_NAMES[i]), 5, y);
                y += 12;
                graphics.drawString("- Level: " + ctx.skills.level(i) + " (+" + ctx.skillTracker.level(i) + ")", 5, y);
                y += 12;
                graphics.drawString("- Experience: "+ Calculations.perHour(experience) + "/hr (+" + Calculations.formatNumber(experience) + ")", 5, y);
                y += 12;
                graphics.drawString("- Time until level: "+ Calculations.formatTime(ctx.skillTracker.timeToLevel(SkillData.Rate.HOUR, i)), 5, y);
                y += 12;
                if(i == Skills.SLAYER) {
                    graphics.drawString("- Task completion: "+ ctx.utilities.getSlayerTaskCompletionPercent() + "% - " + ctx.utilities.getSlayerAmountRemaining() + " left.", 5, y);
                    y += 12;
                }
            }
        }
        drawY = y;

        final int exp = ctx.skills.experience(Skills.CONSTITUTION);
        if(exp > lastXP) {
            kills++;
            lastXP = exp;
        }

    }

    public void setMonsterIds(int[] monsterIds) {
        this.monsterIds = monsterIds;
    }

    public void setLootNames(String[] lootNames) {
        this.lootNames = lootNames;
    }

    public int[] getMonsterIds() {
        return monsterIds;
    }

    public String[] getLootNames() {
        return lootNames;
    }
}

package org.kenneh.util;

import org.kenneh.core.context.Accessor;
import org.kenneh.core.context.Context;
import org.powerbot.script.rt6.ClientContext;

public final class SkillData extends Accessor<ClientContext> {

    public static final int NUM_SKILL = 26;
    public final int[] initialExp = new int[NUM_SKILL];
    public final int[] initialLevels = new int[NUM_SKILL];
    private Timer timer;

    public static enum Rate {
        MINUTE(60000d),
        HOUR(3600000d),
        DAY(86400000d),
        WEEK(604800000d);

        public final double time;

        Rate(double time) {
            this.time = time;
        }

        /**
         * Gets the time for this rate.
         *
         * @return this rate's time
         */
        public double getTime() {
            return time;
        }

    }

    public SkillData(Context ctx) {
        this(ctx, new Timer(0l));
    }

    public SkillData(Context ctx, Timer timer) {
        super(ctx);
        for (int index = 0; index < NUM_SKILL; index++) {
            initialExp[index] = ctx.skills.experience(index);
            initialLevels[index] = ctx.skills.realLevel(index);
        }
        this.timer = timer == null ? new Timer(0l) : timer;
    }

    /**
     * Calculates the experience gained for the given skill index.
     *
     * @param index the skill index
     * @return the experience gained
     */
    public int experience(int index) {
        if (index < 0 || index > NUM_SKILL) {
            throw new IllegalArgumentException("0 > index < " + NUM_SKILL);
        }
        return ctx.skills.experience(index) - initialExp[index];
    }

    /**
     * Calculates the experience gained for the given skill index at the given rate.
     *
     * @param rate  the rate in which to calculate the experience gained
     * @param index the skill index
     * @return the experience gained at the given rate
     */
    public int experience(Rate rate, int index) {
        return (int) (experience(index) * rate.time / timer.getElapsed());
    }

    /**
     * Calculates the number of levels gained for the given skill index.
     *
     * @param index the skill index
     * @return the number of levels gained
     */
    public int level(int index) {
        if (index < 0 || index > NUM_SKILL) {
            throw new IllegalArgumentException("0 > index < " + NUM_SKILL);
        }
        return ctx.skills.realLevel(index) - initialLevels[index];
    }

    /**
     * Calculates the number of levels gained for the given skill index at the given rate.
     *
     * @param rate  the rate in which to calculate the number of levels gained
     * @param index the skill index
     * @return the number of levels gained at the given rate
     */
    public int level(Rate rate, int index) {
        return (int) (level(index) * rate.time / timer.getElapsed());
    }

    /**
     * Calculates the time to level up at the given rate for the given skill index.
     *
     * @param rate  the rate in which to calculate the time to level up
     * @param index the skill index
     * @return the estimated time to level up at the given rate
     */
    public long timeToLevel(Rate rate, int index) {
        double exp = experience(rate, index);
        if (exp == 0d) {
            return 0l;
        }
        return (long) (expToNextLevel(index) / exp * rate.time);
    }

    /**
     * Gets the experience remaining until reaching the next level in a given
     * skill.
     *
     * @param index The index of the skill.
     * @return The experience to the next level of the skill.
     */
    public int expToNextLevel(final int index) {
        final int lvl = ctx.skills.realLevel(index);
        if (lvl == 99) {
            return 0;
        }
        return XP_TABLE[lvl + 1] - ctx.skills.experience(index);
    }

    /**
     * A table containing the experiences that begin each level.
     */
    public static final int[] XP_TABLE = {0, 0, 83, 174, 276, 388, 512, 650,
            801, 969, 1154, 1358, 1584, 1833, 2107, 2411, 2746, 3115, 3523,
            3973, 4470, 5018, 5624, 6291, 7028, 7842, 8740, 9730, 10824, 12031,
            13363, 14833, 16456, 18247, 20224, 22406, 24815, 27473, 30408,
            33648, 37224, 41171, 45529, 50339, 55649, 61512, 67983, 75127,
            83014, 91721, 101333, 111945, 123660, 136594, 150872, 166636,
            184040, 203254, 224466, 247886, 273742, 302288, 333804, 368599,
            407015, 449428, 496254, 547953, 605032, 668051, 737627, 814445,
            899257, 992895, 1096278, 1210421, 1336443, 1475581, 1629200,
            1798808, 1986068, 2192818, 2421087, 2673114, 2951373, 3258594,
            3597792, 3972294, 4385776, 4842295, 5346332, 5902831, 6517253,
            7195629, 7944614, 8771558, 9684577, 10692629, 11805606, 13034431,
            14391160, 15889109, 17542976, 19368992, 21385073, 23611006,
            26068632, 28782069, 31777943, 35085654, 38737661, 42769801,
            47221641, 52136869, 57563718, 63555443, 70170840, 77474828,
            85539082, 94442737, 104273167};

    public static final String[] SKILL_NAMES = {"attack", "defence",
            "strength", "constitution", "range", "prayer", "magic", "cooking",
            "woodcutting", "fletching", "fishing", "firemaking", "crafting",
            "smithing", "mining", "herblore", "agility", "thieving", "slayer",
            "farming", "runecrafting", "hunter", "construction", "summoning",
            "dungeoneering", "divination"};

}
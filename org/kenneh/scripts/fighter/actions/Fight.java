package org.kenneh.scripts.fighter.actions;

import org.kenneh.core.Action;
import org.kenneh.core.context.Context;
import org.kenneh.scripts.fighter.AutoFighter;
import org.powerbot.script.Filter;
import org.powerbot.script.rt6.Npc;

import java.util.Arrays;
import java.util.concurrent.Callable;

/**
 * Created by Kenneth on 7/20/2014.
 */
public class Fight extends Action {

    private final AutoFighter fighter;

    public Fight(Context context) {
        super(context);
        fighter = (AutoFighter) ctx.controller.script();
    }

    @Override
    public boolean activate() {
        return fighter.getMonsterIds() != null
                && ctx.utilities.getHealthPercent() > 40
                && (!ctx.players.local().inCombat() || !ctx.players.local().interacting().valid())
                && getTarget().valid();
    }

    @Override
    public void execute() {
        final Npc target = getTarget();
        if(target.valid()) {
            ctx.interaction.object(target, "Attack", new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return ctx.players.local().interacting().valid();
                }
            });
        }
    }

    public Npc getTarget() {
        return !ctx.npcs.select().select(aggroFilter).isEmpty()
                ? ctx.npcs.nearest().poll() : ctx.npcs.select().id(fighter.getMonsterIds()).select(fightFilter).nearest().poll();
    }

    private final Filter<Npc> aggroFilter = new Filter<Npc>() {
        @Override
        public boolean accept(Npc npc) {
            return npc.interacting().valid()
                    && npc.interacting().equals(ctx.players.local())
                    && Arrays.asList(npc.actions()).contains("Attack");
        }
    };

    private final Filter<Npc> fightFilter = new Filter<Npc>() {
        @Override
        public boolean accept(Npc npc) {
            return (!npc.interacting().valid() || !npc.inCombat())
                    && npc.tile().matrix(ctx).reachable()
                    && npc.animation() == -1
                    && npc.healthPercent() > 5;
        }
    };

}

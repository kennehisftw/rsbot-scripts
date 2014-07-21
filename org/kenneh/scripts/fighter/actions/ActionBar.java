package org.kenneh.scripts.fighter.actions;

import org.kenneh.core.Action;
import org.kenneh.core.context.Context;
import org.powerbot.script.Filter;

/**
 * Created by Kenneth on 7/21/2014.
 */
public class ActionBar extends Action {

    public ActionBar(Context context) {
        super(context);
    }

    @Override
    public boolean activate() {
        return ctx.players.local().inCombat() && ctx.combatBar.adrenaline() >= 50 && !ctx.combatBar.select().id(150, 118).select(actionFilter).isEmpty();
    }

    @Override
    public void execute() {
        final org.powerbot.script.rt6.Action action = ctx.combatBar.poll();
        if(action.valid()) {
            ctx.status.set("Queueing ability - " + action.id());
            action.select(true);
        }
    }

    private final Filter<org.powerbot.script.rt6.Action> actionFilter = new Filter<org.powerbot.script.rt6.Action>() {
        @Override
        public boolean accept(org.powerbot.script.rt6.Action action) {
            return action.ready();
        }
    };
}

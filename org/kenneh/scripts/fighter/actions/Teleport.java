package org.kenneh.scripts.fighter.actions;

import org.kenneh.core.Action;
import org.kenneh.core.context.Context;
import org.powerbot.script.rt6.Item;

import java.util.concurrent.Callable;

/**
 * Created by Kenneth on 7/21/2014.
 */
public class Teleport extends Action {
    public Teleport(Context context) {
        super(context);
    }

    @Override
    public boolean activate() {
        return ctx.utilities.getHealthPercent() < 20 && ctx.utilities.getTeleport().valid();
    }

    @Override
    public void execute() {
        final Item teleport = ctx.utilities.getTeleport();
        ctx.interaction.item(teleport, "Break", new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !teleport.valid();
            }
        });
    }

    @Override
    public int priority() {
        return 4;
    }
}

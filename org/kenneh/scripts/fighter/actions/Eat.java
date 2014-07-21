package org.kenneh.scripts.fighter.actions;

import org.kenneh.core.Action;
import org.kenneh.core.context.Context;
import org.kenneh.util.Utilities;
import org.powerbot.script.rt6.Item;

import java.util.concurrent.Callable;

/**
 * Created by Kenneth on 7/21/2014.
 */
public class Eat extends Action {

    public Eat(Context context) {
        super(context);
    }

    @Override
    public boolean activate() {
        return ctx.utilities.getHealthPercent() <= 40
                && !ctx.utilities.getFood().isEmpty();
    }

    @Override
    public void execute() {
        final Item food = ctx.utilities.getFood().poll();
        ctx.interaction.item(food, "Eat", new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return !food.valid();
            }
        });
    }

    @Override
    public int priority() {
        return 2;
    }
}

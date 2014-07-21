package org.kenneh.scripts.fighter.actions;

import org.kenneh.core.Action;
import org.kenneh.core.context.Context;
import org.kenneh.scripts.fighter.AutoFighter;
import org.kenneh.util.Utilities;
import org.powerbot.script.Filter;
import org.powerbot.script.rt6.GroundItem;
import org.powerbot.script.rt6.Item;

import java.util.concurrent.Callable;


/**
 * Created by Kenneth on 7/21/2014.
 */
public class Loot extends Action {

    private AutoFighter fighter;

    public Loot(Context context) {
        super(context);
        fighter = (AutoFighter) context.controller.script();
    }

    @Override
    public boolean activate() {
        return fighter.getLootNames() != null
                && !ctx.groundItems.select().name(fighter.getLootNames()).select(lootFilter).within(15).isEmpty();
    }

    @Override
    public void execute() {
        final GroundItem loot = ctx.groundItems.nearest().poll();
        if(loot.valid()) {
            if(ctx.backpack.select().count() == 28) {
                ctx.status.set("Eating food for loot room");
                final Item food = ctx.utilities.getFood();
                if(food.valid()) {
                    ctx.interaction.item(food, "Eat", new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return !food.valid();
                        }
                    });
                }
            } else {
                ctx.interaction.object(loot, "Take", new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return !loot.valid();
                    }
                });
            }
        }
    }

    private final Filter<GroundItem> lootFilter = new Filter<GroundItem>() {
        @Override
        public boolean accept(GroundItem groundItem) {
            return groundItem.tile().matrix(ctx).reachable();
        }
    };

    @Override
    public int priority() {
        return 1;
    }
}

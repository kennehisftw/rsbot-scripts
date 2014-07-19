package org.kenneh.scripts.winegrabber.actions;

import org.kenneh.core.Action;
import org.kenneh.core.context.Context;
import org.kenneh.scripts.winegrabber.Constants;
import org.powerbot.script.Condition;

import java.util.concurrent.Callable;

public class CloseBank extends Action {

    public CloseBank(Context context) {
        super(context);
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().count() != 28
                && ctx.bank.opened()
                && ctx.backpack.select().id(Constants.WINE_ID).isEmpty()
                && !ctx.backpack.select().id(Constants.LAW_RUNE_ID).isEmpty();
    }

    @Override
    public void execute() {
        ctx.status.set("Closing bank");
        if(ctx.bank.close()) {
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return !ctx.bank.opened();
                }
            }, 200, 5);
        }
    }

    @Override
    public int priority() {
        return 1;
    }

}

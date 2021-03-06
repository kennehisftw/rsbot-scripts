package org.kenneh.scripts.winegrabber.actions;

import org.kenneh.core.Action;
import org.kenneh.core.context.Context;
import org.kenneh.scripts.winegrabber.Constants;
import org.powerbot.script.Condition;
import org.powerbot.script.rt6.Bank;

import java.util.concurrent.Callable;

public class BankWines extends Action {

    public BankWines(Context context) {
        super(context);
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().size() == 28 && Constants.BANK_TILE.matrix(ctx).inViewport();
    }

    @Override
    public void execute() {
        if(!ctx.bank.opened()) {
            if(ctx.bank.open()) {
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return ctx.bank.opened();
                    }
                });
            }
        } else {
            if (!ctx.backpack.select().id(Constants.WINE_ID).isEmpty()) {
                if(ctx.bank.deposit(Constants.WINE_ID,Bank.Amount.ALL)) {
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return ctx.backpack.select().id(Constants.WINE_ID).isEmpty();
                        }
                    }, 200, 5);
                }
            }
        }
    }

    @Override
    public int priority() {
        return 1;
    }
}

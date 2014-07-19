package org.kenneh.scripts.winegrabber.actions;

import org.kenneh.core.Action;
import org.kenneh.core.context.Context;
import org.kenneh.scripts.winegrabber.Constants;
import org.kenneh.scripts.winegrabber.WineGrabber;
import org.powerbot.script.Condition;
import org.powerbot.script.rt6.Component;
import org.powerbot.script.rt6.GameObject;

import java.util.concurrent.Callable;

public class BankWines extends Action {

    public BankWines(Context context) {
        super(context);
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().size() == 28 && Constants.BANK_TILE.distanceTo(ctx.players.local()) < 10;
    }

    @Override
    public void execute() {
        if(!ctx.bank.opened()) {
            final GameObject banker = ctx.objects.select().name("Bank booth").nearest().poll();
            if (banker.valid()) {
                ctx.interaction.object(banker, "Bank", new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return ctx.bank.opened();
                    }
                });
            }
        } else {
            if (!ctx.backpack.select().id(Constants.WINE_ID).isEmpty()) {
                Component comp = ((WineGrabber) ctx.controller.script()).getInventoryWidget(Constants.WINE_ID);
                if(comp != null) {
                    if(comp.interact("Deposit-All")) {
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
    }

    @Override
    public int priority() {
        return 1;
    }
}

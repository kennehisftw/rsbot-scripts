package org.kristie.scripts.winegrabber.actions;

import org.kristie.core.Action;
import org.kristie.core.context.Context;
import org.kristie.scripts.winegrabber.Constants;
import org.powerbot.script.rt6.Item;
import org.powerbot.script.rt6.Npc;

public class BankWines extends Action {

    public BankWines(Context context) {
        super(context);
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().size() != 28 && Constants.BANK_TILE.distanceTo(ctx.players.local()) < 10;
    }

    @Override
    public void execute() {
        if(!ctx.bank.opened()) {
            final Npc banker = ctx.npcs.select().name("Banker").nearest().poll();
            if (banker.valid()) {
                ctx.interaction.object(banker, "Bank");
            }
        } else {
            if (!ctx.backpack.select().id(Constants.WINE_ID).isEmpty()) {
                final Item wine = ctx.backpack.poll();
                if(wine.valid()) {
                    ctx.interaction.item(wine, "Deposit-All").waitFor(!wine.valid());
                }
            }
        }
    }
}

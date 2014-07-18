package org.kristie.scripts.winegrabber.actions;

import org.kristie.core.Action;
import org.kristie.core.context.Context;
import org.kristie.methods.Lodestone;
import org.kristie.scripts.winegrabber.Constants;

public class Teleport extends Action {

    public Teleport(Context context) {
        super(context);
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().count() == 28
                && Constants.BANK_TILE.distanceTo(ctx.players.local()) > 50;
    }

    @Override
    public void execute() {
        ctx.status.set("Teleporting to Falador");
        ctx.lodestone.teleport(Lodestone.Location.FALADOR);
    }

}

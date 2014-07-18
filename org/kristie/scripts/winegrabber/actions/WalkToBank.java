package org.kristie.scripts.winegrabber.actions;

import org.kristie.core.Action;
import org.kristie.core.context.Context;
import org.kristie.scripts.winegrabber.Constants;
import org.powerbot.script.rt6.LocalPath;

public class WalkToBank extends Action {

    public WalkToBank(Context context) {
        super(context);
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().size() == 28
                && !ctx.bank.opened()
                && Constants.BANK_TILE.distanceTo(ctx.players.local()) < 50;
    }

    @Override
    public void execute() {
        final LocalPath path = ctx.movement.findPath(Constants.BANK_TILE);
        if (path.valid()) {
            ctx.status.set("Walking towards bank");
            path.traverse();
        }
    }

}

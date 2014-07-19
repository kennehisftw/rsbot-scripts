package org.kenneh.scripts.winegrabber.actions;

import org.kenneh.core.Action;
import org.kenneh.core.context.Context;
import org.kenneh.scripts.winegrabber.Constants;

public class WalkToExactSpot extends Action {

    public WalkToExactSpot(Context context) {
        super(context);
    }

    @Override
    public boolean activate() {
        return Constants.WINE_STANDING_TILE.distanceTo(ctx.players.local()) < 10
                && Constants.WINE_STANDING_TILE.distanceTo(ctx.players.local()) > 0
                && ctx.backpack.select().size() != 28
                && !ctx.backpack.id(Constants.LAW_RUNE_ID).isEmpty();
    }

    @Override
    public void execute() {
        ctx.status.set("Walking to exact spot");
        if(!Constants.WINE_STANDING_TILE.matrix(ctx).inViewport()) {
            ctx.camera.turnTo(Constants.WINE_STANDING_TILE);
        } else {
            Constants.WINE_STANDING_TILE.matrix(ctx).interact("Walk");
        }
    }

}

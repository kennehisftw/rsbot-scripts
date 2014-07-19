package org.kenneh.scripts.winegrabber.actions;

import org.kenneh.core.Action;
import org.kenneh.core.context.Context;
import org.kenneh.scripts.winegrabber.Constants;
import org.powerbot.script.rt6.TilePath;

public class WalkToTemple extends Action {

    public WalkToTemple(Context context) {
        super(context);
    }

    @Override
    public boolean activate() {
        return Constants.WINE_STANDING_TILE.distanceTo(ctx.players.local()) > 10
                && ctx.backpack.select().size() != 28
                && !ctx.backpack.id(Constants.LAW_RUNE_ID).isEmpty();
    }

    @Override
    public void execute() {
        ctx.status.set("Walking back to alter");
        templePath.traverse();
    }

    public TilePath templePath = ctx.movement.newTilePath(Constants.TILE_ARRAY);

}

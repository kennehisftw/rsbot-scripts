package org.kristie.scripts.winegrabber.actions;

import org.kristie.core.Action;
import org.kristie.core.context.Context;
import org.kristie.scripts.winegrabber.Constants;
import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.GroundItem;

public class TakeWine extends Action {

    public TakeWine(Context context) {
        super(context);
    }

    @Override
    public boolean activate() {
        return ctx.backpack.select().count() != 28
                && !ctx.backpack.select().id(Constants.LAW_RUNE_ID).isEmpty()
                && Constants.WINE_STANDING_TILE.distanceTo(ctx.players.local()) == 0;
    }

    @Override
    public void execute() {

        if(!ctx.client().isSpellSelected()) {
            ctx.status.set("Selecting ability");
            ctx.combatBar.actionAt(0).select();
        }

        final GroundItem wine = ctx.groundItems.select().id(Constants.WINE_ID).nearest().poll();
        if(!wine.valid()) {

            final Tile wineLocation = Constants.WINE_STANDING_TILE.derive(0, -1);
            if(!wineLocation.matrix(ctx).bounds().contains(ctx.input.getLocation())) {
                ctx.input.move(wineLocation.matrix(ctx).centerPoint());
            }

            if(ctx.camera.yaw() >= 190 || ctx.camera.yaw() <= 170) {
                ctx.status.set("Adjusting camera angle");
                ctx.camera.angle(180);
            }

            if(ctx.camera.pitch() >= 90 || ctx.camera.pitch() <= 70) {
                ctx.status.set("Adjusting camera pitch");
                ctx.camera.pitch(80);
            }

        } else {

            wine.bounds(Constants.WINE_MODEL_BOUNDS);
            ctx.input.hop(wine.centerPoint());
            ctx.status.set("Casting spell on wine");
            if(ctx.menu.click(command -> command.option.contains("Grab -> Wine"))) {
                Condition.sleep(500);
            }

        }
    }
}

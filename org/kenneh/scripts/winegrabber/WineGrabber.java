package org.kenneh.scripts.winegrabber;

import org.kenneh.core.ActionScript;
import org.kenneh.core.context.Context;
import org.kenneh.scripts.winegrabber.actions.*;
import org.kenneh.util.Calculations;
import org.powerbot.script.MessageEvent;
import org.powerbot.script.MessageListener;
import org.powerbot.script.PaintListener;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.GeItem;

import java.awt.*;


@Script.Manifest(name = "Wine Grabber", description = "Steals Wine of Zamorak from the temple north of Falador")
public class WineGrabber extends ActionScript<Context> implements PaintListener, MessageListener {

    private int lawStart = 0;
    private int missed = 0;
    private int winePrice = 0;

    public void start() {

        log.info("Telegrab ID: "+ ctx.combatBar.actionAt(0).id());

        winePrice = GeItem.price(Constants.WINE_ID);
        lawStart = getInventoryCount(Constants.LAW_RUNE_ID);

        add(new BankWines(ctx), new CloseBank(ctx), new TakeWine(ctx), new Teleport(ctx),
                new WalkToBank(ctx), new WalkToExactSpot(ctx), new WalkToTemple(ctx));
    }

    public int getUsedLaws() {
        return lawStart - getInventoryCount(Constants.LAW_RUNE_ID);
    }

    public int getWinesGrabbed() {
        return getUsedLaws() - missed;
    }

    @Override
    public void repaint(Graphics g) {
        g.drawString("Runtime: "+ Calculations.formatTime(getRuntime()), 10, 203);
        g.drawString("Status: "+ ctx.status.get(), 10, 215);
        g.drawString("Law count: "+ getInventoryCount(Constants.LAW_RUNE_ID), 10, 227);
        g.drawString("Wine count: "+ getInventoryCount(Constants.WINE_ID), 10, 239);
        g.drawString("Wines grabbed: "+ getWinesGrabbed(), 10, 251);
        g.drawString("Wines missed: "+ missed, 10, 263);

        final int profit = getWinesGrabbed() * winePrice;
        g.drawString("Profit: "+ Calculations.perHour(profit) + "/hr (+" + Calculations.formatNumber(profit) + ")", 10, 387);
    }

    public int getInventoryCount(int itemId) {
        return ctx.backpack.select().id(itemId).isEmpty() ? 0 : ctx.backpack.count(true);
    }

    @Override
    public void messaged(MessageEvent messageEvent) {
        if(messageEvent.getMessage().contains("Too late")) {
            missed++;
        }
    }
}

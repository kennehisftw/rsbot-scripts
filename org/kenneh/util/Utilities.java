package org.kenneh.util;

import org.kenneh.core.context.Accessor;
import org.kenneh.core.context.Context;
import org.powerbot.script.Filter;
import org.powerbot.script.rt6.Item;

import java.util.Arrays;

/**
 * Created by Kenneth on 7/21/2014.
 */
public class Utilities extends Accessor<Context> {

    public Utilities(Context context) {
        super(context);
    }

    public Item getFood() {
        return ctx.backpack.select().name("Shrimp", "Trout", "Salmon", "Tuna", "Lobster", "Swordfish", "Monkfish", "Shark", "Cavefish", "Rocktail").poll();
    }

    public Item getTeleport() {
        return ctx.backpack.select().select(new Filter<Item>() {
            @Override
            public boolean accept(Item item) {
                return item.name().toLowerCase().contains("teleport");
            }
        }).poll();
    }

    public int getHealthPercent() { // 1430, 4, 7
        final String health = ctx.widgets.component(1430, 4).component(7).text();
        final int curr = Integer.parseInt(health.split("/")[0]);
        final int max = Integer.parseInt(health.split("/")[1]);
        return (curr * 100) / max;
    }

    public int getSlayerAmountRemaining() {
        return ctx.varpbits.varpbit(183);
    }

    public int getSlayerAmountKilled() {
        return ctx.varpbits.varpbit(189);
    }

    public int getSlayerTaskAmount() {
        return getSlayerAmountRemaining() + getSlayerAmountKilled();
    }

    public int getSlayerTaskCompletionPercent() {
        return (getSlayerAmountKilled() * 100) / getSlayerTaskAmount();
    }

}

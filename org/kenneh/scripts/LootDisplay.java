package org.kenneh.scripts;

import org.powerbot.script.*;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GeItem;
import org.powerbot.script.rt6.GroundItem;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

@Script.Manifest(
        name = "Loot Display",
        description = "Displays the values of the items on the ground while alt is pressed"
)
public class LootDisplay extends PollingScript<ClientContext> implements PaintListener, KeyListener {


    private final Font calibri = new Font("Calibri", Font.PLAIN, 13);
    private final Map<Integer, Integer> priceMap = new HashMap<>();
    private final DecimalFormat format = new DecimalFormat("###,###,###");

    private final Filter<GroundItem> priceFilter = new Filter<GroundItem>() {
        @Override
        public boolean accept(GroundItem groundItem) {
            return !priceMap.containsKey(groundItem.id());
        }
    };

    private final Filter<GroundItem> displayFilter = new Filter<GroundItem>() {
        @Override
        public boolean accept(GroundItem groundItem) {
            return priceMap.containsKey(groundItem.id()) && groundItem.inViewport();
        }
    };

    private boolean show = false;

    @Override
    public void start() {
        priceMap.put(995, 1);
    }

    @Override
    public void repaint(Graphics graphics) {
        if(show) {
            final FontMetrics fm = graphics.getFontMetrics();
            graphics.setFont(calibri);
            for (GroundItem groundItem : ctx.groundItems.select().select(displayFilter)) {
                graphics.setColor(Color.BLACK);
                final Point center = groundItem.centerPoint();
                final String displayString = groundItem.name() + " x" + groundItem.stackSize() + " - $" + format.format((priceMap.get(groundItem.id()) * groundItem.stackSize()));
                final int width = fm.stringWidth(displayString);
                final int height = fm.getHeight();
                graphics.fillRoundRect(center.x - (width / 2), center.y + (height / 2), width + 4, height + 5, 10, 10);
                graphics.setColor(Color.WHITE);
                graphics.drawString(displayString, center.x - (width/ 2) + 2, center.y + (height / 2) + height);
            }
        }
    }

    @Override
    public void poll() {
        for(GroundItem groundItem : ctx.groundItems.select().select(priceFilter).within(20)) {
            if(groundItem.valid()) {
                final int price = GeItem.price(groundItem.id());
                priceMap.put(groundItem.id(), price);
            }
        }
        Condition.sleep(600);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ALT) {
            show = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ALT) {
            show = false;
        }
    }
}

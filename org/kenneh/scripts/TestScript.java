package org.kenneh.scripts;

import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;

import java.awt.*;

/**
 * Created by Kenneth on 7/18/2014.
 */
@Script.Manifest(
        name = "Test Script",
        description = "Used for debugging various things"
)
public class TestScript extends PollingScript<ClientContext> implements PaintListener {

    @Override
    public void poll() {

    }

    @Override
    public void start() {

    }

    @Override
    public void repaint(Graphics graphics) {
        for(GameObject obj : ctx.objects.select().within(5)) {
            obj.draw(graphics);
        }
    }
}

package org.kenneh.scripts;

import org.kenneh.core.Action;
import org.kenneh.core.ActionScript;
import org.kenneh.core.context.Context;
import org.powerbot.script.PaintListener;
import org.powerbot.script.Script;

import java.awt.*;

/**
 * Created by Kenneth on 7/18/2014.
 */
@Script.Manifest(
        name = "Test Script",
        description = "Used for debugging various things"
)
public class TestScript extends ActionScript<Context> implements PaintListener {


    @Override
    public void start() {
        add(new Action(ctx) {
            @Override
            public boolean activate() {
                return true;
            }

            @Override
            public void execute() {
                log.info("Items: " + ctx.backpack.select().count(true));
            }
        });
    }

    @Override
    public void repaint(Graphics graphics) {

    }
}

package org.kenneh.methods;

import org.kenneh.core.context.Accessor;
import org.kenneh.core.context.Context;
import org.powerbot.script.Condition;
import org.powerbot.script.Locatable;
import org.powerbot.script.Nameable;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.Hud;
import org.powerbot.script.rt6.Interactive;
import org.powerbot.script.rt6.LocalPath;

import java.util.concurrent.Callable;

public class Interaction extends Accessor<Context> {

    public Interaction(Context ctx) {
        super(ctx);
    }

    public <E extends Interactive & Locatable & Nameable> void object(E obj, String action, Callable<Boolean> condition) {
        if(!obj.inViewport()) {
            final LocalPath path = ctx.movement.findPath(obj);
            ctx.status.set("Turning camera towards " + obj.name());
            ctx.camera.turnTo(obj);
            if(path.valid()) {
                ctx.status.set("Walking towards " + obj.name());
                if(path.traverse()) {
                    Condition.wait(new Callable<Boolean>() {
                        @Override
                        public Boolean call() throws Exception {
                            return !ctx.players.local().inMotion();
                        }
                    }, 200, 5);
                }
            }
        } else {
            ctx.status.set("Attempting to " + action + " " + obj.name());
            if(obj.interact(action, obj.name())) {
                Condition.wait(condition, 200, 10);
            } else {
                ctx.status.set("Unable to interact, turning camera randomly");
                ctx.camera.angle(Random.nextInt(90, 180));
            }
        }
    }

    public <E extends Interactive & Nameable> Interaction item(E obj, String action, Callable<Boolean> condition) {
        ctx.status.set("Attempting to " + action + " " + obj.name());
        if(obj.interact(action, obj.name())) {
            Condition.wait(condition, 200, 5);
        } else {
            ctx.status.set("Unable to interact, attempting to open backpack");
            ctx.hud.open(Hud.Window.BACKPACK);
        }
        return this;
    }

}

package org.kristie.methods;

import org.kristie.core.context.Accessor;
import org.kristie.core.context.Context;
import org.powerbot.script.Condition;
import org.powerbot.script.Locatable;
import org.powerbot.script.Nameable;
import org.powerbot.script.Random;
import org.powerbot.script.rt6.Hud;
import org.powerbot.script.rt6.Interactive;
import org.powerbot.script.rt6.LocalPath;

import java.util.concurrent.Callable;

public class Interaction extends Accessor {

    public Interaction(Context ctx) {
        super(ctx);
    }

    public <E extends Interactive & Locatable & Nameable> Interaction object(E obj, String action) {
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
                    }, 200, 10);
                }
            }
        } else {
            ctx.status.set("Attempting to " + action + " " + obj.name());
            if(obj.interact(action, obj.name())) {
                Condition.wait(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return getCondition();
                    }
                }, 200, 10);
            } else {
                ctx.status.set("Unable to interact, turning camera randomly");
                ctx.camera.angle(Random.nextInt(90, 180));
            }
        }
        return this;
    }

    public <E extends Interactive & Nameable> Interaction item(E obj, String action) {
        ctx.status.set("Attempting to " + action + " " + obj.name());
        if(obj.interact(action, obj.name())) {
            Condition.wait(new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return getCondition();
                }
            }, 200, 10);
        } else {
            ctx.status.set("Unable to interact, attempting to open backpack");
            ctx.hud.open(Hud.Window.BACKPACK);
        }
        return this;
    }

    private boolean getCondition() {
        return condition;
    }

    private boolean condition = false;

    public void waitFor(boolean condition) {
        this.condition = condition;
    }
}

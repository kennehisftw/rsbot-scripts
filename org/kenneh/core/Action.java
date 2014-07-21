package org.kenneh.core;

import org.kenneh.core.context.Accessor;
import org.kenneh.core.context.Context;

public abstract class Action extends Accessor<Context> {

    public Action(Context context) {
        super(context);
    }

    public int priority() {
        return 0;
    }

    public abstract boolean activate();
    public abstract void execute();
}
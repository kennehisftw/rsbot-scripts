package org.kristie.core;

import org.kristie.core.context.Accessor;
import org.kristie.core.context.Context;

public abstract class Action extends Accessor {

    public Action(Context context) {
        super(context);
    }

    public int priority() {
        return 0;
    }

    public abstract boolean activate();
    public abstract void execute();
}

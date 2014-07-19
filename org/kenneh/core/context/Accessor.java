package org.kenneh.core.context;

import org.powerbot.script.ClientAccessor;
import org.powerbot.script.ClientContext;

public abstract class Accessor<C extends ClientContext> extends ClientAccessor<C> {

    public Accessor(C c) {
        super(c);
    }
}

package org.kristie.core.context;

import org.powerbot.script.ClientAccessor;

public abstract class Accessor extends ClientAccessor<Context> {

    public Accessor(Context context) {
        super(context);
    }
}

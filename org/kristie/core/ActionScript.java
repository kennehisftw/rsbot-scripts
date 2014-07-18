package org.kristie.core;

import org.kristie.core.context.Context;
import org.powerbot.script.PollingScript;

import java.util.*;

public abstract class ActionScript<S extends ActionScript, C extends Context> extends PollingScript<C> implements Comparator<Action> {

    private final List<Action> actionList;

    public ActionScript() {
        actionList = new LinkedList<>();
    }

    public S script() {
        return(S) this;
    }

    public void add(Action... actions) {
        Collections.addAll(actionList, actions);
        Collections.sort(actionList, this);
    }

    @Override
    public void poll() {
        for(Action action : actionList) {
            if(action.activate())
                action.execute();
        }
    }

    @Override
    public int compare(Action o1, Action o2) {
        return o2.priority() - o1.priority();
    }

    public abstract void start();
}
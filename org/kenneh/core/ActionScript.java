package org.kenneh.core;

import org.powerbot.script.ClientContext;
import org.powerbot.script.PollingScript;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public abstract class ActionScript<C extends ClientContext> extends PollingScript<C> implements Comparator<Action> {

    private final List<Action> actionList;

    public ActionScript() {
        actionList = new LinkedList<>();
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
package org.kristie.core.context;

import org.kristie.methods.Interaction;
import org.kristie.methods.Lodestone;
import org.kristie.methods.Status;
import org.kristie.util.SkillData;
import org.powerbot.script.rt6.ClientContext;

public class Context extends ClientContext {

    public Lodestone lodestone;
    public SkillData skillTracker;
    public Interaction interaction;
    public Status status;

    public Context(ClientContext context) {
        super(context);
        this.status = new Status();
        this.interaction = new Interaction(this);
        this.skillTracker = new SkillData(this);
        this.lodestone = new Lodestone(this);
    }
}

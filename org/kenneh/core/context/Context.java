package org.kenneh.core.context;

import org.kenneh.methods.Interaction;
import org.kenneh.methods.Lodestone;
import org.kenneh.methods.Status;
import org.kenneh.util.SkillData;
import org.kenneh.util.Utilities;
import org.powerbot.script.rt6.ClientContext;

public class Context extends ClientContext {

    public Lodestone lodestone;
    public SkillData skillTracker;
    public Interaction interaction;
    public Status status;
    public Utilities utilities;

    public Context(ClientContext ctx) {
        super(ctx);
        this.status = new Status();
        this.interaction = new Interaction(this);
        this.skillTracker = new SkillData(this);
        this.lodestone = new Lodestone(this);
        this.utilities = new Utilities(this);
    }

}

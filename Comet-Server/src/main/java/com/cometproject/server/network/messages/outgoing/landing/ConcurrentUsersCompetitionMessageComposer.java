package com.cometproject.server.network.messages.outgoing.landing;

import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;

public class ConcurrentUsersCompetitionMessageComposer extends MessageComposer {

    private final int phase;
    private final int current;
    private final int goal;

    public ConcurrentUsersCompetitionMessageComposer(int phase, int current, int goal) {
        this.phase = phase;
        this.current = current;
        this.goal = goal;
    }

    @Override
    public short getId() {
        return Composers.HotelViewConcurrentUsersMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(this.phase);
        msg.writeInt(this.current);
        msg.writeInt(this.goal);
    }
}

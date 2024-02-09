package com.cometproject.server.network.messages.incoming.gamecenter.snowwar;

/*
 * ****************
 * @author capos *
 * ****************
 */

import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;

public class ThrowSnowballAtHumanParser implements Event {
    @Override
    public void handle(Session client, MessageEvent msg) throws Exception {
        int playerId = msg.readInt();
        int type = msg.readInt();
        client.snowWarPlayerData.throwSnowballAtHuman(playerId, type);
    }
}

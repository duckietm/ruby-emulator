package com.cometproject.server.network.messages.incoming.navigator;

import com.cometproject.server.boot.Comet;
import com.cometproject.server.game.rooms.RoomManager;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.messages.incoming.Event;
import com.cometproject.server.network.messages.outgoing.navigator.NavigatorFlatListMessageComposer;
import com.cometproject.server.network.messages.outgoing.notification.AlertMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.messages.MessageEvent;
import com.cometproject.server.storage.queries.crafting.CraftingDao;


public class SearchRoomMessageEvent implements Event {
    public void handle(Session client, MessageEvent msg) {
        String query = msg.readString();
        client.send(new NavigatorFlatListMessageComposer(8, "", RoomManager.getInstance().getRoomsByQuery(query)));
    }
}

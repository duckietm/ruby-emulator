package com.cometproject.api.networking.sessions;

import com.cometproject.api.networking.messages.IMessageComposer;
import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public interface ISessionManager {
    boolean disconnectByPlayerId(int id);

    ISession getByPlayerId(int id);

    ISession fromPlayer(int id);

    ISession fromPlayer(String username);

    Set<ISession> getByPlayerPermission(String permission);

    ISession getByPlayerUsername(String username);

    int getUsersOnlineCount();

    Map<Integer, ISession> getSessions();

    ArrayList<String> getPendingConnections();

    void broadcast(IMessageComposer msg);

    void broadcastToModerators(IMessageComposer messageComposer);

    void parseCommand(String[] message, ChannelHandlerContext ctx);
}

package com.cometproject.server.network.messages.outgoing.messenger;

import com.cometproject.api.config.CometSettings;
import com.cometproject.api.game.GameContext;
import com.cometproject.api.game.groups.types.IGroup;
import com.cometproject.api.game.players.data.PlayerAvatar;
import com.cometproject.api.game.players.data.components.messenger.IMessengerFriend;
import com.cometproject.api.game.players.data.components.messenger.RelationshipLevel;
import com.cometproject.api.networking.messages.IComposer;
import com.cometproject.server.game.players.types.Player;
import com.cometproject.server.network.NetworkManager;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.protocol.headers.Composers;
import com.cometproject.server.protocol.messages.MessageComposer;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BuddyListMessageComposer extends MessageComposer {
    private final Player player;
    private final Map<Integer, IMessengerFriend> friends;
    private final List<PlayerAvatar> avatars;
    private final Set<Integer> groups;

    private final boolean hasStaffChat;
    private final boolean hasLogChat;
    private final boolean hasAlfaChat;

    public BuddyListMessageComposer(final Player player, Map<Integer, IMessengerFriend> friends, final boolean hasStaffChat, final boolean hasLogChat, final boolean hasAlfaChat, final Set<Integer> groups) {
        this.hasStaffChat = hasStaffChat;
        this.hasLogChat = hasLogChat;
        this.hasAlfaChat = hasAlfaChat;

        this.player = player;
        this.friends = Maps.newHashMap(friends);
        this.avatars = Lists.newArrayList();

        for (final Map.Entry<Integer, IMessengerFriend> friend : friends.entrySet()) {
            if (friend.getValue() != null) {
                final PlayerAvatar playerAvatar = friend.getValue().getAvatar();

                if (playerAvatar != null) {
                    avatars.add(playerAvatar);
                }
            }
        }

        this.groups = groups;
    }

    @Override
    public short getId() {
        return Composers.BuddyListMessageComposer;
    }

    @Override
    public void compose(IComposer msg) {
        msg.writeInt(1); // total pages
        msg.writeInt(0); // pageIndex

        if (CometSettings.groupChatEnabled) {
            msg.writeInt(avatars.size() + (hasStaffChat ? 1 : 0) + (hasLogChat ? 1 : 0) + (hasAlfaChat ? 1 : 0) + this.groups.size());
        } else {
            msg.writeInt(avatars.size() + (hasStaffChat ? 1 : 0) + (hasLogChat ? 1 : 0) + (hasAlfaChat ? 1 : 0));
        }

        for (final PlayerAvatar playerAvatar : avatars) {
            msg.writeInt(playerAvatar.getId());
            msg.writeString(playerAvatar.getUsername());
            msg.writeInt(playerAvatar.getGender().equalsIgnoreCase("M") ? 0 : 1); // Male.

            boolean isOnline = friends.get(playerAvatar.getId()).isOnline();
            boolean isInRoom = friends.get(playerAvatar.getId()).isInRoom();

            msg.writeBoolean(isOnline);
            msg.writeBoolean(isInRoom);

            msg.writeString(isOnline ? playerAvatar.getFigure() : "");
            msg.writeInt(0); // category id
            msg.writeString(playerAvatar.getMotto());
            msg.writeString("");
            msg.writeString("");
            msg.writeBoolean(true); // Receive offline messages
            msg.writeBoolean(false);
            msg.writeBoolean(false);

            final RelationshipLevel level = this.player.getRelationships().get(playerAvatar.getId());

            msg.writeShort(level == null ? 0 : level.getLevelId());
        }

        if (hasLogChat) {
            msg.writeInt(Integer.MAX_VALUE - 1);
            msg.writeString("Log Chat");
            msg.writeInt(77);
            msg.writeBoolean(true);
            msg.writeBoolean(false);
            msg.writeString("ch-3215-92.hr-831-45.sh-290-64.fa-1206-91.lg-270-92.ha-3129-92.hd-180-2.cc-3039-92");
            msg.writeInt(0);
            msg.writeString("");
            msg.writeString("");
            msg.writeString("");
            msg.writeBoolean(false);
            msg.writeBoolean(false);
            msg.writeBoolean(false);
            msg.writeShort(0);
        }

        if (hasAlfaChat) {
            msg.writeInt(Integer.MIN_VALUE + 5001);
            msg.writeString("Alfa Chat");
            msg.writeInt(0);
            msg.writeBoolean(true);
            msg.writeBoolean(false);
            msg.writeString("ch-3215-92.hr-831-45.sh-290-64.fa-1206-91.lg-270-92.ha-3129-92.hd-180-2.cc-3039-92");
            msg.writeInt(0);
            msg.writeString("");
            msg.writeString("");
            msg.writeString("");
            msg.writeBoolean(false);
            msg.writeBoolean(false);
            msg.writeBoolean(false);
            msg.writeShort(0);
        }

        if (hasStaffChat) {
            msg.writeInt(Integer.MAX_VALUE - 2);
            msg.writeString("Staff Chat");
            msg.writeInt(77);
            msg.writeBoolean(true);
            msg.writeBoolean(false);
            msg.writeString("ch-3215-92.hr-831-45.sh-290-64.fa-1206-91.lg-270-92.ha-3129-92.hd-180-2.cc-3039-92");
            msg.writeInt(0);
            msg.writeString("");
            msg.writeString("");
            msg.writeString("");
            msg.writeBoolean(false);
            msg.writeBoolean(false);
            msg.writeBoolean(false);
            msg.writeShort(0);
        }


        if (CometSettings.groupChatEnabled) {
            for (Integer groupId : this.groups) {
                final IGroup group = GameContext.getCurrent().getGroupService().getGroup(groupId);

                msg.writeInt(-groupId);
                msg.writeString(group == null || group.getData() == null ? "Unknown Group" : group.getData().getTitle());
                msg.writeInt(0);
                msg.writeBoolean(true);
                msg.writeBoolean(false);
                msg.writeString(group == null || group.getData() == null ? "" : group.getData().getBadge());
                msg.writeInt(1);
                msg.writeString("");
                msg.writeString("");
                msg.writeString("");
                msg.writeBoolean(false);
                msg.writeBoolean(false);
                msg.writeBoolean(false);
                msg.writeShort(0);

            }
        }
    }

    @Override
    public void dispose() {
        this.avatars.clear();
    }
}
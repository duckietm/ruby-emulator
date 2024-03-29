package com.cometproject.server.game.commands.user;

import com.cometproject.server.config.Locale;
import com.cometproject.server.game.commands.ChatCommand;
import com.cometproject.server.network.messages.outgoing.user.inventory.BotInventoryMessageComposer;
import com.cometproject.server.network.messages.outgoing.user.inventory.UpdateInventoryMessageComposer;
import com.cometproject.server.network.sessions.Session;
import com.cometproject.server.storage.queries.bots.PlayerBotDao;

public class EmptyBotsCommand extends ChatCommand {
    @Override
    public void execute(Session client, String[] params) {
        if (params.length != 1) {
            sendAlert(Locale.getOrDefault("command.emptybots.confirm", "<b>Warning!</b>\rAre you sure you want to delete all of your bots?\r\rIf you are sure type  <b>:" + Locale.get("command.emptybots.name") + " yes</b>"), client);
        } else {
            final String yes = Locale.getOrDefault("command.empty.yes", "yes");

            if (!params[0].equals(yes)) {
                sendAlert(Locale.getOrDefault("command.emptybots.confirm", "<b>Warning!</b>\rAre you sure you want to delete all of your bots?\r\rIf you are sure type  <b>:" + Locale.get("command.emptybots.name") + " yes</b>"), client);
            } else {
                PlayerBotDao.deleteBots(client.getPlayer().getId());

                client.getPlayer().getBots().clearBots();
                client.send(new BotInventoryMessageComposer());

                sendNotif(Locale.getOrDefault("command.emptybots.emptied", "Your bots inventory was cleared."), client);
            }

            client.send(new UpdateInventoryMessageComposer());
        }
    }

    @Override
    public String getPermission() {
        return "emptybots_command";
    }

    @Override
    public String getParameter() {
        return Locale.getOrDefault("command.empty.yes", "yes");
    }

    @Override
    public String getDescription() {
        return Locale.getOrDefault("command.emptybots.description", "Elimina los bots que tengas en tu inventario");
    }
}

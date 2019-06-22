package pl.xdgwg.bans.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import pl.xdgwg.bans.managers.ConfigManager;
import pl.xdgwg.bans.managers.DataManager;
import pl.xdgwg.bans.managers.UserManager;
import pl.xdgwg.bans.object.User;
import pl.xdgwg.bans.utils.Util;

public class AsyncPlayerChatListener implements Listener {

    @EventHandler
    public void asyncPlayerChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        User u = UserManager.get(p.getUniqueId());

        //Tempmute
        if (u.getMuteStatus() == 2){
            long now = Util.getSystemTime();
            long ban = u.getMuteTime();
            long number = u.getMuteNumber();
            if ((now - ban) > number){
                u.setMuteStatus(0);
                u.setMuteAdmin("-");
                u.setMuteReason("-");
                u.setMuteTime(0);
                u.setMuteNumber(0);
                DataManager.saveUser(u);
            } else {
                e.setCancelled(true);
                Util.sendMessage(p, ConfigManager.getTempMuteMessage().replace("{ADMIN}", u.getMuteAdmin()).replace("{REASON}", u.getMuteReason()).replace("{TIME}", Util.getTime(u, u.getMuteStatus(), Util.getSystemTime(), u.getMuteTime(), u.getMuteNumber())));
            }
        }

        //Mute
        if (u.getMuteStatus() == 1){
            e.setCancelled(true);
            Util.sendMessage(p, ConfigManager.getMuteMessage().replace("{ADMIN}", u.getMuteAdmin()).replace("{REASON}", u.getMuteReason()));
            return;
        }
    }
}

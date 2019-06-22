package pl.xdgwg.bans.listeners;

import pl.xdgwg.bans.managers.DataManager;
import pl.xdgwg.bans.managers.UserManager;
import pl.xdgwg.bans.object.User;
import pl.xdgwg.bans.utils.Util;
import pl.xdgwg.bans.managers.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class AsyncPlayerPreLoginListener implements Listener {

    @EventHandler
    public void asyncPlayerPreLogin(AsyncPlayerPreLoginEvent e){
        //Sprawdzanie nicku
        if (ConfigManager.getPlayerNameFilter()) {
            for (User us : UserManager.getUsers()) {
                if ((us.getName().equalsIgnoreCase(e.getName())) && (!us.getName().equals(e.getName()))) {
                    e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, Util.fixColor(ConfigManager.getBadNickname().replace("{NORMAL-NAME}", us.getName())).replace("{NAME}", e.getName()));
                    return;
                }
            }
        }

        //Tworzenie nowego usera jak trzeba
        if (UserManager.get(e.getUniqueId()) == null) {
            new User(e.getName(), e.getUniqueId().toString(), e.getAddress().toString(), false, 0, 0, 0, "-", "-", 0, 0, 0, "-", "-", 0);
        }
        User u = UserManager.get(e.getUniqueId());
        String messageBan = Util.stringList(ConfigManager.getBanJoin()).replace("{ADMIN}", u.getBanAdmin()).replace("{REASON}", u.getBanReason());
        String messageTempBan = Util.stringList(ConfigManager.getTempBanJoin()).replace("{ADMIN}", u.getBanAdmin()).replace("{REASON}", u.getBanReason()).replace("{TIME}", Util.getTime(u, u.getBanStatus(), Util.getSystemTime(), u.getBanTime(), u.getBanNumber()));

        //Tempban
        if (u.getBanStatus() == 2){
            long now = Util.getSystemTime();
            long ban = u.getBanTime();
            long number = u.getBanNumber();
            if ((now - ban) > number){
                u.setBanStatus(0);
                u.setBanAdmin("-");
                u.setBanReason("-");
                u.setBanTime(0);
                u.setBanNumber(0);
                DataManager.saveUser(u);
            } else {
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, Util.fixColor(messageTempBan));
            }
        }

        //Ban
        if (u.getBanStatus() == 1){
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, Util.fixColor(messageBan));
        }
    }
}
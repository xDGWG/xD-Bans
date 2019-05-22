package pl.xdgwg.bans.listeners;

import pl.xdgwg.bans.managers.DataManager;
import pl.xdgwg.bans.managers.UserManager;
import pl.xdgwg.bans.object.User;
import pl.xdgwg.bans.utils.Util;
import pl.xdgwg.bans.managers.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import java.util.ArrayList;

public class AsyncPlayerPreLoginListener implements Listener {

    private static ArrayList<String> names = new ArrayList<String>();
    private static ArrayList<String> normalNick = new ArrayList<String>();

    @EventHandler
    public void asyncPlayerPreLogin(AsyncPlayerPreLoginEvent e){
        //Sprawdzanie nicku
        if (ConfigManager.getPlayerNameFilter()) {
            for (User u : UserManager.getUsers()) {
                names.add(u.getName().toUpperCase());
                normalNick.add(u.getName());
            }
            if ((names.contains(e.getName().toUpperCase()) && (UserManager.get(e.getUniqueId()) == null))) {
                String normal_nick = "";
                for (int i = 0; i < names.size(); i++) {
                    if (normalNick.get(i).equalsIgnoreCase(e.getName())) {
                        normal_nick = normalNick.get(i);
                    }
                }
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, Util.fixColor(ConfigManager.getBadNickname().replace("{NORMAL-NAME}", normal_nick)).replace("{NAME}", e.getName()));
                names.clear();
                normalNick.clear();
                return;
            }
        }
        //Tworzenie nowego usera jak trzeba
        if (UserManager.get(e.getUniqueId()) == null) {
            new User(e.getName(), e.getUniqueId().toString(), e.getAddress().toString(), false, 0, 0, "-", "-", 0);
        }
        User u = UserManager.get(e.getUniqueId());
        String messageBan = Util.stringList(ConfigManager.getBanJoin()).replace("{ADMIN}", u.getAdmin()).replace("{REASON}", u.getReason());
        String messageTempBan = Util.stringList(ConfigManager.getTempBanJoin()).replace("{ADMIN}", u.getAdmin()).replace("{REASON}", u.getReason()).replace("{TIME}", Util.getBanTime(u, Util.getSystemTime(), u.getTime(), u.getNumber()));
        //Tempban
        if (u.getStatus() == 2){
            long now = Util.getSystemTime();
            long ban = u.getTime();
            long number = u.getNumber();
            if ((now - ban) > number){
                u.setStatus(0);
                u.setAdmin("-");
                u.setReason("-");
                u.setTime(0);
                u.setNumber(0);
                DataManager.saveUser(u);
            } else {
                e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, Util.fixColor(messageTempBan));
            }
        }
        //Ban
        if (u.getStatus() == 1){
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, Util.fixColor(messageBan));
        }
    }
}
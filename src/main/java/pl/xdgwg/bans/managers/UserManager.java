package pl.xdgwg.bans.managers;

import pl.xdgwg.bans.BansPlugin;
import pl.xdgwg.bans.object.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pl.xdgwg.bans.utils.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class UserManager {

    private static List<User> Users = new ArrayList<User>();

    public static List<User> getUsers() {
        return Users;
    }

    public static User get(UUID uuid){
        for (User user : getUsers()){
            if (user.getUuid().equals(uuid)){
                return user;
            }
        }
        return null;
    }

    public static void exist(){
        for (Player p : Bukkit.getOnlinePlayers()){
            if (get(p.getUniqueId()) == null){
                new User(p.getName(), p.getUniqueId().toString(), p.getAddress().toString(), false, 0, 0, 0, "-", "-", 0, 0, 0, "-", "-", 0);
            }
        }
    }

    public static void load() {
        if (ConfigManager.getDataType().equalsIgnoreCase("mysql")){
            try {
                MySQLManager.loadUsers();
            } catch (Exception e){
                Util.sendConsole(Util.getTag() + " &8&l>> &4Mysql error! &cCheck config.yml!");
                Bukkit.getPluginManager().disablePlugin(BansPlugin.getInstance());
            }
        } else {
            FlatManager.loadUsers();
        }
    }
}
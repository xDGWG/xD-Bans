package pl.xdgwg.bans.managers;

import pl.xdgwg.bans.object.User;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import java.util.ArrayList;
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
                new User(p.getName(), p.getUniqueId().toString(), p.getAddress().toString(), false, 0, 0, "-", "-", 0);
            }
        }
    }
}
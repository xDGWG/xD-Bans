package pl.xdgwg.bans.managers;

import pl.xdgwg.bans.BansPlugin;
import pl.xdgwg.bans.object.User;
import pl.xdgwg.bans.utils.Util;
import org.bukkit.Bukkit;

public class DataManager {

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

    public static void saveUser(User u){
        if (ConfigManager.getDataType().equalsIgnoreCase("mysql")){
            try{
                MySQLManager.saveUser(u);
            } catch (Exception e){
                Util.sendConsole(Util.getTag() + " &8&l>> &4Mysql error! &cCheck config.yml!");
                Bukkit.getPluginManager().disablePlugin(BansPlugin.getInstance());
            }
        } else {
            FlatManager.saveUser(u);
        }
    }
}
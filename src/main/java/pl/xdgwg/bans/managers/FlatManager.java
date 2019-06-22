package pl.xdgwg.bans.managers;

import pl.xdgwg.bans.BansPlugin;
import pl.xdgwg.bans.object.User;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

public class FlatManager {

    private static File users = new File(BansPlugin.getInstance().getDataFolder(), "users");

    public static void saveUser(User u){
        File file = new File(users, u.getName() + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        yamlConfiguration.set("name", u.getName());
        yamlConfiguration.set("uuid", u.getUuid().toString());
        yamlConfiguration.set("ip", u.getIp());
        yamlConfiguration.set("bypass", u.getBypass());
        yamlConfiguration.set("warnings", u.getWarnings());
        yamlConfiguration.set("banStatus", u.getBanStatus());
        yamlConfiguration.set("banTime", u.getBanTime());
        yamlConfiguration.set("banReason", u.getBanReason());
        yamlConfiguration.set("banAdmin", u.getBanAdmin());
        yamlConfiguration.set("banNumber", u.getBanNumber());
        yamlConfiguration.set("muteStatus", u.getMuteStatus());
        yamlConfiguration.set("muteTime", u.getMuteTime());
        yamlConfiguration.set("muteReason", u.getMuteReason());
        yamlConfiguration.set("muteAdmin", u.getMuteAdmin());
        yamlConfiguration.set("muteNumber", u.getMuteNumber());
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadUsers(){
        if (!users.exists()) users.mkdir();
        for (File file : users.listFiles()){
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
            String name = yamlConfiguration.getString("name");
            String uuid = yamlConfiguration.getString("uuid");
            String ip = yamlConfiguration.getString("ip");
            boolean bypass = yamlConfiguration.getBoolean("bypass");
            int warnings = yamlConfiguration.getInt("warnings");
            int banStatus = yamlConfiguration.getInt("banStatus");
            long banTime = yamlConfiguration.getLong("banTime");
            String banReason = yamlConfiguration.getString("banReason");
            String banAdmin = yamlConfiguration.getString("banAdmin");
            long banNumber = yamlConfiguration.getLong("banNumber");
            int muteStatus = yamlConfiguration.getInt("muteStatus");
            long muteTime = yamlConfiguration.getLong("muteTime");
            String muteReason = yamlConfiguration.getString("muteReason");
            String muteAdmin = yamlConfiguration.getString("muteAdmin");
            long muteNumber = yamlConfiguration.getLong("muteNumber");
            new User(name, uuid, ip, bypass, warnings, banStatus, banTime, banReason, banAdmin, banNumber, muteStatus, muteTime, muteReason, muteAdmin, muteNumber);
        }
    }
}
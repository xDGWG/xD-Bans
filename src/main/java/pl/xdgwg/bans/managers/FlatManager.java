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
        yamlConfiguration.set("status", u.getStatus());
        yamlConfiguration.set("time", u.getTime());
        yamlConfiguration.set("reason", u.getReason());
        yamlConfiguration.set("admin", u.getAdmin());
        yamlConfiguration.set("number", u.getNumber());
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
            int status = yamlConfiguration.getInt("status");
            long time = yamlConfiguration.getLong("time");
            String reason = yamlConfiguration.getString("reason");
            String admin = yamlConfiguration.getString("admin");
            long number = yamlConfiguration.getLong("number");
            new User(name, uuid, ip, bypass, status, time, reason, admin, number);
        }
    }
}
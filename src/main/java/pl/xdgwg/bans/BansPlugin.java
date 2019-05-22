package pl.xdgwg.bans;

import pl.xdgwg.bans.commands.*;
import pl.xdgwg.bans.managers.MySQLManager;
import pl.xdgwg.bans.managers.UserManager;
import pl.xdgwg.bans.utils.Util;
import pl.xdgwg.bans.listeners.AsyncPlayerPreLoginListener;
import pl.xdgwg.bans.managers.ConfigManager;
import pl.xdgwg.bans.managers.DataManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BansPlugin extends JavaPlugin {

    private static BansPlugin instance;

    @Override
    public void onEnable(){
        Util.sendConsole(Util.getTag() + " &2Enabled ban plugin by &axDGWG&2!");
        instance = this;
        ConfigManager.loads();
        MySQLManager.connect();
        getServer().getPluginManager().registerEvents(new AsyncPlayerPreLoginListener(), this);
        DataManager.load();
        UserManager.exist();
        getCommand("ban").setExecutor(new BanCommand());
        getCommand("unban").setExecutor(new UnbanCommand());
        getCommand("bypass").setExecutor(new BypassCommand());
        getCommand("check").setExecutor(new CheckCommand());
        getCommand("tempban").setExecutor(new TempbanCommand());
        getCommand("kick").setExecutor(new KickCommand());
    }

    @Override
    public void onDisable(){
        Util.sendConsole(Util.getTag() + " &4Disabled ban plugin by &cxDGWG&2!");
        MySQLManager.disconnect();
    }

    public static BansPlugin getInstance(){
        return instance;
    }
}
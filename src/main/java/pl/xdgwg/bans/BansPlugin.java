package pl.xdgwg.bans;

import pl.xdgwg.bans.commands.*;
import pl.xdgwg.bans.listeners.AsyncPlayerChatListener;
import pl.xdgwg.bans.managers.MySQLManager;
import pl.xdgwg.bans.managers.UserManager;
import pl.xdgwg.bans.utils.Util;
import pl.xdgwg.bans.listeners.AsyncPlayerPreLoginListener;
import pl.xdgwg.bans.managers.ConfigManager;
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
        getServer().getPluginManager().registerEvents(new AsyncPlayerChatListener(), this);
        UserManager.load();
        UserManager.exist();
        getCommand("ban").setExecutor(new BanCommand());
        getCommand("unban").setExecutor(new UnbanCommand());
        getCommand("bypass").setExecutor(new BypassCommand());
        getCommand("check").setExecutor(new CheckCommand());
        getCommand("tempban").setExecutor(new TempbanCommand());
        getCommand("kick").setExecutor(new KickCommand());
        getCommand("mute").setExecutor(new MuteCommand());
        getCommand("unmute").setExecutor(new UnmuteCommand());
        getCommand("tempmute").setExecutor(new TempmuteCommand());
        getCommand("warn").setExecutor(new WarnCommand());
        getCommand("banlist").setExecutor(new BanListCommand());
        getCommand("mutelist").setExecutor(new MuteListCommand());
        getCommand("bypasslist").setExecutor(new BypassListCommand());
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
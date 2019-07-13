package pl.xdgwg.bans.commands;

import pl.xdgwg.bans.managers.UserManager;
import pl.xdgwg.bans.object.User;
import pl.xdgwg.bans.utils.Util;
import pl.xdgwg.bans.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BypassCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("xdgwg.bypass")){
            Util.sendMessage(commandSender, ConfigManager.getNoPermission().replace("{PERM}", "xdgwg.bypass"));
            return false;
        }
        if (strings.length != 1){
            Util.sendMessage(commandSender, ConfigManager.getUsage().replace("{USAGE}", "/bypass [player]"));
            return false;
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(strings[0]);
        String playerName = offlinePlayer.getName();
        User u = UserManager.get(offlinePlayer.getUniqueId());
        if (!UserManager.getUsers().contains(UserManager.get(offlinePlayer.getUniqueId()))){
            Util.sendMessage(commandSender, ConfigManager.getNotExist());
            return false;
        }
        if (u.getBypass()){
            u.setBypass(false);
            u.save();
            Util.sendMessage(commandSender, ConfigManager.getBypassOff().replace("{PLAYER}", playerName));
            return true;
        }
        if (!u.getBypass()){
            u.setBypass(true);
            u.save();
            Util.sendMessage(commandSender, ConfigManager.getBypassOn().replace("{PLAYER}", playerName));
            return true;
        }
        return false;
    }
}
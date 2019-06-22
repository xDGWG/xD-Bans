package pl.xdgwg.bans.commands;

import pl.xdgwg.bans.managers.DataManager;
import pl.xdgwg.bans.managers.UserManager;
import pl.xdgwg.bans.object.User;
import pl.xdgwg.bans.utils.Util;
import pl.xdgwg.bans.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UnbanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("xdgwg.unban")){
            Util.sendMessage(commandSender, ConfigManager.getNoPermission().replace("{PERM}", "xdgwg.unban"));
            return false;
        }
        if (strings.length != 1){
            Util.sendMessage(commandSender, ConfigManager.getUsage().replace("{USAGE}", "/unban [player]"));
            return false;
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(strings[0]);
        User u = UserManager.get(offlinePlayer.getUniqueId());
        if (!UserManager.getUsers().contains(UserManager.get(offlinePlayer.getUniqueId()))){
            Util.sendMessage(commandSender, ConfigManager.getNotExist());
            return false;
        }
        if (u.getBanStatus() == 0){
            Util.sendMessage(commandSender, ConfigManager.getNotBanned());
            return false;
        }
        u.setBanStatus(0);
        u.setBanAdmin("-");
        u.setBanReason("-");
        u.setBanTime(0);
        u.setBanNumber(0);
        DataManager.saveUser(u);
        Util.sendBroadcast(ConfigManager.getUnbanBroadcast().replace("{PLAYER}", u.getName()).replace("{ADMIN}", commandSender.getName()));
        return true;
    }
}
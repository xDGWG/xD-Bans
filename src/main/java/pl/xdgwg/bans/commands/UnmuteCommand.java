package pl.xdgwg.bans.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.xdgwg.bans.managers.ConfigManager;
import pl.xdgwg.bans.managers.DataManager;
import pl.xdgwg.bans.managers.UserManager;
import pl.xdgwg.bans.object.User;
import pl.xdgwg.bans.utils.Util;

public class UnmuteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("xdgwg.unmute")){
            Util.sendMessage(commandSender, ConfigManager.getNoPermission().replace("{PERM}", "xdgwg.unmute"));
            return false;
        }
        if (strings.length != 1){
            Util.sendMessage(commandSender, ConfigManager.getUsage().replace("{USAGE}", "/unmute [player]"));
            return false;
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(strings[0]);
        User u = UserManager.get(offlinePlayer.getUniqueId());
        if (!UserManager.getUsers().contains(UserManager.get(offlinePlayer.getUniqueId()))){
            Util.sendMessage(commandSender, ConfigManager.getNotExist());
            return false;
        }
        if (u.getMuteStatus() == 0){
            Util.sendMessage(commandSender, ConfigManager.getNotMuted());
            return false;
        }
        u.setMuteStatus(0);
        u.setMuteTime(0);
        u.setMuteReason("-");
        u.setMuteAdmin("-");
        u.setMuteNumber(0);
        DataManager.saveUser(u);
        Util.sendBroadcast(ConfigManager.getUnmuteBroadcast().replace("{PLAYER}", u.getName()).replace("{ADMIN}", commandSender.getName()));
        return true;
    }
}
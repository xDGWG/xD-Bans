package pl.xdgwg.bans.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.xdgwg.bans.managers.ConfigManager;
import pl.xdgwg.bans.managers.UserManager;
import pl.xdgwg.bans.object.User;
import pl.xdgwg.bans.utils.Util;

public class MuteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("xdgwg.mute")){
            Util.sendMessage(commandSender, ConfigManager.getNoPermission().replace("{PERM}", "xdgwg.mute"));
            return false;
        }
        if (strings.length < 2){
            Util.sendMessage(commandSender, ConfigManager.getUsage().replace("{USAGE}", "/mute [player] [reason]"));
            return false;
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(strings[0]);
        User u = UserManager.get(offlinePlayer.getUniqueId());
        String playerName = strings[0];
        String adminName = commandSender.getName();
        String reason = StringUtils.join(strings, " ").replaceFirst(playerName + " ", "");
        if (!UserManager.getUsers().contains(UserManager.get(offlinePlayer.getUniqueId()))){
            Util.sendMessage(commandSender, ConfigManager.getNotExist());
            return false;
        }
        if (u.getBypass()){
            Util.sendMessage(commandSender, ConfigManager.getBypass());
            return false;
        }
        if (playerName.equalsIgnoreCase(adminName)){
            Util.sendMessage(commandSender, ConfigManager.getMuteYourself());
            return false;
        }
        if (u.getMuteStatus() == 1 || u.getMuteStatus() == 2){
            Util.sendMessage(commandSender, ConfigManager.getMuted());
            return false;
        }
        u.setMuteStatus(1);
        u.setMuteReason(reason);
        u.setMuteAdmin(adminName);
        u.save();
        Util.sendBroadcast(ConfigManager.getMuteBroadcast().replace("{PLAYER}", playerName).replace("{ADMIN}", adminName).replace("{REASON}", reason));
        return true;
    }
}

package pl.xdgwg.bans.commands;

import org.apache.commons.lang.StringUtils;
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

public class BanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("xdgwg.ban")){
            Util.sendMessage(commandSender, ConfigManager.getNoPermission().replace("{PERM}", "xdgwg.ban"));
            return false;
        }
        if (strings.length < 2){
            Util.sendMessage(commandSender, ConfigManager.getUsage().replace("{USAGE}", "/ban [player] [reason]"));
            return false;
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(strings[0]);
        User u = UserManager.get(offlinePlayer.getUniqueId());
        String playerName = strings[0];
        String adminName = commandSender.getName();
        String reason = StringUtils.join(strings, " ").replaceFirst(playerName + " ", "");
        String kickMessage = Util.stringList(ConfigManager.getBanKick()).replace("{ADMIN}", adminName).replace("{REASON}", reason);
        if (!UserManager.getUsers().contains(UserManager.get(offlinePlayer.getUniqueId()))){
            Util.sendMessage(commandSender, ConfigManager.getNotExist());
            return false;
        }
        if (u.getBypass()){
            Util.sendMessage(commandSender, ConfigManager.getBypass());
            return false;
        }
        if (playerName.equalsIgnoreCase(adminName)){
            Util.sendMessage(commandSender, ConfigManager.getBanYourself());
            return false;
        }
        if (u.getBanStatus() == 1 || u.getBanStatus() == 2){
            Util.sendMessage(commandSender, ConfigManager.getBanned());
            return false;
        }
        u.setBanStatus(1);
        u.setBanReason(reason);
        u.setBanAdmin(adminName);
        DataManager.saveUser(u);
        if (offlinePlayer.isOnline()) offlinePlayer.getPlayer().kickPlayer(Util.fixColor(kickMessage));
        Util.sendBroadcast(ConfigManager.getBanBroadcast().replace("{PLAYER}", playerName).replace("{ADMIN}", adminName).replace("{REASON}", reason));
        return true;
    }
}
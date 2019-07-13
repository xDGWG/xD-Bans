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

public class TempmuteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("xdgwg.tempmute")){
            Util.sendMessage(commandSender, ConfigManager.getNoPermission().replace("{PERM}", "xdgwg.tempmute"));
            return false;
        }
        if (strings.length < 4){
            Util.sendMessage(commandSender, ConfigManager.getUsage().replace("{USAGE}", "/tempmute [player] [number] [s/m/h/d] [reason]"));
            return false;
        }
        try{
            int number = Integer.parseInt(strings[1]);
        } catch (NumberFormatException e){
            Util.sendMessage(commandSender, ConfigManager.getUsage().replace("{USAGE}", "/tempmute [player] [number] [s/m/h/d] [reason]"));
            return false;
        }
        if (!(strings[2].equalsIgnoreCase("s") || strings[2].equalsIgnoreCase("m") || strings[2].equalsIgnoreCase("h") || strings[2].equalsIgnoreCase("d"))){
            Util.sendMessage(commandSender, ConfigManager.getUsage().replace("{USAGE}", "/tempmute [player] [number] [s/m/h/d] [reason]"));
            return false;
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(strings[0]);
        User u = UserManager.get(offlinePlayer.getUniqueId());
        String playerName = strings[0];
        String adminName = commandSender.getName();
        String reason = StringUtils.join(strings, " ").replaceFirst(playerName + " ", "").replaceFirst(strings[1] + " ", "").replaceFirst(strings[2] + " ", "");
        String timeType = strings[2];
        int time = Integer.parseInt(strings[1]);
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
        u.setMuteStatus(2);
        u.setMuteReason(reason);
        u.setMuteAdmin(adminName);
        u.setMuteTime(Util.getSystemTime());
        long number = 0;
        String bcTime = "";
        if (timeType.equalsIgnoreCase("s")){
            number = time;
            bcTime = "" + time + " " + ConfigManager.getTimeFormatSeconds();
        } else if (timeType.equalsIgnoreCase("m")){
            number = time * 60;
            bcTime = "" + time + " " + ConfigManager.getTimeFormatMinutes();
        } else  if (timeType.equalsIgnoreCase("h")){
            number = (time * 60) * 60;
            bcTime = "" + time + " " + ConfigManager.getTimeFormatHours();
        } else if (timeType.equalsIgnoreCase("d")){
            number = ((time* 60) * 60) * 24;
            bcTime = "" + time + " " + ConfigManager.getTimeFormatDays();
        }
        u.setMuteNumber(number);
        u.save();
        Util.sendBroadcast(ConfigManager.getTempMuteBroadcast().replace("{PLAYER}", playerName).replace("{ADMIN}", adminName).replace("{REASON}", reason).replace("{TIME}", bcTime));
        return true;
    }
}
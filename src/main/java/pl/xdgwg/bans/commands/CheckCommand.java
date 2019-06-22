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

public class CheckCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("xdgwg.check")){
            Util.sendMessage(commandSender, ConfigManager.getNoPermission().replace("{PERM}", "xdgwg.check"));
            return false;
        }
        if (strings.length != 1){
            Util.sendMessage(commandSender, ConfigManager.getUsage().replace("{USAGE}", "/check [player]"));
            return false;
        }
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(strings[0]);
        User u = UserManager.get(offlinePlayer.getUniqueId());
        if (!UserManager.getUsers().contains(UserManager.get(offlinePlayer.getUniqueId()))){
            Util.sendMessage(commandSender, ConfigManager.getNotExist());
            return false;
        }
        long now = Util.getSystemTime();
        long banTime = u.getBanTime();
        long banNumber = u.getBanNumber();
        long muteTime = u.getMuteTime();
        long muteNumber = u.getMuteNumber();
        if ((u.getBanStatus() == 2) && (now - banTime) > banNumber) {
            u.setBanStatus(0);
            u.setBanAdmin("-");
            u.setBanReason("-");
            u.setBanTime(0);
            u.setBanNumber(0);
            DataManager.saveUser(u);
        }

        if ((u.getMuteStatus() == 2) && (now - muteTime) > muteNumber) {
            u.setMuteStatus(0);
            u.setMuteAdmin("-");
            u.setMuteReason("-");
            u.setMuteTime(0);
            u.setMuteNumber(0);
            DataManager.saveUser(u);
        }
        String string = Util.stringList(ConfigManager.getCheck()).replace("{PLAYER}", u.getName()).replace("{UUID}", u.getUuid().toString()).replace("{WARNINGS}", String.valueOf(u.getWarnings())).replace("{IP}", u.getIp()).replace("{BYPASS}", u.getBypass() ? ConfigManager.getCheckBypassOff() : ConfigManager.getCheckBypassOn()).replace("{BAN-STATUS}", Util.checkBanStatus(u.getBanStatus())).replace("{BAN-TIME}", Util.getTime(u, u.getBanStatus(), Util.getSystemTime(), u.getBanTime(), u.getBanNumber())).replace("{BAN-REASON}", u.getBanReason()).replace("{BAN-ADMIN}", u.getBanAdmin()).replace("{MUTE-STATUS}", Util.checkMuteStatus(u.getMuteStatus())).replace("{MUTE-ADMIN}", u.getMuteAdmin()).replace("{MUTE-REASON}", u.getMuteReason()).replace("{MUTE-TIME}", Util.getTime(u, u.getMuteStatus(), Util.getSystemTime(), u.getMuteTime(), u.getMuteNumber()));
        Util.sendMessage(commandSender, string);
        return true;
    }
}
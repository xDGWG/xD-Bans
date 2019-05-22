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
        long ban = u.getTime();
        long number = u.getNumber();
        if ((u.getStatus() == 2) && (now - ban) > number) {
            u.setStatus(0);
            u.setAdmin("-");
            u.setReason("-");
            u.setTime(0);
            u.setNumber(0);
            DataManager.saveUser(u);
        }
        String string = Util.stringList(ConfigManager.getCheck()).replace("{PLAYER}", u.getName()).replace("{UUID}", u.getUuid().toString()).replace("{IP}", u.getIp()).replace("{BYPASS}", u.getBypass() ? ConfigManager.getCheckBypassOff() : ConfigManager.getCheckBypassOn()).replace("{STATUS}", Util.checkStatus(u.getStatus())).replace("{TIME}", Util.getBanTime(u, Util.getSystemTime(), u.getTime(), u.getNumber())).replace("{REASON}", u.getReason()).replace("{ADMIN}", u.getAdmin());
        Util.sendMessage(commandSender, string);
        return true;
    }
}
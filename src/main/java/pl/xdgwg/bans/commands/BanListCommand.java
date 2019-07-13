package pl.xdgwg.bans.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.xdgwg.bans.managers.ConfigManager;
import pl.xdgwg.bans.managers.UserManager;
import pl.xdgwg.bans.object.User;
import pl.xdgwg.bans.utils.Util;
import java.util.HashSet;
import java.util.Set;

public class BanListCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("xdgwg.banlist")){
            Util.sendMessage(commandSender, ConfigManager.getNoPermission().replace("{PERM}", "xdgwg.banlist"));
            return false;
        }
        if (strings.length > 0){
            Util.sendMessage(commandSender, ConfigManager.getUsage().replace("{USAGE}", "/banlist"));
            return false;
        }
        Set<String> banPlayers = new HashSet<String>();
        for (User user : UserManager.getUsers()){
            if (user.getBanStatus() == 1 || user.getBanStatus() == 2){
                banPlayers.add(user.getName());
            }
        }
        Util.sendMessage(commandSender, ConfigManager.getBanList().replace("{LIST}", banPlayers.toString()));
        return true;
    }
}
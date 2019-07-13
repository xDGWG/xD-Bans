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

public class MuteListCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("xdgwg.mutelist")){
            Util.sendMessage(commandSender, ConfigManager.getNoPermission().replace("{PERM}", "xdgwg.mutelist"));
            return false;
        }
        if (strings.length > 0){
            Util.sendMessage(commandSender, ConfigManager.getUsage().replace("{USAGE}", "/mutelist"));
            return false;
        }
        Set<String> mutePlayers = new HashSet<String>();
        for (User user : UserManager.getUsers()){
            if (user.getMuteStatus() == 1 || user.getMuteStatus() == 2){
                mutePlayers.add(user.getName());
            }
        }
        Util.sendMessage(commandSender, ConfigManager.getMuteList().replace("{LIST}", mutePlayers.toString()));
        return true;
    }
}
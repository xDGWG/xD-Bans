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

public class BypassListCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("xdgwg.bypasslist")){
            Util.sendMessage(commandSender, ConfigManager.getNoPermission().replace("{PERM}", "xdgwg.bypasslist"));
            return false;
        }
        if (strings.length > 0){
            Util.sendMessage(commandSender, ConfigManager.getUsage().replace("{USAGE}", "/bypasslist"));
            return false;
        }
        Set<String> bypassPlayers = new HashSet<String>();
        for (User user : UserManager.getUsers()){
            if (user.getBypass()){
                bypassPlayers.add(user.getName());
            }
        }
        Util.sendMessage(commandSender, ConfigManager.getBypassList().replace("{LIST}", bypassPlayers.toString()));
        return true;
    }
}
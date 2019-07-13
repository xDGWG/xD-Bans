package pl.xdgwg.bans.commands;

import org.apache.commons.lang.StringUtils;
import pl.xdgwg.bans.managers.UserManager;
import pl.xdgwg.bans.object.User;
import pl.xdgwg.bans.utils.Util;
import pl.xdgwg.bans.managers.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KickCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("xdgwg.kick")){
            Util.sendMessage(commandSender, ConfigManager.getNoPermission().replace("{PERM}", "xdgwg.kick"));
            return false;
        }
        if (strings.length < 2){
            Util.sendMessage(commandSender, ConfigManager.getUsage().replace("{USAGE}", "/kick [player/@] [reason]"));
            return false;
        }
        String reason = StringUtils.join(strings, " ").replaceFirst(strings[0] + " ", "");
        if (strings[0].equalsIgnoreCase("@")){
            for (Player players : Bukkit.getOnlinePlayers()){
                User u = UserManager.get(players.getUniqueId());
                if (!u.getBypass()){
                    players.kickPlayer(Util.fixColor(Util.stringList(ConfigManager.getKick()).replace("{REASON}", reason).replace("{ADMIN}", commandSender.getName())));
                }
            }
            Util.sendBroadcast(ConfigManager.getKickAllBroadcast().replace("{ADMIN}", commandSender.getName()).replace("{REASON}", reason));
            return true;
        } else {
            Player player = Bukkit.getPlayer(strings[0]);
            if (player == null){
                Util.sendMessage(commandSender, ConfigManager.getNotExist());
                return false;
            }
            User u = UserManager.get(player.getUniqueId());
            if (u.getBypass()){
                Util.sendMessage(commandSender, ConfigManager.getBypass());
                return false;
            }
            player.kickPlayer(Util.fixColor(Util.stringList(ConfigManager.getKick()).replace("{REASON}", reason).replace("{ADMIN}", commandSender.getName())));
            Util.sendBroadcast(ConfigManager.getKickPlayerBroadcast().replace("{ADMIN}", commandSender.getName()).replace("{REASON}", reason).replace("{PLAYER}", player.getName()));
            return true;
        }
    }
}
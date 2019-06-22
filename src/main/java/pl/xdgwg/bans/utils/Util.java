package pl.xdgwg.bans.utils;

import pl.xdgwg.bans.managers.ConfigManager;
import pl.xdgwg.bans.object.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.List;

public class Util {

    private static String tag = "&8[&4xD-Bans&8]";

    public static String fixColor(String string){
        return ChatColor.translateAlternateColorCodes('&', string.replace(">>", "»").replace("<<", "«"));
    }

    public static void sendMessage(CommandSender commandSender, String string){
        commandSender.sendMessage(fixColor(string));
    }

    public static void sendConsole(String string){
        Bukkit.getConsoleSender().sendMessage(fixColor(string));
    }

    public static void sendBroadcast(String string){
        Bukkit.broadcastMessage(fixColor(string));
    }

    public static void sendTtile(String string, Player player){
        player.sendTitle(fixColor(string), "");
    }

    public static void sendTtile(String string, String string2, Player player){
        player.sendTitle(fixColor(string), fixColor(string2));
    }

    public static long getSystemTime() {
        return System.currentTimeMillis() / 1000;
    }

    public static String getTag() {
        return tag;
    }

    public static String stringList(List<String> list){
        String string = "";
        for (int i = 0; i < list.size(); i++){
            string = string + list.get(i) + "\n";
        }
        return string;
    }

    public static String checkBanStatus(int i){
        String string = "";
        if (i == 0){
            string = ConfigManager.getCheckStatusNoBan();
        } else if (i == 1){
            string = ConfigManager.getCheckStatusPermBan();
        } else if (i == 2){
            string = ConfigManager.getCheckStatusTempBan();
        }
        return string;
    }

    public static String checkMuteStatus(int i){
        String string = "";
        if (i == 0){
            string = ConfigManager.getCheckStatusNoMute();
        } else if (i == 1){
            string = ConfigManager.getCheckStatusPermMute();
        } else if (i == 2){
            string = ConfigManager.getCheckStatusTempMute();
        }
        return string;
    }

    public static String getTime(User u, int status, long now, long ban, long number){
        if (status != 2){
            return "-";
        }
        if ((now - ban) - number > 0){
            return "-";
        }
        long sec = ((now - ban) - number) - (((now - ban) - number) / 60) * 60;
        long min = (((now - ban) - number) / 60) - ((((now - ban) - number) / 60) / 60) * 60;
        long h = ((((now - ban) - number) / 60) / 60) - (((((now - ban) - number) / 60) / 60) / 24) * 24;
        long d = ((((now - ban) - number) / 60) / 60) / 24;
        String seconds = String.valueOf(sec) + " " + ConfigManager.getTimeFormatSeconds();
        String minutes = String.valueOf(min) + " " + ConfigManager.getTimeFormatMinutes() + " ";
        String hours = String.valueOf(h) + " " + ConfigManager.getTimeFormatHours() + " ";
        String days = String.valueOf(d) + " " + ConfigManager.getTimeFormatDays() + " ";
        String time = "";
        if (d != 0){
            time = time + days;
        }
        if (h != 0){
            time = time + hours;
        }
        if (min != 0){
            time = time + minutes;
        }
        if (sec != 0){
            time = time + seconds;
        }
        return fixColor(time.replace("-", ""));
    }
}
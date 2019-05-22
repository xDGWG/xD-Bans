package pl.xdgwg.bans.managers;

import pl.xdgwg.bans.BansPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.List;

public class ConfigManager {

    private static String noPermission;
    private static String usage;
    private static String notExist;
    private static String bypass;
    private static String banned;
    private static String notBanned;
    private static String banBroadcast;
    private static String tempBanBroadcast;
    private static String unbanBroadcast;
    private static List<String> banKick;
    private static List<String> tempBanKick;
    private static List<String> banJoin;
    private static List<String> tempBanJoin;
    private static List<String> kick;
    private static String banYourself;
    private static String bypassOn;
    private static String bypassOff;
    private static List<String> check;
    private static String checkStatusNoBan;
    private static String checkStatusPermBan;
    private static String checkStatusTempBan;
    private static String checkBypassOn;
    private static String checkBypassOff;
    private static String badNickname;
    private static String timeFormatSeconds;
    private static String timeFormatMinutes;
    private static String timeFormatHours;
    private static String timeFormatDays;
    private static String dataType;
    private static String mysqlHost;
    private static String mysqlPort;
    private static String mysqlUser;
    private static String mysqlPassword;
    private static String mysqlName;
    private static Boolean playerNameFilter;
    private static String kickPlayerBroadcast;
    private static String kickAllBroadcast;

    public static void loads(){
        BansPlugin.getInstance().saveDefaultConfig();
        FileConfiguration fileConfiguration = BansPlugin.getInstance().getConfig();
        noPermission = fileConfiguration.getString("messages.noPermission");
        usage = fileConfiguration.getString("messages.usage");
        notExist = fileConfiguration.getString("messages.notExist");
        bypass = fileConfiguration.getString("messages.bypass");
        banned = fileConfiguration.getString("messages.banned");
        notBanned = fileConfiguration.getString("messages.notBanned");
        banBroadcast = fileConfiguration.getString("messages.banBroadcast");
        tempBanBroadcast = fileConfiguration.getString("messages.tempBanBroadcast");
        unbanBroadcast = fileConfiguration.getString("messages.unbanBroadcast");
        banKick = fileConfiguration.getStringList("messages.banKick");
        banJoin = fileConfiguration.getStringList("messages.banJoin");
        banYourself = fileConfiguration.getString("messages.banYourself");
        bypassOn = fileConfiguration.getString("messages.bypassOn");
        bypassOff = fileConfiguration.getString("messages.bypassOff");
        check = fileConfiguration.getStringList("messages.check.lines");
        checkStatusNoBan = fileConfiguration.getString("messages.check.status.noBan");
        checkStatusPermBan = fileConfiguration.getString("messages.check.status.permBan");
        checkStatusTempBan = fileConfiguration.getString("messages.check.status.tempBan");
        checkBypassOn = fileConfiguration.getString("messages.check.bypass.enable");
        checkBypassOff = fileConfiguration.getString("messages.check.bypass.disable");
        tempBanKick = fileConfiguration.getStringList("messages.tempBanKick");
        tempBanJoin = fileConfiguration.getStringList("messages.tempBanJoin");
        badNickname = fileConfiguration.getString("messages.badNickname");
        timeFormatSeconds = fileConfiguration.getString("messages.timeFormat.seconds");
        timeFormatMinutes = fileConfiguration.getString("messages.timeFormat.minutes");
        timeFormatHours = fileConfiguration.getString("messages.timeFormat.hours");
        timeFormatDays = fileConfiguration.getString("messages.timeFormat.days");
        dataType = fileConfiguration.getString("configs.dataType");
        mysqlHost = fileConfiguration.getString("configs.mysql.host");
        mysqlPort = fileConfiguration.getString("configs.mysql.port");
        mysqlUser = fileConfiguration.getString("configs.mysql.user");
        mysqlPassword = fileConfiguration.getString("configs.mysql.password");
        mysqlName = fileConfiguration.getString("configs.mysql.name");
        playerNameFilter = fileConfiguration.getBoolean("configs.playerNameFilter");
        kick = fileConfiguration.getStringList("messages.kick");
        kickPlayerBroadcast = fileConfiguration.getString("messages.kickPlayerBroadcast");
        kickAllBroadcast = fileConfiguration.getString("messages.kickAllBroadcast");
    }

    public static String getNoPermission() {
        return noPermission;
    }

    public static String getUsage() {
        return usage;
    }

    public static String getNotExist() {
        return notExist;
    }

    public static String getBypass() {
        return bypass;
    }

    public static String getBanned() {
        return banned;
    }

    public static String getNotBanned() {
        return notBanned;
    }

    public static String getBanBroadcast() {
        return banBroadcast;
    }

    public static String getUnbanBroadcast() {
        return unbanBroadcast;
    }

    public static List<String> getBanKick() {
        return banKick;
    }

    public static List<String> getBanJoin() {
        return banJoin;
    }

    public static String getBanYourself() {
        return banYourself;
    }

    public static String getBypassOn() {
        return bypassOn;
    }

    public static String getBypassOff() {
        return bypassOff;
    }

    public static List<String> getCheck() {
        return check;
    }

    public static String getCheckStatusNoBan() {
        return checkStatusNoBan;
    }

    public static String getCheckStatusPermBan() {
        return checkStatusPermBan;
    }

    public static String getCheckStatusTempBan() {
        return checkStatusTempBan;
    }

    public static String getCheckBypassOn() {
        return checkBypassOn;
    }

    public static String getCheckBypassOff() {
        return checkBypassOff;
    }

    public static List<String> getTempBanKick() {
        return tempBanKick;
    }

    public static List<String> getTempBanJoin() {
        return tempBanJoin;
    }

    public static String getTempBanBroadcast() {
        return tempBanBroadcast;
    }

    public static String getBadNickname() {
        return badNickname;
    }

    public static String getTimeFormatSeconds() {
        return timeFormatSeconds;
    }

    public static String getTimeFormatMinutes() {
        return timeFormatMinutes;
    }

    public static String getTimeFormatHours() {
        return timeFormatHours;
    }

    public static String getTimeFormatDays() {
        return timeFormatDays;
    }

    public static String getDataType() {
        return dataType;
    }

    public static String getMysqlHost() {
        return mysqlHost;
    }

    public static String getMysqlPort() {
        return mysqlPort;
    }

    public static String getMysqlUser() {
        return mysqlUser;
    }

    public static String getMysqlPassword() {
        return mysqlPassword;
    }

    public static String getMysqlName() {
        return mysqlName;
    }

    public static Boolean getPlayerNameFilter() {
        return playerNameFilter;
    }

    public static List<String> getKick() {
        return kick;
    }

    public static String getKickPlayerBroadcast() {
        return kickPlayerBroadcast;
    }

    public static String getKickAllBroadcast() {
        return kickAllBroadcast;
    }
}
package pl.xdgwg.bans.object;

import pl.xdgwg.bans.managers.UserManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class User {

    private String name;
    private final UUID uniqueId;
    private String ip;
    private boolean bypass;
    private int banStatus;
    private long banTime;
    private String banReason;
    private String banAdmin;
    private long banNumber;
    private int muteStatus;
    private long muteTime;
    private String muteReason;
    private String muteAdmin;
    private long muteNumber;

    public User(String name, String uniqueId, String ip, boolean banBypass, int banStatus, long banTime, String banReason, String banAdmin, long banNumber, int muteStatus, long muteTime, String muteReason, String muteAdmin, long muteNumber){
        this.name = name;
        this.uniqueId = UUID.fromString(uniqueId);
        this.ip = ip.replace("/", "");
        this.bypass = banBypass;
        this.banStatus = banStatus;
        this.banTime = banTime;
        this.banReason = banReason;
        this.banAdmin = banAdmin;
        this.banNumber = banNumber;
        this.muteStatus = muteStatus;
        this.muteTime = muteTime;
        this.muteReason = muteReason;
        this.muteAdmin = muteAdmin;
        this.muteNumber = muteNumber;
        UserManager.getUsers().add(this);
    }

    public User(ResultSet resultSet) throws SQLException {
        this.name = resultSet.getString("name");
        this.uniqueId = UUID.fromString(resultSet.getString("uuid"));
        this.ip = resultSet.getString("ip").replace("/", "");
        this.bypass = resultSet.getBoolean("bypass");
        this.banStatus = resultSet.getInt("banStatus");
        this.banTime = resultSet.getLong("banTime");
        this.banReason = resultSet.getString("banReason");
        this.banAdmin = resultSet.getString("banAdmin");
        this.banNumber = resultSet.getLong("banNumber");
        this.muteStatus = resultSet.getInt("muteStatus");
        this.muteTime = resultSet.getLong("muteTime");
        this.muteReason = resultSet.getString("muteReason");
        this.muteAdmin = resultSet.getString("muteAdmin");
        this.muteNumber = resultSet.getInt("muteNumber");
        UserManager.getUsers().add(this);
    }

    //Gettery
    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uniqueId;
    }

    public boolean getBypass() {
        return bypass;
    }

    public String getIp() {
        return ip;
    }

    public int getBanStatus() {
        return banStatus;
    }

    public long getBanTime() {
        return banTime;
    }

    public String getBanReason() {
        return banReason;
    }

    public String getBanAdmin() {
        return banAdmin;
    }

    public long getBanNumber() {
        return banNumber;
    }

    public int getMuteStatus() {
        return muteStatus;
    }

    public long getMuteTime() {
        return muteTime;
    }

    public String getMuteReason() {
        return muteReason;
    }

    public String getMuteAdmin() {
        return muteAdmin;
    }

    public long getMuteNumber() {
        return muteNumber;
    }

    //Settery
    public void setName(String name) {
        this.name = name;
    }

    public void setBypass(boolean bypass) {
        this.bypass = bypass;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setBanStatus(int banStatus) {
        this.banStatus = banStatus;
    }

    public void setBanTime(long banTime) {
        this.banTime = banTime;
    }

    public void setBanReason(String banReason) {
        this.banReason = banReason;
    }

    public void setBanAdmin(String banAdmin) {
        this.banAdmin = banAdmin;
    }

    public void setBanNumber(long banNumber) {
        this.banNumber = banNumber;
    }

    public void setMuteStatus(int muteStatus) {
        this.muteStatus = muteStatus;
    }

    public void setMuteTime(long muteTime) {
        this.muteTime = muteTime;
    }

    public void setMuteReason(String muteReason) {
        this.muteReason = muteReason;
    }

    public void setMuteAdmin(String muteAdmin) {
        this.muteAdmin = muteAdmin;
    }

    public void setMuteNumber(long muteNumber) {
        this.muteNumber = muteNumber;
    }
}
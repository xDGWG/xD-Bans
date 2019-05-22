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
    private int status;
    private long time;
    private String reason;
    private String admin;
    private long number;

    public User(String name, String uniqueId, String ip, boolean bypass, int status, long time, String reason, String admin, long number){
        this.name = name;
        this.uniqueId = UUID.fromString(uniqueId);
        this.ip = ip.replace("/", "");
        this.bypass = bypass;
        this.status = status;
        this.time = time;
        this.reason = reason;
        this.admin = admin;
        this.number = number;
        UserManager.getUsers().add(this);
    }

    public User(ResultSet resultSet) throws SQLException {
        this.name = resultSet.getString("name");
        this.uniqueId = UUID.fromString(resultSet.getString("uuid"));
        this.ip = resultSet.getString("ip").replace("/", "");
        this.bypass = resultSet.getBoolean("bypass");
        this.status = resultSet.getInt("status");
        this.time = resultSet.getLong("time");
        this.reason = resultSet.getString("reason");
        this.admin = resultSet.getString("admin");
        this.number = resultSet.getLong("number");
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

    public int getStatus() {
        return status;
    }

    public long getTime() {
        return time;
    }

    public String getReason() {
        return reason;
    }

    public String getAdmin() {
        return admin;
    }

    public long getNumber() {
        return number;
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

    public void setStatus(int status) {
        this.status = status;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public void setNumber(long number) {
        this.number = number;
    }
}
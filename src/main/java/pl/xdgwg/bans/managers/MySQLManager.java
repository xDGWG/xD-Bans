package pl.xdgwg.bans.managers;

import com.zaxxer.hikari.HikariDataSource;
import pl.xdgwg.bans.object.User;
import java.sql.*;

public class MySQLManager {

    private static HikariDataSource hikari;
    private static Connection connection;

    public static void connect(){
        hikari = new HikariDataSource();
        hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikari.addDataSourceProperty("serverName", ConfigManager.getMysqlHost());
        hikari.addDataSourceProperty("port", ConfigManager.getMysqlPort());
        hikari.addDataSourceProperty("databaseName", ConfigManager.getMysqlName());
        hikari.addDataSourceProperty("user", ConfigManager.getMysqlUser());
        hikari.addDataSourceProperty("password", ConfigManager.getMysqlPassword());
        hikari.setPoolName("MySQL");
        hikari.addDataSourceProperty("cachePrepStmts", true);
        hikari.addDataSourceProperty("prepStmtCacheSize", 250);
        hikari.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        hikari.addDataSourceProperty("useServerPrepStmts", true);
        hikari.addDataSourceProperty("useLocalSessionState", true);
        hikari.addDataSourceProperty("useLocalTransactionState", true);
        hikari.addDataSourceProperty("rewriteBatchedStatements", true);
        hikari.addDataSourceProperty("cacheResultSetMetadata", true);
        hikari.addDataSourceProperty("cacheServerConfiguration", true);
        hikari.addDataSourceProperty("elideSetAutoCommits", true);
        hikari.addDataSourceProperty("maintainTimeStats", false);
        hikari.setMaxLifetime(0);
        hikari.setConnectionTimeout(15000);
        hikari.setValidationTimeout(3000);
        hikari.setMaximumPoolSize(10);
    }

    public static void disconnect(){
        if (hikari != null){
            hikari.close();
        }
    }

    public static void createTable() throws SQLException{
        connection = hikari.getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS users(" +
                "name VARCHAR(50) not null," +
                "uuid VARCHAR(36) not null, " +
                "ip VARCHAR(100) not null, " +
                "bypass VARCHAR(100) not null, " +
                "warnings int(16) not null, " +
                "banStatus int(16) not null, " +
                "banTime long not null, " +
                "banReason varchar(200) not null, " +
                "banAdmin varchar(50) not null, " +
                "banNumber long not null, " +
                "muteStatus int(16) not null, " +
                "muteTime long not null, " +
                "muteReason varchar(200) not null, " +
                "muteAdmin varchar(50) not null, " +
                "muteNumber long not null, " +
                "PRIMARY KEY(uuid))");
        statement.close();
        connection.close();
    }

    public static void saveUser(User u) throws SQLException{
        createTable();
        connection = hikari.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("REPLACE INTO users (name, uuid, ip, bypass, warnings, banStatus, banTime, banReason, banAdmin, banNumber, muteStatus, muteTime, muteReason, muteAdmin, muteNumber) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        preparedStatement.setString(1, u.getName());
        preparedStatement.setString(2, u.getUuid().toString());
        preparedStatement.setString(3, u.getIp());
        preparedStatement.setBoolean(4, u.getBypass());
        preparedStatement.setInt(5, u.getWarnings());
        preparedStatement.setInt(6, u.getBanStatus());
        preparedStatement.setLong(7, u.getBanTime());
        preparedStatement.setString(8, u.getBanReason());
        preparedStatement.setString(9, u.getBanAdmin());
        preparedStatement.setLong(10, u.getBanNumber());
        preparedStatement.setInt(11, u.getMuteStatus());
        preparedStatement.setLong(12, u.getMuteTime());
        preparedStatement.setString(13, u.getMuteReason());
        preparedStatement.setString(14, u.getMuteAdmin());
        preparedStatement.setLong(15, u.getMuteNumber());
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }

    public static void loadUsers() throws SQLException{
        createTable();
        connection = hikari.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            new User(resultSet);
        }
        preparedStatement.close();
        resultSet.close();
        connection.close();
    }
}
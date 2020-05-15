package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSql implements Database{
    private static PostgreSql database = null;
    private static Connection connection = null;
    private static String userName;
    private static String password;
    private static String dbName;
    private static String address;
    private static String port;
    public static void setUserName(String name) {
        userName = name;
    }

    public static void setPassword(String pass) {
        password = pass;
    }

    public static void setDbName(String name) {
        dbName = name;
    }

    public static void setAddress(String addr) {
        address = addr;
    }

    public static void setPort(String p) {
        port = p;
    }

    private final static String driverName = "org.postgresql.Driver";
    private final static String connectionName = "jdbc:postgresql";
    private PostgreSql() {
        connection();
    }
    public static PostgreSql getDatabase() {
        if (database == null) {
            database = new PostgreSql();
        }
        return database;
    }
    public void connection() {
        try {
            Class.forName(driverName).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
                connection = DriverManager.getConnection(connectionName + "://" + address + ":" + port + "/"+ dbName, userName, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}

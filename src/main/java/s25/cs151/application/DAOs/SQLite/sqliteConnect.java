package s25.cs151.application.DAOs.SQLite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class sqliteConnect {
    private static Connection connector;
    private static String databaseName;

    public static void establishSqliteConnection(String dbName){
        databaseName = dbName;

        //Connect to database
        try{
            connector = DriverManager.getConnection(databaseName);
            System.out.println("Connection to db was successful for db");
        } catch (SQLException e) {
            System.out.println("Connection to (db) failed");
        }

    }
    public static void closeSqliteConnection(){
        try{
            connector.close();
            System.out.println("Successfully close connection to db: Faculty Sync");
        } catch (SQLException e) {
            System.out.println("Failed to close connection to db");
        }
    }
    public static Connection getConnector(){return connector;}
    public static String getDatabaseName(){return databaseName;}

}

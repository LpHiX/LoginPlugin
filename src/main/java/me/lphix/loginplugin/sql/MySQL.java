package me.lphix.loginplugin.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    private String host = "localhost";
    private String port = "3306";
    private String database = "hellomysql";
    private String username = "root";
    private String password = "";

    private Connection connection;

    public boolean isConnected(){
        return(connection!=null);
    }

    public void connect() throws ClassNotFoundException, SQLException {
        if(isConnected()) return;
        connection = DriverManager.getConnection("jdbc:mysql://" +
                host + ":" + port + "/" + database + "?userSSL=false", username, password);
    }

    public boolean disconnect(){
        if(!isConnected()) return false;
        try{
            connection.close();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public Connection getConnection() {return connection;}
}

package me.lphix.loginplugin.sql;

import me.lphix.loginplugin.LoginPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLGetter {
    LoginPlugin plugin;
    MySQL sql;
    public SQLGetter(LoginPlugin plugin) {
        this.plugin = plugin;
        this.sql = plugin.getSQL();
    }

    public void startupMethods() {
        createUsersTable();
        createLoggedOnTable();
    }

    public void createUsersTable(){
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement(
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "id INT AUTO_INCREMENT PRIMARY KEY," +
                            "username VARCHAR(50) NOT NULL UNIQUE," +
                            "password VARCHAR(50)" +
                        ");");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createLoggedOnTable(){
        try{
            PreparedStatement ps = sql.getConnection().prepareStatement(
                "CREATE TABLE IF NOT EXISTS logged_users (" +
                        "uuid VARCHAR(100)" +
                        "account_id INT" +
                    ");");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void register(String username, String password){
        if(userExists(username)) return;
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("INSERT INTO users " +
                    "(username, password) VALUES (?,?)");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean userExists(String username){
        try{
            PreparedStatement ps = sql.getConnection().prepareStatement("SELECT * FROM users WHERE username=?");
            ps.setString(1, username);
            ResultSet results = ps.executeQuery();
            return results.next();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean login(Player p, String username, String password){
        ResultSet results;
        try{
            PreparedStatement ps = sql.getConnection().prepareStatement("SELECT * FROM users WHERE username=? AND password=?");
            ps.setString(1, username);
            ps.setString(2, password);
            results = ps.executeQuery();
            if(!results.next()){
                Bukkit.getLogger().info("No user by username: " + username + " and password: " + password + " has been found.");
                return false;
            }
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        try {
            PreparedStatement ps = sql.getConnection().prepareStatement("INSERT INTO logged_users " +
                    "(uuid, account_id) VALUES (?, ?)");
            ps.setString(1, p.getUniqueId().toString());
            ps.setInt(2, results.getInt("id"));
            ps.executeUpdate();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }
    public boolean loggedOn(Player p){
        try{
            PreparedStatement ps = sql.getConnection().prepareStatement("SELECT * FROM logged_users WHERE uuid=?");
            ps.setString(1, p.getUniqueId().toString());
            ResultSet results = ps.executeQuery();
            return results.next();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean logout(Player p){
        ResultSet results;
        if(!loggedOn(p)) return false;
        try{
            PreparedStatement ps = sql.getConnection().prepareStatement("DELETE FROM logged_users WHERE uuid=?");
            ps.setString(1, p.getUniqueId().toString());
            ps.executeUpdate();
            return true;
        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}

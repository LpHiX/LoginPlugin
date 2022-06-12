package me.lphix.loginplugin;

import me.lphix.essentialsl.EssentialsL;
import me.lphix.essentialsl.sql.MySQL;
import me.lphix.loginplugin.commands.LoginCommand;
import me.lphix.loginplugin.commands.LogoutCommand;
import me.lphix.loginplugin.commands.RegisterCommand;
import me.lphix.loginplugin.sql.LoginSQLGetter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class LoginPlugin extends JavaPlugin {

    private MySQL SQL;
    private LoginSQLGetter data;
    @Override
    public void onEnable() {


        this.SQL = EssentialsL.getSQL();
        this.data = new LoginSQLGetter(this);

        this.getCommand("login").setExecutor(new LoginCommand(this));
        this.getCommand("logout").setExecutor(new LogoutCommand(this));
        this.getCommand("register").setExecutor(new RegisterCommand(this));

        try{
            SQL.connect();
        } catch (SQLException | ClassNotFoundException e) {
            Bukkit.getLogger().info("Database is not connected");
        }

        if(SQL.isConnected()){
            Bukkit.getLogger().info("Database has been connected");
            data.startupMethods();
        }

    }

    @Override
    public void onDisable() {
        SQL.disconnect();
    }

    public MySQL getSQL(){return SQL;}
    public LoginSQLGetter getData(){return data;}
}

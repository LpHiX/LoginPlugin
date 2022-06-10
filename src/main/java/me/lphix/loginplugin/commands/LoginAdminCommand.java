package me.lphix.loginplugin.commands;

import me.lphix.loginplugin.LoginPlugin;
import me.lphix.loginplugin.sql.MySQL;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class LoginAdminCommand implements CommandExecutor {
    LoginPlugin plugin;
    MySQL sql;
    public LoginAdminCommand(LoginPlugin plugin) {
        this.plugin = plugin;
        this.sql = plugin.getSQL();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1){
            switch (args[0]){
                case "connect":
                    if(sql.isConnected()) {
                        try {
                            sql.connect();
                            sender.sendMessage(Component.text("Database has been connected", NamedTextColor.GREEN));
                            return true;
                        } catch (ClassNotFoundException | SQLException e) {
                            e.printStackTrace();
                            sender.sendMessage(Component.text("Database could not be connected", NamedTextColor.RED));
                            return true;
                        }
                    } else{
                        sender.sendMessage(Component.text("Database is already connected", NamedTextColor.RED));
                        return true;
                    }
                case "disconnect":
                    if(sql.disconnect()){
                        sender.sendMessage(Component.text("Database has been disconnected", NamedTextColor.GREEN));
                    } else {
                        sender.sendMessage(Component.text("Database could not be disconnected", NamedTextColor.RED));
                    }
                    return true;
            }
        }
        return false;
    }
}

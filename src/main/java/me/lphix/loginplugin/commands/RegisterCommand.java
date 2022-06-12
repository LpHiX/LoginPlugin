package me.lphix.loginplugin.commands;

import me.lphix.loginplugin.LoginPlugin;
import me.lphix.loginplugin.sql.LoginSQLGetter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class RegisterCommand implements CommandExecutor {
    LoginPlugin plugin;
    LoginSQLGetter data;
    public RegisterCommand(LoginPlugin plugin) {
        this.plugin = plugin;
        this.data = plugin.getData();
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length != 3) {
            sender.sendMessage(Component.text("Usage: /register <username> <password> <confirm password>", NamedTextColor.RED));
            return true;
        }
        if(data.userExists(args[0])){
            sender.sendMessage(Component.text("Username already taken", NamedTextColor.RED));
            return true;
        }
        if(!args[1].equals(args[2])) {
            sender.sendMessage(Component.text("Passwords do not match", NamedTextColor.RED));
            return true;
        }
        data.register(args[0], args[1]);
        sender.sendMessage(Component.text("Account with username " + args[0] + " has been registered."));
        return true;
    }
}

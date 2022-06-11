package me.lphix.loginplugin.commands;

import me.lphix.loginplugin.LoginPlugin;
import me.lphix.loginplugin.sql.SQLGetter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LoginCommand implements CommandExecutor {
    LoginPlugin plugin;
    SQLGetter data;
    public LoginCommand(LoginPlugin plugin) {
        this.plugin = plugin;
        this.data = plugin.getData();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage(Component.text("This command can only be run by players", NamedTextColor.RED));
            return true;
        }
        if(args.length != 2) return false;
        if(data.login(p, args[0], args[1])){
            sender.sendMessage(Component.text("You have been logged in successfully", NamedTextColor.GREEN));
        } else {
            sender.sendMessage(Component.text("You could not be logged in", NamedTextColor.RED));
        }
        return true;
    }
}

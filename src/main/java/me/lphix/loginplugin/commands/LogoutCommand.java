package me.lphix.loginplugin.commands;

import com.sun.tools.javac.Main;
import me.lphix.loginplugin.LoginPlugin;
import me.lphix.loginplugin.sql.LoginSQLGetter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LogoutCommand implements CommandExecutor {
    LoginPlugin plugin;
    LoginSQLGetter data;
    public LogoutCommand (LoginPlugin plugin){
        this.plugin = plugin;
        this.data = plugin.getData();
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player p)) {
            sender.sendMessage(Component.text("This command can only be run by players", NamedTextColor.RED));
            return true;
        }
        if(data.logout(p)){
            p.sendMessage(Component.text("You have been successfully logged out", NamedTextColor.GREEN));
        } else{
            p.sendMessage(Component.text("You could not be logged out", NamedTextColor.RED));
        }
        return true;
    }
}

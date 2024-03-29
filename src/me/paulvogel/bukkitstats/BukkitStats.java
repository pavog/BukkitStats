package me.paulvogel.bukkitstats;

import me.paulvogel.bukkitstats.commands.BukkitStatsCommandExecutor;
import me.paulvogel.bukkitstats.handlers.DBHandler;
import me.paulvogel.bukkitstats.handlers.FilesHandler;
import me.paulvogel.bukkitstats.handlers.LogHandler;
import me.paulvogel.bukkitstats.utils.DatabaseType;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Paul on 07.02.2015.
 */
public class BukkitStats extends JavaPlugin {
    
    private static BukkitStats instance;
    public static boolean debug = true;
    public static String logprefix = "";
    public static String chatprefix = "";
    
    public void onEnable() {
        instance = this;
        getCommand("bukkitstats").setExecutor(new BukkitStatsCommandExecutor());
        FilesHandler.init();
        DBHandler.init();
    }
    
    public void onDisable() {
        if (DBHandler.type == DatabaseType.MongoDB)
            DBHandler.getMongodbDatabaseInstance().close();
        else if (DBHandler.type == DatabaseType.MySQL)
            DBHandler.getMysqlDatabaseInstance().close();
    }
    
    public static BukkitStats getInstance() {
        if (instance == null) {
            LogHandler.err("Error, plugin tried to access instance but it was not set.");
            if (Bukkit.getPluginManager().getPlugin("BukkitStats") instanceof BukkitStats) 
                instance = (BukkitStats) Bukkit.getPluginManager().getPlugin("BukkitStats");
        }
        return instance;
    }
}

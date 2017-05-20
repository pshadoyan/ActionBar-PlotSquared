package me.patrick.actionbar;

import com.plotsquared.bukkit.events.PlayerEnterPlotEvent;
import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Plot;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Patrick on 4/30/2017.
 */

public class Utilities extends JavaPlugin implements Listener {

    public static PlotAPI api;
    public static String path = "No_owner_message";
    public static String path2 = "Player_name";
    public static String path3 = "Owned_by_prefix";
    public static ArrayList<String> playername = new ArrayList<>();
    public FileConfiguration plugin = getConfig();
    public String name = plugin.getString(path2);
    public String no_owner_message = plugin.getString(path);
    public String prefix = plugin.getString(path3);
    ArrayList<UUID> uuid = new ArrayList<>();

    public Utilities() {
    }

    public static String getName(Plot plot) {
        Set<UUID> uuid = plot.getOwners();
        UUID firstuuid = uuid.iterator().next();

        return Bukkit.getOfflinePlayer(firstuuid).getName();
    }

    public static void sendActionbar(Player p, String message) {
        IChatBaseComponent icbc = IChatBaseComponent
                .ChatSerializer
                .a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', message) + "\"}");
        PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte) 2);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(bar);
    }

    public void onEnable() {
        registerEvents();

        if (Bukkit.getPluginManager().isPluginEnabled("PlotSquared")) {
            api = new PlotAPI(this);
        }
        loadConfiguration();
    }

    public void loadConfiguration() {
        plugin.addDefault(path, "This plot is unclaimed");
        plugin.addDefault(path2, "Color-code + %Actionbar_name%");
        plugin.addDefault(path3, "Owned by:");
        plugin.options().copyDefaults(true);
        saveConfig();
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);
    }

    @EventHandler
    public void plotEnter(PlayerEnterPlotEvent event) {
        Player player = event.getPlayer();
        Plot plot = event.getPlot();

        if (plot.getOwners().isEmpty()) {
            sendActionbar(player, ChatColor.translateAlternateColorCodes('&', no_owner_message));
        } else {
            String prefix1 = ChatColor.translateAlternateColorCodes('&', prefix);
            String plot_owner = getName(plot);
            playername.add(0, plot_owner);

            if (name.contains("%Actionbar_name%")) {
                String owner_name = name.replace("%Actionbar_name%", plot_owner);
                String colorized = ChatColor.translateAlternateColorCodes('&', owner_name);
                sendActionbar(player, prefix1 + " " + colorized);
            }
        }

    }

}

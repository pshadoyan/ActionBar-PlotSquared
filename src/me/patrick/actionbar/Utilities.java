package me.patrick.actionbar;

import com.plotsquared.bukkit.events.PlayerEnterPlotEvent;
import net.minecraft.server.v1_11_R1.IChatBaseComponent;
import net.minecraft.server.v1_11_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
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
public class Utilities extends JavaPlugin implements Listener
{
    public static PlotAPI api;

    ArrayList<UUID> uuid = new ArrayList <UUID>();

    public Utilities() {}


    public void onEnable()
    {
        registerEvents();

        if (Bukkit.getPluginManager().isPluginEnabled("PlotSquared")) {
            api = new PlotAPI(this);
        }
    }

    private void registerEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new actionBar(), this);

    }

    public static String getName(Plot plot, PlayerEnterPlotEvent event)
    {
        Set<UUID> uuid = plot.getOwners();
        UUID firstuuid = uuid.iterator().next();

        if (event.getPlayer().isOnline())
        {
            return ChatColor.GREEN + "Owned by " + ChatColor.GRAY + Bukkit.getPlayer(firstuuid).getName();
        } else {
            return ChatColor.GREEN + "Owned by " + ChatColor.GRAY + Bukkit.getOfflinePlayer(firstuuid).getName();
        }
    }

    public static void sendActionbar(Player p, String message) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', message) + "\"}");
        PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte)2);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(bar);
    }

}

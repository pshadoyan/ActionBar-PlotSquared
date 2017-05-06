package me.patrick.actionbar;

import com.intellectualcrafters.plot.object.Plot;
import com.plotsquared.bukkit.events.PlayerEnterPlotEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import static me.patrick.actionbar.Utilities.getName;

/**
 * Created by Patrick on 4/30/2017.
 */
public class actionBar implements Listener{

    public actionBar() {}

    @org.bukkit.event.EventHandler
    public void plotEnter(PlayerEnterPlotEvent event)
    {
        Player player = event.getPlayer();
        Plot plot = event.getPlot();

        if (plot.getOwners().toString().equals("[]"))
        {
            String noOwner = ChatColor.GREEN + "This plot is unclaimed";
            Utilities.sendActionbar(player, noOwner);
        } else {
            String name = getName(plot, event);

            Utilities.sendActionbar(player, name);

        }

    }

}

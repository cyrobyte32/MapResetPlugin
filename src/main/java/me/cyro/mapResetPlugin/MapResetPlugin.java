package me.cyro.mapResetPlugin;

import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public final class MapResetPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().log(new LogRecord(Level.ALL, "MapResetPlugin is starting..."));

        this.saveDefaultConfig();


        long delayTicks = 200L;    // How long to wait before starting the first time

        int seconds = getConfig().getInt("resetdelay");
        long periodTicks = seconds * 20L;

        getServer().getScheduler().runTaskTimer(this, () -> {
            int sx = this.getConfig().getInt("source.x");
            int sy = this.getConfig().getInt("source.y");
            int sz = this.getConfig().getInt("source.z");
            int sx2 = this.getConfig().getInt("source.x2");
            int sy2 = this.getConfig().getInt("source.y2");
            int sz2 = this.getConfig().getInt("source.z2");

            int tx = this.getConfig().getInt("target.x");
            int ty = this.getConfig().getInt("target.y");
            int tz = this.getConfig().getInt("target.z");
            int tx2 = this.getConfig().getInt("target.x2");
            int ty2 = this.getConfig().getInt("target.y2");
            int tz2 = this.getConfig().getInt("target.z2");

            World w = Bukkit.getWorld(this.getConfig().getString("world"));

            resetMap(sx, sy, sz, sx2, sy2, sz2, tx, ty, tz, tx2, ty2, tz2, w);
        }, delayTicks, periodTicks);




        getLogger().log(new LogRecord(Level.ALL, "MapResetPlugin has fully started."));

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equalsIgnoreCase("mineresetplugin")) {
            if (sender instanceof Player p) {
                if (p.isOp()) {

                    if (args.length == 0) {

                        p.sendMessage("§cNo argument specified.");
                        p.sendMessage("§c/mineresetplugin setupsource x y z x2 y2 z2");
                        p.sendMessage("§c/mineresetplugin setuptarget x y z x2 y2 z2");
                        p.sendMessage("§c/mineresetplugin resetconfig");
                        p.sendMessage("§c/mineresetplugin resetdelay (time in s)");
                        p.sendMessage("§c/mineresetplugin teleportPlayersToY y");
                        p.sendMessage("§c/mineresetplugin reset");
                        p.sendMessage("§c/mineresetplugin setworld");
                        p.sendMessage("§c/mineresetplugin setannouncement (chat/subtitle)");



                        return true;
                    }

                    if (args[0].equalsIgnoreCase("setupsource") && (args.length == 7)) {

                        // save source area to config
                        int sourceX = Integer.parseInt(args[1]);
                        int sourceY = Integer.parseInt(args[2]);
                        int sourceZ = Integer.parseInt(args[3]);

                        int sourceX2 = Integer.parseInt(args[4]);
                        int sourceY2 = Integer.parseInt(args[5]);
                        int sourceZ2 = Integer.parseInt(args[6]);


                        this.getConfig().set("source.x", sourceX);
                        this.getConfig().set("source.y", sourceY);
                        this.getConfig().set("source.z", sourceZ);

                        this.getConfig().set("source.x2", sourceX2);
                        this.getConfig().set("source.y2", sourceY2);
                        this.getConfig().set("source.z2", sourceZ2);

                        this.saveConfig();

                        p.sendMessage("§aSource successfully updated!");
                        p.sendMessage(String.format("§2Area: §ax%d y%d z%d §2to §ax%d y%d z%d", sourceX, sourceY, sourceZ, sourceX2, sourceY2, sourceZ2));

                    } else if (args[0].equalsIgnoreCase("setuptarget") && (args.length == 7)) {

                        // save target area to config
                        int targetX = Integer.parseInt(args[1]);
                        int targetY = Integer.parseInt(args[2]);
                        int targetZ = Integer.parseInt(args[3]);

                        int targetX2 = Integer.parseInt(args[4]);
                        int targetY2 = Integer.parseInt(args[5]);
                        int targetZ2 = Integer.parseInt(args[6]);


                        this.getConfig().set("target.x", targetX);
                        this.getConfig().set("target.y", targetY);
                        this.getConfig().set("target.z", targetZ);

                        this.getConfig().set("target.x2", targetX2);
                        this.getConfig().set("target.y2", targetY2);
                        this.getConfig().set("target.z2", targetZ2);

                        this.saveConfig();

                        p.sendMessage("§aTarget successfully updated!");
                        p.sendMessage(String.format("§2Area: §ax%d y%d z%d §2to §ax%d y%d z%d", targetX, targetY, targetZ, targetX2, targetY2, targetZ2));



                    } else if (args[0].equalsIgnoreCase("resetconfig")) {

                        // reset config
                        this.getConfig().set("source.x", "null");
                        this.getConfig().set("source.y", "null");
                        this.getConfig().set("source.z", "null");
                        this.getConfig().set("source.x2", "null");
                        this.getConfig().set("source.y2", "null");
                        this.getConfig().set("source.z2", "null");
                        this.getConfig().set("target.x", "null");
                        this.getConfig().set("target.y", "null");
                        this.getConfig().set("target.z", "null");
                        this.getConfig().set("target.x2", "null");
                        this.getConfig().set("target.y2", "null");
                        this.getConfig().set("target.z2", "null");
                        this.getConfig().set("playerteleportylevel", "null");
                        this.getConfig().set("resetdelay", "null");
                        this.saveConfig();



                    } else if (args[0].equalsIgnoreCase("resetdelay")) {
                        if(args.length == 2) {
                            this.getConfig().set("resetdelay", Integer.parseInt(args[1]));
                            this.saveConfig();
                            p.sendMessage("§aReset Delay updated to " +args[1]+ "s.");
                            p.sendMessage("§aFull Server Restart required.");
                        }

                    } else if (args[0].equalsIgnoreCase("teleportplayerstoy")) {
                        if(args.length == 2) {
                            this.getConfig().set("playerteleportylevel", args[1]);
                            this.saveConfig();
                            p.sendMessage("§aPlayers get teleported to y level " +args[1]+ " every reset.");
                        }


                    } else if (args[0].equalsIgnoreCase("reset")) {

                        if(!(this.getConfig().getString("source.x").equalsIgnoreCase("null"))) {
                            p.sendMessage("§aManually resetting the area...");


                            int sx = this.getConfig().getInt("source.x");
                            int sy = this.getConfig().getInt("source.y");
                            int sz = this.getConfig().getInt("source.z");
                            int sx2 = this.getConfig().getInt("source.x2");
                            int sy2 = this.getConfig().getInt("source.y2");
                            int sz2 = this.getConfig().getInt("source.z2");

                            int tx = this.getConfig().getInt("target.x");
                            int ty = this.getConfig().getInt("target.y");
                            int tz = this.getConfig().getInt("target.z");
                            int tx2 = this.getConfig().getInt("target.x2");
                            int ty2 = this.getConfig().getInt("target.y2");
                            int tz2 = this.getConfig().getInt("target.z2");

                            World w = Bukkit.getWorld(this.getConfig().getString("world"));

                            resetMap(sx, sy, sz, sx2, sy2, sz2, tx, ty, tz, tx2, ty2, tz2, w);
                            p.sendMessage("§aMap has successfully been reset.");
                        }

                    } else if (args[0].equalsIgnoreCase("setworld")) {

                        this.getConfig().set("world", p.getWorld().getName());
                        p.sendMessage("§aworld updated");
                        this.saveConfig();

                    } else if (args[0].equalsIgnoreCase("setannouncement")) {

                        this.getConfig().set("announcereset", args[1]);
                        p.sendMessage("§aannouncements set to "+ args[1]);
                        this.saveConfig();

                    } else {
                        p.sendMessage("§cArguments are not valid. try /mapresetplugin");
                    }




                } else {
                    p.sendMessage("§cMRP: You must have vanilla operator to run a command.");
                }
            }
        }


        this.saveConfig();
        return true;
    }

    private void resetMap(int sx, int sy, int sz, int sx2, int sy2, int sz2, int tx, int ty, int tz, int tx2, int ty2, int tz2, World w) {
        for (int offX = 0; offX <= sx2-sx; offX++) {
            for (int offY = 0; offY <= sy2-sy; offY++) {
                for (int offZ = 0; offZ <= sz2-sz; offZ++) {

                    // offset = currX, currY, currZ.
                    // copying target from source
                    Block sourceBlock = w.getBlockAt(new Location(w, sx+offX, sy+offY, sz+offZ));
                    Block targetBlock = w.getBlockAt(new Location(w, tx+offX, ty+offY, tz+offZ));


                    targetBlock.setType(sourceBlock.getType());
                    targetBlock.setBlockData(sourceBlock.getBlockData());


                }
            }
        }

        if(this.getConfig().getString("announcereset").equalsIgnoreCase("subtitle")) {
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.sendTitle("", "§7§oThe Map has been reset.");
            }
        } else if(this.getConfig().getString("announcereset").equalsIgnoreCase("chat")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendMessage("§7The Map has been reset.");
            }
        }

    }

    @Override
    public void onDisable() {
        getLogger().log(new LogRecord(Level.ALL, "MapResetPlugin shutting down."));

    }
}

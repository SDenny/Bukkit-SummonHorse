package com.minecraftport.summonHorse;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.logging.Logger;


public class main extends JavaPlugin {
    public final Logger log = Logger.getLogger("Minecraft");

    @Override
    public void onDisable() {
        PluginDescriptionFile descFile = this.getDescription();
        this.log.info(ChatColor.GREEN + descFile.getName() + " v." + descFile.getVersion() + " has been disabled!");
    }

    @Override
    public void onEnable() {
        PluginDescriptionFile descFile = this.getDescription();
        this.log.info(ChatColor.GREEN + descFile.getName() + " v." + descFile.getVersion() + " has been enabled!");
        getServer().getPluginManager().registerEvents(new HorseDismountListener(), this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
        Player player = (Player)sender;


        if(commandLabel.equalsIgnoreCase("horse") && player.getVehicle() == null && player.hasPermission(".summonhorse") && !player.isFlying()){
            Location location = player.getLocation();
            player.playEffect(location, Effect.MOBSPAWNER_FLAMES, 5);
            Horse horse = (Horse) player.getWorld().spawnEntity(location, EntityType.HORSE);
            horse.setCustomName(player.getName() + "'s Skelehorse");
            horse.setVariant(Horse.Variant.SKELETON_HORSE);
            horse.setAdult();
            horse.setJumpStrength(1);
            horse.setMaxHealth(2);
            horse.setTamed(true);
            horse.setOwner(player);
            horse.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 18000, 100));
            horse.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 18000, 100));
            horse.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 18000, 100));
            horse.getInventory().setSaddle(new ItemStack(Material.SADDLE, 1));
            horse.getInventory().setArmor(new ItemStack(Material.SKULL_ITEM, 1));
            horse.setPassenger(player);
            player.sendMessage(ChatColor.BLUE + "Your horse has been summoned!");
        }else if(player.getVehicle() != null){
            player.sendMessage(ChatColor.BLUE + "You seem to already be riding something!");
        }else if(!player.hasPermission(".summonhorse")){
            player.sendMessage(ChatColor.BLUE + "You do not have permission for that!");
        }else if(player.isFlying()){
            player.sendMessage(ChatColor.BLUE + "You are flying! Get on the ground to summon the horse!");
        }else{
            return false;
        }


        return false;
    }

}

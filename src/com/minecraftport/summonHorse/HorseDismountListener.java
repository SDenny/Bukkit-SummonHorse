package com.minecraftport.summonHorse;

import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.HorseInventory;


public class HorseDismountListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void OnVehicleExit (VehicleExitEvent event){
        Horse horse = (Horse)event.getVehicle();
        if(horse.getCustomName().endsWith("'s Skelehorse") && horse.getVariant() == Horse.Variant.SKELETON_HORSE) {
            horse.remove();
        }
    }


    @EventHandler
    public void onVehicleEnter(VehicleEnterEvent event) {
        Player player = (Player) event.getEntered();
        if (player.getVehicle() instanceof Horse) {
            player.getVehicle().remove();
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory() instanceof HorseInventory && event.getSlotType() != InventoryType.SlotType.QUICKBAR && event.getSlot() < 9) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.HORSE && ((LivingEntity) event.getEntity()).getCustomName() != null && ((LivingEntity) event.getEntity()).getCustomName().endsWith("'s Shadowmere")) {
            event.setCancelled(true);
        }
        if (event.getEntityType() == EntityType.PLAYER && event.getEntity().getVehicle() != null) {
            Damageable p = (Damageable) event.getEntity();
            if (event.getDamage() >= p.getHealth()) {
                Horse h = (Horse) event.getEntity().getVehicle();
                h.remove();
            }
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if(event.getEntityType() == EntityType.HORSE) {
            event.getDrops().clear();
            event.setDroppedExp(0);
        }
    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        if ((event.getEntity().getItemStack().getType() == Material.SADDLE || event.getEntity().getItemStack().getType() == Material.IRON_BARDING || event.getEntity().getItemStack().getType() == Material.GOLD_BARDING || event.getEntity().getItemStack().getType() == Material.DIAMOND_BARDING)) {
            if (event.getEntity().getVelocity().getY() == 0.20000000298023224) {
                event.setCancelled(true);
            }
        }
    }



}

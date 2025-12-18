package Bandages;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Main extends JavaPlugin implements Listener {
    
    @Override
    public void onEnable(){
        getLogger().info("Chronicle Plugins have been enabled");
        getServer().getPluginManager().registerEvents(this, this);
    }
 
    @Override
    public void onDisable(){
        getLogger().info("Chronicle Plugins have been disabled");
    }
    
    @EventHandler
    public void onPlayerShear(PlayerShearEntityEvent event) {
        Entity sheared=event.getEntity();
        if (sheared instanceof Player) {
            if ("dadrewster569".equals(((Player)sheared).getName())) {
                ItemSpawnEvent ev=new ItemSpawnEvent((Item)new ItemStack(Material.WOOL, 3), sheared.getLocation());
                ((Player)sheared).damage(5);
            }
        }
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        Entity interacted=event.getRightClicked();
        if (interacted instanceof Player) {
            if(event.getPlayer().getItemInHand().getType()==Material.WOOL) {
                Player healed=(Player)interacted;
                event.getPlayer().sendMessage("You have bandaged "+healed.getDisplayName());
                healed.sendMessage(event.getPlayer().getDisplayName()+" has bandaged you.");
                healed.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 2));
                int woolam=event.getPlayer().getItemInHand().getAmount();
                if (woolam>1) {event.getPlayer().getItemInHand().setAmount(woolam-1);}
                else if (woolam==1) {event.getPlayer().getInventory().remove(event.getPlayer().getItemInHand());}
            }
        }
    }
}


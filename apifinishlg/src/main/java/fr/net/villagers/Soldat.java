package fr.net.villagers;

import io.github.ph1lou.werewolfapi.GetWereWolfAPI;
import io.github.ph1lou.werewolfapi.PlayerWW;
import io.github.ph1lou.werewolfapi.WereWolfAPI;
import io.github.ph1lou.werewolfapi.enumlg.State;
import io.github.ph1lou.werewolfapi.events.DayEvent;
import io.github.ph1lou.werewolfapi.events.EnchantmentEvent;
import io.github.ph1lou.werewolfapi.events.NightEvent;
import io.github.ph1lou.werewolfapi.rolesattributs.RolesVillage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class Soldat extends RolesVillage {
    public Soldat(GetWereWolfAPI main, WereWolfAPI game, UUID uuid) {
        super(main, game, uuid);
    }

    @EventHandler
    public void onNight(NightEvent event) {
        if (!((PlayerWW)this.game.getPlayersWW().get(getPlayerUUID())).isState(State.ALIVE)) {
            return;
        }

        if (Bukkit.getPlayer(getPlayerUUID()) == null) {
            return;
        }

        Player player = Bukkit.getPlayer(getPlayerUUID());
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 2147483647, 0, false, false));
    }

    @EventHandler
    public void onDay(DayEvent event) {
        if (!((PlayerWW)this.game.getPlayersWW().get(getPlayerUUID())).isState(State.ALIVE)) {
            return;
        }

        if (Bukkit.getPlayer(getPlayerUUID()) == null) {
            return;
        }

        Player player = Bukkit.getPlayer(getPlayerUUID());
        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
    }

    @EventHandler
    public void enchante(EnchantmentEvent event){
        if(!event.getPlayerUUID().equals(getPlayerUUID())) return;

        ItemStack item = event.getItem();

        if(event.getEnchants().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL)){

            if (item.getType().equals(Material.DIAMOND_BOOTS) || item.getType().equals(Material.DIAMOND_LEGGINGS) ||  item.getType().equals(Material.DIAMOND_HELMET) ||  item.getType().equals(Material.DIAMOND_CHESTPLATE)){
                event.getFinalEnchants().put(Enchantment.PROTECTION_ENVIRONMENTAL,Math.min(event.getEnchants().get(Enchantment.PROTECTION_ENVIRONMENTAL),game.getConfig().getLimitProtectionDiamond()+1));
            }
            else {
                event.getFinalEnchants().put(Enchantment.PROTECTION_ENVIRONMENTAL,Math.min(event.getEnchants().get(Enchantment.PROTECTION_ENVIRONMENTAL),game.getConfig().getLimitProtectionIron()+1));
            }
        }
    }
    public String getDescription() {
        return this.game.translate("werewolf.role.soldat.description", new Object[1]);
    }

    public String getDisplay() {
        return "werewolf.role.soldat.display";
    }

    public boolean isDisplay(String s) {
        return s.equals(getDisplay());
    }
}
package fr.net.werewolf;

import io.github.ph1lou.werewolfapi.GetWereWolfAPI;
import io.github.ph1lou.werewolfapi.WereWolfAPI;
import io.github.ph1lou.werewolfapi.enumlg.State;
import io.github.ph1lou.werewolfapi.enumlg.TimerLG;
import io.github.ph1lou.werewolfapi.events.NightEvent;
import io.github.ph1lou.werewolfapi.rolesattributs.RolesWereWolf;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


import java.util.UUID;

public class NewWereWolf extends RolesWereWolf {
    public NewWereWolf(GetWereWolfAPI main, WereWolfAPI game, UUID uuid)  {
        super(main, game, uuid);
    }

    @EventHandler
    public void onNight(NightEvent event) {
        if(!game.getPlayersWW().get(getPlayerUUID()).isState(State.ALIVE)) return;
        if(Bukkit.getPlayer(getPlayerUUID()) == null) return;
        Player p = Bukkit.getPlayer(getPlayerUUID());
        if(!p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)){
                p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, game.getConfig().getTimerValues().get(TimerLG.DAY_DURATION)*20, 0, false, false));
        }
    }

        @Override
        public String getDescription() {
            return game.translate("werewolf.role.new_werewolf.display");
        }

        @Override
        public String getDisplay() {
            return "werewolf.role.new_werewolf.display";
        }

}

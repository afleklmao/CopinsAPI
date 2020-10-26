package fr.net.neutres;

import io.github.ph1lou.werewolfapi.GetWereWolfAPI;
import io.github.ph1lou.werewolfapi.PlayerWW;
import io.github.ph1lou.werewolfapi.WereWolfAPI;
import io.github.ph1lou.werewolfapi.enumlg.Camp;
import io.github.ph1lou.werewolfapi.enumlg.State;
import io.github.ph1lou.werewolfapi.enumlg.TimerLG;
import io.github.ph1lou.werewolfapi.events.FinalDeathEvent;
import io.github.ph1lou.werewolfapi.rolesattributs.RolesNeutral;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class Cannibale extends RolesNeutral {

    private String s;

    public Cannibale(GetWereWolfAPI main, WereWolfAPI game, UUID uuid) {
        super(main, game, uuid);
    }

    @EventHandler
    public void deadD(FinalDeathEvent e) {
        if (!game.getPlayersWW().get(getPlayerUUID()).isState(State.ALIVE)) return;
        if (Bukkit.getPlayer(getPlayerUUID()) == null) return;
        UUID killer = e.getUuid();
        if (game.getPlayersWW().get(killer).getLastKiller().equals(getPlayerUUID())) {
            PlayerWW  deadPlayer = game.getPlayersWW().get(killer);
            if(deadPlayer.getLastKiller()== null ) return;
             Player p = Bukkit.getPlayer(deadPlayer.getLastKiller());
            if (deadPlayer.getRole().getCamp().equals(Camp.NEUTRAL)) {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, game.getConfig().getTimerValues().get(TimerLG.DAY_DURATION)*20, 0, false, false));
                {

                }
                if (deadPlayer.getRole().getCamp().equals(Camp.WEREWOLF))
                    p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, game.getConfig().getTimerValues().get(TimerLG.DAY_DURATION)*20, -1, false, false));

            }
            if (deadPlayer.getRole().getCamp().equals(Camp.VILLAGER))
                p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,game.getConfig().getTimerValues().get(TimerLG.DAY_DURATION)*20 , 0, false, false));

        }
    }


        public String getDescription() {
            return this.game.translate("werewolf.role.cannibale.description", new Object[0]);
        }

        public String getDisplay() {
            return "werewolf.role.cannibale.display";

        }

    }


package fr.net.werewolf;

import fr.net.Game.PlayerLG;
import fr.net.Main;
import fr.net.commands.CommandLgA;
import io.github.ph1lou.werewolfapi.GetWereWolfAPI;
import io.github.ph1lou.werewolfapi.PlayerWW;
import io.github.ph1lou.werewolfapi.WereWolfAPI;
import io.github.ph1lou.werewolfapi.enumlg.Camp;
import io.github.ph1lou.werewolfapi.enumlg.Day;
import io.github.ph1lou.werewolfapi.enumlg.State;
import io.github.ph1lou.werewolfapi.enumlg.TimerLG;
import io.github.ph1lou.werewolfapi.events.*;
import io.github.ph1lou.werewolfapi.rolesattributs.Power;
import io.github.ph1lou.werewolfapi.rolesattributs.Roles;
import io.github.ph1lou.werewolfapi.rolesattributs.RolesWereWolf;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LoupGarouAlpha extends RolesWereWolf implements Power {

    private Boolean power = true;

    public LoupGarouAlpha(GetWereWolfAPI main, WereWolfAPI game, UUID uuid) {
        super(main, game, uuid);
    }

    @Override
    public String getDescription() {
        return game.translate("werewolf.role.werewolf_alpha.description");
    }



    @Override
    public String getDisplay() {
        return "werewolf.role.werewolf_alpha.display";
    }

    @EventHandler
    public void onNight(NightEvent e){
        if(getPlayerUUID() == null)return;
        if(!game.getPlayersWW().get(getPlayerUUID()).isState(State.ALIVE))return;
        Player p = Bukkit.getPlayer(getPlayerUUID());
        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, game.getConfig().getTimerValues().get(TimerLG.DAY_DURATION)*20, 0, false, false));
    }
    @EventHandler
    public void onCheck(UpdateEvent e){
        if(getPlayerUUID() == null)return;
        if(!game.getPlayersWW().get(getPlayerUUID()).isState(State.ALIVE))return;
        if(game.isDay(Day.DAY))return;
        Player p = Bukkit.getPlayer(getPlayerUUID());
        if(p.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
            p.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
        }
    }

    @Override
    public void setPower(Boolean power) {
        this.power = power;
    }

    @Override
    public Boolean hasPower() {
        return power;
    }

    @EventHandler
    public void onDead(FinalDeathEvent e) {
        if (e.getUuid() != getPlayerUUID()) return;
        if (CommandLgA.s == null) return;
        Player p = Bukkit.getPlayer(CommandLgA.s);
        if (!game.getPlayersWW().get(p.getUniqueId()).isState(State.ALIVE)) return;
        PlayerWW playerWW = game.getPlayersWW().get(p.getUniqueId());
        if(game.getPlayersWW().containsKey(p.getUniqueId())){
            game.getPlayersWW().remove(p.getUniqueId());
            String roleLG = playerWW.getRole().getDisplay();
            game.getConfig().getRoleCount().put(roleLG, game.getConfig().getRoleCount().get(roleLG) - 1);
        }
                PlayerWW plg = new PlayerLG(main, game, p);
                game.getPlayersWW().put(p.getUniqueId(),plg);
                plg.setRole(new NewWereWolf(main,game,p.getUniqueId()));
                String roleLG2 = plg.getRole().getDisplay();
                game.getConfig().getRoleCount().put(roleLG2, game.getConfig().getRoleCount().get(roleLG2) + 1);
                Bukkit.getPluginManager().callEvent((Event) new NewWereWolfEvent(p.getUniqueId()));

    }
}


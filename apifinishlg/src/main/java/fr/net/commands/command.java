package fr.net.commands;

import fr.net.villagers.Spy;
import io.github.ph1lou.werewolfapi.Commands;
import io.github.ph1lou.werewolfapi.GetWereWolfAPI;
import io.github.ph1lou.werewolfapi.PlayerWW;
import io.github.ph1lou.werewolfapi.WereWolfAPI;
import io.github.ph1lou.werewolfapi.enumlg.State;
import io.github.ph1lou.werewolfapi.enumlg.StateLG;
import io.github.ph1lou.werewolfapi.enumlg.TimerLG;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class command implements Commands {

    GetWereWolfAPI api;
    public command(GetWereWolfAPI ww) {
        this.api = ww;
    }

        @Override
        public void execute(CommandSender sender, String[] args) {

            WereWolfAPI game = api.getWereWolfAPI();

            if (!(sender instanceof Player)) {
                sender.sendMessage(game.translate("werewolf.check.console"));
                return;
            }


            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();

            if(!game.getPlayersWW().containsKey(uuid)) {
                player.sendMessage(game.translate("werewolf.check.not_in_game"));
                return;
            }

            PlayerWW plg = game.getPlayersWW().get(uuid);


            if (!game.isState(StateLG.GAME)) {
                player.sendMessage(game.translate("werewolf.check.game_not_in_progress"));
                return;
            }

            if (!plg.isState(State.ALIVE)) {
                player.sendMessage(game.translate("werewolf.check.death"));
                return;
            }
            if(plg.getRole().isDisplay("werewolf.role.spy.display")){
                player.sendMessage(game.translate("werewolf.role.spy.is_spy"));
                return;
            }
            if (plg.getRole() instanceof Spy) {
                player.sendMessage(game.translate("werewolf.role.spy.is_spy"));
                return;
            }
            if (!plg.getRole().isWereWolf()) {
                sender.sendMessage(game.translate("werewolf.role.werewolf.not_werewolf"));
                return;
            }
            if (game.getConfig().getTimerValues().get(TimerLG.WEREWOLF_LIST) > 0) {
                sender.sendMessage(game.translate("werewolf.role.werewolf.list_not_revealed"));
                return;
            }

            StringBuilder list = new StringBuilder();

            for (UUID playerUUID : game.getPlayersWW().keySet()) {

                PlayerWW lg = game.getPlayersWW().get(playerUUID);

                if (lg.isState(State.ALIVE) && lg.getRole().isWereWolf()) {
                    list.append(lg.getName()).append(" ");
                }
            }
            player.sendMessage(game.translate("werewolf.role.werewolf.werewolf_list", list.toString()));
        }
    }


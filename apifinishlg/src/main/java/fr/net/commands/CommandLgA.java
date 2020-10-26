package fr.net.commands;

import fr.net.werewolf.LoupGarouAlpha;
import io.github.ph1lou.werewolfapi.Commands;
import io.github.ph1lou.werewolfapi.GetWereWolfAPI;
import io.github.ph1lou.werewolfapi.PlayerWW;
import io.github.ph1lou.werewolfapi.WereWolfAPI;
import io.github.ph1lou.werewolfapi.enumlg.State;
import io.github.ph1lou.werewolfapi.enumlg.StateLG;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class CommandLgA implements Commands {
    GetWereWolfAPI api;
    public static String s;

    public CommandLgA(GetWereWolfAPI ww) {
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

        if (!game.getPlayersWW().containsKey(uuid)) {

            player.sendMessage(game.translate("werewolf.check.not_in_game"));

            return;

        }

        PlayerWW plg = game.getPlayersWW().get(uuid);


        if (!game.isState(StateLG.GAME)) {

            player.sendMessage(game.translate("werewolf.check.game_not_in_progress"));

            return;

        }


        if (!(plg.getRole() instanceof LoupGarouAlpha)) {

            player.sendMessage(game.translate("werewolf.check.role", game.translate("werewolf.role.werewolf_alpha.display")));

            return;

        }

        LoupGarouAlpha loupgaroualpha = (LoupGarouAlpha) plg.getRole();

        if (!plg.isState(State.ALIVE)) {
            player.sendMessage(game.translate("werewolf.check.death"));
            return;

        }

        if (!loupgaroualpha.hasPower()) {
            player.sendMessage(game.translate("werewolf.check.power"));
            return;
        }

        if (args.length!=1) {
            player.sendMessage(game.translate("werewolf.check.parameters",2));
            return ;
        }
        if(Bukkit.getPlayer(args[0])==null){
            player.sendMessage(game.translate("werewolf.check.offline_player"));
            return;
        }
        Player enchanted = Bukkit.getPlayer(args[0]);
        UUID playerUUID = enchanted.getUniqueId();

        if(!game.getPlayersWW().containsKey(playerUUID) || game.getPlayersWW().get(playerUUID).isState(State.DEATH)){
            player.sendMessage(game.translate("werewolf.check.player_not_found"));
            return ;
        }

        if(!game.getPlayersWW().containsKey(playerUUID)){
            player.sendMessage(game.translate("werewolf.check.not_in_game_player"));
            return ;
        }
        s = enchanted.getName();

        player.sendMessage(game.translate("werewolf.role.werewolf_alpha.perform", enchanted.getName()));
        loupgaroualpha.setPower(false);
    }
}

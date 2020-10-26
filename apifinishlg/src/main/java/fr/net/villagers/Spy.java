package fr.net.villagers;

import io.github.ph1lou.werewolfapi.GetWereWolfAPI;
import io.github.ph1lou.werewolfapi.WereWolfAPI;
import io.github.ph1lou.werewolfapi.enumlg.Camp;
import io.github.ph1lou.werewolfapi.rolesattributs.RolesVillage;

import java.util.UUID;

public class Spy extends RolesVillage {

    public Spy(GetWereWolfAPI main, WereWolfAPI game, UUID uuid) {
        super(main, game, uuid);
    }

    @Override
    public boolean isWereWolf() {
        return true;
    }

    @Override
    public boolean isNeutral() {
        return true;
    }

    @Override
    public Camp getCamp() { return Camp.VILLAGER; }

        @Override
        public String getDescription () { return this.game.translate("werewolf.role.spy.description"); }


        @Override
        public String getDisplay () { return "werewolf.role.spy.display"; }
        }





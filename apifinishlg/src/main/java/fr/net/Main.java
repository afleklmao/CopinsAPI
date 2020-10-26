package fr.net;

import fr.net.commands.CommandLgA;
import fr.net.commands.command;
import fr.net.villagers.Soldat;
import fr.net.villagers.Spy;
import fr.net.werewolf.LoupGarouAlpha;
import fr.net.werewolf.NewWereWolf;
import io.github.ph1lou.werewolfapi.GetWereWolfAPI;
import io.github.ph1lou.werewolfapi.RoleRegister;
import io.github.ph1lou.werewolfapi.enumlg.Category;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;

public class Main extends JavaPlugin {

    public static Main instance;
    public static Main getInstance(){ return instance; }

    @Override
    public void onEnable() {
        instance = this;
        GetWereWolfAPI ww = (GetWereWolfAPI) Bukkit.getPluginManager().getPlugin("WereWolfPlugin");
        ww.loadTranslation(this,"fr");
        ww.getListCommands().remove(ww.getExtraTexts().get("werewolf.role.werewolf.command"));
        ww.getListCommands().put(ww.getExtraTexts().get("werewolf.role.werewolf.command"),new command(ww));
        ww.getListCommands().put(ww.getExtraTexts().get("werewolf.role.werewolf_alpha.command"),new CommandLgA(ww));
        ww.getAddonsList().add(this);

        try {

            new RoleRegister(this,ww,"werewolf.role.new_werewolf.display").registerRole(NewWereWolf.class).create();

            RoleRegister cannibale = new RoleRegister(this,ww,"werewolf.role.cannibale.display").registerRole(fr.net.neutres.Cannibale.class);
            cannibale.setLore(Collections.singletonList("desc")).addCategory(Category.ADDONS).addCategory(Category.NEUTRAL).create();

            RoleRegister soldat = new RoleRegister(this,ww,"werewolf.role.soldat.display").registerRole(Soldat.class);
            soldat.setLore(Collections.singletonList("desc")).addCategory(Category.ADDONS).addCategory(Category.VILLAGER).create();

            RoleRegister spy = new RoleRegister(this,ww,"werewolf.role.spy.display").registerRole(Spy.class);
            spy.setLore(Collections.singletonList("desc")).addCategory(Category.ADDONS).addCategory(Category.VILLAGER).create();

            RoleRegister loupgaroualpha = new RoleRegister(this,ww,"werewolf.role.werewolf_alpha.display").registerRole(LoupGarouAlpha.class);
            loupgaroualpha.setLore(Collections.singletonList("desc")).addCategory(Category.ADDONS).addCategory(Category.WEREWOLF).create();

            RoleRegister prêtre = new RoleRegister(this,ww,"werewolf.role.prêtre.display").registerRole(Prêtre.class);
            prêtre.setLore(Collections.singletonList("desc")).addCategory(Category.ADDONS).addCategory(Category.VILLAGER).create();

        }catch (NoSuchMethodException e) {
            e.printStackTrace();
        }


    }


}






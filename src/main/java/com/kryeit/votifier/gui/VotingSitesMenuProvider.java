package com.kryeit.votifier.gui;
import com.kryeit.votifier.config.ConfigReader;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

import static com.kryeit.votifier.config.GuiConfigReader.MENU_SLOTS;


public class VotingSitesMenuProvider implements MenuProvider {

    @Override
    public Component getDisplayName() {
        return Component.literal(ConfigReader.GUI_TITLE);
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return CustomChestMenu.threeRows(containerId, playerInventory, new VotingSitesSimpleContainer(27, MENU_SLOTS));
    }

    public static void open(ServerPlayer player) {
        player.openMenu(new VotingSitesMenuProvider());
    }

}
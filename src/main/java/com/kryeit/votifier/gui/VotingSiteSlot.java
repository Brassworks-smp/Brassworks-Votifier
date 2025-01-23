package com.kryeit.votifier.gui;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record VotingSiteSlot
        (int slot, ItemStack item, String name, List<Component> lore,
         String link, boolean enchanted) {
}

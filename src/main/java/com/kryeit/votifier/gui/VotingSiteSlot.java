package com.kryeit.votifier.gui;

import net.minecraft.world.item.ItemStack;

public record VotingSiteSlot
        (int slot, ItemStack item, String name, String lore,
         String link, boolean enchanted) {
}

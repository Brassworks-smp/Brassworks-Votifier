package com.kryeit.votifier.gui;

import com.kryeit.votifier.MinecraftServerSupplier;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.List;

import static com.kryeit.votifier.config.GuiConfigReader.MENU_SLOTS;

public class GuiUtils {

    public static VotingSiteSlot getVotingSiteSlot(int slot) {
        List<VotingSiteSlot> slots = MENU_SLOTS;

        for (VotingSiteSlot vosingSlot : slots) {
            if (vosingSlot.slot() == slot) {
                return vosingSlot;
            }
        }

        return null;
    }

    public static MutableComponent getFormatted(String text) {
        String formattedText = text.replace('&', '§');
        return Component.literal(formattedText);
    }

    public static void executeCommandAsServer(String command) {
        // Create a command source that represents the server
        CommandSourceStack source = MinecraftServerSupplier.getServer().createCommandSourceStack();

        // Parse the command
        ParseResults<CommandSourceStack> parseResults = MinecraftServerSupplier.getServer().getCommands().getDispatcher().parse(new StringReader(command), source);

        // Execute the command
        MinecraftServerSupplier.getServer().getCommands().performCommand(parseResults, command);
    }
}

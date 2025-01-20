package com.kryeit.votifier.command;

import com.kryeit.votifier.gui.VotingSitesMenuProvider;
import com.kryeit.votifier.model.VotifierEvent;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class Vote {
    public static int execute(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();
        ServerPlayer player = source.getPlayer();

        if (player == null) {
            source.sendSystemMessage(Component.literal("Can't execute from console"));
            return 0;
        }

        VotingSitesMenuProvider.open(player);
        return Command.SINGLE_SUCCESS;
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("vote")
                .executes(Vote::execute)
        );
    }
}

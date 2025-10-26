/*
 * Copyright (C) 2011 Vex Software LLC
 * This file is part of Votifier.
 *
 * Votifier is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Votifier is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Votifier.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.brassworks.votifier.model.listeners;

import com.brassworks.votifier.Votifier;
import com.brassworks.votifier.model.VotifierEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

import static com.brassworks.votifier.Votifier.LOGGER;
import com.brassworks.votifier.config.ConfigReader;

import net.swzo.brassworkscore.util.vote.VoteRewardHandler;

/**
 * A basic vote listener for demonstration purposes.
 *
 * @author Blake Beaupain
 */
@EventBusSubscriber(modid = Votifier.MODID)
public class BasicVoteListener {

    @SubscribeEvent
    public static void onVoteReceived(VotifierEvent event) {
        LOGGER.info("Received: " + event.getVote());

        String SITE1 = ConfigReader.SITE1;
        String SITE2 = ConfigReader.SITE2;
        String SITE3 = ConfigReader.SITE3;
        String service = event.getVote().getServiceName().toLowerCase();

        int siteId;
        if (service.equalsIgnoreCase(SITE1)) {
            siteId = 1;
        } else if (service.equalsIgnoreCase(SITE2)) {
            siteId = 2;
        } else if (service.equalsIgnoreCase(SITE3)) {
            siteId = 3;
        } else {
            siteId = 0;
        }

        MinecraftServer server = Votifier.getInstance().getServer();
        if (server == null) {
            Votifier.LOGGER.warn("Server instance not available yet.");
            return;
        }

        String playerName = event.getVote().getUsername();
        UUID playerUUID = null;

        ServerPlayer player = server.getPlayerList().getPlayerByName(playerName);
        if (player == null) {
            Votifier.LOGGER.warn("No player found on the server.");
            return;
        }
        playerUUID = player.getUUID();

        LOGGER.info("Player UUID: " + playerUUID + " | Site ID: " + siteId);

        VoteRewardHandler.onPlayerVote(server, playerUUID, siteId);
    }
}
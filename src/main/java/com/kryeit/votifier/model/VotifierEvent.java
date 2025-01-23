package com.kryeit.votifier.model;

import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;

public class VotifierEvent extends Event {
	private final Vote vote;

	public VotifierEvent(Vote vote) {
		this.vote = vote;
	}

	public Vote getVote() {
		return vote;
	}

	public static void post(Vote vote) {
		NeoForge.EVENT_BUS.post(new VotifierEvent(vote));
	}
}
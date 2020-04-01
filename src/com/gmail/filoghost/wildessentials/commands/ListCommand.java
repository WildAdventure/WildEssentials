/*
 * Copyright (c) 2020, Wild Adventure
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 * 4. Redistribution of this software in source or binary forms shall be free
 *    of all charges or fees to the recipient of this software.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.gmail.filoghost.wildessentials.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.filoghost.wildessentials.WildEssentials;

import wild.api.WildCommons;
import wild.api.bridges.PexBridge;
import wild.api.bridges.PexBridge.PrefixSuffix;
import wild.api.command.CommandFramework;
import wild.api.command.CommandFramework.Permission;

@Permission("wildessentials.list")
public class ListCommand extends CommandFramework {

	public ListCommand() {
		super(WildEssentials.plugin, "list");
	}

	@Override
	public void execute(CommandSender sender, String label, String[] args) {
		Collection<? extends Player> players = Bukkit.getOnlinePlayers();
		List<String> formattedPlayers = new ArrayList<String>();

		for (Player player : players) {
			PrefixSuffix prefixSuffix = PexBridge.getCachedPrefixSuffix(player);
			formattedPlayers.add("§f" + WildCommons.color(prefixSuffix.getPrefix()) + player.getDisplayName() + WildCommons.color(prefixSuffix.getSuffix()) + "§f");
		}

		sender.sendMessage("§9Ci sono §c" + players.size() + " / " + Bukkit.getMaxPlayers() + " §9giocatori online:");
		sender.sendMessage(StringUtils.join(formattedPlayers, ", "));
	}
}

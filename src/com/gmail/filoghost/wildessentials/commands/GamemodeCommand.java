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

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import wild.api.command.CommandFramework;
import wild.api.command.CommandFramework.Permission;

import com.gmail.filoghost.wildessentials.WildEssentials;

@Permission("wildessentials.gamemode")
public class GamemodeCommand extends CommandFramework {
	
	public GamemodeCommand() {
		super(WildEssentials.plugin, "gamemode");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String label, String[] args) {
		Player target = null;
		
		CommandValidate.minLength(args, 1, "Utilizzo comando: /" + label + " <modalità> [giocatore]");
		
		if (args.length > 1) {
			target = Bukkit.getPlayerExact(args[1]);
			CommandValidate.notNull(target, "Giocatore \"" + args[1] + "\" non trovato.");
			
		} else {
			target = CommandValidate.getPlayerSender(sender);
		}
		
		GameMode mode = null;
		for (GameMode m : GameMode.values()) {
			if (m.toString().equalsIgnoreCase(args[0])) {
				mode = m;
				break;
			}
		}
		
		if (mode == null) {
			try {
				int modeNumber = Integer.parseInt(args[0]);
				mode = GameMode.getByValue(modeNumber);
			} catch (NumberFormatException e) {	}
		}
		
		CommandValidate.notNull(mode, "Modalità \"" + args[0] + "\" non trovata.");
		
		if (sender instanceof Player && sender != target) {
			CommandValidate.isTrue(sender.hasPermission("wildessentials.gamemode.others"), "Non hai il permesso per impostare la gamemode altrui.");
		}
		
		target.setGameMode(mode);
		sender.sendMessage(ChatColor.GRAY + "Impostata modalità " + mode.toString().toLowerCase() + " per " + target.getName() + ".");
	}

}

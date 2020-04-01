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
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import wild.api.command.CommandFramework;
import wild.api.command.CommandFramework.Permission;

import com.gmail.filoghost.wildessentials.WildEssentials;

@Permission("wildessentials.teleport")
public class TeleportCommand extends CommandFramework {
	
	public TeleportCommand() {
		super(WildEssentials.plugin, "teleport");
	}

	@Override
	public void execute(CommandSender sender, String label, String[] args) {
		
		Player s = CommandValidate.getPlayerSender(sender);
		CommandValidate.isTrue(args.length > 0, "Utilizzo comando: /" + label + " <giocatore> [giocatore]");
		
		if (args.length > 1) {
			CommandValidate.isTrue(sender.hasPermission("wildessentials.teleport.others"), "Non hai il permesso per teletrasportare altri giocatori.");
			
			Player targetFrom = Bukkit.getPlayer(args[0]);
			CommandValidate.notNull(targetFrom, "Giocatore 1 non trovato.");
			
			Player targetTo = Bukkit.getPlayer(args[1]);
			CommandValidate.notNull(targetTo, "Giocatore 2 non trovato.");
			
			targetFrom.teleport(targetTo);
			s.sendMessage("§6Hai teletrasportato " + targetFrom.getName() + " da " + targetTo.getName());
			
		} else {
		
			Player target = Bukkit.getPlayer(args[0]);
			CommandValidate.notNull(target, "Giocatore non trovato.");
			s.teleport(target);
			s.sendMessage("§6Teletrasportato da " + target.getName() + ".");
			
		}
	}

}

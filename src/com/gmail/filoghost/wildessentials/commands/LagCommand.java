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
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import wild.api.command.CommandFramework;
import wild.api.command.CommandFramework.Permission;

import com.gmail.filoghost.wildessentials.WildEssentials;

@Permission("wildessentials.lag")
public class LagCommand extends CommandFramework {
	
	public LagCommand() {
		super(WildEssentials.plugin, "lag");
	}

	@Override
	public void execute(CommandSender sender, String label, String[] args) {
		
		List<String> info = new ArrayList<String>();
		info.add("§6Memoria massima: §c" + (Runtime.getRuntime().maxMemory() / 1024 / 1024) + " §6mb");
		info.add("§6Memoria totale: §c" + (Runtime.getRuntime().totalMemory() / 1024 / 1024) + " §6mb");
		info.add("§6Memoria libera: §c" + (Runtime.getRuntime().freeMemory() / 1024 / 1024) + " §6mb");
		for (World world : Bukkit.getWorlds()) {
			
			int tileEntities = 0;
			for (Chunk chunk : world.getLoadedChunks()) {
				tileEntities += chunk.getTileEntities().length;
			}
			
			info.add("§6Mondo \"§c" + world.getName() + "§6\": §c" + world.getLoadedChunks().length + " §6chunks, §c" + world.getEntities().size() + " §6entità, §c"+ tileEntities + " §6tile entities.");
		}
		
		sender.sendMessage(info.toArray(new String[0]));
	}

}

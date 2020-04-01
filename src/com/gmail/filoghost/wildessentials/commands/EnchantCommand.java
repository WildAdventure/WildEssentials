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

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import wild.api.command.CommandFramework;
import wild.api.command.CommandFramework.Permission;

import com.gmail.filoghost.wildessentials.WildEssentials;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

@Permission("wildessentials.enchant")
public class EnchantCommand extends CommandFramework {
	
	private static final int MAX_LEVEL = 5;
	
	public EnchantCommand() {
		super(WildEssentials.plugin, "enchant");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String label, String[] args) {
		Player player = CommandValidate.getPlayerSender(sender);
		
		CommandValidate.minLength(args, 1, "Utilizzo comando: /enchant <incantesimo> <livello> oppure /enchant remove");
		
		ItemStack item = player.getInventory().getItemInHand();
		CommandValidate.isTrue(item != null && item.getType() != Material.AIR, "Non stai tenendo nulla in mano.");
		if (args[0].equals("remove")) {
			for (Enchantment ench : item.getEnchantments().keySet()) {
				item.removeEnchantment(ench);
			}
			
			sender.sendMessage(ChatColor.GREEN + "Rimossi tutti gli incantesimi.");
		} else {
			CommandValidate.minLength(args, 2, "Utilizzo comando: /enchant <incantesimo> <livello> oppure /enchant remove");
			String name = args[0];
			int level = CommandValidate.getPositiveIntegerNotZero(args[1]);
			CommandValidate.isTrue(level <= MAX_LEVEL, "Il livello massimo è " + MAX_LEVEL + ".");
			
			Enchantment targetEnch = null;
			
			if (isInteger(name)) {
				targetEnch = Enchantment.getById(Integer.parseInt(name));
				
			} else {
				for (Enchantment e : Enchantment.values()) {
					if (e.getName().replace("_", "").equalsIgnoreCase(name.replace("_", ""))) {
						targetEnch = e;
						break;
					}
				}
			}
			
			if (targetEnch == null) {
				sender.sendMessage(ChatColor.RED + "Incantesimo \"" + name + "\" non trovato. Nomi validi:");
				List<String> enchs = Lists.newArrayList();
				for (Enchantment e : Enchantment.values()) {
					enchs.add(capitalizeFully(e.getName().replace("_", " ")).replace(" ", ""));
				}
				sender.sendMessage("§c" + Joiner.on("§8, §c").join(enchs));
				return;
			}
			
			item.removeEnchantment(targetEnch);
			item.addUnsafeEnchantment(targetEnch, level);
			sender.sendMessage(ChatColor.GREEN + "Incantato con successo!");
		}
	}
	
	
	private boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private String capitalizeFully(String input) {
		if (input == null) return null;
		
		String s = input.toLowerCase();
		
		int strLen = s.length();
	    StringBuffer buffer = new StringBuffer(strLen);
	    boolean capitalizeNext = true;
	    for (int i = 0; i < strLen; i++) {
	    	char ch = s.charAt(i);

	    	if (Character.isWhitespace(ch)) {
	    		buffer.append(ch);
	    		capitalizeNext = true;
	    	} else if (capitalizeNext) {
	    		buffer.append(Character.toTitleCase(ch));
	    		capitalizeNext = false;
	    	} else {
	    		buffer.append(ch);
	    	}
	    }
	    return buffer.toString();
	}

}

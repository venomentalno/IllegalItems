package com.venomentalno.illegitems.command;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class GiveCommandParser {
    private String itemId;
    private int amount = 1;
    private NbtCompound nbt;

    public GiveCommandParser(String commandString) throws Exception {
        parseCommand(commandString);
    }

    private void parseCommand(String commandString) throws Exception {
        // Clean up the input
        String input = commandString.trim();
        
        if (input.isEmpty()) {
            throw new Exception("No item specified");
        }

        // Find the item ID (first word)
        int firstSpace = input.indexOf(' ');
        if (firstSpace == -1) {
            itemId = input;
        } else {
            itemId = input.substring(0, firstSpace);
            String rest = input.substring(firstSpace).trim();
            
            // Parse amount and NBT
            int nextSpace = rest.indexOf(' ');
            if (nextSpace == -1) {
                // Either amount or NBT
                if (rest.startsWith("{")) {
                    nbt = parseNbt(rest);
                } else {
                    try {
                        amount = Integer.parseInt(rest);
                    } catch (NumberFormatException e) {
                        throw new Exception("Invalid amount: " + rest);
                    }
                }
            } else {
                // Amount first, then NBT
                String amountStr = rest.substring(0, nextSpace);
                try {
                    amount = Integer.parseInt(amountStr);
                } catch (NumberFormatException e) {
                    throw new Exception("Invalid amount: " + amountStr);
                }
                
                String nbtStr = rest.substring(nextSpace).trim();
                if (!nbtStr.isEmpty()) {
                    nbt = parseNbt(nbtStr);
                }
            }
        }

        if (!isValidItemId(itemId)) {
            throw new Exception("Unknown item: " + itemId);
        }
    }

    private boolean isValidItemId(String itemId) {
        try {
            Identifier id = parseItemIdentifier(itemId);
            return Registries.ITEM.get(id) != null && 
                   Registries.ITEM.get(id) != Registries.ITEM.get(Identifier.of("minecraft", "air"));
        } catch (Exception e) {
            return false;
        }
    }

    private NbtCompound parseNbt(String nbtString) throws Exception {
        if (!nbtString.startsWith("{") || !nbtString.endsWith("}")) {
            throw new Exception("Invalid NBT format: must start with { and end with }");
        }
        
        try {
            return StringNbtReader.parse(nbtString);
        } catch (Exception e) {
            throw new Exception("Failed to parse NBT: " + e.getMessage());
        }
    }

    private Identifier parseItemIdentifier(String itemId) {
        if (itemId.contains(":")) {
            String[] parts = itemId.split(":", 2);
            return Identifier.of(parts[0], parts[1]);
        } else {
            return Identifier.of("minecraft", itemId);
        }
    }

    // Getters
    public String getItemId() {
        return itemId;
    }

    public int getAmount() {
        return Math.max(1, Math.min(amount, 64)); // Clamp between 1 and 64
    }

    public NbtCompound getNbt() {
        return nbt;
    }

    public Identifier getItemIdentifier() {
        return parseItemIdentifier(itemId);
    }
}

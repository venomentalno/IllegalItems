package com.venomentalno.illegitems.command;

import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ItemGiver {

    public static void giveItem(GiveCommandParser parser) {
        MinecraftClient client = MinecraftClient.getInstance();
        
        if (client.player == null) {
            showError("You must be in game");
            return;
        }

        if (!client.player.isCreative()) {
            showError("You must be in creative mode");
            return;
        }

        try {
            // Create the item stack
            ItemStack itemStack = new ItemStack(
                Registries.ITEM.get(parser.getItemIdentifier()),
                parser.getAmount()
            );

            // Apply NBT data if present
            NbtCompound nbt = parser.getNbt();
            if (nbt != null) {
                // Handle enchantments specifically for Minecraft 1.21+
                if (nbt.contains("enchantments", NbtElement.COMPOUND_TYPE)) {
                    NbtCompound enchantmentsNbt = nbt.getCompound("enchantments");
                    NbtList enchantmentsList = new NbtList();
                    
                    for (String key : enchantmentsNbt.getKeys()) {
                        NbtCompound enchantmentEntry = new NbtCompound();
                        enchantmentEntry.putString("id", "minecraft:" + key);
                        enchantmentEntry.putInt("lvl", enchantmentsNbt.getInt(key));
                        enchantmentsList.add(enchantmentEntry);
                    }
                    
                    NbtCompound enchantmentsComponent = new NbtCompound();
                    enchantmentsComponent.put("enchants", enchantmentsList);
                    enchantmentsComponent.putBoolean("showInTooltip", true);
                    itemStack.set(DataComponentTypes.ENCHANTMENTS, NbtComponent.of(enchantmentsComponent).copy());
                    
                    // Remove enchantments from custom data since we handled it separately
                    nbt.remove("enchantments");
                }
                
                // Apply remaining NBT as custom data
                if (!nbt.isEmpty()) {
                    itemStack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
                }
            }

            // Give the item to the player
            client.player.getInventory().insertStack(itemStack);
            
            showSuccess("Gave " + parser.getAmount() + "x " + parser.getItemId());
        } catch (Exception e) {
            showError("Failed to give item: " + e.getMessage());
        }
    }

    private static void showSuccess(String message) {
        MinecraftClient.getInstance().inGameHud.getChatHud()
            .addMessage(Text.literal("§a✓ " + message));
    }

    private static void showError(String message) {
        MinecraftClient.getInstance().inGameHud.getChatHud()
            .addMessage(Text.literal("§c✗ " + message));
    }
}

package com.venomentalno.illegitems.command;

import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;

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
            if (parser.getNbt() != null) {
                itemStack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(parser.getNbt()));
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

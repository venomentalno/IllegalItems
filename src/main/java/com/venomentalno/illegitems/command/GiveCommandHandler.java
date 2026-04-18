package com.venomentalno.illegitems.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class GiveCommandHandler {

    public static void registerCommand() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(
                ClientCommandManager.literal("give")
                    .then(ClientCommandManager.argument("item", StringArgumentType.word())
                        .executes(context -> handleGiveCommand(StringArgumentType.getString(context, "item"), ""))
                        .then(ClientCommandManager.argument("amount", StringArgumentType.greedyString())
                            .executes(context -> {
                                String item = StringArgumentType.getString(context, "item");
                                String rest = StringArgumentType.getString(context, "amount");
                                return handleGiveCommand(item, rest);
                            })
                        )
                    )
            );
        });
    }

    private static int handleGiveCommand(String itemId, String rest) {
        try {
            String fullCommand = rest.isEmpty() ? itemId : itemId + " " + rest;
            GiveCommandParser parser = new GiveCommandParser(fullCommand);
            ItemGiver.giveItem(parser);
            return 1;
        } catch (Exception e) {
            MinecraftClient.getInstance().inGameHud.getChatHud()
                .addMessage(Text.literal("§cError: " + e.getMessage()));
            return 0;
        }
    }
}

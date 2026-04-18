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
                ClientCommandManager.literal("giveillegal")
                    .then(ClientCommandManager.argument("item", StringArgumentType.greedyString())
                        .executes(context -> {
                            String fullCommand = StringArgumentType.getString(context, "item");
                            return handleGiveCommand(fullCommand);
                        })
                    )
            );
        });
    }

    private static int handleGiveCommand(String fullCommand) {
        try {
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

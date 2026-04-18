package com.venomentalno.illegitems;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;

import com.venomentalno.illegitems.command.GiveCommandHandler;
import com.venomentalno.illegitems.client.ClientEvents;
import com.venomentalno.illegitems.screen.KeyBindings;

public class IllegalItemsMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        GiveCommandHandler.registerCommand();
        KeyBindings.register();
        ClientEvents.registerClientEvents();
    }
}

package com.venomentalno.illegitems.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import com.venomentalno.illegitems.screen.GiveCommandScreen;
import com.venomentalno.illegitems.screen.KeyBindings;

public class ClientEvents {
    
    public static void registerClientEvents() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (KeyBindings.OPEN_GUI.wasPressed()) {
                client.setScreen(new GiveCommandScreen(client.currentScreen));
            }
        });
    }
}

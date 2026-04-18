package com.venomentalno.illegitems.screen;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {
    public static final KeyBinding OPEN_GUI = KeyBindingHelper.registerKeyBinding(new KeyBinding(
        "key.illegitems.open_gui",
        InputUtil.Type.KEYSYM,
        GLFW.GLFW_KEY_I,
        "category.illegitems"
    ));
}

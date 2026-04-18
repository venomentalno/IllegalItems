package com.venomentalno.illegitems.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import com.venomentalno.illegitems.command.GiveCommandParser;
import com.venomentalno.illegitems.command.ItemGiver;

public class GiveCommandScreen extends Screen {
    private TextFieldWidget commandInput;
    private Screen previousScreen;

    public GiveCommandScreen(Screen previousScreen) {
        super(Text.literal("Illegal Items - Give Command"));
        this.previousScreen = previousScreen;
    }

    @Override
    protected void init() {
        // Create text field for command input (no character limit)
        this.commandInput = new TextFieldWidget(this.textRenderer, this.width / 2 - 150, 80, 300, 20, Text.literal("Command"));
        this.commandInput.setPlaceholder(Text.literal("item_name [amount] {nbt} or diamond[enchantments={...}] 64"));
        this.commandInput.setMaxLength(Integer.MAX_VALUE); // Remove character limit
        this.addDrawableChild(this.commandInput);
        this.setInitialFocus(this.commandInput);

        // Give button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("§aGive Item"), (button) -> {
            String command = this.commandInput.getText();
            if (!command.isEmpty()) {
                try {
                    GiveCommandParser parser = new GiveCommandParser(command);
                    ItemGiver.giveItem(parser);
                    this.close();
                } catch (Exception e) {
                    this.commandInput.setPlaceholder(Text.literal("§cError: " + e.getMessage()));
                }
            }
        }).dimensions(this.width / 2 - 155, 110, 150, 20).build());

        // Cancel button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("§cCancel"), (button) -> {
            this.close();
        }).dimensions(this.width / 2 + 5, 110, 150, 20).build());

        // Help text
        super.init();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        
        // Title
        context.drawCenteredTextWithShadow(this.textRenderer, 
            Text.literal("§6Illegal Items"), 
            this.width / 2, 30, 0xFFFFFF);
        
        // Instructions
        context.drawCenteredTextWithShadow(this.textRenderer,
            Text.literal("§7Use /giveillegal or enter item here"),
            this.width / 2, 60, 0xAAAAAA);
        
        context.drawCenteredTextWithShadow(this.textRenderer,
            Text.literal("§7Example: diamond[enchantments={levels:{sharpness:255}}] 64"),
            this.width / 2, 150, 0x888888);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        this.client.setScreen(this.previousScreen);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
}

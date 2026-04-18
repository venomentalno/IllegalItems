package com.venomentalno.illegitems.screen;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.item.Items;

public class ItemBrowserScreen extends Screen {
    private Screen previousScreen;
    private int scrollOffset = 0;

    public ItemBrowserScreen(Screen previousScreen) {
        super(Text.literal("Item Browser"));
        this.previousScreen = previousScreen;
    }

    @Override
    protected void init() {
        // Back button
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Back"), (button) -> {
            this.close();
        }).dimensions(this.width - 105, 10, 100, 20).build());

        super.init();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, 
            Text.literal("§6Item Browser"), 
            this.width / 2, 10, 0xFFFFFF);
        
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

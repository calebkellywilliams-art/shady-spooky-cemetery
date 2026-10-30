package dev.kryptonx.gui;

import dev.kryptonx.KryptonXClient;
import dev.kryptonx.module.ClientModule;
import dev.kryptonx.module.ModuleCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public final class DashboardScreen extends Screen {
    private static final int BG = 0xF20F1115;
    private static final int PANEL = 0xFF191C22;
    private static final int CARD = 0xFF222630;
    private static final int MUTED = 0xFF9BA3B4;
    private static final int WHITE = 0xFFF5F7FA;

    private String page = "Home";
    private ModuleCategory selectedCategory = ModuleCategory.HUD;
    private final List<Button> dynamicButtons = new ArrayList<>();

    public DashboardScreen() {
        super(Component.literal("KryptonX Dashboard"));
    }

    @Override
    protected void init() {
        rebuildButtons();
    }

    private void rebuildButtons() {
        clearWidgets();
        dynamicButtons.clear();

        int left = Math.max(18, width / 2 - 250);
        int top = Math.max(18, height / 2 - 160);

        String[] pages = {"Home", "Modules", "HUD Editor", "Spotify", "Configs", "Themes", "Settings"};
        for (int i = 0; i < pages.length; i++) {
            final String target = pages[i];
            Button button = Button.builder(Component.literal(target), b -> {
                page = target;
                rebuildButtons();
            }).bounds(left + 14, top + 48 + i * 34, 112, 27).build();
            addRenderableWidget(button);
        }

        if (page.equals("Modules")) {
            int x = left + 158;
            int y = top + 96;
            for (ClientModule module : KryptonXClient.MODULES.all()) {
                if (module.category() != selectedCategory) continue;
                Button toggle = Button.builder(
                        Component.literal(module.name() + (module.enabled() ? "  • ON" : "  • OFF")),
                        b -> {
                            module.toggle();
                            KryptonXClient.CONFIG.save();
                            rebuildButtons();
                        }).bounds(x, y, 300, 28).build();
                addRenderableWidget(toggle);
                y += 36;
            }
        }

        if (page.equals("Modules")) {
            int x = left + 158;
            int y = top + 58;
            for (ModuleCategory category : ModuleCategory.values()) {
                Button categoryButton = Button.builder(Component.literal(category.label()), b -> {
                    selectedCategory = category;
                    rebuildButtons();
                }).bounds(x, y, 72, 24).build();
                addRenderableWidget(categoryButton);
                x += 78;
            }
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        renderBackground(graphics, mouseX, mouseY, delta);

        int panelW = 500;
        int panelH = 320;
        int left = (width - panelW) / 2;
        int top = (height - panelH) / 2;

        graphics.fill(0, 0, width, height, 0x99000000);
        graphics.fill(left, top, left + panelW, top + panelH, BG);
        graphics.fill(left, top, left + 140, top + panelH, PANEL);
        graphics.fill(left + 12, top + 12, left + 128, top + 38, CARD);
        graphics.drawString(font, "KRYPTON", left + 22, top + 21, WHITE, false);
        graphics.drawString(font, "X", left + 83, top + 21, 0xFFB9C7FF, false);

        graphics.drawString(font, page, left + 158, top + 22, WHITE, false);
        graphics.drawString(font, "A clean space for your Minecraft setup", left + 158, top + 39, MUTED, false);

        if (page.equals("Home")) {
            drawCard(graphics, left + 158, top + 82, 148, 64, "CLIENT", "KryptonX  •  0.1.0");
            drawCard(graphics, left + 318, top + 82, 164, 64, "MODULES", String.valueOf(KryptonXClient.MODULES.all().size()) + " available");
            drawCard(graphics, left + 158, top + 158, 324, 92, "GETTING STARTED",
                    "Open Modules to enable your HUD components.\nPress Right Shift to reopen this dashboard.");
            graphics.drawString(font, "Spotify connection and draggable HUD editing are planned next.",
                    left + 158, top + 270, MUTED, false);
        } else if (page.equals("HUD Editor")) {
            drawCard(graphics, left + 158, top + 82, 324, 112, "HUD EDITOR",
                    "Preview and drag-to-position widgets will be added in the next iteration.");
        } else if (page.equals("Spotify")) {
            drawCard(graphics, left + 158, top + 82, 324, 112, "SPOTIFY",
                    "Spotify playback integration is not connected yet.\nThis panel is ready for OAuth/API integration.");
        } else if (page.equals("Configs")) {
            drawCard(graphics, left + 158, top + 82, 324, 112, "CONFIGURATIONS",
                    "Local profile save/load is the next configuration milestone.");
        } else if (page.equals("Themes")) {
            drawCard(graphics, left + 158, top + 82, 324, 112, "THEMES",
                    "Current theme: Midnight Minimal. A light theme toggle is coming.");
        } else if (page.equals("Settings")) {
            drawCard(graphics, left + 158, top + 82, 324, 112, "SETTINGS",
                    "Dashboard shortcut: Right Shift. More settings will appear here.");
        } else if (page.equals("Modules")) {
            graphics.drawString(font, "Choose a category, then toggle a module.", left + 158, top + 45, MUTED, false);
        }

        super.render(graphics, mouseX, mouseY, delta);
    }

    private void drawCard(GuiGraphics graphics, int x, int y, int w, int h, String title, String body) {
        graphics.fill(x, y, x + w, y + h, CARD);
        graphics.drawString(font, title, x + 12, y + 12, 0xFFB9C7FF, false);
        String[] lines = body.split("\\n");
        for (int i = 0; i < lines.length; i++) {
            graphics.drawString(font, lines[i], x + 12, y + 31 + i * 13, WHITE, false);
        }
    }

    @Override
    public boolean isPauseScreen() { return false; }
}

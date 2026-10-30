package dev.kryptonx;

import dev.kryptonx.config.ConfigManager;
import dev.kryptonx.gui.DashboardScreen;
import dev.kryptonx.module.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

public final class KryptonXClient implements ClientModInitializer {
    public static final String MOD_ID = "kryptonx";
    public static final ModuleManager MODULES = new ModuleManager();
    public static final ConfigManager CONFIG = new ConfigManager();

    private static KeyMapping openDashboardKey;

    @Override
    public void onInitializeClient() {
        MODULES.registerDefaults();
        CONFIG.load();

        openDashboardKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.kryptonx.open_dashboard",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                KeyMapping.Category.MISC
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openDashboardKey.consumeClick()) {
                Minecraft.getInstance().setScreen(new DashboardScreen());
            }
            MODULES.tick();
        });
    }
}

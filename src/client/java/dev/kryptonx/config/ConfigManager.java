package dev.kryptonx.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dev.kryptonx.KryptonXClient;
import dev.kryptonx.module.ClientModule;
import net.fabricmc.loader.api.FabricLoader;

import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public final class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Type MAP_TYPE = new TypeToken<Map<String, Boolean>>() {}.getType();
    private final Path file = FabricLoader.getInstance().getConfigDir().resolve("kryptonx.json");

    public void save() {
        Map<String, Boolean> state = new HashMap<>();
        for (ClientModule module : KryptonXClient.MODULES.all()) {
            state.put(module.name(), module.enabled());
        }
        try {
            Files.createDirectories(file.getParent());
            try (Writer writer = Files.newBufferedWriter(file)) {
                GSON.toJson(state, writer);
            }
        } catch (Exception exception) {
            System.err.println("[KryptonX] Could not save config: " + exception.getMessage());
        }
    }

    public void load() {
        if (!Files.exists(file)) return;
        try (Reader reader = Files.newBufferedReader(file)) {
            Map<String, Boolean> state = GSON.fromJson(reader, MAP_TYPE);
            if (state == null) return;
            for (ClientModule module : KryptonXClient.MODULES.all()) {
                Boolean enabled = state.get(module.name());
                if (enabled != null) module.setEnabled(enabled);
            }
        } catch (Exception exception) {
            System.err.println("[KryptonX] Could not load config: " + exception.getMessage());
        }
    }
}

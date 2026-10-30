package dev.kryptonx.module;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ModuleManager {
    private final List<ClientModule> modules = new ArrayList<>();

    public void register(ClientModule module) { modules.add(module); }

    public void registerDefaults() {
        register(new ClientModule("Clean HUD", "Shows a minimal information overlay.", ModuleCategory.HUD) {});
        register(new ClientModule("Module List", "Displays enabled client modules.", ModuleCategory.HUD) {});
        register(new ClientModule("Interface Animations", "Enables smooth dashboard transitions.", ModuleCategory.CLIENT) {});
        register(new ClientModule("Status Panel", "Shows client-side status information.", ModuleCategory.UTILITY) {});
    }

    public List<ClientModule> all() { return Collections.unmodifiableList(modules); }

    public List<ClientModule> inCategory(ModuleCategory category) {
        return modules.stream().filter(m -> m.category() == category).toList();
    }

    public void tick() {
        for (ClientModule module : modules) {
            if (module.enabled()) module.tick();
        }
    }
}

package dev.kryptonx.module;

public abstract class ClientModule {
    private final String name;
    private final String description;
    private final ModuleCategory category;
    private boolean enabled;

    protected ClientModule(String name, String description, ModuleCategory category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public final String name() { return name; }
    public final String description() { return description; }
    public final ModuleCategory category() { return category; }
    public final boolean enabled() { return enabled; }

    public final void setEnabled(boolean value) {
        if (enabled == value) return;
        enabled = value;
        if (value) onEnable(); else onDisable();
    }

    public final void toggle() { setEnabled(!enabled); }
    public void tick() {}
    protected void onEnable() {}
    protected void onDisable() {}
}

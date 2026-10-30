package dev.kryptonx.module;

public enum ModuleCategory {
    HUD("HUD"), VISUAL("Visual"), UTILITY("Utility"), CLIENT("Client");

    private final String label;
    ModuleCategory(String label) { this.label = label; }
    public String label() { return label; }
}

# KryptonX

A client-side Fabric starter project for Minecraft **1.21.11**, written in Java 21.

## Included in this starter
- Fabric client initializer
- Right Shift dashboard shortcut
- Sidebar dashboard with Home, Modules, HUD Editor, Spotify, Configs, Themes and Settings pages
- Basic module/category framework
- Module toggles
- JSON config persistence for module enabled states

## Build
1. Install JDK 21.
2. Open this folder in IntelliJ IDEA as a Gradle project.
3. Let Gradle sync and download dependencies.
4. Run the Gradle task `runClient`.
5. Build the mod with `build`; the remapped jar is normally in `build/libs`.

## Notes
- This is a foundation, not a finished Krypton replacement.
- Spotify playback requires a separate user-authorized Spotify OAuth integration; this starter intentionally does not ask for or store account passwords.
- HUD rendering, draggable layout editing, config UI controls, animation polish and Spotify API wiring are planned follow-up work.
- Fabric/Minecraft dependency versions are pinned in `gradle.properties`. If Fabric publishes a newer compatible 1.21.11 set, update those values from the official Fabric Develop page.

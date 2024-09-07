package agmas.org.vr_spigot_recorder;

import org.bukkit.plugin.java.JavaPlugin;

public final class VR_Spigot_Recorder extends JavaPlugin {

    public static boolean build__forCutsceneRecordings = false;

    @Override
    public void onEnable() {
        // Plugin startup logic
        new GeneralTask().runTaskTimer(VR_Spigot_Recorder.getPlugin(VR_Spigot_Recorder.class), 0, 1);
        this.getCommand("rgn").setExecutor(new RegainCommand());
        this.getCommand("scale").setExecutor(new ScaleCommand());
        if (build__forCutsceneRecordings) {
            this.getCommand("record").setExecutor(new RecordCommand());
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

package agmas.org.vr_spigot_recorder;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RegainCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        for (VRPlayer vr : GeneralTask.allHands) {
            if (vr.linkedPlayer.equals((Player) sender)) {
                vr.arm1.remove();
                vr.arm2.remove();
                vr.head.remove();
                vr.linkedPlayer.removeMetadata("fakeHandsEnabled", VR_Spigot_Recorder.getPlugin(VR_Spigot_Recorder.class));
                GeneralTask.allHands.remove(vr);
            }
        }
        return true;
    }
}
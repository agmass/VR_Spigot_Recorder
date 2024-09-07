package agmas.org.vr_spigot_recorder;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ScaleCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        for (VRPlayer vr : GeneralTask.allHands) {
            if (vr.linkedPlayer.equals((Player) sender)) {
                if (args[0] != null) {
                    try {
                        vr.worldScale = Double.parseDouble(args[0]);
                    } catch (Exception e) {}
                }
            }
        }
        return true;
    }
}
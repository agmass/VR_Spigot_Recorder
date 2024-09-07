package agmas.org.vr_spigot_recorder;


import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;

public class RecordCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (VR_Spigot_Recorder.build__forCutsceneRecordings) {
            if (GeneralTask.currentRecording != null) {

                String filename = Date.from(Instant.now()).toString().replace(" ", "-").replace(":", "_") + ".vrrec";
                File myObj = new File(filename);
                try {
                    if (myObj.createNewFile()) {
                        FileWriter myWriter = new FileWriter(filename);
                        for (RecordingData.VRLocation vr : GeneralTask.currentRecording.VRLocations) {
                            myWriter.write(
                                    "|PL;" + vr.playerLocation.x + "=" + vr.playerLocation.y + "=" + vr.playerLocation.z + "" +
                                            "|RHP;" + vr.rightHandPosition.x + "=" + vr.rightHandPosition.y + "=" + vr.rightHandPosition.z + "" +
                                            "|RHR;" + vr.rightHandRotation.x + "=" + vr.rightHandRotation.y + "=" + vr.rightHandRotation.z + "=" + vr.rightHandRotation.w +
                                            "|LHP;" + vr.leftHandPosition.x + "=" + vr.leftHandPosition.y + "=" + vr.leftHandPosition.z + "" +
                                            "|LHR;" + vr.leftHandRotation.x + "=" + vr.leftHandRotation.y + "=" + vr.leftHandRotation.z + "=" + vr.leftHandRotation.w +
                                            "|HR;" + vr.headRotation.x + "=" + vr.headRotation.y + "=" + vr.headRotation.z + "=" + vr.headRotation.w + "\n");
                        }
                        myWriter.close();
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                GeneralTask.currentRecording = null;
            } else {
                GeneralTask.currentRecording = new RecordingData();
                GeneralTask.currentRecording.tracking = (Player) sender;
            }
        }
        return true;
    }
}
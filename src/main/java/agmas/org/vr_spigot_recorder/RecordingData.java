package agmas.org.vr_spigot_recorder;


import org.bukkit.entity.Player;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import javax.xml.stream.Location;
import java.util.ArrayList;

public class RecordingData {
    public ArrayList<VRLocation> VRLocations = new ArrayList<>();
    public Player tracking;

    public static class VRLocation {
        public Quaternionf leftHandRotation;
        public Vector3f leftHandPosition;
        public Quaternionf rightHandRotation;
        public Vector3f rightHandPosition;
        public Quaternionf headRotation;
        public Vector3f playerLocation;

        public VRLocation(Player p) {
            leftHandPosition = new Vector3f();
            rightHandPosition = new Vector3f();
            rightHandRotation = new Quaternionf();
            leftHandRotation = new Quaternionf();
            playerLocation = p.getLocation().toVector().toVector3f();
        }
        public VRLocation(VRPlayer p) {
            rightHandPosition = p.arm2.getLocation().toVector().toVector3f();
            rightHandRotation = p.arm2.getTransformation().getLeftRotation();
            GeneralTask.currentRecording.tracking.sendMessage(p.arm2.getTransformation().getLeftRotation().x+"");
            leftHandPosition = p.arm1.getLocation().toVector().toVector3f();
            leftHandRotation = p.arm1.getTransformation().getLeftRotation();
            headRotation = p.head.getTransformation().getLeftRotation();
            playerLocation = p.linkedPlayer.getLocation().toVector().toVector3f();
        }
    }

}

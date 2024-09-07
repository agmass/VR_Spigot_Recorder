package agmas.org.vr_spigot_recorder;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.MetadataValueAdapter;
import org.bukkit.metadata.Metadatable;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public class GeneralTask extends BukkitRunnable {

    public static ArrayList<VRPlayer> allHands = new ArrayList<>();
    public static RecordingData currentRecording = new RecordingData();


    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasMetadata("seated") && !allHands.contains(p) && !p.hasMetadata("fakeHandsEnabled")) {
                if (!p.getGameMode().equals(GameMode.SPECTATOR) || !p.isDead() || p.getMetadata("righthand.pos").get(0).value() != null) {
                    p.setMetadata("fakeHandsEnabled", new FixedMetadataValue(VR_Spigot_Recorder.getPlugin(VR_Spigot_Recorder.class), true));
                    new VRPlayer(p);
                }
            }
        }
        if (VR_Spigot_Recorder.build__forCutsceneRecordings) {
            if (currentRecording != null) {
                if (currentRecording.tracking != null) {
                    if (!currentRecording.tracking.hasMetadata("fakeHandsEnabled")) {
                        currentRecording.VRLocations.add(new RecordingData.VRLocation(currentRecording.tracking));
                    }
                }
            }
        }
        for (VRPlayer vr : allHands) {
            if (vr.linkedPlayer == null) {
                vr.arm1.remove();
                vr.arm2.remove();
                vr.head.remove();
                vr.linkedPlayer.removeMetadata("fakeHandsEnabled", VR_Spigot_Recorder.getPlugin(VR_Spigot_Recorder.class));
                allHands.remove(vr);
                continue;
            }
            if (!vr.linkedPlayer.isOnline() || vr.linkedPlayer.getGameMode().equals(GameMode.SPECTATOR) || vr.linkedPlayer.isDead() || vr.linkedPlayer.getMetadata("righthand.pos").get(0).value() == null) {
                vr.arm1.remove();
                vr.arm2.remove();
                vr.head.remove();
                vr.linkedPlayer.removeMetadata("fakeHandsEnabled", VR_Spigot_Recorder.getPlugin(VR_Spigot_Recorder.class));
                allHands.remove(vr);
                continue;
            }
            if (VR_Spigot_Recorder.build__forCutsceneRecordings) {
                if (currentRecording != null) {
                    if (currentRecording.tracking != null) {
                        if (currentRecording.tracking.equals(vr.linkedPlayer))
                            currentRecording.VRLocations.add(new RecordingData.VRLocation(vr));
                    }
                }
            }

            for (Player p : Bukkit.getOnlinePlayers()) {
                //if (p != vr.linkedPlayer ) {
                if (VR_Spigot_Recorder.build__forCutsceneRecordings || p != vr.linkedPlayer) {
                    p.sendEquipmentChange(vr.linkedPlayer, EquipmentSlot.HAND, new ItemStack(Material.AIR));
                    p.sendEquipmentChange(vr.linkedPlayer, EquipmentSlot.OFF_HAND, new ItemStack(Material.AIR));


                    p.sendEquipmentChange(vr.linkedPlayer, EquipmentSlot.HEAD, new ItemStack(Material.AIR));
                    p.sendEquipmentChange(vr.linkedPlayer, EquipmentSlot.CHEST, new ItemStack(Material.AIR));
                    p.sendEquipmentChange(vr.linkedPlayer, EquipmentSlot.LEGS, new ItemStack(Material.AIR));
                    p.sendEquipmentChange(vr.linkedPlayer, EquipmentSlot.FEET, new ItemStack(Material.AIR));
                }
                //}
            }
            vr.linkedPlayer.addPotionEffect(PotionEffectType.INVISIBILITY.createEffect(4, 255));
            Location arm1 = (Location) vr.linkedPlayer.getMetadata("righthand.pos").get(0).value();
            arm1.setPitch(0);
            arm1.setYaw(0);
            vr.arm1.teleport(arm1);
            Location spinsy = (Location) vr.linkedPlayer.getMetadata("head.pos").get(0).value();
            spinsy.setPitch(0);
            spinsy.setYaw(0);
            vr.head.teleport(spinsy);
            arm1 = (Location) vr.linkedPlayer.getMetadata("lefthand.pos").get(0).value();
            arm1.setPitch(0);
            arm1.setYaw(0);
            vr.arm2.teleport(arm1);
            if (vr.linkedPlayer.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                vr.arm1.setItemStack(new ItemStack(Material.WHITE_CONCRETE));

            } else {
                vr.arm1.setItemStack(vr.linkedPlayer.getInventory().getItemInMainHand());
            }
            if (vr.linkedPlayer.getInventory().getItemInOffHand().getType().equals(Material.AIR)) {
                vr.arm2.setItemStack(new ItemStack(Material.WHITE_CONCRETE));
            } else {
                vr.arm2.setItemStack(vr.linkedPlayer.getInventory().getItemInOffHand());
            }

            Transformation trans = vr.arm2.getTransformation();
            float[] vivecraftArray = (float[]) vr.linkedPlayer.getMetadata("lefthand.rot").get(0).value();
            Quaternionf newLeft = trans.getLeftRotation().set(new Quaternionf(vivecraftArray[1], vivecraftArray[2], vivecraftArray[3],vivecraftArray[0]));
            if (!vr.arm2.getItemStack().getType().isBlock()) {
                trans = vr.arm2.getTransformation();
                vr.arm2.setItemDisplayTransform(ItemDisplay.ItemDisplayTransform.FIRSTPERSON_RIGHTHAND);
                newLeft.rotateXYZ(-180, 0, -135);
                trans.getScale().set(0.75f * vr.worldScale);
            } else {
                trans = vr.arm2.getTransformation();
                vr.arm2.setItemDisplayTransform(ItemDisplay.ItemDisplayTransform.NONE);
                trans.getScale().set(0.25f * vr.worldScale);
            }
            vr.arm2.setTransformation(new Transformation(trans.getTranslation(),newLeft, trans.getScale(), trans.getRightRotation()));

            trans = vr.arm1.getTransformation();
            vivecraftArray = (float[]) vr.linkedPlayer.getMetadata("righthand.rot").get(0).value();
            newLeft = trans.getLeftRotation().set(new Quaternionf(vivecraftArray[1], vivecraftArray[2], vivecraftArray[3],vivecraftArray[0]));
            Vector3f newTranslation = trans.getTranslation();

            if (!vr.arm1.getItemStack().getType().isBlock()) {
                trans = vr.arm1.getTransformation();
                vr.arm1.setItemDisplayTransform(ItemDisplay.ItemDisplayTransform.FIRSTPERSON_RIGHTHAND);
                newLeft.rotateXYZ(-180, 0, -135);
                trans.getScale().set(0.75f * vr.worldScale);
            } else {
                trans = vr.arm1.getTransformation();
                newTranslation.set(0f);
                vr.arm1.setItemDisplayTransform(ItemDisplay.ItemDisplayTransform.NONE);
                trans.getScale().set(0.25f * vr.worldScale);
            }

            vr.arm1.setTransformation(new Transformation(newTranslation,newLeft, trans.getScale(), trans.getRightRotation()));

            trans = vr.head.getTransformation();
            trans.getScale().set(0.75f * vr.worldScale);
            vivecraftArray = (float[]) vr.linkedPlayer.getMetadata("head.rot").get(0).value();
            newLeft = trans.getLeftRotation().set(new Quaternionf(vivecraftArray[1], vivecraftArray[2], vivecraftArray[3],vivecraftArray[0]));
            vr.head.setTransformation(new Transformation(trans.getTranslation(),newLeft, trans.getScale(), trans.getRightRotation()));


        }
    }
}

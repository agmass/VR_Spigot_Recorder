package agmas.org.vr_spigot_recorder;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.ItemDisplay;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.util.Transformation;

public class VRPlayer {

    Player linkedPlayer;
    ItemDisplay arm1;
    ItemDisplay head;
    ItemDisplay arm2;
    double worldScale = 1.0f;

    public VRPlayer(Player p) {
        linkedPlayer = p;
        head = p.getWorld().spawn(p.getLocation(), ItemDisplay.class);

        ItemStack hed = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta sm = (SkullMeta) hed.getItemMeta();
        sm.setOwningPlayer(p);
        hed.setItemMeta(sm);
        head.setItemStack(hed);
        head.setInterpolationDuration(5);
        linkedPlayer.hideEntity(VR_Spigot_Recorder.getPlugin(VR_Spigot_Recorder.class), head);

        Transformation trans2 = head.getTransformation();
        trans2.getScale().set(0.75f);
        head.setTransformation(trans2);
        arm1 = p.getWorld().spawn(p.getLocation(), ItemDisplay.class);
        arm1.setItemStack(new ItemStack(Material.WHITE_CONCRETE));
        Transformation trans = arm1.getTransformation();
        trans.getScale().set(0.5f);
        arm1.setInterpolationDuration(5);
        arm1.setTransformation(trans);


        arm2 = p.getWorld().spawn(p.getLocation(), ItemDisplay.class);
        arm2.setItemStack(new ItemStack(Material.WHITE_CONCRETE));
        arm2.setTransformation(trans);
        arm2.setInterpolationDuration(5);
        GeneralTask.allHands.add(this);
    }

}

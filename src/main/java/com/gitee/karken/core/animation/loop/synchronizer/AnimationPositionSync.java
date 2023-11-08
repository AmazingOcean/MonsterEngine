package com.gitee.karken.core.animation.loop.synchronizer;

import com.gitee.karken.core.animation.KarkenStackLinearity;
import com.gitee.karken.core.player.bone.KarkenBone;
import com.gitee.karken.core.player.bone.MinecraftBoneType;
import com.gitee.karken.util.vector.KarkenVector3f;
import net.minecraft.client.model.geom.ModelPart;

public class AnimationPositionSync implements AnimationSynchronizer {

    @Override
    public void batch(MinecraftBoneType type,ModelPart modelPart, KarkenVector3f vector3f) {
        modelPart.setPos(vector3f.getX(), vector3f.getY(), vector3f.getZ());
    }

    @Override
    public KarkenStackLinearity getKarkenStackLinearity(KarkenBone bone) {
        return bone.getPosition();
    }
}

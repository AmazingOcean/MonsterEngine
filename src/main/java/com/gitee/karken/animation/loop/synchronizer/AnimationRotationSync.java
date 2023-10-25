package com.gitee.karken.animation.loop.synchronizer;

import com.gitee.karken.animation.Animation;
import com.gitee.karken.animation.KarkenStackLinearity;
import com.gitee.karken.animation.loop.AnimationTransitioned;
import com.gitee.karken.bone.KarkenBone;
import com.gitee.karken.bone.MinecraftBoneType;
import com.gitee.karken.player.KarkenAnimatedHumanoid;
import com.gitee.karken.vector.KarkenVector3f;
import com.gitee.monsterengine.MonsterEngine;
import com.gitee.monsterengine.MonsterEngineClient;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;

public class AnimationRotationSync implements AnimationSynchronizer  {

    @Override
    public void batch(MinecraftBoneType type,ModelPart modelPart, KarkenVector3f vector3f) {
        modelPart.setRotation(vector3f.getX(), vector3f.getY(), vector3f.getZ());
    }

    @Override
    public KarkenStackLinearity getKarkenStackLinearity(KarkenBone bone) {
        return bone.getRotation();
    }
}

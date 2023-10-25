package com.gitee.karken.animation.loop.synchronizer;


import com.gitee.karken.animation.Animation;
import com.gitee.karken.animation.KarkenStackLinearity;
import com.gitee.karken.animation.loop.AnimationTransitioned;
import com.gitee.karken.animation.loop.synchronizer.AnimationRotationSync;
import com.gitee.karken.bone.KarkenBone;
import com.gitee.karken.bone.MinecraftBoneType;
import com.gitee.karken.player.KarkenAnimatedHumanoid;
import com.gitee.karken.vector.KarkenVector3f;
import com.gitee.monsterengine.MonsterEngine;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;

import java.util.Arrays;
import java.util.List;

public interface AnimationSynchronizer {

    default void render(KarkenAnimatedHumanoid humanoid, HumanoidModel<?> player, MinecraftBoneType type) {
        Animation animation = humanoid.getAnimationController().getAnimation();
        if (animation != null) {
            KarkenBone bone = animation.getBone(type.getName());
            if (bone == null) return;
            KarkenStackLinearity stackLinearity = getKarkenStackLinearity(bone);
            AnimationTransitioned transitioned = stackLinearity.getTransitioned();
            float tickDelta = animation.getTickDelta();
            ModelPart modelPart = type.getModelPart(player);
            KarkenVector3f peeked = transitioned.peek(); // 获取上一次的存储
            // 插值取相对向量
            KarkenVector3f withRadians = stackLinearity.get().withRadians().multiply(animation.getInterpolation());
            // 为空则应用本次赋值
            if (peeked == null) {
                peeked = withRadians;
            }
            // 插值计算向量值
            KarkenVector3f current = peeked.withLerp(withRadians,tickDelta);
            batch(type,modelPart,current);
            // 存储上一个动画的效果
            transitioned.push(current);
        }
    }

    KarkenStackLinearity getKarkenStackLinearity(KarkenBone bone);

    void batch(MinecraftBoneType type,ModelPart modelPart,KarkenVector3f vector3f);

}

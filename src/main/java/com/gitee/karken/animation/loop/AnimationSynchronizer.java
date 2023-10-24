package com.gitee.karken.animation.loop;


import com.gitee.karken.animation.Animation;
import com.gitee.karken.bone.KarkenBone;
import com.gitee.karken.bone.MinecraftBoneType;
import com.gitee.karken.player.KarkenAnimatedHumanoid;
import com.gitee.karken.vector.KarkenVector3f;
import com.gitee.monsterengine.MonsterEngine;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;

public class AnimationSynchronizer {

    public static AnimationSynchronizer INSTANCE = new AnimationSynchronizer();

    public void update(KarkenAnimatedHumanoid humanoid, HumanoidModel<?> player, MinecraftBoneType type) {
        if (humanoid.getAnimationController().getAnimation() != null) {
            Animation animation = humanoid.getAnimationController().getAnimation();
            AnimationTransitioned transitioned = animation.getTransitioned();
            float tickDelta = transitioned.getTickDelta();
            KarkenBone bone = humanoid.getAnimationController().getBone(type.getName());
            if (bone == null) return;
            ModelPart modelPart = type.getModelPart(player);
            KarkenVector3f peeked = transitioned.peek(); // 获取上一次的存储
            KarkenVector3f withRadians = getKarkenVector3fWithRadians(bone.getRotation().get());
            // 为空则应用本次赋值
            if (peeked == null) {
                peeked = withRadians;
            }
            KarkenVector3f rotation = getKarkenVector3fWithLerp(peeked, withRadians, tickDelta);
            MonsterEngine.getLogger().info("tick delta {} peeked {} current {}",tickDelta,peeked,withRadians);
            modelPart.setRotation(rotation.getX(), rotation.getY(), rotation.getZ());
            // 存储上一个动画的效果
            transitioned.push(rotation);
        }
    }

    // 线性插值计算
    private KarkenVector3f getKarkenVector3fWithLerp(KarkenVector3f start, KarkenVector3f end, float delta) {
        float x = lerp(start.getX(), end.getX(), delta);
        float y = lerp(start.getY(), end.getY(), delta);
        float z = lerp(start.getZ(), end.getZ(), delta);
        return new KarkenVector3f(x, y, z);
    }

    // 实现基本的线性插值
    private float lerp(float start, float end, float delta) {
        return start + delta * (end - start);
    }

    public KarkenVector3f getKarkenVector3fWithRadians(KarkenVector3f vector3f) {
        return new KarkenVector3f(
                (float) Math.toRadians(vector3f.getX()),
                (float) Math.toRadians(vector3f.getY()),
                (float) Math.toRadians(vector3f.getZ())
        );
    }

    /**
     * //        modelPart.x = bone.getPosition().get().getX();
     * //        modelPart.y = bone.getPosition().get().getY();
     * //        modelPart.z = bone.getPosition().get().getZ();
     * //        modelPart.xScale = bone.getScale().get().getX();
     * //        modelPart.yScale = bone.getScale().get().getY();
     * //        modelPart.zScale = bone.getScale().get().getZ();
     */

}

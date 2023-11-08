package com.gitee.karken.core.animation;

import com.gitee.karken.core.player.bone.KarkenBone;
import com.gitee.karken.core.animation.serializer.AnimationMetaBone;

public interface AnimationController {

    void onTick();

    void set(Animation animation);

    KarkenBone getBone(String boneId);

    default KarkenBone getBone(AnimationMetaBone bone) {
        return getBone(bone.id());
    }

    void set(Animation animation, Long delay);

    Animation getAnimation();

}

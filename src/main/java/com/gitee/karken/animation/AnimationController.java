package com.gitee.karken.animation;

import com.gitee.karken.bone.KarkenBone;
import com.gitee.karken.animation.serializer.AnimationMetaBone;

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

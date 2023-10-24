package com.gitee.karken.animation;

import com.gitee.karken.animation.loop.AnimationTransitioned;
import com.gitee.karken.bone.KarkenBone;

import javax.annotation.Nullable;

public interface Animation {

    boolean onTick();

    Lifecycle getLifecycle();

    void setLifecycle(Lifecycle lifecycle);

    Long getHealth();

    Long getTick();

    @Nullable
    KarkenBone getBone(String boneId);

    AnimationTransitioned getTransitioned();

    enum Lifecycle {

        CREATED, RUNNING, CLOSED

    }

}

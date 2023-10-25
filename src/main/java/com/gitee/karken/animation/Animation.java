package com.gitee.karken.animation;

import com.gitee.karken.animation.loop.AnimationTransitioned;
import com.gitee.karken.bone.KarkenBone;

import javax.annotation.Nullable;

public interface Animation {

    boolean onTick();

    Lifecycle getLifecycle();

    float getInterpolation();

    void setLifecycle(Lifecycle lifecycle);

    Long getHealth();

    Long getTick();

    float getTickDelta();

    void setTickDelta(float delta);

    @Nullable
    KarkenBone getBone(String boneId);

    enum Lifecycle {

        CREATED, RUNNING, CLOSED

    }

}

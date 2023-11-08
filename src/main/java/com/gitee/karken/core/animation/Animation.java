package com.gitee.karken.core.animation;

import com.gitee.karken.core.player.bone.KarkenBone;
public interface Animation {

    boolean onTick();

    Lifecycle getLifecycle();

    float getInterpolation();

    void setLifecycle(Lifecycle lifecycle);

    Long getHealth();

    Long getTick();

    float getTickDelta();

    void setTickDelta(float delta);

    KarkenBone getBone(String boneId);

    enum Lifecycle {

        CREATED, RUNNING, CLOSED

    }

}

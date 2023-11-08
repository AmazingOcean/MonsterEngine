package com.gitee.karken.core.player.bone;

import com.gitee.karken.core.animation.KarkenStackLinearity;
import com.gitee.karken.core.animation.serializer.AnimationMetaBone;

public interface KarkenBone {


    AnimationMetaBone getBone();

    KarkenStackLinearity getScale();

    KarkenStackLinearity getRotation();

    KarkenStackLinearity getPosition();

}

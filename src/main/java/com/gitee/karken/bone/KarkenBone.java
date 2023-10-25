package com.gitee.karken.bone;

import com.gitee.karken.animation.KarkenStackLinearity;
import com.gitee.karken.animation.serializer.AnimationMetaBone;

public interface KarkenBone {

    AnimationMetaBone getBone();

    KarkenStackLinearity getScale();

    KarkenStackLinearity getRotation();

    KarkenStackLinearity getPosition();

}

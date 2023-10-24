package com.gitee.karken.bone;

import com.gitee.karken.animation.KarkenStackLinearity;
import com.gitee.karken.serializer.AnimationMetaBone;
import com.gitee.karken.vector.KarkenVector3f;

public interface KarkenBone {

    AnimationMetaBone getBone();

    KarkenStackLinearity getScale();

    KarkenStackLinearity getRotation();

    KarkenStackLinearity getPosition();

}

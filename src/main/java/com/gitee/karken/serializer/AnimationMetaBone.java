package com.gitee.karken.serializer;

import com.gitee.karken.vector.KarkenMomentumTicks3f;

public interface AnimationMetaBone extends AnimationMeta {


    String id();

    KarkenMomentumTicks3f rotation();

    KarkenMomentumTicks3f position();

    KarkenMomentumTicks3f scale();



}

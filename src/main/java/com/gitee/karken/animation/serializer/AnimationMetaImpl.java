package com.gitee.karken.animation.serializer;

import javax.annotation.Nullable;
import java.util.Map;

public interface AnimationMetaImpl extends AnimationMeta {

    String id();

    double length();

    Map<String, AnimationMetaBone> bones();

    @Nullable
    AnimationMetaBone getBone(String boneId);

}

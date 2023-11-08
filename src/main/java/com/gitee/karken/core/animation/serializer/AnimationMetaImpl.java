package com.gitee.karken.core.animation.serializer;

import java.util.Map;

public interface AnimationMetaImpl extends AnimationMeta {

    String id();

    double length();

    Map<String, AnimationMetaBone> bones();

    AnimationMetaBone getBone(String boneId);

}

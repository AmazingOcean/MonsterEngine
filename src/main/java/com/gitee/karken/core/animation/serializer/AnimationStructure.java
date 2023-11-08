package com.gitee.karken.core.animation.serializer;

import java.util.Map;

public interface AnimationStructure extends AnimationMeta  {

    String version();

    Map<String, AnimationMetaImpl> animationImpls();

    AnimationMetaImpl getAnimation(String id);

}

package com.gitee.karken.animation.serializer;

import javax.annotation.Nullable;
import java.util.Map;

public interface AnimationStructure extends AnimationMeta  {

    String version();

    Map<String, AnimationMetaImpl> animationImpls();

    @Nullable
    AnimationMetaImpl getAnimation(String id);

}

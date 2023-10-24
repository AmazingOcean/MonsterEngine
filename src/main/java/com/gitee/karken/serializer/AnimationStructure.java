package com.gitee.karken.serializer;

import javax.annotation.Nullable;
import java.util.Map;

public interface AnimationStructure extends AnimationMeta  {

    String version();

    Map<String, AnimationMetaImpl> animationImpls();

    @Nullable
    AnimationMetaImpl getAnimation(String id);

}

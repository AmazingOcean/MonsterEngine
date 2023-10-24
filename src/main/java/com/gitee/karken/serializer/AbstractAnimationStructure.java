package com.gitee.karken.serializer;

import java.util.Map;

public class AbstractAnimationStructure implements AnimationStructure {
    @Override
    public String version() {
        return null;
    }

    @Override
    public Map<String, AnimationMetaImpl> animationImpls() {
        return null;
    }

    @Override
    public AnimationMetaImpl getAnimation(String id) {
        return animationImpls().get(id);
    }
}

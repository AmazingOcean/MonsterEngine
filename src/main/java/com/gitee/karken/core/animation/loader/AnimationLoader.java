package com.gitee.karken.core.animation.loader;

import com.gitee.karken.core.animation.serializer.AnimationStructure;

import java.util.Map;

public interface AnimationLoader {

    Map<String, AnimationStructure> getAnimationStructures();

    AnimationStructure getAnimationStructure(String id);

    void register(String name,AnimationStructure structure);

}

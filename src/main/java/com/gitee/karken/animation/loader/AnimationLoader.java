package com.gitee.karken.animation.loader;

import com.gitee.karken.serializer.AnimationStructure;

import javax.annotation.Nullable;
import java.util.Map;

public interface AnimationLoader {

    Map<String, AnimationStructure> getAnimationStructures();

    @Nullable
    AnimationStructure getAnimationStructure(String id);

    void register(String name,AnimationStructure structure);

}

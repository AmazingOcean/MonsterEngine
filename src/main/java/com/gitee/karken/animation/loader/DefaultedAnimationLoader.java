package com.gitee.karken.animation.loader;

import com.gitee.karken.animation.serializer.AnimationStructure;
import com.google.common.collect.Maps;

import javax.annotation.Nullable;
import java.util.Map;

public class DefaultedAnimationLoader implements AnimationLoader {

    public static DefaultedAnimationLoader INSTANCE = new DefaultedAnimationLoader();

    private Map<String, AnimationStructure> structures = Maps.newLinkedHashMap();

    @Override
    public Map<String, AnimationStructure> getAnimationStructures() {
        return structures;
    }

    @Nullable
    @Override
    public AnimationStructure getAnimationStructure(String id) {
        return structures.get(id);
    }

    @Override
    public void register(String name,AnimationStructure structure) {
        this.structures.put(name,structure);
        System.out.println("[DefaultedAnimationLoader] animation " + structure);
    }

//    private List<File> getFiles(ResourceLocation location, ResourceManager manager) {
//
//    }

}

package com.gitee.karken.core.player.serializer;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface AnimatedModel {

    String version();

    List<AnimatedBone> bones();

    AnimatedDescription description();

    default List<AnimatedBone> getBonesWithParent(AnimatedBone parent) {
        return bones().stream().filter(bone -> Objects.equals(bone.parent(), parent.name())).collect(Collectors.toList());
    }

}

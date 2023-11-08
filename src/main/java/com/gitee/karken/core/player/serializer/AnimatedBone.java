package com.gitee.karken.core.player.serializer;

import com.gitee.karken.util.vector.KarkenVector3d;
import com.gitee.karken.util.vector.KarkenVector3i;

import java.util.List;

public interface AnimatedBone {

    String name();

    String parent();

    KarkenVector3d pivot();

    List<AnimatedCube> cubes();

    float inflation();

}

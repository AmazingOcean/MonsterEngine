package com.gitee.karken.core.player;

import com.gitee.karken.core.player.serializer.AnimatedBone;
import com.gitee.karken.core.player.serializer.AnimatedCube;
import com.gitee.karken.core.player.serializer.AnimatedModel;
import com.gitee.karken.util.vector.KarkenVector;
import com.gitee.karken.util.vector.KarkenVector3d;
import com.gitee.karken.util.vector.KarkenVector3f;
import com.gitee.karken.util.vertex.KarkenQuad;
import com.gitee.karken.util.vertex.KarkenVertexSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.core.Direction;

import java.util.EnumMap;
import java.util.List;

public class KarkenAnimatedModel {

    private List<KarkenAnimatedBone> bones = Lists.newArrayList();

    public KarkenAnimatedModel(AnimatedModel model) {
        model.bones().forEach(bone -> {
            bones.add(createKarkenAnimatedBone(model, null, bone));
        });
    }

    private KarkenAnimatedBone createKarkenAnimatedBone(AnimatedModel model, KarkenAnimatedBone parent, AnimatedBone bone) {
        KarkenAnimatedBone animatedBone = new KarkenAnimatedBone(bone.name(), parent, bone.inflation());
        model.getBonesWithParent(bone).forEach(b -> {
            animatedBone.getChildren().add(createKarkenAnimatedBone(model, animatedBone, b));
        });
        return animatedBone;
    }

    private KarkenAnimatedCube createKarkenAnimatedCube(AnimatedBone bone, AnimatedCube cube) {
        KarkenVector3d origin = cube.origin();
        KarkenVector3d size = cube.size();
        KarkenVector3d rotation = cube.rotation();
        KarkenVector3d pivot = cube.pivot().multiply(-1, 1, 1);
        // 重新赋值
        origin = new KarkenVector3d(-(origin.getX() + size.getX()) / 16f, origin.getY() / 16f, origin.getZ() / 16f);
        KarkenVector3d vertexSize = size.multiply(1 / 16d);
        rotation = new KarkenVector3d(Math.toRadians(-rotation.getX()), Math.toRadians(-rotation.getY()), Math.toRadians(rotation.getZ()));

        return new KarkenAnimatedCube(
                createKarkenQuadEnumMap(cube, new KarkenVertexSet(origin, vertexSize, cube.inflate())),
                pivot, rotation, size
        );
    }

    private EnumMap<Direction, KarkenQuad> createKarkenQuadEnumMap(AnimatedCube cube, KarkenVertexSet vertexSet) {
        EnumMap<Direction, KarkenQuad> map = Maps.newEnumMap(Direction.class);
        for (Direction direction : Direction.values()) {
            map.put(direction, KarkenQuad.buildBox(vertexSet, cube, direction));
        }
        return map;
    }

    public List<KarkenAnimatedBone> getBones() {
        return bones;
    }


}

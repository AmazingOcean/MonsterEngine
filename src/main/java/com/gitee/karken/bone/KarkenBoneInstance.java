package com.gitee.karken.bone;

import com.gitee.karken.animation.KarkenStackLinearity;
import com.gitee.karken.animation.serializer.AnimationMetaBone;

public class KarkenBoneInstance implements KarkenBone {

    private AnimationMetaBone bone;

    private KarkenStackLinearity position;

    private KarkenStackLinearity rotation;

    private KarkenStackLinearity scale;

    public KarkenBoneInstance(AnimationMetaBone bone) {
        this.bone = bone;
        this.position = bone.position().toKarkenStackLinearity();
        this.rotation = bone.rotation().toKarkenStackLinearity();
        this.scale = bone.scale().toKarkenStackLinearity();
    }

    public AnimationMetaBone getBone() {
        return bone;
    }

    @Override
    public KarkenStackLinearity getScale() {
        return scale;
    }

    @Override
    public KarkenStackLinearity getRotation() {
        return rotation;
    }

    @Override
    public KarkenStackLinearity getPosition() {
        return position;
    }
}

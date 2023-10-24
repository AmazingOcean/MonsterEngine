package com.gitee.karken.serializer;

import com.gitee.karken.serializer.json.JsonAnimationBone;

import java.util.Map;

public abstract class AbstractAnimationMetaImpl implements AnimationMetaImpl {
    @Override
    public String id() {
        return null;
    }

    @Override
    public double length() {
        return 0;
    }

    @Override
    public Map<String, AnimationMetaBone> bones() {
        return null;
    }

    @Override
    public AnimationMetaBone getBone(String boneId) {
        return bones().get(boneId);
    }
}

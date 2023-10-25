package com.gitee.karken.animation.serializer.json;

import com.gitee.karken.animation.serializer.AnimationMeta;
import com.google.gson.JsonObject;

public interface JsonAnimationMeta extends AnimationMeta {

    void read(JsonObject object);

}

package com.gitee.karken.serializer.json;

import com.gitee.karken.serializer.AnimationMeta;
import com.google.gson.JsonObject;

public interface JsonAnimationMeta extends AnimationMeta {

    void read(JsonObject object);

}

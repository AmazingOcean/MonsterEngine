package com.gitee.karken.util.json;

import com.gitee.karken.util.KarkenJson;
import com.gitee.karken.util.vector.KarkenVector3d;
import com.gitee.karken.util.vector.KarkenVector3f;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.List;

public class KarkenJsonArray {

    public List<KarkenJsonElement> elements = Lists.newArrayList();

    public KarkenJsonArray(JsonArray jsonArray) {
        for (JsonElement element : jsonArray) {
            this.elements.add(new KarkenJsonElement(element));
        }
    }

    public KarkenJsonElement get(int index) {
        // element size = 3 , index = 3
        if (this.elements.size() <= index) {
            return new KarkenJsonElement(null);
        }
        return this.elements.get(index);
    }


    public List<KarkenJsonElement> getValues() {
        return elements;
    }

    public KarkenVector3d getKarkenVector3d() {
        return new KarkenVector3d(get(0).getDouble(), get(1).getDouble(), get(2).getDouble());
    }
    public KarkenVector3f getKarkenVector3f() {
        return new KarkenVector3f(get(0).getFloat(), get(1).getFloat(), get(2).getFloat());
    }



}

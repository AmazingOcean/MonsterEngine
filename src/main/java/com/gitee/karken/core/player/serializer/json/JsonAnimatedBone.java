package com.gitee.karken.core.player.serializer.json;

import com.gitee.karken.core.player.serializer.AnimatedBone;
import com.gitee.karken.core.player.serializer.AnimatedCube;
import com.gitee.karken.util.KarkenJson;
import com.gitee.karken.util.json.KarkenJsonElement;
import com.gitee.karken.util.json.KarkenJsonObject;
import com.gitee.karken.util.vector.KarkenVector3d;
import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class JsonAnimatedBone implements AnimatedBone {

    private String name;

    private String parent;

    private KarkenVector3d pivot;

    private KarkenVector3d size;

    private List<AnimatedCube> cubes = Lists.newArrayList();

    private boolean mirror;

    private float inflation;

    public JsonAnimatedBone(KarkenJsonObject object) {
        this.parent = object.getString("parent");
        this.name = object.getString("name");
        this.pivot = object.getArray("pivot").getKarkenVector3d();
        this.size = object.getArray("size").getKarkenVector3d();
        this.mirror = object.get("mirror").getBoolean();
        for (KarkenJsonElement cube : object.getArray("cubes").getValues()) {
            this.cubes.add(new JsonAnimatedCube(cube.getObject()));
        }
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String parent() {
        return parent;
    }

    @Override
    public KarkenVector3d pivot() {
        return pivot;
    }

    @Override
    public float inflation() {
        return inflation;
    }

    @Override
    public List<AnimatedCube> cubes() {
        return cubes;
    }

    @Override
    public String toString() {
        return "JsonAnimatedBone{" +
                "name='" + name + '\'' +
                ", parent='" + parent + '\'' +
                ", pivot=" + pivot +
                ", size=" + size +
                ", cubes=" + cubes +
                ", mirror=" + mirror +
                ", inflation=" + inflation +
                '}';
    }
}

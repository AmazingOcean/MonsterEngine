package com.gitee.karken.util.json;

import com.gitee.karken.util.vector.KarkenVector3d;
import com.gitee.karken.util.vector.KarkenVector3f;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public class KarkenJsonElement {

    private JsonElement element;

    public KarkenJsonElement(JsonElement element) {
        this.element = element;
    }

    public Object getOrNull() {
        return element;
    }

    public JsonElement getOrElse(JsonElement defaultValue) {
        return Optional.ofNullable(this.element).orElse(defaultValue);
    }

    public JsonElement getOrElse(Supplier<JsonElement> supplier) {
        return Optional.ofNullable(this.element).orElseGet(supplier);
    }

    public String getString() {
        return getStringOrElse(null);
    }

    public String getStringOrElse(String defaultValue) {
        // 直接截断返回
        if (this.element == null && defaultValue == null) {
            return null;
        }
        // 防止异常处理方法
        return getOrElse(() -> new JsonPrimitive(defaultValue)).getAsString();
    }

    public Double getDouble() {
        return getDoubleElse(0.0);
    }

    public Double getDoubleElse(double defaultValue) {
        return getOrElse(new JsonPrimitive(defaultValue)).getAsDouble();
    }

    public Float getFloat() {
        return getFloatElse(0f);
    }

    public Float getFloatElse(float defaultValue) {
        return getOrElse(new JsonPrimitive(defaultValue)).getAsFloat();
    }

    public boolean getBoolean() {
        return getBooleanOrElse(false);
    }

    public boolean getBooleanOrElse(boolean defaultValue) {
        return getOrElse(new JsonPrimitive(defaultValue)).getAsBoolean();
    }

    public KarkenJsonArray getArray() {
        if (element == null) {
            return new KarkenJsonArray(new JsonArray());
        }
        return new KarkenJsonArray(element.getAsJsonArray());
    }

    public KarkenJsonObject getObject() {
        if (element == null) {
            return new KarkenJsonObject(null);
        }
        return new KarkenJsonObject(element.getAsJsonObject());
    }

    public JsonElement getJsonElement() {
        return element;
    }

    public KarkenVector3d getKarkenVector3d() {
        return getArray().getKarkenVector3d();
    }

    public KarkenVector3f getKarkenVector3f() {
        return getArray().getKarkenVector3f();
    }


}

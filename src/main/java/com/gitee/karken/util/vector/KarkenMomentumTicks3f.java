package com.gitee.karken.util.vector;

import com.gitee.karken.core.animation.KarkenStackLinearity;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 渐进式动量控制器（tick）
 */
public class KarkenMomentumTicks3f {

    private final List<Element> vector3fs = Lists.newLinkedList();

    public KarkenMomentumTicks3f(Map<Float, KarkenVector3f> vector3fs) {
        vector3fs.forEach((key, value) -> this.vector3fs.add(new Element((long) (key * 20), value)));
        System.out.println(this.vector3fs);
    }

    @Override
    public String toString() {
        return "KarkenMomentumTicks3f{" +
                "vector3fs=" + vector3fs +
                '}';
    }

    public KarkenStackLinearity toKarkenStackLinearity() {
        Map<Long, KarkenVector3f> collect = vector3fs.stream().collect(Collectors.toMap(Element::getTick, Element::getVector3f));
        return new KarkenStackLinearity(collect);
    }

    public static class Element {

        public Long tick;

        public KarkenVector3f vector3f;

        public Element(Long tick, KarkenVector3f vector3f) {
            this.tick = tick;
            this.vector3f = vector3f;
        }

        public Long getTick() {
            return tick;
        }

        public void setTick(Long tick) {
            this.tick = tick;
        }

        public KarkenVector3f getVector3f() {
            return vector3f;
        }

        public void setVector3f(KarkenVector3f vector3f) {
            this.vector3f = vector3f;
        }

        @Override
        public String toString() {
            return "Element{" +
                    "tick=" + tick +
                    ", vector3f=" + vector3f +
                    '}';
        }
    }

}

package com.gitee.karken;

import com.gitee.karken.animation.KarkenStackLinearity;
import com.gitee.karken.vector.KarkenVector3f;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;

public class Test {

    public static void main(String[] args) {

        Map<Long, KarkenVector3f> vector3fMap = new HashMap<>();
        vector3fMap.put(0L,new KarkenVector3f(0f,0f,0f));
        vector3fMap.put(360L,new KarkenVector3f(-360f,0f,0f));
        KarkenStackLinearity linearity = new KarkenStackLinearity(vector3fMap);
        linearity.get(1L);
        System.out.println(linearity.get());
    }

}

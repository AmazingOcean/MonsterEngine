package com.gitee.karken.animation;

import com.gitee.karken.vector.KarkenVector;
import com.gitee.karken.vector.KarkenVector3f;
import com.google.common.collect.Maps;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class KarkenStackLinearity {

    private Map<Long, KarkenVector3f> table = new TreeMap<>(Comparator.naturalOrder());

    /**
     * 下一帧的动画
     */
    private long select;
    ;

    public KarkenStackLinearity(Map<Long, KarkenVector3f> vector3fs) {
        this.table.putAll(vector3fs);
        this.get(0L);
        System.out.println("table " + table);
    }

    public KarkenVector3f next(Long currentTick) {
        // 如果是第0帧 则直接应用第0帧的动画
        if (currentTick == 0L) {
            select = 0L;
        }
        // 应用下一帧的动画
        else {
            for (Map.Entry<Long, KarkenVector3f> entry : table.entrySet()) {
                if (entry.getKey() >= currentTick) {
                    this.select = entry.getKey();
                    break;
                }
            }
        }

        return get();
    }

    public KarkenVector3f get() {
        return Optional.ofNullable(this.table.get(this.select)).orElse(new KarkenVector3f(0f, 0f, 0f));
    }

    public KarkenVector3f get(Long currentTick) {
        return next(currentTick);
    }

}

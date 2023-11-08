package com.gitee.karken.core.player.serializer;

import com.gitee.karken.util.vector.KarkenVector3d;
import com.gitee.karken.util.vector.KarkenVector3f;
import com.gitee.karken.util.vector.KarkenVector3i;

public interface AnimatedCube {

    double inflate();

    boolean mirror();

    KarkenVector3d origin();

    KarkenVector3d size();

    /**
     * 一个表示立方体旋转轴心点的向量。在进行旋转动画时，这个轴心点是立方体旋转的中心。
     * @return 立方体旋转轴心点的向量
     */
    KarkenVector3d pivot();

    KarkenVector3d rotation();

    AnimatedUV uv();

}

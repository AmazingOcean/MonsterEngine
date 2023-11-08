package com.gitee.karken.util;

import com.gitee.karken.util.vector.KarkenVector3d;
import com.gitee.karken.util.vector.KarkenVector3f;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public class KarkenMatrix4f {

    private Matrix4f matrix4f;

    public KarkenMatrix4f(Matrix4f matrix4f) {
        this.matrix4f = matrix4f;
    }

    public KarkenMatrix4f clone() {
        return new KarkenMatrix4f(new Matrix4f(matrix4f));
    }

    public Vector4f transform(KarkenVector3f vector3f) {
        return matrix4f.transform(new Vector4f(vector3f.getX(), vector3f.getY(), vector3f.getZ(), 1f));
    }

    public Vector4f transform(KarkenVector3d vector3d) {
        return transform(vector3d.getKarkenVector3f());
    }
}

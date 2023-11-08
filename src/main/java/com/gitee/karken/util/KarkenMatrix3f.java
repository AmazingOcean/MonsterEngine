package com.gitee.karken.util;

import com.gitee.karken.util.vector.KarkenVector3f;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class KarkenMatrix3f {

    private Matrix3f matrix3f;

    public KarkenMatrix3f(Matrix3f matrix3f) {
        this.matrix3f = matrix3f;
    }

    public KarkenVector3f transform(KarkenVector3f vector3f) {
        Vector3f transform = matrix3f.transform(new Vector3f(vector3f.getX(), vector3f.getY(), vector3f.getZ()));
        return new KarkenVector3f(transform.x, transform.y, transform.z);
    }


    public KarkenMatrix3f clone() {
        return new KarkenMatrix3f(new Matrix3f(matrix3f));
    }

}

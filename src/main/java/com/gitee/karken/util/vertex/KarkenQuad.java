package com.gitee.karken.util.vertex;

import com.gitee.karken.core.player.serializer.AnimatedCube;
import com.gitee.karken.core.player.serializer.AnimatedDescription;
import com.gitee.karken.core.player.serializer.AnimatedUV;
import com.gitee.karken.util.KarkenJson;
import com.gitee.karken.util.vector.KarkenVector3d;
import com.gitee.karken.util.vector.KarkenVector3f;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.Direction;
import org.joml.Vector3f;

import java.util.Arrays;

/**
 * 表示一个四边形
 */
public class KarkenQuad {

    /**
     * 四个顶点
     */
    private KarkenVertex[] vertices;

    // 法向量
    private KarkenVector3f normal;

    private Direction direction;

    public KarkenQuad(KarkenVertex[] vertices, KarkenVector3f normal, Direction direction) {
        this.vertices = vertices;
        this.normal = normal;
        this.direction = direction;
    }

    public KarkenVertex getVertex(int index) {
        return vertices[index];
    }

    public KarkenVertex[] getVertices() {
        return vertices;
    }

    public void setVertices(KarkenVertex[] vertices) {
        this.vertices = vertices;
    }

    public KarkenVector3f getNormal() {
        return normal;
    }

    public void setNormal(KarkenVector3f normal) {
        this.normal = normal;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public static KarkenVertexSet.Face buildFace(Direction direction, KarkenVertex[] vertices) {
        return new KarkenVertexSet.Face(direction, vertices);
    }

    public static KarkenQuad build(AnimatedDescription description, KarkenVertexSet.Face face, AnimatedUV.Texture animatedUV) {
        KarkenVertex[] vertices = face.getVertices();
        float textureWidth = (float) description.textureWidth();
        float textureHeight = (float) description.textureHeight();
        float textureAdjustU = 0.0F / textureWidth;
        float textureAdjustV = 0.0F / textureHeight;
        double textureStartU = animatedUV.getX();
        double textureStartV = animatedUV.getY();
        double textureEndU = animatedUV.getWidth();
        double textureEndV = animatedUV.getHeight();
        vertices[0] = vertices[0].remap(textureEndU / textureWidth - textureAdjustU, textureStartV / textureHeight + textureAdjustV);
        vertices[1] = vertices[1].remap(textureStartU / textureWidth + textureAdjustU, textureStartV / textureHeight + textureAdjustV);
        vertices[2] = vertices[2].remap(textureStartU / textureWidth + textureAdjustU, textureEndV / textureHeight - textureAdjustV);
        vertices[3] = vertices[3].remap(textureEndU / textureWidth - textureAdjustU, textureEndV / textureHeight - textureAdjustV);
//        if (mirror) {
//            int vertexCount = vertices.length;
//            for (int i = 0; i < vertexCount / 2; ++i) {
//                KarkenVertex tempVertex = vertices[i];
//                vertices[i] = vertices[vertexCount - 1 - i];
//                vertices[vertexCount - 1 - i] = tempVertex;
//            }
//        }

        KarkenVector3f normal = step(face.getDirection());
//        if (mirror) {
//            normal = normal.negationX();
//        }
        return new KarkenQuad(vertices, normal, face.getDirection());
    }

    public static KarkenVector3f step(Direction direction) {
        Vector3f step = direction.step();
        return new KarkenVector3f(step.x, step.y, step.z);
    }

    @Override
    public String toString() {
        return "KarkenQuad{" +
                "vertices=" + Arrays.toString(vertices) +
                ", normal=" + normal +
                ", direction=" + direction +
                '}';
    }
}

package com.gitee.karken.util.vertex;

import com.gitee.karken.core.player.serializer.AnimatedCube;
import com.gitee.karken.core.player.serializer.AnimatedUV;
import com.gitee.karken.util.vector.KarkenVector3d;
import com.gitee.karken.util.vector.KarkenVector3f;
import net.minecraft.core.Direction;

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

    public static float DEFAULT_TEXTURE_WEIGHT = 256;

    public static float DEFAULT_TEXTURE_HEIGHT = 256;

    public static KarkenQuad build(KarkenVertexSet vertexSet, AnimatedCube cube, Direction direction) {
        // 渲染盒子块
        AnimatedUV uv = cube.uv();
        if (uv instanceof AnimatedUV.Box) {
            return buildBox(vertexSet, cube, direction);
        }
        // 渲染自定义模型
        else {
            AnimatedUV.Quad animatedUV = (AnimatedUV.Quad) uv;
            AnimatedUV.Texture texture = animatedUV.getTexture(direction);
            if (texture == null) {
                return null;
            }
            return build(vertexSet.getFaceByDirection(direction), texture);
        }
    }

    public static KarkenQuad buildBox(KarkenVertexSet vertexSet, AnimatedCube cube, Direction direction) {
        AnimatedUV.Texture animatedTexture = getAnimatedTexture(cube.size().floor(), (AnimatedUV.Box) cube.uv(), direction);
        KarkenVertexSet.Face face = vertexSet.getFaceByDirection(direction);
        return build(face, animatedTexture);
    }

    public static KarkenQuad build(KarkenVertexSet.Face face, AnimatedUV.Texture animatedUV) {
        float width = (float) ((animatedUV.getX() + animatedUV.getWidth()) / DEFAULT_TEXTURE_WEIGHT);
        float height = (float) ((animatedUV.getY() + animatedUV.getHeight()) / DEFAULT_TEXTURE_HEIGHT);
        float u = (float) (animatedUV.getX() / DEFAULT_TEXTURE_WEIGHT);
        float v = (float) (animatedUV.getY() / DEFAULT_TEXTURE_HEIGHT);
        KarkenVector3f normal = new KarkenVector3f(face.getDirection().step());
        face.getVertices()[0] = face.getVertices()[0].withTexture(u, v);
        face.getVertices()[1] = face.getVertices()[1].withTexture(width, v);
        face.getVertices()[2] = face.getVertices()[2].withTexture(width, height);
        face.getVertices()[3] = face.getVertices()[2].withTexture(u, height);
        return new KarkenQuad(face.getVertices(), normal, face.getDirection());
    }

    public static AnimatedUV.Texture getAnimatedTexture(KarkenVector3d size, AnimatedUV.Box box, Direction direction) {
        double ux = box.getX();
        double uy = box.getY();

        return switch (direction) {
            case UP -> new AnimatedUV.Texture(ux + size.getZ(), uy, size.getX(), size.getZ());

            case DOWN ->
                    new AnimatedUV.Texture(ux + size.getZ() + size.getX(), uy + size.getZ(), size.getX(), size.getZ());

            case WEST ->
                    new AnimatedUV.Texture(ux + size.getZ() + size.getX(), uy + size.getZ(), size.getZ(), size.getY());

            case EAST -> new AnimatedUV.Texture(ux, uy + size.getZ(), size.getZ(), size.getY());

            case NORTH -> new AnimatedUV.Texture(ux + size.getZ(), uy + size.getZ(), size.getX(), size.getY());

            case SOUTH ->
                    new AnimatedUV.Texture(ux + size.getZ() + size.getX() + size.getZ(), uy + size.getZ(), size.getX(), size.getY());

        };
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

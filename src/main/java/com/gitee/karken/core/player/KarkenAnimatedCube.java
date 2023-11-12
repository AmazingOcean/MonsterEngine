package com.gitee.karken.core.player;

import com.gitee.karken.core.player.serializer.AnimatedCube;
import com.gitee.karken.core.player.serializer.AnimatedDescription;
import com.gitee.karken.core.player.serializer.AnimatedUV;
import com.gitee.karken.util.vector.KarkenVector3d;
import com.gitee.karken.util.vector.KarkenVector3f;
import com.gitee.karken.util.vertex.KarkenQuad;
import com.gitee.karken.util.vertex.KarkenVertex;
import com.google.common.collect.Maps;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.Direction;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public class KarkenAnimatedCube {

    private KarkenQuad[] quads;

    private KarkenVector3f pivot = new KarkenVector3f(0f, 0f, 0f);

    private KarkenVector3f rotation = new KarkenVector3f(0f, 0f, 0f);

    private KarkenVector3f size = new KarkenVector3f(0f, 0f, 0f);

    public KarkenAnimatedCube(KarkenQuad[] quads) {
        this.quads = quads;
    }

    public KarkenQuad[] getQuads() {
        return quads;
    }

    public KarkenVector3f getPivot() {
        return pivot;
    }

    public void setPivot(KarkenVector3f pivot) {
        this.pivot = pivot;
    }

    public KarkenVector3f getRotation() {
        return rotation;
    }

    public void setRotation(KarkenVector3f rotation) {
        this.rotation = rotation;
    }

    public KarkenVector3f getSize() {
        return size;
    }

    public void setSize(KarkenVector3f size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "KarkenAnimatedCube{" +
                "quads=" + quads +
                ", pivot=" + pivot +
                ", rotation=" + rotation +
                ", size=" + size +
                '}';
    }



    public static class Builder {
        public float minX;
        public float minY;
        public float minZ;
        public float maxX;
        public float maxY;
        public float maxZ;
        public KarkenQuad[] quads;

        public Builder(AnimatedDescription desc, AnimatedUV.Box texture, KarkenVector3f origin, KarkenVector3f size, boolean mirror, Set<Direction> directions) {
            this(desc, texture, origin, size, KarkenVector3f.ZERO, mirror, directions);
        }

        public Builder(AnimatedDescription desc, AnimatedUV.Quad texture, KarkenVector3f origin, KarkenVector3f size, KarkenVector3f grow, boolean mirror, Set<Direction> directions) {
            float originX = origin.getX();
            float originY = origin.getY();
            float originZ = origin.getZ();

            this.minX = originX;
            this.minY = originY;
            this.minZ = originZ;
            this.maxX = originX + size.getX();
            this.maxY = originY + size.getY();
            this.maxZ = originZ + size.getZ();
            this.quads = new KarkenQuad[directions.size()];
            float expandedMaxX = originX + size.getX();
            float expandedMaxY = originY + size.getY();
            float expandedMaxZ = originZ + size.getZ();
            originX -= grow.getX();
            originY -= grow.getY();
            originZ -= grow.getZ();
            expandedMaxX += grow.getX();
            expandedMaxY += grow.getY();
            expandedMaxZ += grow.getZ();
            if (mirror) {
                float temp = expandedMaxX;
                expandedMaxX = originX;
                originX = temp;
            }

            KarkenVertex vertex1 = new KarkenVertex(originX, originY, originZ, 0.0F, 0.0F);
            KarkenVertex vertex2 = new KarkenVertex(expandedMaxX, originY, originZ, 0.0F, 8.0F);
            KarkenVertex vertex3 = new KarkenVertex(expandedMaxX, expandedMaxY, originZ, 8.0F, 8.0F);
            KarkenVertex vertex4 = new KarkenVertex(originX, expandedMaxY, originZ, 8.0F, 0.0F);
            KarkenVertex vertex5 = new KarkenVertex(originX, originY, expandedMaxZ, 0.0F, 0.0F);
            KarkenVertex vertex6 = new KarkenVertex(expandedMaxX, originY, expandedMaxZ, 0.0F, 8.0F);
            KarkenVertex vertex7 = new KarkenVertex(expandedMaxX, expandedMaxY, expandedMaxZ, 8.0F, 8.0F);
            KarkenVertex vertex8 = new KarkenVertex(originX, expandedMaxY, expandedMaxZ, 8.0F, 0.0F);

            int polygonIndex = 0;
            if (directions.contains(Direction.DOWN)) {
                this.quads[polygonIndex++] = KarkenQuad.build(desc, KarkenQuad.buildFace(Direction.DOWN, new KarkenVertex[]{vertex6, vertex5, vertex1, vertex2}), texture.getTexture(Direction.DOWN));
            }

            if (directions.contains(Direction.UP)) {
                this.quads[polygonIndex++] = KarkenQuad.build(desc, KarkenQuad.buildFace(Direction.UP, new KarkenVertex[]{vertex3, vertex4, vertex8, vertex7}), texture.getTexture(Direction.UP));
            }

            if (directions.contains(Direction.WEST)) {
                this.quads[polygonIndex++] = KarkenQuad.build(desc, KarkenQuad.buildFace(Direction.WEST, new KarkenVertex[]{vertex1, vertex5, vertex8, vertex4}), texture.getTexture(Direction.WEST));
            }

            if (directions.contains(Direction.NORTH)) {
                this.quads[polygonIndex++] = KarkenQuad.build(desc, KarkenQuad.buildFace(Direction.NORTH, new KarkenVertex[]{vertex2, vertex1, vertex4, vertex3}), texture.getTexture(Direction.NORTH));
            }

            if (directions.contains(Direction.EAST)) {
                this.quads[polygonIndex++] = KarkenQuad.build(desc, KarkenQuad.buildFace(Direction.EAST, new KarkenVertex[]{vertex6, vertex2, vertex3, vertex7}), texture.getAnimatedUVByDirection(Direction.EAST));
            }

            if (directions.contains(Direction.SOUTH)) {
                this.quads[polygonIndex] = KarkenQuad.build(desc, KarkenQuad.buildFace(Direction.SOUTH, new KarkenVertex[]{vertex5, vertex6, vertex7, vertex8}), texture.getTexture(Direction.SOUTH));
            }
        }

        public Builder(AnimatedDescription desc, AnimatedUV.Box texture, KarkenVector3f origin, KarkenVector3f size, KarkenVector3f grow, boolean mirror, Set<Direction> directions) {
            float originX = origin.getX();
            float originY = origin.getY();
            float originZ = origin.getZ();

            this.minX = originX;
            this.minY = originY;
            this.minZ = originZ;
            this.maxX = originX + size.getX();
            this.maxY = originY + size.getY();
            this.maxZ = originZ + size.getZ();
            this.quads = new KarkenQuad[directions.size()];
            float expandedMaxX = originX + size.getX();
            float expandedMaxY = originY + size.getY();
            float expandedMaxZ = originZ + size.getZ();
            originX -= grow.getX();
            originY -= grow.getY();
            originZ -= grow.getZ();
            expandedMaxX += grow.getX();
            expandedMaxY += grow.getY();
            expandedMaxZ += grow.getZ();
            if (mirror) {
                float temp = expandedMaxX;
                expandedMaxX = originX;
                originX = temp;
            }

            KarkenVertex vertex1 = new KarkenVertex(originX, originY, originZ, 0.0F, 0.0F);
            KarkenVertex vertex2 = new KarkenVertex(expandedMaxX, originY, originZ, 0.0F, 8.0F);
            KarkenVertex vertex3 = new KarkenVertex(expandedMaxX, expandedMaxY, originZ, 8.0F, 8.0F);
            KarkenVertex vertex4 = new KarkenVertex(originX, expandedMaxY, originZ, 8.0F, 0.0F);
            KarkenVertex vertex5 = new KarkenVertex(originX, originY, expandedMaxZ, 0.0F, 0.0F);
            KarkenVertex vertex6 = new KarkenVertex(expandedMaxX, originY, expandedMaxZ, 0.0F, 8.0F);
            KarkenVertex vertex7 = new KarkenVertex(expandedMaxX, expandedMaxY, expandedMaxZ, 8.0F, 8.0F);
            KarkenVertex vertex8 = new KarkenVertex(originX, expandedMaxY, expandedMaxZ, 8.0F, 0.0F);

            float textureU1 = (float) texture.getX();
            float textureU2 = (float) texture.getX() + size.getZ();
            float textureU3 = (float) texture.getX() + size.getZ() + size.getX();
            float textureU4 = (float) texture.getX() + size.getZ() + size.getX() + size.getX();
            float textureU5 = (float) texture.getX() + size.getZ() + size.getX() + size.getZ();
            float textureU6 = (float) texture.getX() + size.getZ() + size.getX() + size.getZ() + size.getX();
            float textureV1 = (float) texture.getY();
            float textureV2 = (float) texture.getY() + size.getZ();
            float textureV3 = (float) texture.getY() + size.getZ() + size.getY();

            int polygonIndex = 0;
            if (directions.contains(Direction.DOWN)) {
                this.quads[polygonIndex++] = KarkenQuad.build(desc, KarkenQuad.buildFace(Direction.DOWN, new KarkenVertex[]{vertex6, vertex5, vertex1, vertex2}), new AnimatedUV.Texture(textureU2, textureV1, textureU3, textureV2));
            }

            if (directions.contains(Direction.UP)) {
                this.quads[polygonIndex++] = KarkenQuad.build(desc, KarkenQuad.buildFace(Direction.UP, new KarkenVertex[]{vertex3, vertex4, vertex8, vertex7}), new AnimatedUV.Texture(textureU3, textureV2, textureU4, textureV1));
            }

            if (directions.contains(Direction.WEST)) {
                this.quads[polygonIndex++] = KarkenQuad.build(desc, KarkenQuad.buildFace(Direction.WEST, new KarkenVertex[]{vertex1, vertex5, vertex8, vertex4}), new AnimatedUV.Texture(textureU1, textureV2, textureU2, textureV3));
            }

            if (directions.contains(Direction.NORTH)) {
                this.quads[polygonIndex++] = KarkenQuad.build(desc, KarkenQuad.buildFace(Direction.NORTH, new KarkenVertex[]{vertex2, vertex1, vertex4, vertex3}), new AnimatedUV.Texture(textureU2, textureV2, textureU3, textureV3));
            }

            if (directions.contains(Direction.EAST)) {
                this.quads[polygonIndex++] = KarkenQuad.build(desc, KarkenQuad.buildFace(Direction.EAST, new KarkenVertex[]{vertex6, vertex2, vertex3, vertex7}), new AnimatedUV.Texture(textureU3, textureV2, textureU5, textureV3));
            }

            if (directions.contains(Direction.SOUTH)) {
                this.quads[polygonIndex] = KarkenQuad.build(desc, KarkenQuad.buildFace(Direction.SOUTH, new KarkenVertex[]{vertex5, vertex6, vertex7, vertex8}), new AnimatedUV.Texture(textureU5, textureV2, textureU6, textureV3));
            }
        }

        public KarkenAnimatedCube build() {
            return new KarkenAnimatedCube(this.quads);
        }

    }


}

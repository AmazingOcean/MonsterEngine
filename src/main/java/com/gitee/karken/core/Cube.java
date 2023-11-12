package com.gitee.karken.core;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.Direction;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.Set;

public class Cube {
    private final ModelPart.Polygon[] polygons;
    public final float minX;
    public final float minY;
    public final float minZ;
    public final float maxX;
    public final float maxY;
    public final float maxZ;

    public Cube(int i, int j, float f, float g, float h, float k, float l, float m, float n, float o, float p, boolean bl, float q, float r, Set<Direction> set) {
        this.minX = f;
        this.minY = g;
        this.minZ = h;
        this.maxX = f + k;
        this.maxY = g + l;
        this.maxZ = h + m;
        this.polygons = new ModelPart.Polygon[set.size()];
        float s = f + k;
        float t = g + l;
        float u = h + m;
        f -= n;
        g -= o;
        h -= p;
        s += n;
        t += o;
        u += p;
        if (bl) {
            float v = s;
            s = f;
            f = v;
        }

        ModelPart.Vertex vertex = new ModelPart.Vertex(f, g, h, 0.0F, 0.0F);
        ModelPart.Vertex vertex2 = new ModelPart.Vertex(s, g, h, 0.0F, 8.0F);
        ModelPart.Vertex vertex3 = new ModelPart.Vertex(s, t, h, 8.0F, 8.0F);
        ModelPart.Vertex vertex4 = new ModelPart.Vertex(f, t, h, 8.0F, 0.0F);
        ModelPart.Vertex vertex5 = new ModelPart.Vertex(f, g, u, 0.0F, 0.0F);
        ModelPart.Vertex vertex6 = new ModelPart.Vertex(s, g, u, 0.0F, 8.0F);
        ModelPart.Vertex vertex7 = new ModelPart.Vertex(s, t, u, 8.0F, 8.0F);
        ModelPart.Vertex vertex8 = new ModelPart.Vertex(f, t, u, 8.0F, 0.0F);
        float w = (float)i;
        float x = (float)i + m;
        float y = (float)i + m + k;
        float z = (float)i + m + k + k;
        float aa = (float)i + m + k + m;
        float ab = (float)i + m + k + m + k;
        float ac = (float)j;
        float ad = (float)j + m;
        float ae = (float)j + m + l;
        int af = 0;
        if (set.contains(Direction.DOWN)) {
            this.polygons[af++] = new ModelPart.Polygon(new ModelPart.Vertex[]{vertex6, vertex5, vertex, vertex2}, x, ac, y, ad, q, r, bl, Direction.DOWN);
        }

        if (set.contains(Direction.UP)) {
            this.polygons[af++] = new ModelPart.Polygon(new ModelPart.Vertex[]{vertex3, vertex4, vertex8, vertex7}, y, ad, z, ac, q, r, bl, Direction.UP);
        }

        if (set.contains(Direction.WEST)) {
            this.polygons[af++] = new ModelPart.Polygon(new ModelPart.Vertex[]{vertex, vertex5, vertex8, vertex4}, w, ad, x, ae, q, r, bl, Direction.WEST);
        }

        if (set.contains(Direction.NORTH)) {
            this.polygons[af++] = new ModelPart.Polygon(new ModelPart.Vertex[]{vertex2, vertex, vertex4, vertex3}, x, ad, y, ae, q, r, bl, Direction.NORTH);
        }

        if (set.contains(Direction.EAST)) {
            this.polygons[af++] = new ModelPart.Polygon(new ModelPart.Vertex[]{vertex6, vertex2, vertex3, vertex7}, y, ad, aa, ae, q, r, bl, Direction.EAST);
        }

        if (set.contains(Direction.SOUTH)) {
            this.polygons[af] = new ModelPart.Polygon(new ModelPart.Vertex[]{vertex5, vertex6, vertex7, vertex8}, aa, ad, ab, ae, q, r, bl, Direction.SOUTH);
        }

    }

    public void compile(PoseStack.Pose pose, VertexConsumer vertexConsumer, int i, int j, float f, float g, float h, float k) {
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        ModelPart.Polygon[] var11 = this.polygons;
        int var12 = var11.length;

        for(int var13 = 0; var13 < var12; ++var13) {
            ModelPart.Polygon polygon = var11[var13];
            Vector3f vector3f = matrix3f.transform(new Vector3f(polygon.normal));
            float l = vector3f.x();
            float m = vector3f.y();
            float n = vector3f.z();
            ModelPart.Vertex[] var19 = polygon.vertices;
            int var20 = var19.length;

            for(int var21 = 0; var21 < var20; ++var21) {
                ModelPart.Vertex vertex = var19[var21];
                float o = vertex.pos.x() / 16.0F;
                float p = vertex.pos.y() / 16.0F;
                float q = vertex.pos.z() / 16.0F;
                Vector4f vector4f = matrix4f.transform(new Vector4f(o, p, q, 1.0F));
                vertexConsumer.vertex(vector4f.x(), vector4f.y(), vector4f.z(), f, g, h, k, vertex.u, vertex.v, j, i, l, m, n);
            }
        }

    }
}

package com.gitee.karken.core.player;

import com.gitee.karken.core.player.serializer.*;
import com.gitee.karken.util.KarkenJson;
import com.gitee.karken.util.MathHelper;
import com.gitee.karken.util.vector.KarkenVector3f;
import com.gitee.karken.util.vertex.KarkenQuad;
import com.gitee.karken.util.vertex.KarkenVertex;
import com.gitee.karken.util.vertex.KarkenVertexSet;
import com.gitee.monsterengine.MonsterEngine;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import org.joml.*;

import java.lang.Math;
import java.util.*;
import java.util.stream.Collectors;

public class KarkenAnimatedModel {

    private transient AnimatedModel model;

    private List<KarkenAnimatedBone> bones = Lists.newArrayList();

    public KarkenAnimatedModel(AnimatedModel model) throws IllegalAccessException {
        this.model = model;
        if (model == null) {
            throw new IllegalAccessException("Model load failure.");
        }
        model.bones().forEach(bone -> {
            bones.add(createKarkenAnimatedBone(null, bone));
        });
    }

    private final ResourceLocation TEXTURE_RESOURCE_LOCATION = new ResourceLocation(MonsterEngine.MOD_ID, "textures/entity/player.png");

    /**
     * render
     */
    public void render(AbstractClientPlayer clientPlayer, Properties properties, PoseStack poseStack) {
        RenderType renderType = RenderType.entityCutoutNoCull(TEXTURE_RESOURCE_LOCATION);
        properties.buffer = properties.source.getBuffer(renderType);
        TextureManager manager = Minecraft.getInstance().getTextureManager();
        AbstractTexture texture = manager.getTexture(TEXTURE_RESOURCE_LOCATION);
        RenderSystem.setShaderTexture(0, texture.getId());

        for (KarkenAnimatedBone animatedBone : getTopAnimationBones()) {
            renderBone(poseStack, animatedBone, properties);
        }
    }

    public void renderBone(PoseStack poseStack, KarkenAnimatedBone animatedBone, Properties properties) {

        List<KarkenAnimatedBone> children = getBonesWithParent(animatedBone);
        // 忽略本次渲染
        if (animatedBone.getCubes().isEmpty() && children.isEmpty()) {
            return;
        }

        // 渲染矩形
        poseStack.pushPose();
        translateAndRotate(animatedBone, poseStack);
        PoseStack.Pose pose = poseStack.last();
        animatedBone.getCubes().forEach(cube -> renderCube(pose, cube, properties));

        for (KarkenAnimatedBone karkenAnimatedBone : children) {
            renderBone(poseStack, karkenAnimatedBone, properties);
        }

        poseStack.popPose();
    }

    public void translateAndRotate(KarkenAnimatedBone bone, PoseStack poseStack) {
        poseStack.translate(
                bone.getPosition().getX() / 16.0f,
                bone.getPosition().getY() / 16.0f,
                bone.getPosition().getZ() / 16.0f
        );
        if (!bone.getRotation().isZero()) {
            poseStack.mulPose(bone.getRotation().rotationZYX());
        }
        if (!bone.getScale().match(1f)) {
            poseStack.scale(bone.getScale().getX(), bone.getScale().getY(), bone.getScale().getZ());
        }
    }


    public void renderCube(PoseStack.Pose pose, KarkenAnimatedCube animatedCube, Properties properties) {
        Matrix4f matrix4f = pose.pose();
        Matrix3f matrix3f = pose.normal();
        for (KarkenQuad quad : animatedCube.getQuads()) {
            renderQuad(quad, matrix3f, matrix4f, properties);
        }
    }

    public void renderQuad(KarkenQuad quad, Matrix3f matrix3f, Matrix4f matrix4f, Properties properties) {
        Vector3f normal = matrix3f.transform(new Vector3f(quad.getNormal().toArray()));
        float l = normal.x();
        float m = normal.y();
        float n = normal.z();
        for (KarkenVertex vertex : quad.getVertices()) {
            float o = (float) (vertex.getPosition().getX() / 16.0f);
            float p = (float) (vertex.getPosition().getY() / 16.0f);
            float q = (float) (vertex.getPosition().getZ() / 16.0f);
            Vector4f vector4f = matrix4f.transform(new Vector4f(o, p, q, 1.0f));
            properties.buffer
                    .vertex(vector4f.x, vector4f.y, vector4f.z)
                    .color(properties.red, properties.green, properties.blue, properties.alpha)
                    .uv((float) vertex.getAnimatedUV().getX(), (float) vertex.getAnimatedUV().getY())
                    .overlayCoords(properties.packedOverlay)
                    .uv2(properties.packedLight)
                    .normal(l, m, n)
                    .endVertex();
        }
    }

    private KarkenAnimatedBone createKarkenAnimatedBone(KarkenAnimatedBone parent, AnimatedBone bone) {
        KarkenAnimatedBone karkenAnimatedBone = new KarkenAnimatedBone(bone.name(), parent != null ? parent.getName() : null, bone.inflation());

        // initial rotation and pivot
//        karkenAnimatedBone.setRotation(new KarkenVector3f(Math.toRadians(-bone.rotation().getX()), Math.toRadians(-bone.rotation().getY()), Math.toRadians(bone.rotation().getZ())));
//        karkenAnimatedBone.setPivot(new KarkenVector3f(-bone.pivot().getX(), bone.pivot().getY(), bone.pivot().getZ()));
        // load cubes
        for (AnimatedCube animatedCube : bone.cubes()) {
            karkenAnimatedBone.getCubes().add(createKarkenAnimatedCube(bone, animatedCube));
        }
        // load children bones
        model.getBonesWithParent(bone).forEach(b -> {
            karkenAnimatedBone.getChildren().add(createKarkenAnimatedBone(karkenAnimatedBone, b));
        });
        return karkenAnimatedBone;
    }

    private KarkenAnimatedCube createKarkenAnimatedCube(AnimatedBone bone, AnimatedCube cube) {
        AnimatedUV animatedUV = cube.uv();
        // 处理 box 模型
        if (animatedUV instanceof AnimatedUV.Box) {
            return new KarkenAnimatedCube.Builder(
                    model.description(),
                    (AnimatedUV.Box) animatedUV,
                    cube.origin().getKarkenVector3f(),
                    cube.size().getKarkenVector3f(),
                    false, EnumSet.allOf(Direction.class)
            ).build();
        }
        // 处理 quad 模型
        else if (animatedUV instanceof AnimatedUV.Quad) {
            return new KarkenAnimatedCube.Builder(
                    model.description(),
                    (AnimatedUV.Quad) animatedUV,
                    cube.origin().getKarkenVector3f(),
                    cube.size().getKarkenVector3f(),
                    KarkenVector3f.ZERO,
                    false,
                    EnumSet.allOf(Direction.class)
            ).build();
        }

        return null;
    }

    public List<KarkenAnimatedBone> getBones() {
        return bones;
    }

    public List<KarkenAnimatedBone> getTopAnimationBones() {
        return getBones().stream().filter(animatedBone -> Objects.isNull(animatedBone.getParent())).collect(Collectors.toList());
    }

    public List<KarkenAnimatedBone> getBonesWithParent(KarkenAnimatedBone bone) {
        return getBones().stream().filter(animatedBone -> Objects.equals(animatedBone.getParent(), bone.getName())).collect(Collectors.toList());
    }

    public static class Properties {

        RenderType renderType;

        MultiBufferSource source;

        VertexConsumer buffer;

        float entityYaw;

        int packedOverlay;

        int packedLight;

        float partialTick;

        float red;

        float blue;

        float green;

        float alpha;

        public Properties(RenderType renderType, MultiBufferSource source, float entityYaw, int packedOverlay, int packedLight, float partialTick, float red, float blue, float green, float alpha) {
            this.renderType = renderType;
            this.source = source;
            this.buffer = source.getBuffer(renderType);
            this.packedOverlay = packedOverlay;
            this.entityYaw = entityYaw;
            this.partialTick = partialTick;
            this.packedLight = packedLight;
            this.red = red;
            this.blue = blue;
            this.green = green;
            this.alpha = alpha;
        }
    }

}

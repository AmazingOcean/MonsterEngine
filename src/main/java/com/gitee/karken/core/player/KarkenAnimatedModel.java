package com.gitee.karken.core.player;

import com.gitee.karken.core.player.serializer.AnimatedBone;
import com.gitee.karken.core.player.serializer.AnimatedCube;
import com.gitee.karken.core.player.serializer.AnimatedModel;
import com.gitee.karken.util.KarkenMatrix3f;
import com.gitee.karken.util.KarkenMatrix4f;
import com.gitee.karken.util.KarkenPoseStack;
import com.gitee.karken.util.MathHelper;
import com.gitee.karken.util.vector.KarkenVector3d;
import com.gitee.karken.util.vector.KarkenVector3f;
import com.gitee.karken.util.vertex.KarkenQuad;
import com.gitee.karken.util.vertex.KarkenVertex;
import com.gitee.karken.util.vertex.KarkenVertexSet;
import com.gitee.monsterengine.MonsterEngine;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector4f;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class KarkenAnimatedModel {

    private List<KarkenAnimatedBone> bones = Lists.newArrayList();

    public KarkenAnimatedModel(AnimatedModel model) throws IllegalAccessException {
        if (model == null) {
            throw new IllegalAccessException("Model load failure.");
        }
        model.bones().forEach(bone -> {
            bones.add(createKarkenAnimatedBone(model, null, bone));
        });
    }

    private final ResourceLocation TEXTURE_RESOURCE_LOCATION = new ResourceLocation(MonsterEngine.MOD_ID, "textures/entity/player.png");

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();
        root.addOrReplaceChild("ball",
                CubeListBuilder.create().addBox(-4, 0, -4, 8, 8, 8),
                PartPose.offset(0, 16, 0)
        );
        return LayerDefinition.create(meshDefinition, 32, 16);
    }

    /**
     * render
     */
    public void render(AbstractClientPlayer clientPlayer, Properties properties, KarkenPoseStack poseStack) {
        AbstractTexture texture = Minecraft.getInstance().getTextureManager().getTexture(TEXTURE_RESOURCE_LOCATION);
        RenderSystem.setShaderTexture(0, texture.getId());


        for (KarkenAnimatedBone animatedBone : getTopAnimationBones()) {
            renderBone(poseStack, animatedBone, properties);
        }
    }

    public void renderBone(KarkenPoseStack poseStack, KarkenAnimatedBone animatedBone, Properties properties) {
        poseStack.pushPose();
        // 平移到局部部件原点
        poseStack.translate(animatedBone.getPosition().negationX().multiply(MathHelper.DEFAULT_SCALE_F));
        // 再次平移到pivot点
        poseStack.translate(animatedBone.getPivot().multiply(MathHelper.DEFAULT_SCALE_F));
        // 旋转
        if (animatedBone.getRotation().getZ() != 0f)
            poseStack.mulPose(animatedBone.getRotation().axisRotationZ());
        if (animatedBone.getRotation().getY() != 0f)
            poseStack.mulPose(animatedBone.getRotation().axisRotationY());
        if (animatedBone.getRotation().getX() != 0f)
            poseStack.mulPose(animatedBone.getRotation().axisRotationX());

        // 缩放模型矩阵
        poseStack.scale(animatedBone.getScale());
        // 平移回来
        poseStack.translate(animatedBone.getPivot().negation().multiply(MathHelper.DEFAULT_SCALE_F));
        // 渲染矩形
        for (KarkenAnimatedCube animatedCube : animatedBone.getCubes()) {
            poseStack.pushPose();
            renderCube(poseStack, animatedCube, properties);
            poseStack.popPose();
        }

        for (KarkenAnimatedBone karkenAnimatedBone : getBonesWithParent(animatedBone)) {
            renderBone(poseStack,karkenAnimatedBone,properties);
        }

        poseStack.popPose();
    }

    public void renderCube(KarkenPoseStack poseStack, KarkenAnimatedCube animatedCube, Properties properties) {

        System.out.println(animatedCube);
        // 平移到立方体的位置
        poseStack.translate(animatedCube.getPivot().multiply(MathHelper.DEFAULT_SCALE_F));
        // 处理旋转
        KarkenVector3f rotation = animatedCube.getRotation();
        poseStack.mulPose(rotation.rotationXYZ(0, 0, rotation.getZ()));
        poseStack.mulPose(rotation.rotationXYZ(0, rotation.getY(), 0));
        poseStack.mulPose(rotation.rotationXYZ(rotation.getX(), 0, 0));
        // 平移回来
        poseStack.translate(animatedCube.getPivot().negation().multiply(MathHelper.DEFAULT_SCALE_F));
        KarkenMatrix3f normalisedPoseState = poseStack.last().getNormal();
        KarkenMatrix4f poseState = poseStack.last().getPose().clone();
        for (Map.Entry<Direction, KarkenQuad> entry : animatedCube.getKarkenQuads().entrySet()) {
            Direction direction = entry.getKey();
            KarkenQuad value = entry.getValue();
            KarkenVector3f normal = normalisedPoseState.transform(value.getNormal().clone());
            normal.fixInvertedFlatCube(animatedCube, normal);
            renderQuad(value, poseState, normal, properties);
        }

    }

    // 渲染一个面
    private void renderQuad(KarkenQuad quad, KarkenMatrix4f poseState, KarkenVector3f normal, Properties properties) {
        VertexConsumer buffer = properties.buffer;
        for (KarkenVertex vertex : quad.getVertices()) {
            Vector4f vector4f = poseState.transform(vertex.getPosition());
            buffer.vertex(vector4f.x, vector4f.y, vector4f.z)
                    .color(properties.red, properties.green, properties.blue, properties.alpha)
                    .overlayCoords(properties.packedOverlay)
                    .uv2(properties.packedLight)
                    .uv((float) vertex.getAnimatedUV().getX(), (float) vertex.getAnimatedUV().getY())
                    .normal(normal.getX(), normal.getY(), normal.getZ());

        }
    }

    private KarkenAnimatedBone createKarkenAnimatedBone(AnimatedModel model, KarkenAnimatedBone parent, AnimatedBone bone) {
        KarkenAnimatedBone karkenAnimatedBone = new KarkenAnimatedBone(bone.name(), parent != null ? parent.getName() : null, bone.inflation());

        // initial rotation and pivot
        karkenAnimatedBone.setRotation(new KarkenVector3f(Math.toRadians(-bone.rotation().getX()), Math.toRadians(-bone.rotation().getY()), Math.toRadians(bone.rotation().getZ())));
        karkenAnimatedBone.setPivot(new KarkenVector3f(-bone.pivot().getX(), bone.pivot().getY(), bone.pivot().getZ()));
        // load cubes
        for (AnimatedCube animatedCube : bone.cubes()) {
            karkenAnimatedBone.getCubes().add(createKarkenAnimatedCube(bone, animatedCube));
        }
        // load children bones
        model.getBonesWithParent(bone).forEach(b -> {
            karkenAnimatedBone.getChildren().add(createKarkenAnimatedBone(model, karkenAnimatedBone, b));
        });
        return karkenAnimatedBone;
    }

    private KarkenAnimatedCube createKarkenAnimatedCube(AnimatedBone bone, AnimatedCube cube) {
        KarkenVector3d origin = cube.origin();
        KarkenVector3f size = cube.size().getKarkenVector3f();
        KarkenVector3f rotation = cube.rotation().getKarkenVector3f();
        KarkenVector3f pivot = cube.pivot().multiply(-1, 1, 1).getKarkenVector3f();
        // 重新赋值
        origin = new KarkenVector3d(-(origin.getX() + size.getX()), origin.getY(), origin.getZ()).multiply(MathHelper.DEFAULT_SCALE_D);
        KarkenVector3d vertexSize = size.multiply(MathHelper.DEFAULT_SCALE_F).getKarkenVector3d();
        rotation = new KarkenVector3f(Math.toRadians(-rotation.getX()), Math.toRadians(-rotation.getY()), Math.toRadians(rotation.getZ()));

        return new KarkenAnimatedCube(createKarkenQuadEnumMap(cube, new KarkenVertexSet(origin, vertexSize, cube.inflate())), pivot, rotation, size);
    }

    /**
     * create karken quad map
     *
     * @param cube      cube
     * @param vertexSet set
     * @return map
     */
    private EnumMap<Direction, KarkenQuad> createKarkenQuadEnumMap(AnimatedCube cube, KarkenVertexSet vertexSet) {
        EnumMap<Direction, KarkenQuad> map = Maps.newEnumMap(Direction.class);
        for (Direction direction : Direction.values()) {
            map.put(direction, KarkenQuad.build(vertexSet, cube, direction));
        }
        return map;
    }

    public List<KarkenAnimatedBone> getBones() {
        return bones;
    }

    public List<KarkenAnimatedBone> getTopAnimationBones() {
        return getBones()
                .stream()
                .filter(animatedBone -> Objects.isNull(animatedBone.getParent()))
                .collect(Collectors.toList());
    }

    public List<KarkenAnimatedBone> getBonesWithParent(KarkenAnimatedBone bone) {
        return getBones()
                .stream()
                .filter(animatedBone -> Objects.equals(animatedBone.getParent(), bone.getName()))
                .collect(Collectors.toList());
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

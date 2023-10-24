package com.gitee.karken.bone;

import com.gitee.karken.mixin.KarkenPlayerModelMixin;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;

public enum MinecraftBoneType {


    RIGHT_HAND("right_arm"),
    LEFT_HAND("left_arm"),
    BODY("body"),
    HEAD("head"),
    HAT("hat"),
    LEFT_LEG("left_leg"),
    RIGHT_LEG("right_leg");

    private String name;

    MinecraftBoneType(String name) {
        this.name = name;
    }

    public ModelPart getModelPart(HumanoidModel<?> model) {
        switch (this) {
            case RIGHT_HAND -> {
                return model.rightArm;
            }
            case LEFT_HAND -> {
                return model.leftArm;
            }
            case HAT -> {
                return model.hat;
            }
            case BODY -> {
                return model.body;
            }
            case HEAD -> {
                return model.head;
            }
            case LEFT_LEG -> {
                return model.leftLeg;
            }
            case RIGHT_LEG -> {
                return model.rightLeg;
            }
        }

        return null;
    }

    public String getName() {
        return name;
    }

}

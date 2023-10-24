package com.gitee.karken.animation;

import com.gitee.karken.animation.loop.AnimationTransitioned;
import com.gitee.karken.bone.KarkenBone;
import com.gitee.karken.bone.KarkenBoneInstance;
import com.gitee.karken.serializer.AnimationMetaBone;
import com.gitee.karken.serializer.AnimationMetaImpl;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.stream.Collectors;

public abstract class AbstractAnimationInstance implements Animation {

    private AnimationMetaImpl meta;

    private Lifecycle lifecycle = Lifecycle.CREATED;

    private AnimationQuest quest = new DefaultAnimationQuest();

    private Map<AnimationMetaBone,KarkenBone> boneMap;

    private AnimationTransitioned transitioned = new AnimationTransitioned();

    public AbstractAnimationInstance(AnimationMetaImpl meta) {
        this.meta = meta;
        this.boneMap = meta
                .bones()
                .values()
                .stream()
                .collect(Collectors.toMap(bone -> bone, KarkenBoneInstance::new));
    }

    @Override
    public boolean onTick() {
        this.boneMap.values().forEach(karkenBone -> {
            karkenBone.getPosition().next(this.getTick());
            karkenBone.getRotation().next(this.getTick());
            karkenBone.getScale().next(this.getTick());
        });
        return quest.next() + 1 < this.getHealth();
    }

    @Override
    public Lifecycle getLifecycle() {
        return lifecycle;
    }


    @Override
    public void setLifecycle(Lifecycle lifecycle) {
        this.lifecycle = lifecycle;
    }

    @Override
    public Long getHealth() {
        return (long) (this.meta.length() * 20L);
    }

    @Override
    public Long getTick() {
        return quest.get();
    }


    @Override
    public KarkenBone getBone(String boneId) {
        for (KarkenBone bone : this.boneMap.values()) {
            if (bone.getBone().id().equals(boneId)) {
                return bone;
            }
        }
        return null;
    }

    @Override
    public AnimationTransitioned getTransitioned() {
        return transitioned;
    }
}

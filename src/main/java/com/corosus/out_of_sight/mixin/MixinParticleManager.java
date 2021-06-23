package com.corosus.out_of_sight.mixin;

import com.corosus.out_of_sight.OutOfSight;
import com.corosus.out_of_sight.config.OutOfSightConfig;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ParticleManager.class)
public abstract class MixinParticleManager {
    @Redirect(method = "renderParticles",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/particle/Particle;renderParticle(Lnet/minecraft/client/renderer/BufferBuilder;Lnet/minecraft/entity/Entity;FFFFFF)V"))
    public void renderParticles(Particle particle, BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        if(!isInRangeToRender3d(entityIn, particle)) {
            return;
        }
        particle.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
    }

    public boolean isInRangeToRender3d(Entity entityIn, Particle particle) {
        return getDistanceSq(entityIn, particle) <= OutOfSightConfig.particleRenderRangeMaxSq ||
            (OutOfSightConfig.particleRenderLimitModdedOnly && !OutOfSight.isModded(entityIn.getClass()));
    }

    public double getDistanceSq(Entity livingEntityIn, Particle particle) {
        MixinParticleAccessor accessor = (MixinParticleAccessor) particle;
        double d0 = livingEntityIn.posX - accessor.getPosX();
        double d1 = livingEntityIn.posY - accessor.getPosY();
        double d2 = livingEntityIn.posZ - accessor.getPosZ();
        return d0 * d0 + d1 * d1 + d2 * d2;
    }
}

package com.corosus.out_of_sight.mixin;

import com.corosus.out_of_sight.OutOfSight;
import com.corosus.out_of_sight.config.OutOfSightConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderGlobal.class)
public abstract class MixinParticleManager {
    @Shadow
    @Final
    private Minecraft mc;

    @Redirect(method = "spawnParticle0(IZZDDDDDD[I)Lnet/minecraft/client/particle/Particle;",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/particle/ParticleManager;spawnEffectParticle(IDDDDDD[I)Lnet/minecraft/client/particle/Particle;"))
    public Particle renderParticles(ParticleManager particleManager, int particleId, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int[] parameters) {
        Entity entity = mc.getRenderViewEntity();
        if(!isInRangeToRender3d(entity, xCoord, yCoord, zCoord)) {
            return null;
        }
        return particleManager.spawnEffectParticle(particleId, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
    }

    public boolean isInRangeToRender3d(Entity entityIn, double x, double y, double z) {
        return !OutOfSightConfig.particleRenderRangeEnabled || getDistanceSq(entityIn, x, y, z) <= OutOfSightConfig.particleRenderRangeMaxSq ||
            (OutOfSightConfig.particleRenderLimitModdedOnly && !OutOfSight.isModded(entityIn.getClass()));
    }

    public double getDistanceSq(Entity livingEntityIn, double x, double y, double z) {
        double d0 = livingEntityIn.posX - x;
        double d1 = livingEntityIn.posY - y;
        double d2 = livingEntityIn.posZ - z;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }
}

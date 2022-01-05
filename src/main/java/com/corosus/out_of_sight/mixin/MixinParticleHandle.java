package com.corosus.out_of_sight.mixin;

import com.corosus.out_of_sight.OutOfSight;
import com.corosus.out_of_sight.config.OutOfSightConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

import javax.annotation.Nullable;

@Mixin(RenderGlobal.class)
public abstract class MixinParticleHandle
{
    @Shadow
    @Final
    private Minecraft mc;

    @Shadow protected abstract int calculateParticleLevel(boolean minimiseLevel);

    /**
     * @reason Variable particle range
     * @author Mikedeejay2
     */
    @Nullable
    @Overwrite
    public Particle spawnParticle0(int particleID, boolean ignoreRange, boolean minParticles, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters) {
        Entity entity = this.mc.getRenderViewEntity();
        if (this.mc != null && entity != null && this.mc.effectRenderer != null)
        {
            int particleLevel = calculateParticleLevel(minParticles);
            double d3 = entity.posX - xCoord;
            double d4 = entity.posY - yCoord;
            double d5 = entity.posZ - zCoord;

            if(ignoreRange) {
                if(OutOfSightConfig.particle.enabled && !isInRangeToRender3dForced(entity, xCoord, yCoord, zCoord)) {
                    return null;
                }
                return mc.effectRenderer.spawnEffectParticle(particleID, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
            }
            else if(OutOfSightConfig.particle.enabled) {
                if(!isInRangeToRender3d(entity, xCoord, yCoord, zCoord)) {
                    return null;
                }
            }
            else if(d3 * d3 + d4 * d4 + d5 * d5 > 1024.0D) {
                return null;
            }
            return particleLevel > 1 ? null : mc.effectRenderer.spawnEffectParticle(particleID, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
        }
        return null;
    }

    public boolean isInRangeToRender3d(Entity entityIn, double x, double y, double z) {
        return getDistanceSq(entityIn, x, y, z) <= OutOfSightConfig.particle.rangeMaxSQ ||
            (OutOfSightConfig.particle.moddedOnly && !OutOfSight.isModded(entityIn.getClass()));
    }

    public boolean isInRangeToRender3dForced(Entity entityIn, double x, double y, double z) {
        return OutOfSightConfig.particle.forcedIgnored || getDistanceSq(entityIn, x, y, z) <= OutOfSightConfig.particle.forcedRangeMaxSq ||
            (OutOfSightConfig.particle.moddedOnly && !OutOfSight.isModded(entityIn.getClass()));
    }

    public double getDistanceSq(Entity livingEntityIn, double x, double y, double z) {
        double d0 = livingEntityIn.posX - x;
        double d1 = livingEntityIn.posY - y;
        double d2 = livingEntityIn.posZ - z;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }
}

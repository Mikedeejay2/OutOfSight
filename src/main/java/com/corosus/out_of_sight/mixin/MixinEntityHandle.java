package com.corosus.out_of_sight.mixin;

import com.corosus.out_of_sight.OutOfSight;
import com.corosus.out_of_sight.config.OutOfSightConfig;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Render.class)
public abstract class MixinEntityHandle
{
    @Inject(method = "shouldRender", at = @At(value = "HEAD"), cancellable = true)
    public <T extends Entity> void shouldRender(T livingEntity, ICamera camera, double camX, double camY, double camZ, CallbackInfoReturnable<Boolean> cir) {
        if (!isInRangeToRender3d(livingEntity, camX, camY, camZ)) {
            cir.cancel();
        }
    }


    public <T extends Entity> boolean isInRangeToRender3d(T livingEntityIn, double x, double y, double z) {
        return !OutOfSightConfig.entity.enabled || getDistanceSq(livingEntityIn, x, y, z) <= OutOfSightConfig.entity.rangeMaxSQ ||
            (OutOfSightConfig.entity.moddedOnly && !OutOfSight.isModded(livingEntityIn.getClass()));
    }

    public <T extends Entity> double getDistanceSq(T livingEntityIn, double x, double y, double z) {
        double d0 = livingEntityIn.posX - x;
        double d1 = livingEntityIn.posY - y;
        double d2 = livingEntityIn.posZ - z;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }
}
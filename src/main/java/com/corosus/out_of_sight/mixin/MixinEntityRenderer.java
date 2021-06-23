package com.corosus.out_of_sight.mixin;

import com.corosus.out_of_sight.OutOfSight;
import com.corosus.out_of_sight.config.OutOfSightConfig;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderManager.class)
public abstract class MixinEntityRenderer {

    @Redirect(method = "shouldRender",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/Render;shouldRender(Lnet/minecraft/entity/Entity;Lnet/minecraft/client/renderer/culling/ICamera;DDD)Z"))
    public <T extends Entity> boolean shouldRender(Render<T> entityrenderer, T livingEntityIn, ICamera camera, double camX, double camY, double camZ) {
        if (!isInRangeToRender3d(livingEntityIn, camX, camY, camZ)) {
            return false;
        }
        return entityrenderer.shouldRender(livingEntityIn, camera, camX, camY, camZ);
    }


    public <T extends Entity> boolean isInRangeToRender3d(T livingEntityIn, double x, double y, double z) {
        return !OutOfSightConfig.config.entity.enabled || getDistanceSq(livingEntityIn, x, y, z) <= OutOfSightConfig.config.entity.rangeMaxSQ ||
            (OutOfSightConfig.config.entity.moddedOnly && !OutOfSight.isModded(livingEntityIn.getClass()));
    }

    public <T extends Entity> double getDistanceSq(T livingEntityIn, double x, double y, double z) {
        double d0 = livingEntityIn.posX - x;
        double d1 = livingEntityIn.posY - y;
        double d2 = livingEntityIn.posZ - z;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }
}
package com.corosus.out_of_sight.mixin;

import com.corosus.out_of_sight.OutOfSight;
import com.corosus.out_of_sight.config.OutOfSightConfig;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TileEntityRendererDispatcher.class)
public abstract class MixinTileEntityHandle
{

    @Inject(method = "render(Lnet/minecraft/tileentity/TileEntity;FI)V", at = @At("HEAD"), cancellable = true)
    public void renderTileEntity(TileEntity tileEntityIn, float partialTicks, int destroyStage, CallbackInfo ci) {
        if(!isInRangeToRender3d(tileEntityIn)) {
            ci.cancel();
        }
    }

    public boolean isInRangeToRender3d(TileEntity tileEntityIn) {
        return !OutOfSightConfig.tileEntity.enabled || getDistanceSq(tileEntityIn) <= OutOfSightConfig.tileEntity.rangeMaxSQ ||
            (OutOfSightConfig.tileEntity.moddedOnly && !OutOfSight.isModded(tileEntityIn.getClass()));
    }

    public double getDistanceSq(TileEntity tileEntity) {
        double d0 = (double)tileEntity.getPos().getX() + 0.5D - TileEntityRendererDispatcher.staticPlayerX;
        double d1 = (double)tileEntity.getPos().getY() + 0.5D - TileEntityRendererDispatcher.staticPlayerY;
        double d2 = (double)tileEntity.getPos().getZ() + 0.5D - TileEntityRendererDispatcher.staticPlayerZ;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }
}
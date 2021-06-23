package com.corosus.out_of_sight.mixin;

import com.corosus.out_of_sight.OutOfSight;
import com.corosus.out_of_sight.config.OutOfSightConfig;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderGlobal.class)
public abstract class MixinTileEntityRendererDispatcher {

    @Redirect(method = "renderEntities",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;render(Lnet/minecraft/tileentity/TileEntity;FI)V"))
    public void renderTileEntity(TileEntityRendererDispatcher dispatcher, TileEntity tileEntityIn, float partialTicks, int destroyStage) {
        if(!isInRangeToRender3d(tileEntityIn)) {
            return;
        }
        dispatcher.render(tileEntityIn, partialTicks, destroyStage);
    }

    public boolean isInRangeToRender3d(TileEntity tileEntityIn) {
        return !OutOfSightConfig.tileEntityRenderRangeEnabled || getDistanceSq(tileEntityIn) <= OutOfSightConfig.tileEntityRenderRangeMaxSq ||
            (OutOfSightConfig.tileEntityRenderLimitModdedOnly && !OutOfSight.isModded(tileEntityIn.getClass()));
    }

    public double getDistanceSq(TileEntity tileEntity) {
        double d0 = (double)tileEntity.getPos().getX() + 0.5D - TileEntityRendererDispatcher.staticPlayerX;
        double d1 = (double)tileEntity.getPos().getY() + 0.5D - TileEntityRendererDispatcher.staticPlayerY;
        double d2 = (double)tileEntity.getPos().getZ() + 0.5D - TileEntityRendererDispatcher.staticPlayerZ;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }
}
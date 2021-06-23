package com.corosus.out_of_sight.mixin;

import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Particle.class)
public interface MixinParticleAccessor
{
    @Accessor
    double getPosX();

    @Accessor
    double getPosY();

    @Accessor
    double getPosZ();
}

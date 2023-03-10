package smacho.macho.content.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import smacho.macho.content.inits.EffectInit;

@Mixin(EntityRenderer.class)
public abstract class RenderMixins <T extends Entity> {
    @Inject(method = "getBlockLightLevel", at = @At("HEAD"), cancellable = true)
    public void applyConcentrationLightChange(T entity, BlockPos blockPos, CallbackInfoReturnable<Integer> cir){
        if(!(entity instanceof LivingEntity) || !(entity.level.isClientSide())) // Checking clientSide does it safer
            return;

        Player player = Minecraft.getInstance().player;
        if (player == null) // Should never be null unless some mods, like ReplayMod, are used
            return;
        if (player.hasEffect(EffectInit.CONCENTRATION.get()))
            cir.setReturnValue(15);
    }
}
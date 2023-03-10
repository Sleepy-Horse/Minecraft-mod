package smacho.macho.content.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class Berserk extends MobEffect {
    public Berserk(int color) {
        super(MobEffectCategory.BENEFICIAL, color);
    }



    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}

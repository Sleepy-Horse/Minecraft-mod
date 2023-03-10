package smacho.macho.content.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class Concentration extends MobEffect {
    public Concentration(int color) {
        super(MobEffectCategory.BENEFICIAL, color);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}

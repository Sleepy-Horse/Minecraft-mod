package smacho.macho.content.inits;

import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import smacho.macho.Main;
import smacho.macho.content.effects.Berserk;
import smacho.macho.content.effects.Concentration;

public class EffectInit {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Main.MODID);

    public static final RegistryObject<MobEffect> BERSERK = MOB_EFFECTS.register("berserk", () ->
        new Berserk(8396810));
    public static final RegistryObject<MobEffect> CONCENTRATION = MOB_EFFECTS.register("concentration", () ->
        new Concentration(14926646));

    public static void register(IEventBus bus){MOB_EFFECTS.register(bus);}
}

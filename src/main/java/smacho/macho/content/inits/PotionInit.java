package smacho.macho.content.inits;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import smacho.macho.Main;

public class PotionInit {
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, Main.MODID);
    RegistryObject<Potion> BERSERK = POTIONS.register("berserk_potion", () ->
            new Potion(new MobEffectInstance(EffectInit.BERSERK.get(), 200, 0)));

    public static void register(IEventBus bus){POTIONS.register(bus);}
}

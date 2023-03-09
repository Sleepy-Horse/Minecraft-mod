package smacho.macho.content.inits;

import net.minecraftforge.eventbus.api.IEventBus;

public class Init {
    // This method registers all the necessary objects in the game
    public static void register(IEventBus bus) {
        // Registers items
        ItemInit.register(bus);

        // Registers entity types
        EntityTypeInit.register(bus);

        //Registers effects
        EffectInit.register(bus);

        //Registers potions
        PotionInit.register(bus);

        // Registers renders
        bus.register(RenderInit.class);
    }
}

package smacho.macho.content.inits;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import smacho.macho.Main;
import smacho.macho.content.entity.BulletEntity;

public class EntityTypeInit {

    // creates a DeferredRegister for entity types
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Main.MODID);

    // registers a new EntityType for BulletEntity
    public static final RegistryObject<EntityType<BulletEntity>> BULLET_ENTITY_TYPE = ENTITY_TYPES.register("bullet",
            () -> EntityType.Builder.<BulletEntity>of(BulletEntity::new, MobCategory.MISC)
                    .sized(0.25f, 0.25f)
                    .setTrackingRange(64)
                    .setUpdateInterval(5)
                    .setShouldReceiveVelocityUpdates(true)
                    .build("bullet"));

    // registers the EntityTypeInit with the given event bus
    public static void register(IEventBus bus) {
        ENTITY_TYPES.register(bus);
    }
}

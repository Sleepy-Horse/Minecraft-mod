package smacho.macho.content.inits;

import java.util.HashMap;
import java.util.function.Supplier;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import smacho.macho.Main;
import smacho.macho.content.customProperties.GunProperties;
import smacho.macho.content.item.Bullet;
import smacho.macho.content.item.Gun;

public class ItemInit {
    // Maps to convert between bullet objects and their string names
    public static HashMap<Bullet, String> bulletToStringMap = new HashMap<>();
    public static HashMap<String, Bullet> stringToBulletMap = new HashMap<>();

    /**
     * Registers a bullet item and adds it to the maps for later reference
     * @param name The registry name of the item
     * @param sup The supplier function to create the Bullet object
     * @return A RegistryObject for the registered item
     */
    public static RegistryObject<Item> BulletRegister(final String name, final Supplier<? extends Bullet> sup){
        return ITEMS.register(name, () -> {
            Bullet bullet = sup.get();
            stringToBulletMap.put(name, bullet);
            bulletToStringMap.put(bullet, name);
            return bullet;
        });
    }

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);

    // Register the bullet and pistol items
    public static final RegistryObject<Item> BULLET = BulletRegister("bullet", () -> new Bullet(new Item.Properties().stacksTo(64), 2F, 1F, null));
    public static final RegistryObject<Item> PISTOL = ITEMS.register("pistol",
            () -> new Gun(new Item.Properties().stacksTo(1).durability(160),
                    new GunProperties().setDamage(3).setFireRate(5).setMaxAmmo(12).setReloadSpeed(60).setShotSpeed(5).
                            addSupportedProjectiles(BULLET.get()).setVerticalSpread(0.1F).setHorizontalSpread(2F)));

    /**
     * Registers all items with the event bus
     * @param bus The event bus to register the items with
     */
    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }
}

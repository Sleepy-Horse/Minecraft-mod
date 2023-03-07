package smacho.macho.content.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Item;

public class Bullet extends Item {

    // The damage inflicted by the bullet
    public final float damage;

    // The effect applied by the bullet on hit (can be null)
    public final MobEffectInstance effect;

    // The speed of the bullet
    public final float speed;

    /**
     * Constructor for the Bullet class.
     *
     * @param properties the properties of the bullet item
     * @param damage the amount of damage the bullet inflicts on hit
     * @param speed the speed of the bullet
     * @param effect the effect applied by the bullet on hit (can be null)
     */
    public Bullet(Properties properties, float damage, float speed, MobEffectInstance effect) {
        super(properties);
        this.damage = damage;
        this.effect = effect;
        this.speed = speed;
    }
}

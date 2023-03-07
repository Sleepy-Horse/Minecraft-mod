package smacho.macho.content.entity;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import smacho.macho.content.inits.ItemInit;
import smacho.macho.content.item.Bullet;

import static smacho.macho.content.inits.ItemInit.*;

public class BulletEntity extends ThrowableItemProjectile implements IEntityAdditionalSpawnData {
    // The type of bullet fired by this entity
    public Bullet bulletType;
    // The damage dealt by this entity
    private final float damage;

    // Constructor for when the bullet type is not specified (defaulting to the first bullet in the registry)
    public BulletEntity(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
        this.bulletType = (Bullet) ItemInit.BULLET.get();
        this.damage = 0;
    }

    // Constructor for when the bullet type is specified
    public BulletEntity(EntityType<? extends ThrowableItemProjectile> entityType, Level level, LivingEntity owner, Bullet bullet, float damage) {
        super(entityType, owner, level);
        this.bulletType = bullet;
        this.damage = bullet.damage + damage;
    }

    // Returns the default item for this entity, which is the bullet item
    @Override
    protected @NotNull Item getDefaultItem() {
        return bulletType.asItem();
    }

    // Called when this entity hits an entity
    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        // The entity that was hit
        Entity entity = result.getEntity();
        // Damage the entity with the bullet's damage amount
        entity.hurt(new DamageSource("bullet"), damage);
        // If the bullet has an effect and the entity is a living entity, add the effect to the entity
        if (this.bulletType.effect != null && entity instanceof LivingEntity) {
            ((LivingEntity) entity).addEffect(this.bulletType.effect);
        }
    }

    // Gets the packet that should be sent to the client to spawn this entity
    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    // Writes the spawn data for this entity to a packet buffer
    @Override
    public void writeSpawnData(FriendlyByteBuf buffer) {
        // Loop through all bullets in the registry
        for (Bullet bullet : bulletToStringMap.keySet()) {
            // If the current bullet matches the type of bullet fired by this entity
            if (bulletType == bullet) {
                // Write the string representation of the bullet's registry name to the buffer
                buffer.writeUtf(bulletToStringMap.get(bullet));
            }
        }
    }

    // Reads the spawn data for this entity to a packet buffer

    @Override
    public void readSpawnData(FriendlyByteBuf buffer) {
        // Iterate through each bullet in the bulletToStringMap
        for(Bullet bullet: bulletToStringMap.keySet()){
            // Check if the string representation of the bullet in the buffer matches the string in the map
            if(buffer.readUtf().equals(bulletToStringMap.get(bullet))){
                // If there is a match, set the bulletType to the corresponding bullet object in the stringToBulletMap
                this.bulletType = stringToBulletMap.get(buffer.readUtf());
            }
        }
    }

}


package smacho.macho.content.item;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import smacho.macho.content.customProperties.GunProperties;
import smacho.macho.content.entity.BulletEntity;
import smacho.macho.content.inits.EntityTypeInit;
import smacho.macho.content.inits.ItemInit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class Gun extends ProjectileWeaponItem {
    // Gun properties
    private final float damage;
    private final int reloadSpeed;
    private final int fireRate;
    private final float shotSpeed;
    private final int maxAmmo;
    private final Item[] supportedProjectiles;
    private final float hSpread;
    private final float vSpread;
    private final SoundEvent reloadSound;
    private final SoundEvent shotSound;
    private final SoundEvent failedReloadSound;

    // List of bullets left in the gun
    public final List<Bullet> bullets_left = new ArrayList<>();

    // Last time the gun was shot
    private long last_time_shot = 0;

    public Gun(Properties properties, GunProperties gunProperties) {
        super(properties);

        // Initialize gun properties
        this.damage = gunProperties.getDamage();
        this.reloadSpeed = gunProperties.getReloadSpeed();
        this.shotSpeed = gunProperties.getShotSpeed();
        this.fireRate = gunProperties.getFireRate();
        this.supportedProjectiles = gunProperties.getSupportedProjectiles().toArray(new Item[0]);
        this.maxAmmo = gunProperties.getMaxAmmo();
        this.hSpread = gunProperties.getHorizontalSpread();
        this.vSpread = gunProperties.getVerticalSpread();
        this.reloadSound = gunProperties.getReloadSound();
        this.shotSound = gunProperties.getShotSound();
        this.failedReloadSound = gunProperties.getFailedReloadSound();
    }


    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        // Check if enough time has passed since last shot and if there are any bullets left to fire
        long time_since_last_shot = level.getGameTime  () - last_time_shot;
        if (time_since_last_shot >= fireRate && !bullets_left.isEmpty())
            // Play a sound effect to indicate that gun shot
            player.playSound(this.shotSound);
        if (time_since_last_shot >= fireRate && !bullets_left.isEmpty() && !level.isClientSide()) {
            // Stop using the item and shoot the gun
            player.stopUsingItem();

            // Create a new bullet entity and shoot it from the player's rotation
            BulletEntity bulletEntity = new BulletEntity(EntityTypeInit.BULLET_ENTITY_TYPE.get(), level, player, bullets_left.get(0), damage);
            bulletEntity.shootFromRotation(player, player.xRotO, player.yRotO, shotSpeed + bullets_left.get(0).speed, hSpread, vSpread);
            level.addFreshEntity(bulletEntity);

            // Remove the bullet from the list and update the last shot time
            bullets_left.remove(0);
            last_time_shot = level.getGameTime();
            // If the player is not in creative mode, then damage the item.
            if(!player.isCreative())
                itemStack.hurtAndBreak(1, player, e -> e.broadcastBreakEvent(player.getUsedItemHand()));

        } else if (time_since_last_shot >= fireRate && bullets_left.isEmpty()) {
            // If there are no bullets left to fire, start reloading the gun again
            player.startUsingItem(hand);
        }

        // Handle on-use tick
        if (player.isUsingItem() && !level.isClientSide()) {
            getUseDuration(itemStack);
            return InteractionResultHolder.consume(itemStack);
        }

        // Return the result of the item use
        return InteractionResultHolder.pass(itemStack);
    }


    /**
     * Reloads the gun by adding bullets from the player's inventory.
     * Only adds bullets that match the supported projectiles.
     * If the gun is already fully loaded, does nothing.
     * If the player is in creative mode, fills the gun to max capacity.
     * Plays a sound effect when reloading.
     *
     * @param player The Player that is reloading the gun.
     */
    public void reload(Player player, Level level) {
        // Find the first item in the player's inventory that matches a supported projectile
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack itemStack = player.getInventory().getItem(i);
            if (Arrays.asList(supportedProjectiles).contains(itemStack.getItem()) && !level.isClientSide()) {
                // If the gun is already fully loaded, play a sound effect and return
                if (bullets_left.size() >= maxAmmo) {
                    player.playSound(reloadSound);
                    return;
                }
                // Subtract one from the item stack and add it to the gun's bullets
                Bullet bullet = (Bullet) itemStack.getItem();
                bullets_left.add(bullet);
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
                i--;
            }
        }
        // If the gun is not fully loaded and the player is in creative mode, fill the gun to max capacity
        if (bullets_left.size() < maxAmmo && player.isCreative()) {
            while (bullets_left.size() < maxAmmo) {
                bullets_left.add((Bullet) ItemInit.BULLET.get());
            }
            // Play a sound effect to indicate that the gun has been reloaded
            player.playSound(reloadSound);
            return;
        }
        // Play a sound effect to indicate that the gun hasn't been reloaded
        player.playSound(failedReloadSound);
    }

    /**
     * Gets the duration of the reload animation.
     *
     * @param stack The ItemStack that represents the gun.
     * @return The duration of the reload animation in ticks.
     */
    @Override
    public int getUseDuration(@NotNull ItemStack stack) {
        return reloadSpeed;
    }

    /**
     * Gets the type of animation that should be played while the gun is being reloaded.
     *
     * @param stack The ItemStack that represents the gun.
     * @return The type of animation that should be played.
     */
    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BOW;
    }


    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entityLiving) {
        if (entityLiving instanceof Player player) {
            reload(player, level);
        }
        return super.finishUsingItem(stack, level, entityLiving);
    }

    @Override
    public @NotNull Predicate<ItemStack> getAllSupportedProjectiles() {
        return (ItemStack itemStack) -> Arrays.asList(this.supportedProjectiles).contains(itemStack.getItem());
    }

    @Override
    public int getDefaultProjectileRange() {
        return 0;
    }
}

package smacho.macho.content.customProperties;

import net.minecraft.world.item.Item;
import java.util.ArrayList;
import java.util.List;

public class GunProperties {

    // Initialize default values for all properties
    private float damage = 0;
    private int fireRate = 5;
    private int maxAmmo = 12;
    private int reloadSpeed = 60;
    private float hSpread = 1;
    private float vSpread = 1;
    private float shotSpeed = 2;
    private final List<Item> supportedProjectiles = new ArrayList<>();

    // Constructor is not needed since we are initializing default values above

    // Getter methods for all properties
    public float getDamage(){
        return damage;
    }
    public int getFireRate(){
        return fireRate;
    }
    public int getMaxAmmo(){
        return maxAmmo;
    }
    public int getReloadSpeed(){
        return reloadSpeed;
    }
    public float getHorizontalSpread(){
        return hSpread;
    }
    public float getVerticalSpread(){
        return vSpread;
    }
    public float getShotSpeed(){
        return shotSpeed;
    }
    public List<Item> getSupportedProjectiles() {
        return supportedProjectiles;
    }

    // Setter methods for all properties, which return this so that we can chain methods
    public GunProperties setDamage(float damage){
        this.damage = damage;
        return this;
    }
    public GunProperties setFireRate(int fireRate){
        this.fireRate = fireRate;
        return this;
    }
    public GunProperties setMaxAmmo(int maxAmmo){
        this.maxAmmo = maxAmmo;
        return this;
    }
    public GunProperties setReloadSpeed(int reloadSpeed){
        this.reloadSpeed = reloadSpeed;
        return this;
    }
    public GunProperties setHorizontalSpread(float spread){
        this.hSpread = spread;
        return this;
    }
    public GunProperties setVerticalSpread(float spread){
        this.vSpread = spread;
        return this;
    }
    public GunProperties setShotSpeed(float shotSpeed){
        this.shotSpeed = shotSpeed;
        return this;
    }
    public GunProperties addSupportedProjectiles(Item bullet) {
        supportedProjectiles.add(bullet);
        return this;
    }
}

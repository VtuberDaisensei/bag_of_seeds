package com.gt_mods.bag_of_seeds;

import com.gt_mods.bag_of_seeds.configs.Config;

import com.gt_mods.bag_of_seeds.items.BagOfSaplings;
import com.gt_mods.bag_of_seeds.items.BagOfSeeds;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(BagOfSeedsMod.MOD_ID)
public class BagOfSeedsMod {
    public static final String MOD_ID = "bag_of_seeds_mod";

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);

    public static final DeferredItem<Item> BAG_OF_SEEDS = ITEMS.register("bag_of_seeds", () ->
            new BagOfSeeds(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> BAG_OF_SAPLINGS = ITEMS.register("bag_of_saplings", () ->
            new BagOfSaplings(new Item.Properties().rarity(Rarity.UNCOMMON)));

    private static final float lootingLevel = 0.0f;

    public BagOfSeedsMod(IEventBus modEventBus, ModContainer modContainer) {

        ITEMS.register(modEventBus);

        modEventBus.addListener(this::addCreative);
        NeoForge.EVENT_BUS.addListener(BagOfSeedsMod::onLivingDrop);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(BAG_OF_SEEDS.get());
            event.accept(BAG_OF_SAPLINGS.get());
        }
    }

    private static void onLivingDrop(LivingDropsEvent event) {
        if (event.getEntity().level().isClientSide) return;

        if (event.getEntity() instanceof Mob killedMob) {

            if (Config.COMMON.dropChance_bag_of_seeds.get() > 0
                    && event.getEntity().getRandom().nextFloat() < Config.COMMON.dropChance_bag_of_seeds.get() + lootingLevel) {
                event.getDrops().add(new net.minecraft.world.entity.item.ItemEntity(
                        event.getEntity().level(),
                        killedMob.getX(),
                        killedMob.getY(),
                        killedMob.getZ(),
                        new net.minecraft.world.item.ItemStack(BAG_OF_SEEDS.get())
                ));
            }
        }

        if (event.getEntity() instanceof Mob killedMob) {
            if (Config.COMMON.dropChance_bag_of_saplings.get() > 0
                    && event.getEntity().getRandom().nextFloat() < Config.COMMON.dropChance_bag_of_saplings.get() + lootingLevel) {
                event.getDrops().add(new net.minecraft.world.entity.item.ItemEntity(
                        event.getEntity().level(),
                        killedMob.getX(),
                        killedMob.getY(),
                        killedMob.getZ(),
                        new net.minecraft.world.item.ItemStack(BAG_OF_SAPLINGS.get())
                ));
            }
        }
    }
}

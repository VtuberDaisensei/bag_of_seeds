package com.daisensei_mods.bag_of_seeds;

import com.daisensei_mods.bag_of_seeds.configs.Config;
import com.daisensei_mods.bag_of_seeds.items.BagOfSaplings;
import com.daisensei_mods.bag_of_seeds.items.BagOfSeeds;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.world.item.Rarity.UNCOMMON;

@Mod(BagOfSeedsMod.MOD_ID)
public class BagOfSeedsMod
{
    public static final String MOD_ID = "bag_of_seeds_mod";

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final RegistryObject<Item> BAG_OF_SEEDS = ITEMS.register("bag_of_seeds", () ->
            new BagOfSeeds(new Item.Properties().rarity(UNCOMMON)));
    public static final RegistryObject<Item> BAG_OF_SAPLINGS = ITEMS.register("bag_of_saplings", () ->
            new BagOfSaplings(new Item.Properties().rarity(UNCOMMON)));

    public BagOfSeedsMod(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        ITEMS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS)
        {
            event.accept(BAG_OF_SEEDS.get());
            event.accept(BAG_OF_SAPLINGS.get());
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) { }
    }

    @SuppressWarnings("resource")
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class MobDropEventHandler
    {
        private static float lootingLevel = 0.0f;

        @SubscribeEvent
        public static void onLootingLevel(@NotNull LootingLevelEvent event) {
            if (event.getEntity().level().isClientSide) return;

            if (event.getEntity() instanceof Mob) {
                lootingLevel = event.getLootingLevel() * 0.01f;
            }
        }

        @SubscribeEvent
        public static void onMobDrops(@NotNull LivingDropsEvent event) {
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
}

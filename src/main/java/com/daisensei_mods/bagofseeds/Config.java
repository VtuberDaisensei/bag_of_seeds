package com.daisensei_mods.bagofseeds;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BagOfSeedsMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final Common COMMON = new Common(BUILDER);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static class Common {
        public final ForgeConfigSpec.IntValue maxSeeds;
        public final ForgeConfigSpec.DoubleValue dropChance_bag_of_seeds;
        public final ForgeConfigSpec.DoubleValue dropChance_bag_of_saplings;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("bag_of_seeds_mods");
            maxSeeds = builder
                    .comment("Maximum number of seeds to drop")
                    .defineInRange("maxSeeds", 4, 1, Integer.MAX_VALUE);
            dropChance_bag_of_seeds = builder
                    .comment("Chance of dropping Bag of seeds (0.00 to 1.00)")
                    .defineInRange("dropChance_bag_of_seeds", 0.05, 0.0, 1.0);
            dropChance_bag_of_saplings = builder
                    .comment("Chance of dropping Bag of saplings (0.00 to 1.00)")
                    .defineInRange("dropChance_bag_of_saplings", 0.05, 0.0, 1.0);
            builder.pop();
        }
    }
}

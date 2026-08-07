package com.gt_mods.bag_of_seeds.configs;

import java.util.List;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    public static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final Common COMMON = new Common(BUILDER);

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static class Common {
        public final ModConfigSpec.IntValue maxSeeds;
        public final ModConfigSpec.DoubleValue dropChance_bag_of_seeds;
        public final ModConfigSpec.DoubleValue dropChance_bag_of_saplings;

        public Common(ModConfigSpec.Builder builder) {
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

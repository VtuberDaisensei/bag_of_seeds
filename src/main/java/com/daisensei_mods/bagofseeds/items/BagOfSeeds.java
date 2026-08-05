package com.daisensei_mods.bagofseeds.items;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class BagOfSeeds extends BagOfSomething {

    private static final TagKey<Item> seedsTag = ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge", "seeds"));

    public BagOfSeeds(Properties properties) {
        super(properties, seedsTag);
    }
}

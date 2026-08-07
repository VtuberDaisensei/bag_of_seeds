package com.gt_mods.bag_of_seeds.items;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class BagOfSeeds extends BagOfSomething {
    private static final TagKey<Item> seedsTag = ItemTags.create(ResourceLocation.fromNamespaceAndPath("c", "seeds"));

    public BagOfSeeds(Item.Properties properties) {
        super(properties, seedsTag);
    }
}

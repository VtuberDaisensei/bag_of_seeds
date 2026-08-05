package com.daisensei_mods.bag_of_seeds.items;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class BagOfSaplings extends BagOfSomething {

    private static final TagKey<Item> saplingsTag = ItemTags.create(ResourceLocation.fromNamespaceAndPath("minecraft", "saplings"));

    public BagOfSaplings(Properties properties) {
        super(properties, saplingsTag);
    }
}

package com.gt_mods.bag_of_seeds.items;

import com.gt_mods.bag_of_seeds.configs.Config;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class BagOfSomething extends Item {

    private final TagKey<Item> tag;

    public BagOfSomething(Properties properties, String tag) {
        super(properties);
        this.tag = ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge", tag));
    }

    public BagOfSomething(Properties properties, TagKey<Item> tag) {
        super(properties);
        this.tag = tag;
    }

    // 右クリックしたときの処理
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {

        if (!level.isClientSide) {

            List<Item> itemsInTag = null;
            if (tag != null) {
                HolderLookup<Item> items = level.registryAccess().lookupOrThrow(Registries.ITEM);
                HolderSet<Item> tagSet = items.getOrThrow(tag);

                itemsInTag = tagSet.stream().map(Holder::value).toList();
            }

            if (Objects.requireNonNull(itemsInTag).isEmpty())
                return super.use(level, player, hand);
            int maxSeeds = Config.COMMON.maxSeeds.get();
            if (maxSeeds < 1) maxSeeds = 1;
            int count = level.random.nextInt(1, maxSeeds);

            for (int i = 0; i < count; i++) {
                int index = level.random.nextInt(itemsInTag.size());
                Item seedItem = itemsInTag.get(index);
                ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(seedItem, 1));
            }

            player.swing(hand);

            if (!player.isCreative()) player.getItemInHand(hand).shrink(1);
        }

        return super.use(level, player, hand);
    }
}

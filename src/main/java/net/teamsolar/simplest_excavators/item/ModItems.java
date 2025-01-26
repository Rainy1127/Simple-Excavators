package net.teamsolar.simplest_excavators.item;

import net.teamsolar.simplest_excavators.SimplestExcavators;
import net.teamsolar.simplest_excavators.item.custom.ExcavatorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, SimplestExcavators.MOD_ID);

    public static final RegistryObject<Item> WOODEN_EXCAVATOR = ITEMS.register("wooden_excavator",
            () -> new ExcavatorItem(Tiers.WOOD, 2.5f, -3.2F, new Item.Properties().durability(88)));
    public static final RegistryObject<Item> STONE_EXCAVATOR = ITEMS.register("stone_excavator",
            () -> new ExcavatorItem(Tiers.STONE, 2.5f, -3.2F, new Item.Properties().durability(196)));
    public static final RegistryObject<Item> IRON_EXCAVATOR = ITEMS.register("iron_excavator",
            () -> new ExcavatorItem(Tiers.IRON, 2.5f, -3.2F, new Item.Properties().durability(375)));
    public static final RegistryObject<Item> GOLDEN_EXCAVATOR = ITEMS.register("golden_excavator",
            () -> new ExcavatorItem(Tiers.GOLD, 2.5f, -3.2F, new Item.Properties().durability(48)));
    public static final RegistryObject<Item> DIAMOND_EXCAVATOR = ITEMS.register("diamond_excavator",
            () -> new ExcavatorItem(Tiers.DIAMOND, 2.5f, -3.2F, new Item.Properties().durability(2341)));
    public static final RegistryObject<Item> NETHERITE_EXCAVATOR = ITEMS.register("netherite_excavator",
            () -> new ExcavatorItem(Tiers.NETHERITE, 2.5f, -3.2F, new Item.Properties().durability(3046).fireResistant()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
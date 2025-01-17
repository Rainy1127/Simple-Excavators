package net.teamsolar.simplest_excavators.datagen;

import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.teamsolar.simplest_excavators.SimplestExcavators;
import net.teamsolar.simplest_excavators.item.ModItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }


    @Override
    protected void buildRecipes(@NotNull RecipeOutput output) {

        /*ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_EXCAVATOR.get())
                .pattern(" B ")
                .pattern(" C ")
                .pattern(" C ")
                .define('B', Items.IRON_BLOCK)
                .define('C', Items.STICK)
                .unlockedBy("has_iron_block", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.IRON_BLOCK).build()))
                .unlockedBy("has_stick", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.STICK).build()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.DIAMOND_EXCAVATOR.get())
                .pattern(" B ")
                .pattern(" C ")
                .pattern(" C ")
                .define('B', Items.DIAMOND_BLOCK)
                .define('C', Items.STICK)
                .unlockedBy("has_diamond_block", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.DIAMOND_BLOCK).build()))
                .unlockedBy("has_stick", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.STICK).build()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GOLDEN_EXCAVATOR.get())
                .pattern(" B ")
                .pattern(" C ")
                .pattern(" C ")
                .define('B', Items.GOLD_BLOCK)
                .define('C', Items.STICK)
                .unlockedBy("has_gold_block", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.GOLD_BLOCK).build()))
                .unlockedBy("has_stick", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.STICK).build()))
                .save(output);


        netheriteSmithing(output, ModItems.DIAMOND_EXCAVATOR.get(), RecipeCategory.MISC, ModItems.NETHERITE_EXCAVATOR.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WOODEN_EXCAVATOR.get())
                .pattern(" B ")
                .pattern(" C ")
                .pattern(" C ")
                .define('B', ItemTags.LOGS)
                .define('C', Items.STICK)
                .unlockedBy("has_logs", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ItemTags.LOGS).build()))
                .unlockedBy("has_stick", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.STICK).build()))
                .save(output);*/

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.EXCAVATOR_SMITHING_TEMPLATE.get(), 2)
                .pattern("ABA")
                .pattern("ACA")
                .pattern("AAA")
                .define('A', Items.EMERALD)
                .define('B', ModItems.EXCAVATOR_SMITHING_TEMPLATE)
                .define('C', Items.COBBLESTONE)
                .unlockedBy("has_excavator_template", hasInInventory(ModItems.EXCAVATOR_SMITHING_TEMPLATE.get()))
                .save(output);

        excavatorSmithingRecipe(
                Ingredient.of(Items.WOODEN_SHOVEL),
                Ingredient.of(ItemTags.LOGS),
                ModItems.WOODEN_EXCAVATOR.get(),
                output
        );
        excavatorSmithingRecipe(
                Ingredient.of(Items.STONE_SHOVEL),
                Ingredient.of(Items.SMOOTH_STONE),
                ModItems.STONE_EXCAVATOR.get(),
                output
        );
        excavatorSmithingRecipe(
                Ingredient.of(Items.IRON_SHOVEL),
                Ingredient.of(Items.IRON_BLOCK.asItem()),
                ModItems.IRON_EXCAVATOR.get(),
                output
        );
        excavatorSmithingRecipe(
                Ingredient.of(Items.GOLDEN_SHOVEL),
                Ingredient.of(Items.GOLD_BLOCK),
                ModItems.GOLDEN_EXCAVATOR.get(),
                output
        );
        excavatorSmithingRecipe(
                Ingredient.of(Items.DIAMOND_SHOVEL),
                Ingredient.of(Items.DIAMOND_BLOCK),
                ModItems.DIAMOND_EXCAVATOR.get(),
                output
        );
        excavatorSmithingRecipe(
                Ingredient.of(Items.NETHERITE_SHOVEL),
                Ingredient.of(Items.DIAMOND_BLOCK),
                ModItems.NETHERITE_EXCAVATOR.get(),
                output
        );
        // Upgrades
        excavatorUpgradeRecipe(
                Ingredient.of(ModItems.WOODEN_EXCAVATOR.get()),
                Ingredient.of(Items.SMOOTH_STONE),
                ModItems.STONE_EXCAVATOR.get(),
                output
        );
        excavatorUpgradeRecipe(
                Ingredient.of(ModItems.STONE_EXCAVATOR.get()),
                Ingredient.of(Items.IRON_BLOCK),
                ModItems.IRON_EXCAVATOR.get(),
                output
        );
        excavatorUpgradeRecipe(
                Ingredient.of(ModItems.IRON_EXCAVATOR.get()),
                Ingredient.of(Items.GOLD_BLOCK),
                ModItems.GOLDEN_EXCAVATOR.get(),
                output
        );
        excavatorUpgradeRecipe(
                Ingredient.of(ModItems.GOLDEN_EXCAVATOR.get()),
                Ingredient.of(Items.DIAMOND_BLOCK),
                ModItems.DIAMOND_EXCAVATOR.get(),
                output
        );
        netheriteSmithing(output, ModItems.DIAMOND_EXCAVATOR.get(), RecipeCategory.MISC, ModItems.NETHERITE_EXCAVATOR.get());

        basicBlastingAndSmeltingRecipe(ModItems.IRON_EXCAVATOR.get(), Items.IRON_NUGGET, output);
        basicBlastingAndSmeltingRecipe(ModItems.GOLDEN_EXCAVATOR.get(), Items.GOLD_NUGGET, output);
    }

    private Criterion<InventoryChangeTrigger.TriggerInstance> hasInInventory(ItemLike item) {
        return inventoryTrigger(ItemPredicate.Builder.item()
                .of(item).build());
    }
    private String stripNamespace(String itemString) {
        Pattern pattern = Pattern.compile("(.+):(.+)");
        var matches = pattern.matcher(itemString);
        if(matches.find()) {
            return matches.group(2);
        }
        return "";
    }

    private void excavatorSmithingRecipe(Ingredient base, Ingredient additional, Item outputItem, RecipeOutput output) {
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.EXCAVATOR_SMITHING_TEMPLATE.get()),
                        base,
                        additional,
                        RecipeCategory.TOOLS,
                        outputItem
                )
                .unlocks("has_excavator_template", hasInInventory(ModItems.EXCAVATOR_SMITHING_TEMPLATE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SimplestExcavators.MODID, stripNamespace(outputItem.toString()).concat("_from_shovel")));
    }
    private void excavatorUpgradeRecipe(Ingredient base, Ingredient additional, Item outputItem, RecipeOutput output) {
        SmithingTransformRecipeBuilder.smithing(
                        Ingredient.of(ModItems.EXCAVATOR_SMITHING_TEMPLATE.get()),
                        base,
                        additional,
                        RecipeCategory.TOOLS,
                        outputItem
                )
                .unlocks("has_excavator_template", hasInInventory(ModItems.EXCAVATOR_SMITHING_TEMPLATE.get()))
                .save(output, ResourceLocation.fromNamespaceAndPath(SimplestExcavators.MODID, stripNamespace(outputItem.toString()).concat("_from_upgrade")));
    }
    private void basicBlastingAndSmeltingRecipe(Item input, Item outputItem, RecipeOutput output) {
        var unqualifiedItemName = stripNamespace(input.toString());
        SimpleCookingRecipeBuilder.blasting(
                Ingredient.of(input),
                RecipeCategory.MISC,
                outputItem,
                0.1F,
                100
        )
                .unlockedBy("has_".concat(unqualifiedItemName), hasInInventory(input))
                .save(output, ResourceLocation.withDefaultNamespace(unqualifiedItemName.concat("_blasting")));
        SimpleCookingRecipeBuilder.smelting(
                        Ingredient.of(input),
                        RecipeCategory.MISC,
                        outputItem,
                        0.1F,
                        200
                )
                .unlockedBy("has_".concat(unqualifiedItemName), hasInInventory(input))
                .save(output, ResourceLocation.withDefaultNamespace(unqualifiedItemName.concat("_smelting")));
    }
}

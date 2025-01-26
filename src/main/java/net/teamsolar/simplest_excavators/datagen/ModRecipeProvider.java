package net.teamsolar.simplest_excavators.datagen;

import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.teamsolar.simplest_excavators.item.ModItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    private static final List<ItemLike> IRON_EXCAVATOR = List.of(ModItems.IRON_EXCAVATOR.get());
    private static final List<ItemLike> GOLDEN_EXCAVATOR = List.of(ModItems.GOLDEN_EXCAVATOR.get());

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> p_251297_) {

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.IRON_EXCAVATOR.get())
                .pattern(" B ")
                .pattern(" C ")
                .pattern(" C ")
                .define('B', Items.IRON_BLOCK)
                .define('C', Items.STICK)
                .unlockedBy("has_iron_block", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.IRON_BLOCK).build()))
                .unlockedBy("has_stick", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.STICK).build()))
                .save(p_251297_);

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
                .save(p_251297_);

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
                .save(p_251297_);


        netheriteSmithing(p_251297_, ModItems.DIAMOND_EXCAVATOR.get(), RecipeCategory.MISC, ModItems.NETHERITE_EXCAVATOR.get());

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
                .save(p_251297_);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.STONE_EXCAVATOR.get())
                .pattern(" B ")
                .pattern(" C ")
                .pattern(" C ")
                .define('B', Items.SMOOTH_STONE)
                .define('C', Items.STICK)
                .unlockedBy("has_smooth_stone", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.SMOOTH_STONE).build()))
                .unlockedBy("has_stick", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.STICK).build()))
                .save(p_251297_);

        oreSmelting(p_251297_, IRON_EXCAVATOR, RecipeCategory.MISC, Items.IRON_NUGGET, 0.1f, 200, "iron");
        oreBlasting(p_251297_, IRON_EXCAVATOR, RecipeCategory.MISC, Items.IRON_NUGGET, 0.1f, 100, "iron");
        oreSmelting(p_251297_, GOLDEN_EXCAVATOR, RecipeCategory.MISC, Items.GOLD_NUGGET, 0.1f, 200, "gold");
        oreBlasting(p_251297_, GOLDEN_EXCAVATOR, RecipeCategory.MISC, Items.GOLD_NUGGET, 0.1f, 100, "gold");
    }

    protected static void oreSmelting(Consumer<FinishedRecipe> p_250654_, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(p_250654_, RecipeSerializer.SMELTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(Consumer<FinishedRecipe> p_248775_, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        oreCooking(p_248775_, RecipeSerializer.BLASTING_RECIPE, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void oreCooking(Consumer<FinishedRecipe> p_250791_, RecipeSerializer<? extends AbstractCookingRecipe> pSerializer, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pSuffix) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pSerializer).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike)).save(p_250791_, getItemName(pResult) + pSuffix + "_" + getItemName(itemlike));
        }

    }
}

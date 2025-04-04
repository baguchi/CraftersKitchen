package baguchi.crafters_kitchen.data;

import baguchi.crafters_kitchen.registry.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class CraftingGenerator extends RecipeProvider {
    public CraftingGenerator(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModBlocks.SANDWICH.get(), 1)
                .pattern("S")
                .pattern("B")
                .define('S', Tags.Items.RODS_WOODEN)
                .define('B', Items.BREAD)
                .unlockedBy("has_item", has(Items.BREAD))
                .save(consumer);
    }
}

package baguchi.crafters_kitchen.item;

import baguchi.crafters_kitchen.api.FoodDecoration;
import baguchi.crafters_kitchen.api.FoodHolder;
import baguchi.crafters_kitchen.api.FoodMaterial;
import baguchi.crafters_kitchen.registry.ModBlocks;
import baguchi.crafters_kitchen.utils.FoodUtils;
import com.google.common.collect.Lists;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class SandwichBlockItem extends BlockItem {
    public SandwichBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (this.isEdible()) {
            FoodHolder foodHolder = FoodUtils.readFoodDataFromItemStack(stack);
            if (foodHolder != null) {
                for (FoodMaterial foodMaterial : foodHolder.stack()) {
                    foodMaterial.stack().finishUsingItem(level, livingEntity);
                }
            }
        }

        return this.isEdible() ? livingEntity.eat(level, stack) : stack;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        FoodHolder foodHolder = FoodUtils.readFoodDataFromItemStack(stack);
        if (foodHolder != null) {
            return super.getUseDuration(stack) + 4 * foodHolder.stack().size();
        }
        return super.getUseDuration(stack);
    }


    public static List<ItemStack> generateFood() {
        List<ItemStack> items = Lists.newArrayList();

        List<FoodHolder> foodHolders = Lists.newArrayList();

        ItemStack stack2 = new ItemStack(ModBlocks.SANDWICH.get());
        ArrayList<FoodMaterial> arrayList = new ArrayList<>();
        ArrayList<FoodDecoration> arrayList2 = new ArrayList<>();
        FoodHolder foodHolder = new FoodHolder(arrayList, arrayList2);
        FoodHolder foodHolder2 = new FoodHolder(arrayList, arrayList2);
        arrayList.add(new FoodMaterial(Items.GOLDEN_APPLE.getDefaultInstance(), Vec3.ZERO));
        arrayList.add(new FoodMaterial(Items.GOLDEN_CARROT.getDefaultInstance(), Vec3.ZERO));
        arrayList.add(new FoodMaterial(Items.BREAD.getDefaultInstance(), Vec3.ZERO));
        FoodUtils.writeFoodDataToItemStack(stack2, foodHolder2);
        items.add(stack2);
        return items;
    }
}
